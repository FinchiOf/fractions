package ru.Boss90.roleplay;

import org.bukkit.command.*;
import org.bukkit.entity.*;

import ru.Boss90.roleplay.enums.*;

import org.bukkit.*;
import org.bukkit.ChatColor;

public class ShtrafCmdHandler implements CommandExecutor
{
    int min;
    int max;
    
    public ShtrafCmdHandler() {
        this.min = 100;
        this.max = 10000;
    }
    
    public boolean onCommand(final CommandSender sender, final Command cmd, final String l, final String[] args) {
        if (cmd.getName().equalsIgnoreCase("shtraf") && sender instanceof Player && (AccessLevel.contains(main.SQL.getPlayerRabota(sender.getName()).getAccess(), AccessLevel.SHTRAF) || sender.isOp())) {
            if (args.length == 0) {
                sender.sendMessage(ChatColor.WHITE + "[" + ChatColor.AQUA + "i" + ChatColor.WHITE + "] /shtraf [�����] [����� �� " + this.min + " �� " + this.max + "] [�������] - ����������� ������");
                return true;
            }
            if (args.length == 3) {
                final String kogo = args[0];
                final String reason = args[2];
                int s = 0;
                if (Bukkit.getPlayer(kogo) == null) {
                    sender.sendMessage(ChatColor.WHITE + "[" + ChatColor.AQUA + "i" + ChatColor.WHITE + "] ����� �������!");
                    return true;
                }
                final Player g = Bukkit.getPlayer(kogo);
                final Player p = (Player)sender;
                if (p.getLocation().distance(g.getLocation()) > 8.0) {
                    sender.sendMessage(ChatColor.WHITE + "[" + ChatColor.AQUA + "i" + ChatColor.WHITE + "] �� ������� ������ �� ������!");
                    return true;
                }
                try {
                    s = Integer.valueOf(args[1]);
                    if (s < this.min || s > this.max) {
                        sender.sendMessage(ChatColor.WHITE + "[" + ChatColor.AQUA + "i" + ChatColor.WHITE + "] ������� ����� �� " + this.min + " �� " + this.max + "!");
                        return true;
                    }
                }
                catch (NumberFormatException e) {
                    sender.sendMessage(ChatColor.WHITE + "[" + ChatColor.AQUA + "i" + ChatColor.WHITE + "] ������� ������ �����!");
                    return true;
                }
                if (VaultManager.getmoney(g) < s) {
                    sender.sendMessage(ChatColor.WHITE + "[" + ChatColor.AQUA + "i" + ChatColor.WHITE + "] � ������ ������������ �����");
                    return true;
                }
                TownMoneyApi.deposite(s);
                VaultManager.withdraw(g, s);
                g.sendMessage(ChatColor.WHITE + "[" + ChatColor.AQUA + "i" + ChatColor.WHITE + "] �� ���� ����������� ������� " + p.getName() + " �� " + ChatColor.AQUA + s + "$" + ChatColor.WHITE + ". �������: " + ChatColor.AQUA + reason);
                p.sendMessage(ChatColor.WHITE + "[" + ChatColor.AQUA + "i" + ChatColor.WHITE + "] �� ����������� ������ " + g.getName() + " �� " + s + "$.");
                return true;
            }
        }
        return true;
    }
}
