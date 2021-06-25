package ru.Boss90.roleplay;

import java.util.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;
import org.bukkit.*;
import org.bukkit.ChatColor;
import org.bukkit.plugin.*;

import ru.Boss90.roleplay.enums.*;
import ru.Boss90.roleplay.interfaces.*;

public class HPcmdHandler implements CommandExecutor
{
    main plugin;
    String forall;
    HashMap<String, String> d;
    HashMap<String, Double> d1;
    
    public HPcmdHandler(final main m) {
        this.plugin = m;
        this.forall = this.plugin.getConfig().getString("nachalo").replace("&", "§");
        this.d = new HashMap<String, String>();
        this.d1 = new HashMap<String, Double>();
    }
    
    @SuppressWarnings("deprecation")
	public boolean onCommand(final CommandSender sender, final Command cmd, final String l, final String[] args) {
        if (cmd.getName().equalsIgnoreCase("hp") && sender instanceof Player) {
            final Player pe = (Player)sender;
            if (args.length == 0) {
                sender.sendMessage(this.forall + ChatColor.WHITE + "/hp accept - Согласиться на запрос вылечиться");
                sender.sendMessage(this.forall + ChatColor.WHITE + "/hp deny - Отказаться от запроса");
                final Work work = main.SQL.getPlayerRabota(sender.getName());
                if (AccessLevel.contains(work.getAccess(), AccessLevel.HEALPLAYERS) || sender.isOp()) {
                    sender.sendMessage(this.forall + ChatColor.WHITE + "/hp [Игрок] - Вылечить игрока");
                }
                return true;
            }
            if (args.length == 2 || args.length == 1) {
                if (args[0].equalsIgnoreCase("accept")) {
                    if (this.d.get(sender.getName()) == null) {
                        sender.sendMessage(this.forall + ChatColor.WHITE + "У вас нет ожидающих запросов!");
                        return true;
                    }
                    if (Bukkit.getPlayer((String)this.d.get(sender.getName())) == null) {
                        sender.sendMessage(this.forall + ChatColor.WHITE + "Игрок, отправляющий вам предложение - вышел!");
                        return true;
                    }
                    final Player ddd = Bukkit.getPlayer((String)this.d.get(sender.getName()));
                    final double summ = this.d1.get(sender.getName());
                    if (VaultManager.getmoney(pe) < summ) {
                        this.d.put(sender.getName(), null);
                        this.d1.put(sender.getName(), null);
                        sender.sendMessage(this.forall + ChatColor.WHITE + "Недостаточно денег на балансе!");
                        ddd.sendMessage(this.forall + ChatColor.WHITE + "У игрока " + sender.getName() + " недостаточно денег для оплаты!");
                        return true;
                    }
                    VaultManager.deposite(ddd, (int)summ);
                    VaultManager.withdraw(pe, (int)summ);
                    pe.setHealth(pe.getMaxHealth());
                    this.d.put(pe.getName(), null);
                    this.d1.put(pe.getName(), null);
                    ddd.sendMessage(this.forall + ChatColor.WHITE + "Вы вылечили игрока " + sender.getName() + " за " + summ + "$.");
                    sender.sendMessage(this.forall + ChatColor.WHITE + "Вы успешно вылечились за " + summ + "$.");
                    return true;
                }
                else if (args[0].equalsIgnoreCase("deny")) {
                    if (this.d.get(sender.getName()) != null) {
                        if (Bukkit.getPlayer((String)this.d.get(sender.getName())) != null) {
                            Bukkit.getPlayer((String)this.d.get(sender.getName())).sendMessage(this.forall + ChatColor.WHITE + "Игрок " + sender.getName() + " отклонил ваше предложение.");
                        }
                        this.d.put(pe.getName(), null);
                        this.d1.put(pe.getName(), null);
                        sender.sendMessage(this.forall + ChatColor.WHITE + "Вы отклонили предложение игрока.");
                        return true;
                    }
                    sender.sendMessage(this.forall + ChatColor.WHITE + "У вас нет ожидающих запросов!");
                    return true;
                }else {
                	
                    final Work work = main.SQL.getPlayerRabota(sender.getName());
                    if (!AccessLevel.contains(work.getAccess(), AccessLevel.HEALPLAYERS) && !sender.isOp()) {
                        sender.sendMessage(this.forall + ChatColor.WHITE + "Нет прав!");
                        return true;
                    }
                    final String k = args[0];
                    if (k == null) {
                        sender.sendMessage(this.forall + ChatColor.WHITE + "Игрок оффлайн!");
                        return true;
                    }
                    final Player p = Bukkit.getPlayer(k);
                    if (pe.getLocation().distance(p.getLocation()) > 8.0D) {
                        sender.sendMessage(this.forall + ChatColor.WHITE + "Вы слишком далеко!");
                        return true;
                    }
                    if (p.getHealth() == p.getMaxHealth()) {
                        sender.sendMessage(this.forall + ChatColor.WHITE + "У игрока нет проблем!");
                        return true;
                    }
                    if(args[1] == null) {
                    	sender.sendMessage(this.forall + ChatColor.WHITE + "Введите цену ваших услуг!");
                    	return true;
                    }
                    final double pr = Integer.valueOf(args[1]);
                    p.sendMessage(ChatColor.WHITE + "Доктор " + ChatColor.AQUA + sender.getName() + ChatColor.WHITE + " предлагает вылечить вас. Цена: " + ChatColor.GREEN + pr + "$");
                    p.sendMessage("                 ");
                    p.sendMessage(ChatColor.GREEN + "/hp accept - Согласиться");
                    p.sendMessage(ChatColor.RED + "/hp deny - Отказаться");
                    p.sendMessage(ChatColor.WHITE + "У вас есть 60 секунд на ответ.");
                    this.d.put(p.getName(), sender.getName());
                    this.d1.put(p.getName(), pr);
                    final String n = p.getName();
                    final String n2 = sender.getName();
                    Bukkit.getScheduler().runTaskLater((Plugin)this.plugin, (Runnable)new Runnable() {
                        @Override
                        public void run() {
                            if (HPcmdHandler.this.d.get(n) != null) {
                                HPcmdHandler.this.d.put(n, null);
                                HPcmdHandler.this.d1.put(n, null);
                                if (Bukkit.getPlayer(n) != null) {
                                    Bukkit.getPlayer(n).sendMessage(ChatColor.RED + "Вы отказались от предложения игрока " + n2 + ".");
                                }
                                if (Bukkit.getPlayer(n2) != null) {
                                    Bukkit.getPlayer(n2).sendMessage(ChatColor.RED + "Игрок " + n + " отказался от вашего предложения!");
                                }
                            }
                        }
                    }, 1200L);
                    return true;
                }
            }
        }
        return true;
    }
}
