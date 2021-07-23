package ru.Boss90.roleplay;

import org.bukkit.plugin.*;

import me.clip.placeholderapi.PlaceholderAPI;
import ru.Boss90.roleplay.enums.*;

import org.bukkit.command.*;
import org.bukkit.entity.*;
import org.bukkit.*;

public class MarkCmd implements CommandExecutor
{
    Plugin plugin;
    String forall;
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public MarkCmd() {
        this.plugin = (Plugin)main.getPlugin((Class)main.class);
        this.forall = this.plugin.getConfig().getString("nachalo").replace("&", "§");
    }
    
    public boolean onCommand(final CommandSender sender, final Command cmd, final String commandlabel, final String[] args) {
        if(cmd.getName().equalsIgnoreCase("marklist")) {
            if (!main.SQL.getPlayerRabota(sender.getName()).getFraction().equals(Fraction.POLICE)) {
            	sender.sendMessage(forall + ChatColor.WHITE + "Вы не работаете в полиции!");
            	return true;
            }
            sender.sendMessage(forall + "В розыске:");
            for(Player p : Bukkit.getOnlinePlayers()) {
            for(int i3 = 0; i3 < Bukkit.getOnlinePlayers().size(); i3++) {
            sender.sendMessage(i3 + ". "+p.getName() +" "+ PlaceholderAPI.setPlaceholders(p, "%Fraction_stars%"));
            }
            }
            return true;
        }
        if (cmd.getName().equalsIgnoreCase("mark") && sender instanceof Player) {
            final Player p = (Player)sender;
            if (sender.isOp() || main.SQL.getPlayerRabota(sender.getName()).getFraction().equals(Fraction.POLICE)) {
                if (args.length == 0) {
                    sender.sendMessage(this.forall + ChatColor.WHITE + "/mark [Игрок] - Показать информацию о местонахождении игрока.");
                }
                if (args.length == 1) {
                    final Player pe = Bukkit.getPlayer(args[0]);
                    if (!Bukkit.getOnlinePlayers().contains(pe)) {
                        sender.sendMessage(this.forall + ChatColor.WHITE + "Этого игрока нет на сервере!");
                        return true;
                    }
                    if (main.SQL.getPlayerStars(pe.getName()) < 2) {
                        sender.sendMessage(this.forall + ChatColor.WHITE + "У этого игрока нет звёзд розыска, или их меньше двух.");
                        return true;
                    }
                    if (pe.hasPermission("atlantworld.freestars")) {
                        sender.sendMessage(this.forall + ChatColor.WHITE + "Этого игрока нельзя посадить!");
                        return true;
                    }
                    p.sendMessage(this.forall + ChatColor.WHITE + "Имя: " + ChatColor.AQUA + pe.getName());
                    final String dasg = PlaceholderAPI.setPlaceholders(pe, "%Fraction_stars%");
                    p.sendMessage(this.forall + ChatColor.WHITE + "Уровень розыска: " + dasg);
                    p.sendMessage(this.forall + ChatColor.WHITE + "Координаты: ");
                    p.sendMessage(this.forall + ChatColor.WHITE + "x: " + ChatColor.AQUA + pe.getLocation().getBlockX() + ChatColor.WHITE + ";");
                    p.sendMessage(this.forall + ChatColor.WHITE + "y: " + ChatColor.AQUA + pe.getLocation().getBlockY() + ChatColor.WHITE + ";");
                    p.sendMessage(this.forall + ChatColor.WHITE + "z: " + ChatColor.AQUA + pe.getLocation().getBlockZ() + ChatColor.WHITE + ".");
                    p.sendMessage("        ");
                    return true;
                }
            }else {
                sender.sendMessage(this.forall + ChatColor.WHITE + "Вы не можете это сделать!");
            }
            return true;
        }
        return true;
    }
}
