package ru.Boss90.roleplay;

import org.bukkit.plugin.*;

import ru.Boss90.roleplay.enums.*;

import org.bukkit.command.*;
import org.bukkit.entity.*;
import org.bukkit.*;
import org.bukkit.ChatColor;

public class MarkCmd implements CommandExecutor
{
    Plugin plugin;
    String forall;
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public MarkCmd() {
        this.plugin = (Plugin)main.getPlugin((Class)main.class);
        this.forall = this.plugin.getConfig().getString("nachalo").replace("&", "�");
    }
    
    public boolean onCommand(final CommandSender sender, final Command cmd, final String commandlabel, final String[] args) {
        if (cmd.getName().equalsIgnoreCase("mark") && sender instanceof Player) {
            final Player p = (Player)sender;
            if (sender.isOp() || main.SQL.getPlayerRabota(sender.getName()).getFraction().equals(Fraction.POLICE)) {
                if (args.length == 0) {
                    sender.sendMessage(this.forall + ChatColor.WHITE + "/mark [�����] - �������� ���������� � ��������������� ������.");
                }
                if (args.length == 1) {
                    final Player pe = Bukkit.getPlayer(args[0]);
                    if (!Bukkit.getOnlinePlayers().contains(pe)) {
                        sender.sendMessage(this.forall + ChatColor.WHITE + "����� ������ ��� �� �������!");
                        return true;
                    }
                    if (main.SQL.getPlayerStars(pe.getName()) < 2) {
                        sender.sendMessage(this.forall + ChatColor.WHITE + "� ����� ������ ��� ���� �������, ��� �� ������ ����.");
                        return true;
                    }
                    if (pe.hasPermission("atlantworld.freestars")) {
                        sender.sendMessage(this.forall + ChatColor.WHITE + "����� ������ ������ ��������!");
                        return true;
                    }
                    final String dasg = ScoreBoard.getStars(p);
                    
                    p.sendMessage(this.forall + ChatColor.WHITE + "���: " + ChatColor.AQUA + pe.getName());
                    p.sendMessage(this.forall + ChatColor.WHITE + "������� �������: " + dasg);
                    p.sendMessage(this.forall + ChatColor.WHITE + "����������: ");
                    p.sendMessage(this.forall + ChatColor.WHITE + "x: " + ChatColor.AQUA + pe.getLocation().getBlockX() + ChatColor.WHITE + ";");
                    p.sendMessage(this.forall + ChatColor.WHITE + "y: " + ChatColor.AQUA + pe.getLocation().getBlockY() + ChatColor.WHITE + ";");
                    p.sendMessage(this.forall + ChatColor.WHITE + "z: " + ChatColor.AQUA + pe.getLocation().getBlockZ() + ChatColor.WHITE + ".");
                    p.sendMessage("        ");
                    return true;
                }
            }
            else {
                sender.sendMessage(this.forall + ChatColor.WHITE + "�� �� ������ ��� �������!");
            }
            return true;
        }
        if(cmd.getName().equalsIgnoreCase("marklist")) {
            if (!main.SQL.getPlayerRabota(sender.getName()).getFraction().equals(Fraction.POLICE)) {
            	sender.sendMessage(forall + ChatColor.WHITE + "�� �� ��������� � �������!");
            	return true;
            }
            sender.sendMessage(forall + "� �������:");
            for(Player p2 : Bukkit.getOnlinePlayers()) {
            	sender.sendMessage(forall + ChatColor.WHITE+p2.getName() + " " + ScoreBoard.getStars(p2));
            }
            return true;
        }
        return false;
    }
}
