package ru.Boss90.roleplay;

import org.bukkit.plugin.*;
import org.bukkit.command.*;

import ru.Boss90.roleplay.enums.*;
import ru.Boss90.roleplay.interfaces.Work;

import org.bukkit.entity.*;
import org.apache.logging.log4j.core.config.CustomLevels;
import org.bukkit.*;
import java.util.*;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.*;

public class PassportCmd implements CommandExecutor
{
    Plugin plugin;
    String forall;
    public HashMap<String, String> passportreq;
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public PassportCmd() {
        this.plugin = (Plugin)main.getPlugin((Class)main.class);
        this.forall = this.plugin.getConfig().getString("nachalo");
        this.passportreq = new HashMap<String, String>();
    }
    
    public boolean onCommand(final CommandSender sender, final Command cmd, final String commandlabel, final String[] args) {
        if (cmd.getName().equalsIgnoreCase("passport")) {
            this.forall = this.forall.replace("&", "§");
            if (args.length == 0) {
                sender.sendMessage(String.valueOf(this.forall) + ChatColor.WHITE + "/passport [Игрок] - Попросить игрока показать паспорт.");
                sender.sendMessage(String.valueOf(this.forall) + ChatColor.WHITE + "/passport accept - Показать паспорт по запросу.");
                sender.sendMessage(String.valueOf(this.forall) + ChatColor.WHITE + "/passport deny - Отказать в просьбе.");
                if (AccessLevel.contains(main.SQL.getPlayerRabota(sender.getName()).getAccess(), AccessLevel.PASSPORTINFO) || sender.isOp()) {
                    sender.sendMessage(String.valueOf(this.forall) + ChatColor.WHITE + "/passport info [Игрок] - посмотреть паспорт игрока.");
                    return true;
                }
                return true;
            }
            else if (args.length == 1) {
                if (!(sender instanceof Player)) {
                    sender.sendMessage(String.valueOf(this.forall) + ChatColor.WHITE + "ты кто?");
                    return true;
                }
                if (args[0].equalsIgnoreCase("accept")) {
                    if (this.passportreq.get(sender.getName()) == null) {
                        sender.sendMessage(String.valueOf(this.forall) + ChatColor.WHITE + "Нечего принимать!");
                        return true;
                    }
                    final Player target = Bukkit.getPlayer((String)this.passportreq.get(sender.getName()));
                    final Player p = (Player)sender;
                    if (!Bukkit.getOnlinePlayers().contains(target)) {
                        this.passportreq.put(sender.getName(), null);
                        sender.sendMessage(String.valueOf(this.forall) + ChatColor.WHITE + "Игрок, отправляющий вам предложение вышел!");
                        return true;
                    }
                    if (p.getLocation().distance(target.getLocation()) > 5.0) {
                        sender.sendMessage(String.valueOf(this.forall) + ChatColor.WHITE + "Вы слишком далеко! Подойдите поближе.");
                        return true;
                    }
                    this.showPass(target, p);
                    this.passportreq.put(sender.getName(), null);
                    return true;
                }
                else if (args[0].equalsIgnoreCase("deny")) {
                    if (this.passportreq.get(sender.getName()) == null) {
                        sender.sendMessage(String.valueOf(this.forall) + ChatColor.WHITE + "Нечего отклонять!");
                        return true;
                    }
                    final Player target = Bukkit.getPlayer((String)this.passportreq.get(sender.getName()));
                    if (!Bukkit.getOnlinePlayers().contains(target)) {
                        this.passportreq.put(sender.getName(), null);
                        sender.sendMessage(String.valueOf(this.forall) + ChatColor.WHITE + "Вы отклонили предложение!");
                        return true;
                    }
                    this.passportreq.put(sender.getName(), null);
                    sender.sendMessage(String.valueOf(this.forall) + ChatColor.WHITE + "Вы отклонили предложение!");
                    target.sendMessage(String.valueOf(this.forall) + ChatColor.WHITE + "Игрок " + sender.getName() + " отклонил ваше предложение!");
                    return true;
                }
                else {
                    if (sender.getName().equalsIgnoreCase(args[0])) {
                        this.showPass((Player)sender, (Player)sender);
                        return true;
                    }
                    final Player paha = Bukkit.getPlayer(args[0]);
                    if (this.passportreq.get(sender.getName()) != null) {
                        sender.sendMessage(String.valueOf(this.forall) + ChatColor.WHITE + "Дождитесь ответа другого запроса!");
                        return true;
                    }
                    if (this.passportreq.get(this.passportreq.get(sender.getName())) != null) {
                        sender.sendMessage(String.valueOf(this.forall) + ChatColor.WHITE + "Дождитесь ответа другого запроса!");
                        return true;
                    }
                    if (!Bukkit.getOnlinePlayers().contains(paha)) {
                        sender.sendMessage(String.valueOf(this.forall) + ChatColor.WHITE + "Игрока нет на сервере!");
                        return true;
                    }
                    final Player p = (Player)sender;
                    if (p.getLocation().distance(paha.getLocation()) > 5.0) {
                        p.sendMessage(String.valueOf(this.forall) + ChatColor.WHITE + "Игрок слишком далеко!");
                        return true;
                    }
                    p.sendMessage(String.valueOf(this.forall) + ChatColor.WHITE + "Вы отправили запрос игроку " + paha.getName() + ".");
                    paha.sendMessage(String.valueOf(this.forall) + ChatColor.WHITE + "Игрок " + ChatColor.GOLD + p.getName() + ChatColor.WHITE + " хочет посмотреть твой паспорт.");
                    paha.sendMessage("     ");
                    paha.sendMessage(String.valueOf(this.forall) + ChatColor.GREEN + "/passport accept - Согласиться.");
                    paha.sendMessage(String.valueOf(this.forall) + ChatColor.RED + "/passport deny - Отказаться.");
                    paha.sendMessage(String.valueOf(this.forall) + ChatColor.WHITE + "У вас есть 60 секунд чтобы ответить.");
                    this.passportreq.put(paha.getName(), p.getName());
                    Bukkit.getScheduler().runTaskLater(this.plugin, (Runnable)new Runnable() {
                        @Override
                        public void run() {
                            if (PassportCmd.this.passportreq.get(paha.getName()) != null) {
                                if (Bukkit.getOnlinePlayers().contains(paha)) {
                                    paha.sendMessage(String.valueOf(PassportCmd.this.forall) + ChatColor.WHITE + "Вы не ответили на запрос игрока " + p.getName() + " показать ваш паспорт.");
                                }
                                if (Bukkit.getOnlinePlayers().contains(p)) {
                                    p.sendMessage(String.valueOf(PassportCmd.this.forall) + ChatColor.WHITE + "Игрок " + paha.getName() + " не ответил на ваш запрос!");
                                }
                                PassportCmd.this.passportreq.put(paha.getName(), null);
                            }
                        }
                    }, 1200L);
                    return true;
                }
            }
            else if (args.length == 2 && args[0].equalsIgnoreCase("info")) {
                if (!sender.isOp() && !AccessLevel.contains(main.SQL.getPlayerRabota(sender.getName()).getAccess(), AccessLevel.PASSPORTINFO)) {
                    sender.sendMessage(String.valueOf(this.forall) + ChatColor.WHITE + "У вас нет прав!");
                    return true;
                }
                final Player pe = (Player)sender;
                if (!main.SQL.hasValue(args[1])) {
                    sender.sendMessage(String.valueOf(this.forall) + ChatColor.WHITE + "Такого игрока нет!");
                    return true;
                }
                this.showPassinfo(pe, args[1]);
                return true;
            }
        }
        return false;
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public void showPass(final Player p, final Player d) {
        final Inventory i = Bukkit.createInventory((InventoryHolder)null, 9, ChatColor.AQUA + "Паспорт");
        final String work = main.SQL.getPlayerRabota(d.getName()).getName();
        final Work work1 = main.SQL.getPlayerRabota(d.getName());
        final String educ = main.SQL.getPlayerEducation(d.getName()).getName();
        final String dasg = getStars(p);
        final ItemStack steklo = new ItemStack(Material.STAINED_GLASS_PANE, 1);
        final ItemMeta steklometa = steklo.getItemMeta();
        steklometa.setDisplayName(this.plugin.getConfig().getString("projname").replace("&", "§"));
        steklo.setItemMeta(steklometa);
        @SuppressWarnings("deprecation")
		final ItemStack head = new ItemStack(397, 1, (short)3);
        final ItemMeta meta = head.getItemMeta();
        meta.setDisplayName(ChatColor.GOLD + "Паспорт");
        final ArrayList<String> listos = new ArrayList<String>();
        listos.add(ChatColor.GOLD + "Имя: " + ChatColor.WHITE + d.getName());
        listos.add(ChatColor.GOLD + "Должность: " + ChatColor.WHITE + work);
        listos.add(ChatColor.GOLD + "Фракция: " + ChatColor.WHITE + work1.getFraction().getName()); 
        listos.add(ChatColor.GOLD + "Образование: " + ChatColor.WHITE + educ);
        listos.add(ChatColor.GOLD + "Уровень розыска: " + dasg);
        listos.add(ChatColor.GOLD + "Был в тюрьме " + ChatColor.WHITE + main.SQL.getPlayerSroki(d.getName()) + ChatColor.GOLD + " раз.");
        meta.setLore((List)listos);
        head.setItemMeta(meta);
        i.setItem(0, steklo);
        i.setItem(1, steklo);
        i.setItem(2, steklo);
        i.setItem(3, steklo);
        i.setItem(4, head);
        i.setItem(5, steklo);
        i.setItem(6, steklo);
        i.setItem(7, steklo);
        i.setItem(8, steklo);
        p.openInventory(i);
        p.sendMessage(String.valueOf(this.forall) + ChatColor.WHITE + "Паспорт игрока " + d.getName());
        p.sendMessage("    ");
        p.sendMessage(String.valueOf(this.forall) + ChatColor.WHITE + "Имя: " + d.getName());
        p.sendMessage(String.valueOf(this.forall) + ChatColor.WHITE + "Должность: " + ChatColor.WHITE + work);
        p.sendMessage(String.valueOf(this.forall) + ChatColor.WHITE + "Фракция: " + ChatColor.WHITE + work1.getFraction().getName()); 
        p.sendMessage(String.valueOf(this.forall) + ChatColor.WHITE + "Образование: " + educ);
        p.sendMessage(String.valueOf(this.forall) + ChatColor.WHITE + "Уровень розыска: " + dasg);
        p.sendMessage(String.valueOf(this.forall) + ChatColor.WHITE + "Был в тюрьме " + main.SQL.getPlayerSroki(d.getName()) + " раз.");
        p.sendMessage("        ");
        d.sendMessage(String.valueOf(this.forall) + ChatColor.WHITE + "Вы показали свой паспорт игроку " + ChatColor.GOLD + p.getName() + ChatColor.WHITE + ".");
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public void showPassinfo(final Player p, final String dname) {
        final Inventory i = Bukkit.createInventory((InventoryHolder)null, 9, ChatColor.AQUA + "Паспорт");
        final String work = main.SQL.getPlayerRabota(dname).getName();
        final ItemStack steklo = new ItemStack(Material.STAINED_GLASS_PANE, 1);
        final ItemMeta steklometa = steklo.getItemMeta();
        steklometa.setDisplayName(this.plugin.getConfig().getString("projname").replace("&", "§"));
        steklo.setItemMeta(steklometa);
        @SuppressWarnings("deprecation")
		final ItemStack head = new ItemStack(397, 1, (short)3);
        final ItemMeta meta = head.getItemMeta();
        meta.setDisplayName(ChatColor.GOLD + "Паспорт");
        final ArrayList<String> listos = new ArrayList<String>();
        listos.add(ChatColor.GOLD + "Имя: " + ChatColor.WHITE + dname);
        listos.add(ChatColor.GOLD + "Работа: " + ChatColor.WHITE + work);
        listos.add(ChatColor.GOLD + "Был в тюрьме " + ChatColor.WHITE + main.SQL.getPlayerSroki(dname) + ChatColor.GOLD + " раз.");
        meta.setLore((List)listos);
        head.setItemMeta(meta);
        i.setItem(0, steklo);
        i.setItem(1, steklo);
        i.setItem(2, steklo);
        i.setItem(3, steklo);
        i.setItem(4, head);
        i.setItem(5, steklo);
        i.setItem(6, steklo);
        i.setItem(7, steklo);
        i.setItem(8, steklo);
        p.openInventory(i);
        p.sendMessage(String.valueOf(this.forall) + ChatColor.WHITE + "Паспорт игрока " + dname);
        p.sendMessage("    ");
        p.sendMessage(String.valueOf(this.forall) + ChatColor.WHITE + "Имя: " + dname);
        p.sendMessage(String.valueOf(this.forall) + ChatColor.WHITE + "Работа: " + work);
        p.sendMessage(String.valueOf(this.forall) + ChatColor.WHITE + "Был в тюрьме " + main.SQL.getPlayerSroki(dname) + " раз.");
        p.sendMessage("        ");
    }
    protected static String bld(final String s, final int f) {
        String d = "";
        for (int j = 0; j < f; ++j) {
            d += s;
        }
        return d;
    }
    protected final String getStars(Player player) {
        final int st = main.SQL.getPlayerStars(player.getName());
        final String star = main.getInstance().getConfig().getString("starsymbol");
        if (st <= 0) {
            return new StringBuilder().append(ChatColor.GRAY).append(ChatColor.BOLD).append(bld(star, 5)).toString();
        }
        if (st == 1) {
            return new StringBuilder().append(ChatColor.YELLOW).append(ChatColor.BOLD).append(star).append(ChatColor.GRAY).append(ChatColor.BOLD).append(bld(star, 4)).toString();
        }
        if (st == 2) {
            return new StringBuilder().append(ChatColor.YELLOW).append(ChatColor.BOLD).append(bld(star, 2)).append(ChatColor.GRAY).append(ChatColor.BOLD).append(bld(star, 3)).toString();
        }
        if (st == 3) {
            return new StringBuilder().append(ChatColor.YELLOW).append(ChatColor.BOLD).append(bld(star, 3)).append(ChatColor.GRAY).append(ChatColor.BOLD).append(bld(star, 2)).toString();
        }
        if (st == 4) {
            return new StringBuilder().append(ChatColor.YELLOW).append(ChatColor.BOLD).append(bld(star, 4)).append(ChatColor.GRAY).append(ChatColor.BOLD).append(star).toString();
        }
        if (st >= 5) {
            return new StringBuilder().append(ChatColor.YELLOW).append(ChatColor.BOLD).append(bld(star, 5)).toString();
        }
        if (player.hasPermission("MSStudio.freestars")) {
            return new StringBuilder().append(ChatColor.YELLOW).append(ChatColor.BOLD).append(bld(star, 5)).toString();
        }
        return bld(star, 5);
    }
}

