package ru.Boss90.roleplay;

import org.bukkit.plugin.*;
import org.bukkit.command.*;
import org.bukkit.*;

public class Boss90 implements CommandExecutor
{
    Plugin plugin;
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public Boss90() {
        this.plugin = (Plugin)main.getPlugin((Class)main.class);
    }
    
    public boolean onCommand(final CommandSender sender, final Command cmd, final String commandlabel, final String[] args) {
        if (cmd.getName().equalsIgnoreCase("boss90") && args.length == 0) {
            sender.sendMessage(new StringBuilder().append(ChatColor.GREEN).append(ChatColor.BOLD).append("§e§kfffffffffffffffffffffffffffffffffffff").toString());
            sender.sendMessage(new StringBuilder().append(ChatColor.GREEN).append(ChatColor.BOLD).append("§6§lFraction §f- §eby Boss90").toString());
            sender.sendMessage(new StringBuilder().append(ChatColor.GOLD).append(ChatColor.BOLD).append("Version: §e").append(this.plugin.getDescription().getVersion()).toString());
            sender.sendMessage(new StringBuilder().append(ChatColor.GOLD).append(ChatColor.BOLD).append("MSSTUDIO COPYRIGHTED ").toString());
            sender.sendMessage(new StringBuilder().append(ChatColor.GREEN).append(ChatColor.BOLD).append("§e§kfffffffffffffffffffffffffffffffffffff").toString());
            return true;
        }
        if (args.length != 1 || !args[0].equalsIgnoreCase("reload")) {
            return false;
        }
        if (sender.isOp()) {
            this.plugin.reloadConfig();
            sender.sendMessage(ChatColor.GREEN + "Óñïåøíî!");
            return true;
        }
        sender.sendMessage(ChatColor.RED + "Íåò ïðàâ!");
        return true;
    }
}
