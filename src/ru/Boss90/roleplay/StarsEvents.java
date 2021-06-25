package ru.Boss90.roleplay;

import org.bukkit.plugin.*;
import org.bukkit.event.entity.*;
import org.bukkit.entity.*;
import org.bukkit.*;
import org.bukkit.ChatColor;
import java.util.*;
import org.bukkit.event.*;

public class StarsEvents implements Listener
{
    Plugin plugin;
    String forall;
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public StarsEvents() {
        this.plugin = (Plugin)main.getPlugin((Class)main.class);
        this.forall = this.plugin.getConfig().getString("nachalo").replace("&", "§");
    }
    
    @EventHandler
    public void dea(final PlayerDeathEvent event) {
        if (event.getEntity() instanceof Player) {
            final Player p = event.getEntity();
            if (event.getEntity().getKiller() != null) {
                final Player killer = event.getEntity().getKiller();
                for (final Player pa : Bukkit.getOnlinePlayers()) {
                    if (pa.getName().equalsIgnoreCase(p.getName())) {
                        continue;
                    }
                    if (pa.getLocation().distance(killer.getLocation()) > 20.0) {
                        continue;
                    }
                    final Random rand = new Random();
                    final int daha = rand.nextInt(2);
                    if (killer.hasPermission("atlantworld.freestars")) {
                        return;
                    }
                    if (daha == 1) {
                        killer.sendMessage(this.forall + ChatColor.WHITE + "Вы убили игрока " + p.getName() + " и получили за это " + ChatColor.AQUA + "+1" + ChatColor.WHITE + " звезду!");
                        main.SQL.plusstars(killer.getName(), 1);
                    }
                }
            }
        }
    }
}
