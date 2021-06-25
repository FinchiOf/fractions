package ru.Boss90.roleplay;

import org.bukkit.plugin.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;

import net.md_5.bungee.api.*;
import ru.Boss90.roleplay.enums.*;

public class SetSalaryCmd implements CommandExecutor
{
    Plugin plugin;
    String forall;
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public SetSalaryCmd() {
        this.plugin = (Plugin)main.getPlugin((Class)main.class);
        this.forall = this.plugin.getConfig().getString("nachalo");
    }
    
    public boolean onCommand(final CommandSender sender, final Command cmd, final String commandlabel, final String[] args) {
        if (cmd.getName().equalsIgnoreCase("setsalary")) {
            this.forall = this.forall.replace("&", "�");
            if (sender instanceof Player) {
                final Player p = (Player)sender;
                if (!AccessLevel.contains(main.SQL.getPlayerRabota(p.getName()).getAccess(), AccessLevel.SETSALARY) && !sender.isOp()) {
                    sender.sendMessage(this.forall + ChatColor.WHITE + "��� ����.");
                    return true;
                }
                if (args.length == 0) {
                    sender.sendMessage(this.forall + ChatColor.WHITE + "/setsalary [�������] [����� �� 400 �� 700] - ���������� �������� ������� ����� �������.");
                    sender.sendMessage(this.forall + ChatColor.WHITE + "/setsalary list - ������ �������, ������� ����� ���������� ��");
                    return true;
                }
                if (args.length == 1 && args[0].equalsIgnoreCase("list")) {
                    sender.sendMessage(this.forall + ChatColor.WHITE + "�����");
                    sender.sendMessage(this.forall + ChatColor.WHITE + "�������");
                    sender.sendMessage(this.forall + ChatColor.WHITE + "��������");
                    return true;
                }
                if (args.length == 2) {
                    final String h = args[0].toLowerCase();
                    if (!h.equalsIgnoreCase("�����") && !h.equalsIgnoreCase("�������") && !h.equalsIgnoreCase("��������")) {
                        sender.sendMessage(this.forall + ChatColor.WHITE + "�������� ������!");
                        return true;
                    }
                    if (h.equalsIgnoreCase("�����")) {
                        int zp = 0;
                        try {
                            zp = Integer.valueOf(args[1]);
                        }
                        catch (NumberFormatException e) {
                            sender.sendMessage(this.forall + ChatColor.WHITE + "������������ �����!");
                            return true;
                        }
                        if (zp < 400 || zp > 700) {
                            sender.sendMessage(this.forall + ChatColor.WHITE + "�������� ������ ���� �� 400 �� 700$!");
                            zp = 0;
                            return true;
                        }
                        this.plugin.getConfig().set("meria1", (Object)zp);
                        sender.sendMessage(this.forall + ChatColor.WHITE + "�������� ������� ����� ����� ������ ���������� " + zp + "!");
                        return true;
                    }
                    else if (h.equalsIgnoreCase("�������")) {
                        int zp = 0;
                        try {
                            zp = Integer.valueOf(args[1]);
                        }
                        catch (NumberFormatException e) {
                            sender.sendMessage(this.forall + ChatColor.WHITE + "������������ �����!");
                            return true;
                        }
                        if (zp < 400 || zp > 700) {
                            sender.sendMessage(this.forall + ChatColor.WHITE + "�������� ������ ���� �� 400 �� 700$!");
                            zp = 0;
                            return true;
                        }
                        this.plugin.getConfig().set("police1", (Object)zp);
                        sender.sendMessage(this.forall + ChatColor.WHITE + "�������� ������� ����� ������� ������ ���������� " + zp + "!");
                        return true;
                    }
                    else if (args[0].equalsIgnoreCase("��������")) {
                        int zp = 0;
                        try {
                            zp = Integer.valueOf(args[1]);
                        }
                        catch (NumberFormatException e) {
                            sender.sendMessage(this.forall + ChatColor.WHITE + "������������ �����!");
                            return true;
                        }
                        if (zp < 400 || zp > 700) {
                            sender.sendMessage(this.forall + ChatColor.WHITE + "�������� ������ ���� �� 400 �� 700$!");
                            zp = 0;
                            return true;
                        }
                        this.plugin.getConfig().set("bol1", (Object)zp);
                        sender.sendMessage(this.forall + ChatColor.WHITE + "�������� ������� ����� �������� ������ ���������� " + zp + "!");
                        return true;
                    }
                }
            }
        }
        return true;
    }
}
