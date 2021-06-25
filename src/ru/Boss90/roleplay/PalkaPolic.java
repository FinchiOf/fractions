package ru.Boss90.roleplay;

import java.util.HashSet;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventException;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import net.md_5.bungee.api.ChatColor;
import ru.Boss90.roleplay.interfaces.Work;

public class PalkaPolic implements Listener {
	Plugin plugin;
	String forall;
	HashSet<Player> da = new HashSet<>();

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public PalkaPolic() {
		this.plugin = (Plugin) main.getPlugin((Class) main.class);
		this.forall = this.plugin.getConfig().getString("nachalo").replace("&", "§");
	}

	@EventHandler
	public void onDamage(PlayerInteractEntityEvent e) throws NullPointerException{
		if (e.getRightClicked().getType() != EntityType.PLAYER) {
			return;
		}
		final Player clicked = (Player) e.getRightClicked();
		final Player p = e.getPlayer();
		if (clicked.hasPermission("atlantworld.freestars")) {
			p.sendMessage(this.forall + ChatColor.WHITE + "Этого игрока нельзя посадить!");
			return;
		}
		try {
		if(!p.getInventory().getItemInMainHand().getItemMeta().getDisplayName().contains(ChatColor.AQUA+"Палка полицейского")) {
			return;
		}
		}catch(NullPointerException e1) {
			e1.printStackTrace();
			return;
		}
		if (p.getGameMode() == GameMode.CREATIVE || p.isFlying() == true) {
			p.sendMessage(this.forall + ChatColor.WHITE + "Выключите флай/гм");
			return;
		}
		if (main.SQL.getPlayerStars(clicked.getName()) == 0) {
			p.sendMessage(this.forall + ChatColor.WHITE + "Данный игрок без розыска!");
			return;
		}
		if (da.contains(clicked)) {
			p.sendMessage(this.forall + ChatColor.WHITE + "Данный игрок уже заморожен!");
			return;
		}
		if (main.SQL.getPlayerInPris(clicked.getName())) {
			p.sendMessage(this.forall + ChatColor.WHITE + "Данный игрок уже в тюрьме!");
			return;
		}
		final Work work = main.SQL.getPlayerRabota(p.getName());
		if (work.getFraction().getName().contains("Полиция")) {
			clicked.setGameMode(GameMode.SURVIVAL);
			da.add(clicked);
			p.sendMessage(
					this.forall + ChatColor.WHITE + "Вы заморозили игрока " + clicked.getName() + " на 10 секунд!");
			clicked.sendMessage(this.forall + ChatColor.WHITE + "Вас заморозил " + work.getName() + " " + p.getName()
					+ " на 30 секунд!");
		}
		new BukkitRunnable() {

			@Override
			public void run() {
				da.remove(clicked);
				clicked.sendMessage(forall + ChatColor.WHITE + "Заморозка кончилась!");
			}

		}.runTaskLaterAsynchronously(plugin, 200);
	}

	@EventHandler
	public void OnMove(PlayerMoveEvent e) {
		if (da.contains(e.getPlayer())) {
			e.setCancelled(true);
		}
		if (cuff.cuffing.contains(e.getPlayer())) {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void OnCommand(PlayerCommandPreprocessEvent e) {
		if (da.contains(e.getPlayer())) {
			e.setCancelled(true);
		}
		if (cuff.cuffing.contains(e.getPlayer())) {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void OnCommand(PlayerQuitEvent e) {
		if (da.contains(e.getPlayer())) {
			main.SQL.setInPrison(e.getPlayer().getName(), 60);
			e.getPlayer().teleport(new Location(e.getPlayer().getWorld(), this.plugin.getConfig().getDouble("xpris"),
					this.plugin.getConfig().getDouble("ypris"), this.plugin.getConfig().getDouble("zpris")));
		}
		if (cuff.cuffing.contains(e.getPlayer())) {
			main.SQL.setInPrison(e.getPlayer().getName(), 60);
			e.getPlayer().teleport(new Location(e.getPlayer().getWorld(), this.plugin.getConfig().getDouble("xpris"),
					this.plugin.getConfig().getDouble("ypris"), this.plugin.getConfig().getDouble("zpris")));
		}
	}
}