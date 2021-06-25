package ru.Boss90.roleplay;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import ru.Boss90.roleplay.enums.Fraction;

public class GiviBilet implements CommandExecutor{
    Plugin plugin;
    String forall;
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public GiviBilet() {
        this.plugin = (Plugin)main.getPlugin((Class)main.class);
        this.forall = this.plugin.getConfig().getString("nachalo");
    }
    
    public boolean onCommand(final CommandSender sender, final Command cmd, final String commandlabel, final String[] args) {
        if (cmd.getName().equalsIgnoreCase("givemilitary")) {
            this.forall = this.forall.replace("&", "�");
            if(!main.SQL.getPlayerRabota(sender.getName()).getFraction().equals(Fraction.ARMIA)) {
            	sender.sendMessage(forall + ChatColor.WHITE+"�� �� ��������� � �����!");
            	return true;
            }
            if(main.SQL.getPlayerRabota(sender.getName()).getNumber() < 5) {
            	sender.sendMessage(forall + ChatColor.WHITE+"� ��� ���� �������!");
            	return true;
            }
            if (args.length == 0) {
                sender.sendMessage(this.forall + ChatColor.WHITE + "/givemilitary [���] - ���� ������ ������� ������ .");
                return true;
            }
    		if(Bukkit.getPlayer(args[0]) == null) {
    			sender.sendMessage(forall + ChatColor.WHITE+"����� �������!");
    			return true;
    		}
    		if (((Player)sender).getLocation().distance(Bukkit.getPlayer(args[0]).getLocation()) > 5.0) {
    			sender.sendMessage(this.forall + ChatColor.WHITE + "����� ������� ������!");
    			return true;
    		}
            if (args.length == 1) {
                if (!main.SQL.hasValue(args[0])) {
                    main.SQL.writeToRoleplay(args[0]);
                }
                final Player p = Bukkit.getPlayer(args[0]);
                        main.SQL.setbilet(p.getName(), 1);
                        p.sendMessage(this.forall + ChatColor.WHITE + "��� ��� ����� ������� �����.");
                        sender.sendMessage(this.forall + ChatColor.WHITE + "������ " + p.getName() + " ����� ������� �����.");
                        return true;
            }
        }
        return true;
    }
}
