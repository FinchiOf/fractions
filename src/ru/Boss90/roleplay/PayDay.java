package ru.Boss90.roleplay;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import ru.Boss90.roleplay.interfaces.Work;

public class PayDay implements CommandExecutor {

	@SuppressWarnings("incomplete-switch")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String str, String[] args) {
		Player p = (Player) sender;
		if(!p.hasPermission("Fraction.payday")) {
			p.sendMessage(ChatColor.RED+"Недостаточно прав.");
			return true;
		}
		for(Player pe : Bukkit.getOnlinePlayers()) {
				int summa = 0;
				final Work work = main.SQL.getPlayerRabota(pe.getName());
				String d = "";
				switch (work.getFraction()) {
				case MERIA: {
					d = "meria";
					break;
				}
				case POLICE: {
					d = "police";
					break;
				}
				case HOSPITAL: {
					d = "hospital";
					break;
				}
				}
				System.out.println(d);
				summa = main.plugin.getConfig().getInt(d + work.getNumber());
				System.out.println(summa);
				main.plugin.givezp(pe, summa);
				TownMoneyApi.withdraw(summa);
				Bukkit.broadcastMessage(main.plugin.forall + ChatColor.WHITE
						+ "\u0417\u0430\u0440\u043f\u043b\u0430\u0442\u044b \u0432\u0441\u0435\u043c \u0440\u0430\u0431\u043e\u0442\u043d\u0438\u043a\u0430\u043c \u0443\u0441\u043f\u0435\u0448\u043d\u043e \u0432\u044b\u0434\u0430\u043d\u044b!");
				pe.sendMessage(main.plugin.forall + ChatColor.WHITE
						+ "\u0412\u0430\u043c \u0432\u044b\u0434\u0430\u043d\u0430 \u0437\u0430\u0440\u043f\u043b\u0430\u0442\u0430 \u0432 \u0440\u0430\u0437\u043c\u0435\u0440\u0435 "
						+ summa + "$.");
			}
		return true;
	}
}
