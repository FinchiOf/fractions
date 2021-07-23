package ru.Boss90.roleplay;

import org.bukkit.plugin.*;

import ru.Boss90.roleplay.enums.Fraction;
import ru.Boss90.roleplay.interfaces.Work;
import org.bukkit.command.*;
import org.bukkit.entity.*;
import org.bukkit.event.Listener;
import org.bukkit.*;

public class ArrestCmd implements CommandExecutor, Listener
{
    Plugin plugin;
    String forall;
    String nachalo;
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public ArrestCmd() {
        this.plugin = (Plugin)main.getPlugin((Class)main.class);
        this.forall = this.plugin.getConfig().getString("nachalo");
        this.nachalo = new StringBuilder().append(ChatColor.AQUA).append(ChatColor.BOLD).append("������ > ").toString();
    }
    
    public boolean onCommand(final CommandSender sender, final Command cmd, final String commandlabel, final String[] args) {
        if (cmd.getName().equalsIgnoreCase("arrest")) {
            this.forall = this.forall.replace("&", "�");
            final Player pe = (Player)sender;
    		final Work work = main.SQL.getPlayerRabota(sender.getName());
    		if(work.getFraction().getName() != "�������") {
    			sender.sendMessage(this.forall + ChatColor.WHITE+"�� �� ��������� � �������!");
    			return true;
    		}
			if (work.getNumber() < 3) {
                sender.sendMessage(this.forall + ChatColor.WHITE + "������������ ����� � ��������(3).");
                return true;
			}
            if (args.length == 0) {
                sender.sendMessage(this.forall + ChatColor.WHITE + "/arrest [���] [�������] - �������� � ������");
                return true;
            }
                final Player p = Bukkit.getPlayer(args[0]);
                if (p == null) {
                    sender.sendMessage(this.forall + ChatColor.WHITE + "������ ��� �� �������.");
                    return true;
                }
                if (p.getName().equalsIgnoreCase(pe.getName())) {
                    pe.sendMessage(this.forall + ChatColor.WHITE + "�� �� ������ �������� ����!");
                    return true;
                }
                StringBuilder sb = new StringBuilder();
                for (int i = 1; i < args.length; i++) sb.append(args[i]).append(' ');
           	    String rule = sb.toString();
                if (main.SQL.getPlayerStars(p.getName()) == 0) {
                    sender.sendMessage(this.forall + ChatColor.WHITE + "������ ����� �� ������������!");
                    return true;
                }
                if(!cuff.cuffing.contains(p)) {
                	sender.sendMessage(this.forall + ChatColor.WHITE + "�������� ��������� �� ������!");
                	return true;
                }
                if (!(sender instanceof Player)) {
                    sender.sendMessage(new StringBuilder().append(ChatColor.RED).append(ChatColor.BOLD).append("���������").toString());
                    return true;
                }
                final Player vevuha = (Player)sender;
                if (vevuha.getLocation().distance(p.getLocation()) > 5.0) {
                    sender.sendMessage(this.forall + ChatColor.WHITE + "����� ������� ������!");
                    return true;
                }
                if (p.hasPermission("atlantworld.freestars")) {
                    sender.sendMessage(this.forall + ChatColor.WHITE + "����� ������ ������ ��������!");
                    return true;
                }
                final int st = main.SQL.getPlayerStars(args[0]);
				for (final Player peha : Bukkit.getOnlinePlayers()) {
					if (main.SQL.getPlayerRabota(peha.getName()).getFraction().equals(Fraction.POLICE)) {
						peha.sendMessage(nachalo +ChatColor.WHITE+"[�������: ����������] "+work.getName() + " " + sender.getName() + " ���������(�) ����������� " + args[0] + " �� �������: " + rule);
					}
				}
                main.SQL.setInPrison(p.getName(), st*10);
                sender.sendMessage(this.forall + ChatColor.WHITE + "����� ������� � ������.");
                p.teleport(new Location(p.getWorld(), this.plugin.getConfig().getDouble("xpris"), this.plugin.getConfig().getDouble("ypris"), this.plugin.getConfig().getDouble("zpris")));
                p.sendMessage(this.forall + ChatColor.WHITE + "�� �������� � ������ �� " + ChatColor.AQUA + st*10 + ChatColor.WHITE + " ����� �� �������: " + rule + "! ��� ������� ��������.");
                cuff.cuffing.remove(p);
                return true;
            }
        return true;
    }
}
