package ru.Boss90.roleplay;

import org.bukkit.plugin.*;
import java.sql.*;
import org.bukkit.event.*;
import org.bukkit.event.block.*;
import org.bukkit.event.entity.*;
import org.bukkit.entity.*;
import org.bukkit.*;
import org.bukkit.ChatColor;
import org.bukkit.event.player.*;

public class ArrestEvents implements Listener
{
    Plugin plugin;
    String forall;
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public ArrestEvents() {
        this.plugin = (Plugin)main.getPlugin((Class)main.class);
        this.forall = this.plugin.getConfig().getString("nachalo");
        this.forall = this.forall.replace("&", "§");
    }
    
    @EventHandler
    public void breakk(final BlockBreakEvent event) throws SQLException {
        if (this.checkArrest(event.getPlayer())) {
            event.setCancelled(true);
            event.getPlayer().sendMessage(this.forall + ChatColor.WHITE + "Вы в тюрьме!");
        }
    }
    
    @EventHandler
    public void drop(final PlayerDropItemEvent event) throws SQLException {
        if (this.checkArrest(event.getPlayer())) {
            event.setCancelled(true);
            event.getPlayer().sendMessage(this.forall + ChatColor.WHITE + "Вы в тюрьме!");
        }
    }
    
    @EventHandler
    public void placa(final BlockPlaceEvent event) throws SQLException {
        if (this.checkArrest(event.getPlayer())) {
            event.setCancelled(true);
            event.getPlayer().sendMessage(this.forall + ChatColor.WHITE + "Вы в тюрьме!");
        }
    }
    
    @EventHandler
    public void ebye(final EntityDamageByEntityEvent event) throws SQLException {
        if (event.getEntity() instanceof Player) {
            final Player p = (Player)event.getEntity();
            if (this.checkArrest(p)) {
                event.setCancelled(true);
                p.sendMessage(this.forall + ChatColor.WHITE + "Вы в тюрьме!");
            }
        }
    }
    
    @EventHandler
    public void deat(final PlayerRespawnEvent event) throws SQLException {
        if (this.checkArrest(event.getPlayer())) {
            Bukkit.getScheduler().runTaskLater(this.plugin, (Runnable)new Runnable() {
                @Override
                public void run() {
                    final Location loca = new Location(event.getPlayer().getWorld(), ArrestEvents.this.plugin.getConfig().getDouble("xpris"), ArrestEvents.this.plugin.getConfig().getDouble("ypris"), ArrestEvents.this.plugin.getConfig().getDouble("zpris"));
                    event.getPlayer().teleport(loca);
                }
            }, 20L);
            event.getPlayer().sendMessage(this.forall + ChatColor.WHITE + "Телепортация в тюрьму...");
        }
    }
    
    @EventHandler
    public void cmd(final PlayerCommandPreprocessEvent event) throws SQLException {
        if (this.checkArrest(event.getPlayer())) {
            final String command = event.getMessage().substring(1).split(" ")[0];
            if (!command.startsWith("unarrest") && !command.startsWith("phone")) {
                event.getPlayer().sendMessage(this.forall + ChatColor.WHITE + "Вы в тюрьме!");
                event.setCancelled(true);
            }
            if (command.startsWith("phone")) {
                event.getPlayer().sendMessage(this.forall + ChatColor.WHITE + "У вас отобрали телефон!");
                event.setCancelled(true);
            }
        }
    }
    
    public boolean checkArrest(final Player p) {
        return main.SQL.getPlayerInPris(p.getName());
    }
    
    public void huh() {
    }
    
    @EventHandler
    public void enter(final PlayerInteractEntityEvent event) {
        if (this.checkArrest(event.getPlayer())) {
            event.setCancelled(true);
        }
    }
}
