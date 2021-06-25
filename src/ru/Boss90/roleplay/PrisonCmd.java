package ru.Boss90.roleplay;

import org.bukkit.plugin.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;
import org.bukkit.*;
import org.bukkit.ChatColor;

public class PrisonCmd implements CommandExecutor
{
    Plugin plugin;
    String forall;
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public PrisonCmd() {
        this.plugin = (Plugin)main.getPlugin((Class)main.class);
        this.forall = this.plugin.getConfig().getString("nachalo");
    }
    
    public boolean onCommand(final CommandSender sender, final Command cmd, final String commandlabel, final String[] args) {
        if (cmd.getName().equalsIgnoreCase("prison")) {
            this.forall = this.forall.replace("&", "§");
            if (!sender.isOp()) {
                sender.sendMessage(this.forall + ChatColor.WHITE + "Нет прав!");
                return true;
            }
            if (args.length == 0) {
                sender.sendMessage(this.forall + ChatColor.WHITE + "/prison setpos - Установить точку телепортации на тюрьму");
                sender.sendMessage(this.forall + ChatColor.WHITE + "/prison setposafter - Установить точку телепортации после выхода из тюрьмы");
                sender.sendMessage(this.forall + ChatColor.WHITE + "/prison cho - Освободить всех (можешь просто удалить db файл)");
                return true;
            }
            if (args[0].equalsIgnoreCase("cho") && args.length == 1) {
                main.SQL.resetPrison();
                sender.sendMessage(ChatColor.GREEN + "готоао типа");
                return true;
            }
            if (args[0].equalsIgnoreCase("setpos") && args.length == 1) {
                if (sender instanceof Player) {
                    final Player p = (Player)sender;
                    final Location loc = p.getLocation();
                    this.plugin.getConfig().set("xpris", (Object)loc.getBlockX());
                    this.plugin.getConfig().set("ypris", (Object)loc.getBlockY());
                    this.plugin.getConfig().set("zpris", (Object)loc.getBlockZ());
                    this.plugin.saveConfig();
                    sender.sendMessage(ChatColor.GREEN + "Сделано!!!!!!");
                    return true;
                }
                sender.sendMessage(ChatColor.RED + "ИДИ НАХУЙ");
                return true;
            }
            else if (args[0].equalsIgnoreCase("setposafter") && args.length == 1) {
                if (sender instanceof Player) {
                    final Player p = (Player)sender;
                    final Location loc = p.getLocation();
                    this.plugin.getConfig().set("xprisafter", (Object)loc.getBlockX());
                    this.plugin.getConfig().set("yprisafter", (Object)loc.getBlockY());
                    this.plugin.getConfig().set("zprisafter", (Object)loc.getBlockZ());
                    this.plugin.saveConfig();
                    sender.sendMessage(ChatColor.GREEN + "Сделано!!!!!!");
                    return true;
                }
                sender.sendMessage(ChatColor.RED + "ИДИ НАХУЙ");
                return true;
            }
        }
        return false;
    }
}
