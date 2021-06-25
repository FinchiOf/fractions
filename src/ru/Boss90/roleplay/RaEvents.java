package ru.Boss90.roleplay;

import java.util.HashSet;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class RaEvents implements Listener{
public static HashSet<Player> police = new HashSet<>();
public static HashSet<Player> hospital = new HashSet<>();
public static HashSet<Player> meria = new HashSet<>();
public static HashSet<Player> armia = new HashSet<>();
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		if(main.SQL.getPlayerRabota(e.getPlayer().getName()).getFraction().getName().contains("Полиция")) {
			police.add(e.getPlayer());
			return;
		}
		if(main.SQL.getPlayerRabota(e.getPlayer().getName()).getFraction().getName().contains("Мэрия")) {
			meria.add(e.getPlayer());
			return;
		}
		if(main.SQL.getPlayerRabota(e.getPlayer().getName()).getFraction().getName().contains("Больница")) {
			hospital.add(e.getPlayer());
			return;
		}
		if(main.SQL.getPlayerRabota(e.getPlayer().getName()).getFraction().getName().contains("Армия")) {
			armia.add(e.getPlayer());
			return;
		}
	}
	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		if(main.SQL.getPlayerRabota(e.getPlayer().getName()).getFraction().getName().contains("Полиция")) {
			police.remove(e.getPlayer());
			return;
		}
		if(main.SQL.getPlayerRabota(e.getPlayer().getName()).getFraction().getName().contains("Мэрия")) {
			meria.remove(e.getPlayer());
			return;
		}
		if(main.SQL.getPlayerRabota(e.getPlayer().getName()).getFraction().getName().contains("Больница")) {
			hospital.remove(e.getPlayer());
			return;
		}
		if(main.SQL.getPlayerRabota(e.getPlayer().getName()).getFraction().getName().contains("Армия")) {
			armia.remove(e.getPlayer());
			return;
		}
	}
}
