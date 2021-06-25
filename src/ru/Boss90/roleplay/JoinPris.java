package ru.Boss90.roleplay;

import org.bukkit.plugin.*;
import org.bukkit.event.player.*;
import org.bukkit.*;
import org.bukkit.event.*;

public class JoinPris implements Listener
{
    Plugin plugin;
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public JoinPris() {
        this.plugin = (Plugin)main.getPlugin((Class)main.class);
    }
    
    @EventHandler(priority = EventPriority.LOWEST)
    public void joinsas(final PlayerJoinEvent event) {
        if (!main.SQL.hasValue(event.getPlayer().getName())) {
            main.SQL.writeToRoleplay(event.getPlayer().getName());
        }
        if (main.SQL.getPlayerInPris(event.getPlayer().getName()) && main.SQL.getPlayerTimeinPris(event.getPlayer().getName()) <= 0) {
            main.SQL.vishelPris(event.getPlayer().getName());
            event.getPlayer().sendMessage(ChatColor.GOLD + "Вы успешно вышли из тюрьмы!");
            final Location locs = new Location(event.getPlayer().getWorld(), this.plugin.getConfig().getDouble("xprisafter"), this.plugin.getConfig().getDouble("yprisafter"), this.plugin.getConfig().getDouble("zprisafter"));
            event.getPlayer().teleport(locs);
        }
    }
}
