package ru.Boss90.roleplay;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import ru.Boss90.roleplay.enums.Fraction;

public class TownMoneyHandler implements CommandExecutor {
	Plugin p = (Plugin) main.getPlugin(main.class);
	String pref = this.p.getConfig().getString("nachalo").replace("&", "§");

	public boolean onCommand(CommandSender sender, Command cmd, String l, String[] args) {
		if (cmd.getName().equalsIgnoreCase("townmoney")) {
			sender.sendMessage(String.valueOf(this.pref) + "Бюджет города: " + ChatColor.AQUA + TownMoneyApi.getMoney()
					+ "$" + ChatColor.WHITE + ".");
		}
		if (cmd.getName().equalsIgnoreCase("towndonate") && sender instanceof Player) {
			if (args.length == 0) {
				sender.sendMessage(
						String.valueOf(this.pref) + "/" + cmd.getName() + " [Сумма] - Задонатить в казну города");
				return true;
			}
			if (args.length == 1) {
				Player p = (Player) sender;
				int f = 0;
				try {
					f = Integer.valueOf(args[0]).intValue();
				} catch (NumberFormatException e) {
					sender.sendMessage(String.valueOf(this.pref) + "Введите верное число!");
					return true;
				}
				if (VaultManager.getmoney(p) < f) {
					sender.sendMessage(String.valueOf(this.pref) + "Недостаточно денег на счету!");
					return true;
				}
				VaultManager.withdraw(p, f);
				TownMoneyApi.deposite(f);
				sender.sendMessage(String.valueOf(this.pref) + "Вы успешно задонатили " + ChatColor.AQUA + f + "$"
						+ ChatColor.WHITE + "в казну города! Теперь в ней " + ChatColor.AQUA + TownMoneyApi.getMoney()
						+ "$" + ChatColor.WHITE + "!");
			}
		}
		if (cmd.getName().equalsIgnoreCase("withdrawtown") && sender instanceof Player) {
			if (main.SQL.getPlayerRabota(sender.getName()).getFraction() != Fraction.MERIA) {
				return true;
			}
			if (main.SQL.getPlayerRabota(sender.getName()).getNumber() != 8) {
				return true;
			}
			if (args.length == 0) {
				sender.sendMessage(
						String.valueOf(this.pref) + "/" + cmd.getName() + " [Сумма] - Забрать деньги с казны города");
				return true;
			}
			StringBuilder sb = new StringBuilder();
			for (int i = 1; i < args.length;) {
				sb.append(args[i]).append(' ');
				i++;
			}
			if(sb.length() == 0) {
				sender.sendMessage(ChatColor.RED+"Введите текст!");
				return true;
			}
			String text = sb.toString();
			Player p = (Player) sender;
			int f = 0;
			try {
				f = Integer.valueOf(args[0]).intValue();
			} catch (NumberFormatException e) {
				sender.sendMessage(String.valueOf(this.pref) + "Введите верное число!");
				return true;
			}
			if (f > TownMoneyApi.getMoney()) {
				sender.sendMessage(String.valueOf(this.pref) + "Таких денег нету на данный момент в казне города.");
				return true;
			}
			VaultManager.deposite(p, f);
			TownMoneyApi.withdraw(f);
			if (f >= 50000) {
				Bukkit.broadcastMessage(String.valueOf(this.pref) + "Мэр " + sender.getName() + " взял с казны города "
						+ f + " причина: " + text);
			}
			sender.sendMessage(String.valueOf(this.pref) + "Вы успешно забрали " + ChatColor.AQUA + f + "$"
					+ ChatColor.WHITE + " из казны города! Теперь в ней " + ChatColor.AQUA + TownMoneyApi.getMoney()
					+ "$" + ChatColor.WHITE + "!");
		}
		return true;
	}
}