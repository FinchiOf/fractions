package ru.Boss90.roleplay;

import org.bukkit.plugin.*;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class TownMoneyApi
{
    static Plugin plugin;
    
    public static void deposite(final int d) {
        final int a = TownMoneyApi.plugin.getConfig().getInt("townmoney");
        final int f = a + d;
        TownMoneyApi.plugin.getConfig().set("townmoney", (Object)f);
        TownMoneyApi.plugin.saveConfig();
    }
    
    public static void withdraw(final int d) {
        final int a = TownMoneyApi.plugin.getConfig().getInt("townmoney");
        final int f = a - d;
        TownMoneyApi.plugin.getConfig().set("townmoney", (Object)f);
        TownMoneyApi.plugin.saveConfig();
    }
    
    public static void set(final int d) {
        TownMoneyApi.plugin.getConfig().set("townmoney", (Object)d);
        TownMoneyApi.plugin.saveConfig();
    }
    
    public static int getMoney() {
        return TownMoneyApi.plugin.getConfig().getInt("townmoney");
    }
    
    static {
        TownMoneyApi.plugin = (Plugin)main.getPlugin((Class)main.class);
    }
}
