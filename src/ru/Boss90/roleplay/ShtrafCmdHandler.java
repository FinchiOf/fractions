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
                sender.sendMessage(ChatColor.WHITE + "[" + ChatColor.AQUA + "i" + ChatColor.WHITE + "] /shtraf [Игрок] [Сумма от " + this.min + " до " + this.max + "] [Причина] - Оштрафовать игрока");
                return true;
            }
                final String kogo = args[0];
                StringBuilder sb = new StringBuilder();

                for(int i = 2; i < args.length; ++i) {
                    sb.append(args[i]).append(' ');
                }
                final String reason = sb.toString();
                int s = 0;
                if (Bukkit.getPlayer(kogo) == null) {
                    sender.sendMessage(ChatColor.WHITE + "[" + ChatColor.AQUA + "i" + ChatColor.WHITE + "] Игрок оффлайн!");
                    return true;
                }
                final Player g = Bukkit.getPlayer(kogo);
                final Player p = (Player)sender;
                
                if (p.getLocation().distance(g.getLocation()) > 8.0) {
                    sender.sendMessage(ChatColor.WHITE + "[" + ChatColor.AQUA + "i" + ChatColor.WHITE + "] Вы слишком далеко от игрока!");
                    return true;
                }
                try {
                    s = Integer.valueOf(args[1]);
                    if (s < this.min || s > this.max) {
                        sender.sendMessage(ChatColor.WHITE + "[" + ChatColor.AQUA + "i" + ChatColor.WHITE + "] Укажите число от " + this.min + " до " + this.max + "!");
                        return true;
                    }
                }
                catch (NumberFormatException e) {
                    sender.sendMessage(ChatColor.WHITE + "[" + ChatColor.AQUA + "i" + ChatColor.WHITE + "] Укажите верные числа!");
                    return true;
                }
                if (VaultManager.getmoney(g) < s) {
                    sender.sendMessage(ChatColor.WHITE + "[" + ChatColor.AQUA + "i" + ChatColor.WHITE + "] У игрока недостаточно денег");
                    return true;
                }
                main.SQL.setstraf(sender.getName(), main.SQL.getStraf(sender.getName())+Integer.valueOf(args[1]));
                g.sendMessage(ChatColor.WHITE + "[" + ChatColor.AQUA + "i" + ChatColor.WHITE + "] Вы были оштрафованы игроком " + p.getName() + " на " + ChatColor.AQUA + s + "$" + ChatColor.WHITE + ". Причина: " + ChatColor.AQUA + reason);
                p.sendMessage(ChatColor.WHITE + "[" + ChatColor.AQUA + "i" + ChatColor.WHITE + "] Вы оштрафовали игрока " + g.getName() + " на " + s + "$.");
                return true;
            }
        if (cmd.getName().equalsIgnoreCase("shtrafpay")){
        	if(VaultManager.getmoney(((Player)sender)) <  main.SQL.getStraf(sender.getName())) {
            	sender.sendMessage(ChatColor.WHITE + "[" + ChatColor.AQUA + "i" + ChatColor.WHITE + "] Недостаточно средств.");
            	return true;
        	}
        	if(main.SQL.getStraf(sender.getName()) == 0) {
        		sender.sendMessage(ChatColor.WHITE + "[" + ChatColor.AQUA + "i" + ChatColor.WHITE + "] На данный момент у вас не имеются активные штрафы.");
        		return true;
        	}
        	Player p  = (Player) sender;
        	VaultManager.withdraw(p, main.SQL.getStraf(p.getName()));
        	main.SQL.setstraf(p.getName(), 0);
            TownMoneyApi.deposite(main.SQL.getStraf(p.getName()));
        	sender.sendMessage(ChatColor.WHITE + "[" + ChatColor.AQUA + "i" + ChatColor.WHITE + "] Вы оплатили все штрафы.");
        	return true;
        }
        if (cmd.getName().equalsIgnoreCase("shtrafinfo")){
        	if(args.length == 0) {
            	if(main.SQL.getStraf(sender.getName()) >= 1) {
                	sender.sendMessage(ChatColor.WHITE + "[" + ChatColor.AQUA + "i" + ChatColor.WHITE + "] Вам осталось выплатить штраф в размере: " + main.SQL.getStraf(sender.getName()));
                	return true;
            	}
            	if(main.SQL.getStraf(sender.getName()) == 0) {
                	sender.sendMessage(ChatColor.WHITE + "[" + ChatColor.AQUA + "i" + ChatColor.WHITE + "] У вас нету штрафов.");	
                	return true;
            	}
        	}
        	if(args.length == 1) {
        		if(args[0].equals(sender.getName())) {
                	if(main.SQL.getStraf(sender.getName()) >= 1) {
                    	sender.sendMessage(ChatColor.WHITE + "[" + ChatColor.AQUA + "i" + ChatColor.WHITE + "] Вам осталось выплатить штраф в размере: " + main.SQL.getStraf(sender.getName()));
                    	return true;
                	}
                	if(main.SQL.getStraf(sender.getName()) == 0) {
                    	sender.sendMessage(ChatColor.WHITE + "[" + ChatColor.AQUA + "i" + ChatColor.WHITE + "] У вас нету штрафов.");	
                    	return true;
                	}
        		}
        		if(main.SQL.getPlayerRabota(sender.getName()).getFraction() != Fraction.POLICE) {
        			sender.sendMessage(ChatColor.WHITE + "[" + ChatColor.AQUA + "i" + ChatColor.WHITE + "] Вы не работаете в полиции!");
        			return true;
        		}
        		if(main.SQL.getPlayerRabota(sender.getName()).getNumber() < 2) {
        			sender.sendMessage(ChatColor.WHITE + "[" + ChatColor.AQUA + "i" + ChatColor.WHITE + "] Недостаточно прав!");
        			return true;
        		}
            	if(main.SQL.getStraf(args[0]) >= 1) {
                	sender.sendMessage(ChatColor.WHITE + "[" + ChatColor.AQUA + "i" + ChatColor.WHITE + "] Гражданину "+ args[0] + " осталось выплатить штраф в размере: " + main.SQL.getStraf(sender.getName()));
                	return true;
            	}
            	if(main.SQL.getStraf(args[0]) == 0) {
                	sender.sendMessage(ChatColor.WHITE + "[" + ChatColor.AQUA + "i" + ChatColor.WHITE + "] У гражданина " + args[0] + " нету штрафов.");	
                	return true;
            	}
        	}
        }
        return true;
    }
}
