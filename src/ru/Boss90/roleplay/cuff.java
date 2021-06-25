package ru.Boss90.roleplay;

import java.util.HashSet;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import net.md_5.bungee.api.ChatColor;
import ru.Boss90.roleplay.enums.AccessLevel;
import ru.Boss90.roleplay.interfaces.Work;

public class cuff implements CommandExecutor {
	Plugin plugin;
	String forall;
	public static HashSet<Player> cuffing = new HashSet<>();

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public cuff() {
		this.plugin = (Plugin) main.getPlugin((Class) main.class);
		this.forall = this.plugin.getConfig().getString("nachalo").replace("&", "�");
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String str, String[] args) {
		if (args.length == 0) {
			sender.sendMessage(this.forall + ChatColor.WHITE + "������� ������!");
			return true;
		}
		Player p = (Player) sender;
		Player cuffer = Bukkit.getPlayer(args[0]);
		final Work work = main.SQL.getPlayerRabota(p.getName());
		if (!AccessLevel.contains(main.SQL.getPlayerRabota(p.getName()).getAccess(), AccessLevel.ARRESTPLAYER)
				&& !sender.isOp()) {
			sender.sendMessage(this.forall + ChatColor.WHITE + "�� �� ������ ��� �������!");
			return true;
		}
		if (cuffer == null) {
			sender.sendMessage(this.forall + ChatColor.WHITE + "������ ��� �� �������.");
			return true;
		}
		if (p.getName().equalsIgnoreCase(args[0])) {
			p.sendMessage(this.forall + ChatColor.WHITE + "�� �� ������ �������� ����!");
			return true;
		}
		if (p.getLocation().distance(cuffer.getLocation()) > 5.0) {
			sender.sendMessage(this.forall + ChatColor.WHITE + "����� ������� ������!");
			return true;
		}
		if (p.getGameMode() == GameMode.CREATIVE || p.isFlying() == true) {
			p.sendMessage(this.forall + ChatColor.WHITE + "��������� ����/��");
			return true;
		}
		if (main.SQL.getPlayerStars(cuffer.getName()) == 0) {
			p.sendMessage(this.forall + ChatColor.WHITE + "������ ����� ��� �������!");
			return true;
		}
		if (work.getFraction().getName() != "�������") {
			p.sendMessage(this.forall + ChatColor.WHITE + "�� �� ��������� � �������!");
			return true;
		}
		if (work.getName() == "�����") {
			p.sendMessage(this.forall + ChatColor.WHITE + "����� ����� ���� ������ (2) � ����!");
			return true;
		}
		if (cuffer.hasPermission("atlantworld.freestars")) {
			p.getPlayer().sendMessage(this.forall + ChatColor.WHITE + "����� ������ ������ ��������!");
			return true;
		}
		if (cuffing.contains(cuffer)) {
			sender.sendMessage(this.forall + ChatColor.WHITE + "�� ������� ����� ��������� � " + cuffer.getName());
			cuffer.sendMessage(this.forall + ChatColor.WHITE + "� ��� ����� ���������!");
			for (Player player2 : Bukkit.getOnlinePlayers()) {
				if (player2.getLocation().distance(player2.getLocation()) <= 10.0D)
					player2.sendMessage(
							ChatColor.DARK_PURPLE + "* " + sender.getName() + " ���� ��������� � " + cuffer.getName());
			}
			cuffing.remove(cuffer);
			return true;
		}
		sender.sendMessage(this.forall + ChatColor.WHITE + "�� ������� ����� ��������� �� " + cuffer.getName());
		cuffer.sendMessage(this.forall + ChatColor.WHITE + "�� ��� ����� ���������!");
		cuffing.add(cuffer);
		for (Player player2 : Bukkit.getOnlinePlayers()) {
			if (player2.getLocation().distance(player2.getLocation()) <= 10.0D)
				player2.sendMessage(
						ChatColor.DARK_PURPLE + "* " + sender.getName() + " ����� ��������� �� " + cuffer.getName());
		}

		return true;
	}
}
