package ru.Boss90.roleplay;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ru.Boss90.roleplay.enums.*;

public class setbase implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String str, String[] args) {
        if (cmd.getName().equalsIgnoreCase("setbase")) {
        	if(!sender.isOp()) {
        		sender.sendMessage(CommandWork.nachalo+ChatColor.WHITE+"У вас недостаточно прав!");
        		return true;
        	}
        	if(main.SQL.getPlayerRabota(sender.getName()).getFraction().equals(Fraction.MERIA)) {
        		main.plugin.getConfig().set("basameria.x", ((Player)sender).getLocation().getBlockX());
        		main.plugin.getConfig().set("basameria.y", ((Player)sender).getLocation().getBlockY());
        		main.plugin.getConfig().set("basameria.z", ((Player)sender).getLocation().getBlockZ());
        		sender.sendMessage(CommandWork.nachalo+ChatColor.WHITE+"Вы успешно установили координаты базы.");
        		return true;
        	}
        	if(main.SQL.getPlayerRabota(sender.getName()).getFraction().equals(Fraction.POLICE)) {
        		main.plugin.getConfig().set("basapolice.x", ((Player)sender).getLocation().getBlockX());
        		main.plugin.getConfig().set("basapolice.y", ((Player)sender).getLocation().getBlockY());
        		main.plugin.getConfig().set("basapolice.z", ((Player)sender).getLocation().getBlockZ());
        		sender.sendMessage(CommandWork.nachalo+ChatColor.WHITE+"Вы успешно установили координаты базы.");
        		return true;
        	}
        	if(main.SQL.getPlayerRabota(sender.getName()).getFraction().equals(Fraction.HOSPITAL)) {
        		main.plugin.getConfig().set("basahospital.x", ((Player)sender).getLocation().getBlockX());
        		main.plugin.getConfig().set("basahospital.y", ((Player)sender).getLocation().getBlockY());
        		main.plugin.getConfig().set("basahospital.z", ((Player)sender).getLocation().getBlockZ());
        		sender.sendMessage(CommandWork.nachalo+ChatColor.WHITE+"Вы успешно установили координаты базы.");
        		return true;
        	}
        }
        if (cmd.getName().equalsIgnoreCase("base")) {
        	if(main.SQL.getPlayerRabota(sender.getName()).getFraction().equals(Fraction.MERIA)) {
        		((Player)sender).teleport(new Location(Bukkit.getWorld("world"), main.plugin.getConfig().getInt("basameria.x"), main.plugin.getConfig().getInt("basameria.y"), main.plugin.getConfig().getInt("basameria.z")));
        		sender.sendMessage(CommandWork.nachalo + ChatColor.WHITE+"Вы телепортировались на базу своей фракции.");
        		return true;
        	}
        	if(main.SQL.getPlayerRabota(sender.getName()).getFraction().equals(Fraction.POLICE)) {
        		((Player)sender).teleport(new Location(Bukkit.getWorld("world"), main.plugin.getConfig().getInt("basapolice.x"), main.plugin.getConfig().getInt("basapolice.y"), main.plugin.getConfig().getInt("basapolice.z")));
        		sender.sendMessage(CommandWork.nachalo + ChatColor.WHITE+"Вы телепортировались на базу своей фракции.");
        		return true;
        	}
        	if(main.SQL.getPlayerRabota(sender.getName()).getFraction().equals(Fraction.HOSPITAL)) {
        		((Player)sender).teleport(new Location(Bukkit.getWorld("world"), main.plugin.getConfig().getInt("basahospital.x"), main.plugin.getConfig().getInt("basahospital.y"), main.plugin.getConfig().getInt("basahospital.z")));
        		sender.sendMessage(CommandWork.nachalo + ChatColor.WHITE+"Вы телепортировались на базу своей фракции.");
        		return true;
        	}
        }
		return true;
	}
}
