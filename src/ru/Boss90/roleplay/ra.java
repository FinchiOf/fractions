package ru.Boss90.roleplay;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import net.md_5.bungee.api.ChatColor;
import ru.Boss90.roleplay.enums.Fraction;

public class ra implements CommandExecutor {
	Plugin plugin;
	String forall;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ra() {
		this.plugin = (Plugin) main.getPlugin((Class) main.class);
		this.forall = this.plugin.getConfig().getString("nachalo").replace("&", "§");
	}

	public boolean onCommand(CommandSender sender, Command cmd, String str, String[] args) {
		Player p = (Player) sender;
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < args.length; i++)
			sb.append(args[i]).append(' ');
		if (main.SQL.getPlayerRabota(p.getName()).getFraction().getName().contains("Не имеется")) {
			p.sendMessage(this.forall + ChatColor.WHITE + "Вы не работаете в гос.фракциях.");
			return true;
		}
			if (main.SQL.getPlayerRabota(sender.getName()).getFraction().equals(Fraction.POLICE)) {
				RaEvents.police.forEach(p2 -> p2.sendMessage(ChatColor.BLUE + "[R] [Полиция] [" + main.SQL.getPlayerRabota(p.getName()).getName()+ "] " + p.getName() + ": " + sb.toString()));
					return true;
				}
			if (main.SQL.getPlayerRabota(sender.getName()).getFraction().equals(Fraction.MERIA)) {
				RaEvents.meria.forEach(p2 -> p2.sendMessage(ChatColor.YELLOW + "[R] [Мэрия] [" + main.SQL.getPlayerRabota(p.getName()).getName()+ "] " + p.getName() + ": " + sb.toString()));
					return true;
				}
			if (main.SQL.getPlayerRabota(sender.getName()).getFraction().equals(Fraction.HOSPITAL)) {
					RaEvents.hospital.forEach(p2 -> p2.sendMessage(ChatColor.RED + "[R] [Больница] [" + main.SQL.getPlayerRabota(p.getName()).getName()+ "] " + p.getName() + ": " + sb.toString()));
					return true;
		}
		return true;
	}
}
