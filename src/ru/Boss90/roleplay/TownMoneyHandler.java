package ru.Boss90.roleplay;

import org.bukkit.plugin.*;
import net.md_5.bungee.api.*;
import ru.Boss90.roleplay.enums.Fraction;

import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.entity.*;

public class TownMoneyHandler implements CommandExecutor
{
    Plugin p;
    String pref;
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public TownMoneyHandler() {
        this.p = (Plugin)main.getPlugin((Class)main.class);
        this.pref = this.p.getConfig().getString("nachalo").replace("&", "�");
    }
    
    public boolean onCommand(final CommandSender sender, final Command cmd, final String l, final String[] args) {
        if (cmd.getName().equalsIgnoreCase("townmoney")) {
            sender.sendMessage(this.pref + "������ ������: " + ChatColor.AQUA + TownMoneyApi.getMoney() + "$" + ChatColor.WHITE + ".");
        }
        if (cmd.getName().equalsIgnoreCase("holotownmoney") && sender instanceof Player && sender.isOp()) {
            final Player d = (Player)sender;
            this.p.getConfig().set("superloc", (Object)(d.getLocation().getBlockX() + " " + d.getLocation().getBlockY() + " " + d.getLocation().getBlockZ()));
            this.p.saveConfig();
            d.sendMessage(ChatColor.GREEN + "��, 5 ��� �������");
        }
        if (cmd.getName().equalsIgnoreCase("towndonate") && sender instanceof Player) {
            if (args.length == 0) {
                sender.sendMessage(this.pref + "/" + cmd.getName() + " [�����] - ���������� � ����� ������");
                return true;
            }
            if (args.length == 1) {
                final Player p = (Player)sender;
                int f = 0;
                try {
                    f = Integer.valueOf(args[0]);
                }
                catch (NumberFormatException e) {
                    sender.sendMessage(this.pref + "������� ������ �����!");
                    return true;
                }
                if (VaultManager.getmoney(p) < f) {
                    sender.sendMessage(this.pref + "������������ ����� �� �����!");
                    return true;
                }
                VaultManager.withdraw(p, f);
                TownMoneyApi.deposite(f);
                sender.sendMessage(this.pref + "�� ������� ���������� " + ChatColor.AQUA + f + "$" + ChatColor.WHITE + "� ����� ������! ������ � ��� " + ChatColor.AQUA + TownMoneyApi.getMoney() + "$" + ChatColor.WHITE + "!");
            }
        }
        if(cmd.getName().equalsIgnoreCase("withdrawtown") && sender instanceof Player) {
        	if(main.SQL.getPlayerRabota(sender.getName()).getFraction() != Fraction.MERIA) {
        		return true;
        	}
        		if(main.SQL.getPlayerRabota(sender.getName()).getNumber() != 8) {
        			return true;
        		}
            if (args.length == 0) {
                sender.sendMessage(this.pref + "/" + cmd.getName() + " [�����] - ������� ������ � ����� ������");
                return true;
            }
            StringBuilder sb = new StringBuilder();
            for (int i = 1; i < args.length; i++) sb.append(args[i]).append(' ');
       	    String text = sb.toString();
                final Player p = (Player)sender;
                int f = 0;
                try {
                    f = Integer.valueOf(args[0]);
                }
                catch (NumberFormatException e) {
                    sender.sendMessage(this.pref + "������� ������ �����!");
                    return true;
                }
                if (VaultManager.getmoney(p) < f) {
                    sender.sendMessage(this.pref + "������������ ����� �� �����!");
                    return true;
                }
                if(f > TownMoneyApi.getMoney()) {
                	sender.sendMessage(this.pref + "����� ����� ���� �� ������ ������ � ����� ������.");
                	return true;
                }
                VaultManager.deposite(p, f);
                TownMoneyApi.withdraw(f);
                if(f >= 50000) {
                	Bukkit.broadcastMessage(pref + "��� " + sender.getName() + " ���� � �����  ������ " + f + " �������: " + text);
                	return true;
                }
                sender.sendMessage(this.pref + "�� ������� ������� " + ChatColor.AQUA + f + "$" + ChatColor.WHITE + " �� ����� ������! ������ � ��� " + ChatColor.AQUA + TownMoneyApi.getMoney() + "$" + ChatColor.WHITE + "!");
            }
        return true;
    }
}
