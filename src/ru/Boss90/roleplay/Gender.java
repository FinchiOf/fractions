package ru.Boss90.roleplay;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class Gender implements CommandExecutor {
	@Override
	public boolean onCommand(final CommandSender sender, final Command cmd, final String strl, final String[] args) {
		if (args.length == 0) {
			sender.sendMessage(ChatColor.GOLD + "�������� ���:");
			sender.sendMessage(ChatColor.AQUA + "�������     " + ChatColor.YELLOW + "�����" + ChatColor.DARK_PURPLE
					+ "     �������");
			return true;
		}
		switch (args[0]) {
		case ("�������"):
			main.SQL.setgender(sender.getName(), 1);
			break;
		case ("�������"):
			main.SQL.setgender(sender.getName(), 0);
			break;
		case ("�����"):
			main.SQL.setgender(sender.getName(), 3);
			break;
		}

		return true;
	}
}