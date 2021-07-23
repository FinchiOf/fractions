package ru.Boss90.roleplay;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

public class BookPasport implements CommandExecutor {
	
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String strl, String[] args) {
        Player p = (Player)sender;
        Player playerok = (Player)sender;
        WorldGuardPlugin worldGuard = (WorldGuardPlugin)Bukkit.getPluginManager().getPlugin("WorldGuard");
        ApplicableRegionSet set = worldGuard.getRegionManager(playerok.getWorld()).getApplicableRegions(playerok.getLocation());
        ProtectedRegion reg = worldGuard.getRegionManager(playerok.getWorld()).getRegion(main.plugin.getConfig().getString("Workregion"));
        if (!set.getRegions().contains(reg)) {
            playerok.sendMessage(CommandWork.nachalo + ChatColor.WHITE + "Чтобы раздавать паспорт вы должны находится в мэрии!");
            return true;
        } else if (!main.SQL.getPlayerRabota(p.getName()).getFraction().getName().contains("Мэрия")) {
            p.sendMessage(CommandWork.nachalo + ChatColor.WHITE + "Вы не работаете в мэрии!");
            return true;
        } else if (main.SQL.getPlayerRabota(p.getName()).getNumber() < 4) {
            p.sendMessage(CommandWork.nachalo + ChatColor.WHITE + "Отказ в доступе.");
            return true;
        } else if (args.length == 0) {
            p.sendMessage(CommandWork.nachalo + ChatColor.WHITE + "Введите кому хотите дать паспорт!");
            return true;
        } else if (Bukkit.getPlayer(args[0]) == null) {
            p.sendMessage(CommandWork.nachalo + ChatColor.WHITE + "В данный момент игрок не в сети.");
            return true;
        } else if (VaultManager.getmoney(Bukkit.getPlayer(args[0])) < 500) {
            sender.sendMessage(CommandWork.nachalo + ChatColor.WHITE+"У игрока недостаточно денег на счету");
            return true;
        }
            Player pe = Bukkit.getPlayer(args[0]);
            if (p.getLocation().distance(pe.getLocation()) > 5.0D) {
                sender.sendMessage(CommandWork.nachalo + ChatColor.RED + "Вы слишком далеко!");
                return true;
            }
            VaultManager.deposite(p, 200);
            VaultManager.withdraw(Bukkit.getPlayer(args[0]), 500);
            TownMoneyApi.deposite(300);
            ItemStack book = new ItemStack(Material.WRITTEN_BOOK, 1);
            book.getItemMeta().setDisplayName(ChatColor.GOLD + "Паспорт " + ChatColor.YELLOW + args[0]);
            BookMeta bm = (BookMeta)book.getItemMeta();
            SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd");
            bm.setAuthor("Мэрия | Дата выдачи: " + format.format(Calendar.getInstance().getTime()));
            bm.setTitle(ChatColor.GOLD + "Паспорт " + ChatColor.YELLOW + args[0]);
            ArrayList<String> pages = new ArrayList<>();
            pages.add("•———————————————————————• \n \n Гражданин: " + pe.getName() + " \n Фракция: " + main.SQL.getPlayerRabota(pe.getName()).getFraction().getName() + "\n Звания: " + main.SQL.getPlayerRabota(pe.getName()).getName() + "\n \n Был в тюрьме: " + main.SQL.getPlayerSroki(pe.getName()) + " раз \n \n Пол: " + main.SQL.getGender(pe.getName()).getName() + " \n Образование: " + main.SQL.getPlayerEducation(pe.getName()).getName() + " \n Мед.карта: " + main.SQL.getMedCart(pe.getName()).getName() + " \n \n •———————————————————————•");
            sender.sendMessage(ChatColor.GRAY + p.getName() + ": минуточку, собираю данные о вас..");
            sender.sendMessage(ChatColor.GRAY + p.getName() + ": ваша роспись пожалуйста..");
            for(Player player2 : Bukkit.getOnlinePlayers()) {
                if (p.getLocation().distance(player2.getLocation()) <= 15.0D) {
                    player2.sendMessage(ChatColor.translateAlternateColorCodes('&', "&5* " + args[0] + " поставил роспись на паспорте."));
                }

            sender.sendMessage(ChatColor.GRAY + p.getName() + ": Ваш паспорт оформлен и является официальным! Держите.");
                if (p.getLocation().distance(player2.getLocation()) <= 15.0D) {
                    player2.sendMessage(ChatColor.translateAlternateColorCodes('&', "&5* " + p.getName() + " передал паспорт гражданину " + p.getName()));
                    player2.sendMessage(ChatColor.translateAlternateColorCodes('&', "&5* " + args[0] + " взял паспорт у паспортёра"));
                }

            bm.setPages(pages);
            book.setItemMeta(bm);
            Bukkit.getPlayer(args[0]).getInventory().addItem(book);
            return true;
        }
    		return true;
    }
}
