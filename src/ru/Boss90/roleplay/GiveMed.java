package ru.Boss90.roleplay;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import ru.Boss90.roleplay.enums.Fraction;
import ru.Boss90.roleplay.enums.Med;

public class GiveMed implements CommandExecutor{
    Plugin plugin;
    String forall;
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public GiveMed() {
        this.plugin = (Plugin)main.getPlugin((Class)main.class);
        this.forall = this.plugin.getConfig().getString("nachalo");
    }
    
    public boolean onCommand(final CommandSender sender, final Command cmd, final String commandlabel, final String[] args) {
        if (cmd.getName().equalsIgnoreCase("givemed")) {
            this.forall = this.forall.replace("&", "�");
            if(!main.SQL.getPlayerRabota(sender.getName()).getFraction().equals(Fraction.HOSPITAL)) {
            	sender.sendMessage(forall + ChatColor.WHITE+"�� �� ��������� � ��������!");
            	return true;
            }
            if(main.SQL.getPlayerRabota(sender.getName()).getNumber() < 5) {
            	sender.sendMessage(forall + ChatColor.WHITE+"� ��� ���� �������!");
            	return true;
            }
            if (args.length == 0) {
                sender.sendMessage(this.forall + ChatColor.WHITE + "/givemed [���] [���.�����] - ���� ������ ���.�����.");
                sender.sendMessage(this.forall + ChatColor.WHITE + "/givemed list - ����� ���� ���.�����, � ����� ����� ��������� � �������.");
                return true;
            }
            if (args.length == 1 && args[0].equalsIgnoreCase("list")) {
                for (final Med educ : Med.values()) {
                    sender.sendMessage(this.forall + ChatColor.WHITE + educ.getName().replace(" ", "_"));
                }
                return true;
            }
    		if (((Player)sender).getLocation().distance(Bukkit.getPlayer(args[0]).getLocation()) > 5.0) {
    			sender.sendMessage(this.forall + ChatColor.WHITE + "����� ������� ������!");
    			return true;
    		}
            if (args.length == 2) {
                if (!main.SQL.hasValue(args[0])) {
                    main.SQL.writeToRoleplay(args[0]);
                }
                final Player p = Bukkit.getPlayer(args[0]);
                for (final Med educ2 : Med.values()) {
                    if (educ2.getName().toLowerCase().equalsIgnoreCase(args[1].toLowerCase().replace("_", " "))) {
                        main.SQL.setMedCart(p.getName(), educ2);
                        p.sendMessage(this.forall + ChatColor.WHITE + "��� ���� ������: "+args[1].replace("_", " ")+" ��������� ���.�����.");
                        sender.sendMessage(this.forall + ChatColor.WHITE + "������ " + p.getName() + " ������ " + args[1].replace("_", " ") + " ��������� ���.�����.");
                        return true;
                    }
                }
                sender.sendMessage(this.forall + ChatColor.WHITE + "������ ����������� ���... /givemed list");
                return true;
            }
        }
        return true;
    }
}
