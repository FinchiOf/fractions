package ru.Boss90.roleplay;

import org.bukkit.plugin.*;

import ru.Boss90.roleplay.enums.*;

import org.bukkit.command.*;
import org.bukkit.*;
import org.bukkit.ChatColor;
import org.bukkit.entity.*;

public class GiveStudyCmd implements CommandExecutor
{
    Plugin plugin;
    String forall;
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public GiveStudyCmd() {
        this.plugin = (Plugin)main.getPlugin((Class)main.class);
        this.forall = this.plugin.getConfig().getString("nachalo");
    }
    
    public boolean onCommand(final CommandSender sender, final Command cmd, final String commandlabel, final String[] args) {
        if (cmd.getName().equalsIgnoreCase("givestudy")) {
            this.forall = this.forall.replace("&", "�");
            if (!sender.isOp()) {
                sender.sendMessage(this.forall + ChatColor.WHITE + "��� ����!");
                return true;
            }
            if (args.length == 0) {
                sender.sendMessage(this.forall + ChatColor.WHITE + "/givestudy [���] [�����������] - ���� ������ �����������.");
                sender.sendMessage(this.forall + ChatColor.WHITE + "/givestudy list - ����� ���� �����������, � ����� ����� ��������� � �������.");
                return true;
            }
            if (args.length == 1 && args[0].equalsIgnoreCase("list")) {
                for (final Education educ : Education.values()) {
                    sender.sendMessage(this.forall + ChatColor.WHITE + educ.getName().replace(" ", "_"));
                }
                return true;
            }
            if (args.length == 2) {
                if (!main.SQL.hasValue(args[0])) {
                    main.SQL.writeToRoleplay(args[0]);
                }
                final Player p = Bukkit.getPlayer(args[0]);
                for (final Education educ2 : Education.values()) {
                    if (educ2.getName().toLowerCase().equalsIgnoreCase(args[1].toLowerCase().replace("_", " "))) {
                        main.SQL.setEducation(p.getName(), educ2);
                        p.sendMessage(this.forall + ChatColor.WHITE + "��� ���� ������: "+args[1].replace("_", " ")+" �����������.");
                        sender.sendMessage(this.forall + ChatColor.WHITE + "������ " + p.getName() + " ������ " + args[1].replace("_", " ") + " �����������.");
                        return true;
                    }
                }
                sender.sendMessage(this.forall + ChatColor.WHITE + "������ ����������� ���... /givestudy list");
                return true;
            }
        }
        return true;
    }
}
