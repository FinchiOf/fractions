package ru.Boss90.roleplay;

import java.util.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;

import net.milkbowl.vault.economy.*;
import ru.Boss90.roleplay.enums.*;

import org.bukkit.*;
import org.bukkit.ChatColor;
import org.bukkit.plugin.*;

public class UnArrestCmd implements CommandExecutor
{
    Plugin plugin;
    String forall;
    public HashMap<String, String> request;
    public HashMap<String, Integer> requestprice;
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public UnArrestCmd() {
        this.plugin = (Plugin)main.getPlugin((Class)main.class);
        this.forall = this.plugin.getConfig().getString("nachalo");
        this.request = new HashMap<String, String>();
        this.requestprice = new HashMap<String, Integer>();
    }
    
    public boolean onCommand(final CommandSender sender, final Command cmd, final String commandlabel, final String[] args) {
        if (cmd.getName().equalsIgnoreCase("unarrest")) {
            this.forall = this.forall.replace("&", "§");
            if (!(sender instanceof Player)) {
                sender.sendMessage(this.forall + ChatColor.WHITE + "Ты кто?");
                return true;
            }
            final Player p = (Player)sender;
            if (args.length == 0) {
                if (AccessLevel.contains(main.SQL.getPlayerRabota(p.getName()).getAccess(), AccessLevel.UNARREST) || p.isOp() || p.hasPermission("atlantworld.unarrest")) {
                    sender.sendMessage(this.forall + ChatColor.WHITE + "/unarrest [Ник] - отправить тюремщику запрос об освобождении за деньги.");
                    return true;
                }
                sender.sendMessage(this.forall + ChatColor.WHITE + "Вы не можете это сделать!");
                return true;
            }
            else if (args.length == 1) {
                if (args[0].equalsIgnoreCase("accept")) {
                    if (this.request.get(sender.getName()) == null) {
                        p.sendMessage(this.forall + ChatColor.WHITE + "Нечего принимать!");
                        return true;
                    }
                    final Player pa = Bukkit.getPlayer((String)this.request.get(sender.getName()));
                    if (!Bukkit.getOnlinePlayers().contains(pa)) {
                        p.sendMessage(this.forall + ChatColor.WHITE + "Игрок " + pa.getName() + " вышел из игры. Процедура невозможна.");
                        this.request.put(sender.getName(), null);
                        this.requestprice.put(sender.getName(), null);
                        return true;
                    }
                    if (p.getLocation().distance(pa.getLocation()) > 10.0) {
                        sender.sendMessage(this.forall + ChatColor.WHITE + "Вы слишком далеко! Подойдите поближе.");
                        return true;
                    }
                    final int price = this.requestprice.get(sender.getName());
                    if (this.getmoney(p) < price) {
                        p.sendMessage(this.forall + ChatColor.WHITE + "Недостаточно средств на счету! Должно быть больше " + price + "!");
                        pa.sendMessage(this.forall + ChatColor.WHITE + "У игрока недостаточно средств на счету.");
                        this.request.put(sender.getName(), null);
                        this.requestprice.put(sender.getName(), null);
                        return true;
                    }
                    this.withdraw(p, price);
                    TownMoneyApi.deposite(price);
                    main.SQL.vishelPris(p.getName());
                    this.request.put(sender.getName(), null);
                    this.requestprice.put(sender.getName(), null);
                    sender.sendMessage(this.forall + ChatColor.WHITE + "Вы успешно были выпущены из тюрьмы за " + price + "$. Осталось: " + this.getmoney(p) + "$.");
                    pa.sendMessage(this.forall + ChatColor.WHITE + "Игрок " + p.getName() + " был выпущен из тюрьмы. Начислено: " + price + "$. Баланс: " + this.getmoney(pa) + "$.");
                    return true;
                }
                else if (args[0].equalsIgnoreCase("deny")) {
                    if (this.request.get(sender.getName()) == null) {
                        p.sendMessage(this.forall + ChatColor.WHITE + "Нечего отклонять!");
                        return true;
                    }
                    final Player target = Bukkit.getPlayer((String)this.request.get(sender.getName()));
                    if (!Bukkit.getOnlinePlayers().contains(target)) {
                        this.request.put(sender.getName(), null);
                        this.requestprice.put(sender.getName(), null);
                        p.sendMessage(this.forall + ChatColor.WHITE + "Вы отклонили предложение.");
                        return true;
                    }
                    this.request.put(sender.getName(), null);
                    this.requestprice.put(sender.getName(), null);
                    p.sendMessage(this.forall + ChatColor.WHITE + "Вы отклонили предложение.");
                    target.sendMessage(this.forall + ChatColor.WHITE + "Игрок " + sender.getName() + " отклонил ваше предложение!");
                    return true;
                }
                else {
                    if (!AccessLevel.contains(main.SQL.getPlayerRabota(p.getName()).getAccess(), AccessLevel.UNARREST) && !p.isOp() && !p.hasPermission("atlantworld.unarrest")) {
                        sender.sendMessage(this.forall + ChatColor.WHITE + "Вы не можете это сделать!");
                        return true;
                    }
                    final Player pa = Bukkit.getPlayer(args[0]);
                    if (!Bukkit.getOnlinePlayers().contains(pa)) {
                        p.sendMessage(this.forall + ChatColor.WHITE + "Данного игрока нет на сервере!");
                        return true;
                    }
                    if (pa.getName().equalsIgnoreCase(p.getName())) {
                        sender.sendMessage(this.forall + ChatColor.WHITE + "Вы не можете освободить себя!");
                        return true;
                    }
                    if (p.isOp() || p.hasPermission("atlantworld.unarrest")) {
                        if (main.SQL.getPlayerInPris(pa.getName())) {
                            main.SQL.vishelPris(pa.getName());
                            pa.sendMessage(this.forall + ChatColor.WHITE + "Вас бесплатно освободил игрок " + p.getName() + "!");
                            p.sendMessage(this.forall + ChatColor.WHITE + "Игрок " + pa.getName() + " успешно освобожден!");
                            return true;
                        }
                        p.sendMessage(this.forall + ChatColor.WHITE + "Игрок не в тюрьме!");
                        return true;
                    }
                    else if (AccessLevel.contains(main.SQL.getPlayerRabota(p.getName()).getAccess(), AccessLevel.UNARREST)) {
                        if (this.request.containsKey(p.getName()) && this.request.get(p.getName()) != null) {
                            p.sendMessage(this.forall + ChatColor.WHITE + "Дождитесь ответа на прошлый запрос!");
                            return true;
                        }
                        if (!main.SQL.getPlayerInPris(pa.getName())) {
                            p.sendMessage(this.forall + ChatColor.WHITE + "Игрок не в тюрьме!");
                            return true;
                        }
                        if (p.getLocation().distance(pa.getLocation()) <= 10.0) {
                            final int cost = 100 * main.SQL.getPlayerTimeinPris(pa.getName());
                            p.sendMessage(this.forall + ChatColor.WHITE + "Ваш запрос отправлен игроку " + pa.getName() + ".");
                            pa.sendMessage(this.forall + ChatColor.WHITE + "Адвокат " + p.getName() + " предлагает вам выйти из тюрьмы.");
                            pa.sendMessage("   ");
                            pa.sendMessage(this.forall + ChatColor.WHITE + "Вы должны будете заплатить " + cost + "$.");
                            pa.sendMessage(this.forall + ChatColor.GREEN + "/unarrest accept - согласиться");
                            pa.sendMessage(this.forall + ChatColor.RED + "/unarrest deny - отказаться");
                            pa.sendMessage(this.forall + ChatColor.WHITE + "Через 60 секунд запрос будет автоматически отклонен!");
                            this.request.put(pa.getName(), p.getName());
                            this.requestprice.put(pa.getName(), cost);
                            Bukkit.getScheduler().runTaskLater(this.plugin, (Runnable)new Runnable() {
                                @Override
                                public void run() {
                                    if (UnArrestCmd.this.request.get(pa.getName()) != null) {
                                        UnArrestCmd.this.request.put(pa.getName(), null);
                                        UnArrestCmd.this.requestprice.put(pa.getName(), null);
                                        if (Bukkit.getOnlinePlayers().contains(p)) {
                                            p.sendMessage(UnArrestCmd.this.forall + ChatColor.WHITE + "Игрок не успел ответить на ваш запрос, он был автоматически отклонен.");
                                        }
                                        if (Bukkit.getOnlinePlayers().contains(pa)) {
                                            pa.sendMessage(UnArrestCmd.this.forall + ChatColor.WHITE + "Время истекло. Вы отклонили предложение игрока " + p.getName() + ".");
                                        }
                                    }
                                }
                            }, 1200L);
                            return true;
                        }
                        p.sendMessage(this.forall + ChatColor.WHITE + "Игрок слишком далеко!");
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    public double getmoney(final Player p) {
    	@SuppressWarnings({ "unchecked", "rawtypes" })
        final RegisteredServiceProvider<Economy> rsp = (RegisteredServiceProvider<Economy>)Bukkit.getServicesManager().getRegistration((Class)Economy.class);
        if (rsp == null) {
            return 0.0;
        }
        final Economy econ = (Economy)rsp.getProvider();
        return econ.getBalance((OfflinePlayer)p);
    }
    
    public void withdraw(final Player p, final int money) {
    	@SuppressWarnings({ "unchecked", "rawtypes" })
        final RegisteredServiceProvider<Economy> rsp = (RegisteredServiceProvider<Economy>)Bukkit.getServicesManager().getRegistration((Class)Economy.class);
        if (rsp == null) {
            return;
        }
        final Economy econ = (Economy)rsp.getProvider();
        econ.withdrawPlayer((OfflinePlayer)p, (double)money);
    }
    
    public void deposite(final Player p, final int money) {
    	@SuppressWarnings({ "unchecked", "rawtypes" })
        final RegisteredServiceProvider<Economy> rsp = (RegisteredServiceProvider<Economy>)Bukkit.getServicesManager().getRegistration((Class)Economy.class);
        if (rsp == null) {
            return;
        }
        final Economy econ = (Economy)rsp.getProvider();
        econ.depositPlayer((OfflinePlayer)p, (double)money);
    }
}
