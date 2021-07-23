package ru.Boss90.roleplay;

import org.bukkit.entity.*;
import net.milkbowl.vault.economy.*;
import org.bukkit.*;
import org.bukkit.plugin.*;

public class VaultManager
{
	private static Economy e;
	public static void init() {
		RegisteredServiceProvider<Economy> reg = Bukkit.getServicesManager().getRegistration(Economy.class);
		if (reg != null) e = reg.getProvider();
	}
	public static boolean withdraw(final Player p, final int amount) {
		if(e == null) return false;
		
		if(e.getBalance(p) < amount) return false;
		return e.withdrawPlayer(p, amount).transactionSuccess();
    }
    
    @SuppressWarnings("unchecked")
	public static void deposite(final Player p, final int summ) {
        @SuppressWarnings("rawtypes")
		final RegisteredServiceProvider<Economy> rsp = (RegisteredServiceProvider<Economy>)Bukkit.getServicesManager().getRegistration((Class)Economy.class);
        if (rsp == null) {
            return;
        }
        final Economy econ = (Economy)rsp.getProvider();
        econ.depositPlayer((OfflinePlayer)p, (double)summ);
    }
    
    @SuppressWarnings("unchecked")
	public static double getmoney(final Player p) {
        @SuppressWarnings("rawtypes")
		final RegisteredServiceProvider<Economy> rsp = (RegisteredServiceProvider<Economy>)Bukkit.getServicesManager().getRegistration((Class)Economy.class);
        if (rsp == null) {
            return 0.0;
        }
        final Economy econ = (Economy)rsp.getProvider();
        return econ.getBalance((OfflinePlayer)p);
    }
}
