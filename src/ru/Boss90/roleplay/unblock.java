package ru.Boss90.roleplay;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import ru.Boss90.roleplay.enums.Fraction;

public class unblock implements CommandExecutor {
    Plugin plugin;
    String nachalo;
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public unblock() {
        this.plugin = (Plugin)main.getPlugin((Class)main.class);
        this.nachalo = new StringBuilder().append(ChatColor.AQUA).append(ChatColor.BOLD).append("Работы > ").toString();
    }
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String str, String[] args) {
		if(args.length == 0) {
		   sender.sendMessage(nachalo + ChatColor.WHITE+"Напишите ник!");
		   return true;
		}
		switch(main.SQL.getPlayerRabota(sender.getName()).getFraction().getName()) {
		case("Полиция"):
			if(main.SQL.getPlayerRabota(sender.getName()).getNumber() >= 9) {
				sender.sendMessage(nachalo +ChatColor.WHITE +"Вы убрали в ЧС игрока " + args[0]);
                List<String> players = plugin.getConfig().getStringList("BlackListPolice");
                players.remove(args[0]);
                plugin.getConfig().set("BlackListPolice", players);
                plugin.saveConfig();
                RaEvents.police.forEach(p3 -> p3.sendMessage(nachalo + ChatColor.WHITE + "[Полиция: Информация] "+args[0]+" был убран из чёрного списка высшем чином " + sender.getName()));
			}
			break;
		case("Больница"):
			if(main.SQL.getPlayerRabota(sender.getName()).getNumber() >= 7) {
                List<String> players = plugin.getConfig().getStringList("BlackListHospital");
				sender.sendMessage(nachalo +ChatColor.WHITE +"Вы убрали в ЧС игрока " + args[0]);
                players.remove(args[0]);
                plugin.getConfig().set("BlackListHospital", players);
                plugin.saveConfig();
                RaEvents.hospital.forEach(p3 -> p3.sendMessage(nachalo + ChatColor.WHITE + "[Больница: Информация] "+args[0]+" был убран из чёрного списка высшем чином " + sender.getName()));
			}
			break;
		case("Мэрия"):
			if(main.SQL.getPlayerRabota(sender.getName()).getNumber() >= 7) {
                List<String> players = plugin.getConfig().getStringList("BlackListMeria");
				sender.sendMessage(nachalo +ChatColor.WHITE +"Вы убрали в ЧС игрока " + args[0]);
                players.remove(args[0]);
                plugin.getConfig().set("BlackListMeria", players);
                plugin.saveConfig();
                RaEvents.meria.forEach(p3 -> p3.sendMessage(nachalo + ChatColor.WHITE + "[Мэрия: Информация] "+args[0]+" был убран из чёрного списка высшем чином " + sender.getName()));
			}
			break;
		case("Армия"):
			if(main.SQL.getPlayerRabota(sender.getName()).getNumber() >= 7) {
                List<String> players = plugin.getConfig().getStringList("BlackListArmia");
				sender.sendMessage(nachalo +ChatColor.WHITE +"Вы убрали в ЧС игрока " + args[0]);
                players.remove(args[0]);
                plugin.getConfig().set("BlackListArmia", players);
                plugin.saveConfig();
                RaEvents.armia.forEach(p3 -> p3.sendMessage(nachalo + ChatColor.WHITE + "[Армия: Информация] "+args[0]+" был убран из чёрного списка высшем чином " + sender.getName()));
			}
			break;
		default:
			sender.sendMessage(nachalo + ChatColor.WHITE + "Вы не можете сделать это!");
			break;
		}
		return true;
	}
}
