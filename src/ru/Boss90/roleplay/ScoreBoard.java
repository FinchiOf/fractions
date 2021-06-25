package ru.Boss90.roleplay;

import org.bukkit.entity.*;

import java.text.DecimalFormat;
import java.util.*;
import org.bukkit.scoreboard.*;
import net.milkbowl.vault.economy.*;
import org.bukkit.*;
import org.bukkit.ChatColor;
import org.bukkit.plugin.*;

public class ScoreBoard
{
    
    @SuppressWarnings("unused")
	private static String replaceString(final String str, final Player player) {
    	DecimalFormat format = new DecimalFormat("###,###,###,###,###,###.##");
        return ChatColor.translateAlternateColorCodes('&', str).replace("%player%", player.getName()).replace("%job%", getJob(player)).replace("%stars%", getStars(player)).replace("%players%", String.valueOf(Bukkit.getOnlinePlayers().size())).replace("%education%", getEducation(player)).replace("%money%", String.valueOf(format.format(getMoney(player))).replace(" ", ",")).replace("%last%", last(player));
    }
    
    private static String last(final Player player) {
        return String.valueOf(main.SQL.getPlayerTimeinPris(player.getName()));
    }
    
    private static String getJob(final Player player) {
        return main.SQL.getPlayerRabota(player.getName()).getName();
    }
    
    private static String getEducation(final Player player) {
        return main.SQL.getPlayerEducation(player.getName()).getName();
    }
    
    public static String getStars(final Player player) {
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
    
    public static long getMoney(final Player p) {
        @SuppressWarnings({ "unchecked", "rawtypes" })
		final RegisteredServiceProvider<Economy> rsp = (RegisteredServiceProvider<Economy>)Bukkit.getServicesManager().getRegistration((Class)Economy.class);
        if (rsp == null) {
            return 0;
        }
        final Economy econ = (Economy)rsp.getProvider();
        final long me = (int)econ.getBalance((OfflinePlayer)p);
        return me;
    }
    
    protected static String bld(final String s, final int f) {
        String d = "";
        for (int j = 0; j < f; ++j) {
            d += s;
        }
        return d;
    }
}
