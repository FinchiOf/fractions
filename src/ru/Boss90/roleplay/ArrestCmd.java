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
        this.nachalo = new StringBuilder().append(ChatColor.AQUA).append(ChatColor.BOLD).append("Работы > ").toString();
    }
    
    public boolean onCommand(final CommandSender sender, final Command cmd, final String commandlabel, final String[] args) {
        if (cmd.getName().equalsIgnoreCase("arrest")) {
            this.forall = this.forall.replace("&", "§");
            final Player pe = (Player)sender;
    		final Work work = main.SQL.getPlayerRabota(sender.getName());
    		if(work.getFraction().getName() != "Полиция") {
    			sender.sendMessage(this.forall + ChatColor.WHITE+"Вы не работаете в полиции!");
    			return true;
    		}
			if (work.getNumber() < 3) {
                sender.sendMessage(this.forall + ChatColor.WHITE + "Арестовывать можно с сержанта(3).");
                return true;
			}
            if (args.length == 0) {
                sender.sendMessage(this.forall + ChatColor.WHITE + "/arrest [Ник] [Причина] - Посадить в тюрьму");
                return true;
            }
                final Player p = Bukkit.getPlayer(args[0]);
                if (p == null) {
                    sender.sendMessage(this.forall + ChatColor.WHITE + "Игрока нет на сервере.");
                    return true;
                }
                if (p.getName().equalsIgnoreCase(pe.getName())) {
                    pe.sendMessage(this.forall + ChatColor.WHITE + "Вы не можете посадить себя!");
                    return true;
                }
                StringBuilder sb = new StringBuilder();
                for (int i = 1; i < args.length; i++) sb.append(args[i]).append(' ');
           	    String rule = sb.toString();
                if (main.SQL.getPlayerStars(p.getName()) == 0) {
                    sender.sendMessage(this.forall + ChatColor.WHITE + "Данный игрок не преследуется!");
                    return true;
                }
                if(!cuff.cuffing.contains(p)) {
                	sender.sendMessage(this.forall + ChatColor.WHITE + "Наденьте наручники на игрока!");
                	return true;
                }
                if (!(sender instanceof Player)) {
                    sender.sendMessage(new StringBuilder().append(ChatColor.RED).append(ChatColor.BOLD).append("КУКАРЯМБА").toString());
                    return true;
                }
                final Player vevuha = (Player)sender;
                if (vevuha.getLocation().distance(p.getLocation()) > 5.0) {
                    sender.sendMessage(this.forall + ChatColor.WHITE + "Игрок слишком далеко!");
                    return true;
                }
                if (p.hasPermission("atlantworld.freestars")) {
                    sender.sendMessage(this.forall + ChatColor.WHITE + "Этого игрока нельзя посадить!");
                    return true;
                }
                final int st = main.SQL.getPlayerStars(args[0]);
				for (final Player peha : Bukkit.getOnlinePlayers()) {
					if (main.SQL.getPlayerRabota(peha.getName()).getFraction().equals(Fraction.POLICE)) {
						peha.sendMessage(nachalo +ChatColor.WHITE+"[Полиция: Информация] "+work.getName() + " " + sender.getName() + " арестовал(а) преступника " + args[0] + " по причине: " + rule);
					}
				}
                main.SQL.setInPrison(p.getName(), st*10);
                sender.sendMessage(this.forall + ChatColor.WHITE + "Игрок посажен в тюрьму.");
                p.teleport(new Location(p.getWorld(), this.plugin.getConfig().getDouble("xpris"), this.plugin.getConfig().getDouble("ypris"), this.plugin.getConfig().getDouble("zpris")));
                p.sendMessage(this.forall + ChatColor.WHITE + "Вы посажены в тюрьму на " + ChatColor.AQUA + st*10 + ChatColor.WHITE + " минут по причине: " + rule + "! Ваш телефон отключён.");
                cuff.cuffing.remove(p);
                return true;
            }
        return true;
    }
}
