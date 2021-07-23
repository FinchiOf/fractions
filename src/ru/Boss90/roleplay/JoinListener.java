package ru.Boss90.roleplay;

import org.bukkit.plugin.*;
import org.bukkit.event.inventory.*;
import org.bukkit.event.*;
import org.bukkit.event.entity.*;
import org.bukkit.entity.*;
import org.bukkit.potion.*;

import ru.Boss90.roleplay.enums.*;

import java.sql.*;
import org.bukkit.*;
import org.bukkit.ChatColor;
import org.bukkit.event.player.*;

public class JoinListener implements Listener
{
    Plugin plugin;
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public JoinListener() {
        this.plugin = (Plugin)main.getPlugin((Class)main.class);
    }
    
    @EventHandler
    public void click(final InventoryClickEvent event) {
        if (ChatColor.stripColor(event.getInventory().getName()).equalsIgnoreCase("Паспорт")) {
            event.setCancelled(true);
        }
    }
    
    @EventHandler
    public void joins(final PlayerJoinEvent event) {
        if (main.SQL.getFuckinTyuremshiki().contains(event.getPlayer().getName()) && main.SQL.getPlayerTimeinPris(event.getPlayer().getName()) <= 0) {
            main.SQL.vishelPris(event.getPlayer().getName());
        }
    }
    
    @SuppressWarnings("deprecation")
	@EventHandler
    public void ebyeee(final EntityDamageByEntityEvent event) throws SQLException {
        if (event.getEntity() instanceof Player && event.getDamager() instanceof Player) {
            final Player p = (Player)event.getEntity();
            final Player d = (Player)event.getDamager();
            if (main.SQL.getPlayerRabota(p.getName()).getFraction().equals(Fraction.POLICE)) {
                if (d.getItemInHand().getType() != Material.STICK) {
                    return;
                }
                if (ChatColor.stripColor(d.getItemInHand().getItemMeta().getDisplayName()).equalsIgnoreCase("Палка полицейского") && d.getItemInHand().getType() == Material.STICK) {
                    if (main.SQL.getPlayerStars(p.getName()) >= 1) {
                        p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 600, 100));
                    }
                    else {
                        event.setCancelled(true);
                    }
                }
            }
        }
    }
    
    @EventHandler
    public void onPlayerJump(final PlayerMoveEvent e) {
        final Location l = e.getPlayer().getLocation();
        l.setY(l.getY() - 1.0);
        if (e.getFrom().getY() < e.getTo().getY() && e.getPlayer().getLocation().subtract(0.0, 1.0, 0.0).getBlock().getType() != Material.AIR && l.getBlock().getType() != Material.QUARTZ_STAIRS && e.getPlayer().getLocation().getBlock().getType() != Material.QUARTZ_STAIRS && e.getPlayer().hasPotionEffect(PotionEffectType.SLOW)) {
            e.setCancelled(true);
        }
    }
    
    @EventHandler
    public void dropa(final PlayerDropItemEvent event) throws SQLException {
        if (main.SQL.getPlayerRabota(event.getPlayer().getName()).getFraction().equals(Fraction.POLICE)) {
            if (event.getItemDrop().getItemStack().getType() != Material.STICK) {
                return;
            }
            if (event.getItemDrop().getItemStack().hasItemMeta() && ChatColor.stripColor(event.getItemDrop().getItemStack().getItemMeta().getDisplayName()).equalsIgnoreCase("Палка полицейского")) {
                event.setCancelled(true);
            }
        }
    }
    
    @SuppressWarnings("deprecation")
	@EventHandler
    public void pickachi(final PlayerPickupItemEvent event) throws SQLException {
        if (event.getItem().getItemStack().getType() != Material.STICK) {
            return;
        }
        if (event.getItem().getItemStack().hasItemMeta() && ChatColor.stripColor(event.getItem().getItemStack().getItemMeta().getDisplayName()).equalsIgnoreCase("Палка полицейского")) {
            event.setCancelled(true);
        }
    }
    
    @EventHandler
    public void invclicka(final InventoryClickEvent event) {
        if (event.getCurrentItem() == null) {
            return;
        }
        if (event.getCurrentItem().getType() != Material.STICK) {
            return;
        }
        if (event.getCurrentItem().hasItemMeta() && ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()).equalsIgnoreCase("Палка полицейского")) {
            event.setCancelled(true);
        }
    }
}
