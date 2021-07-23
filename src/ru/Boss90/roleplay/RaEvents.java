package ru.Boss90.roleplay;

import java.util.HashSet;
import java.util.List;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class RaEvents implements Listener{
public static HashSet<Player> police = new HashSet<>();
public static HashSet<Player> hospital = new HashSet<>();
public static HashSet<Player> meria = new HashSet<>();
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		if(main.SQL.getPlayerRabota(e.getPlayer().getName()).getFraction().getName().contains("Полиция")) {
			police.add(e.getPlayer());
            List<String> players = main.plugin.getConfig().getStringList("Police");
            players.add(e.getPlayer().getName());
            main.plugin.getConfig().set("Police", players);
            main.plugin.saveConfig();
			return;
		}
		if(main.SQL.getPlayerRabota(e.getPlayer().getName()).getFraction().getName().contains("Мэрия")) {
			meria.add(e.getPlayer());
            List<String> players = main.plugin.getConfig().getStringList("Meria");
            players.add(e.getPlayer().getName());
            main.plugin.getConfig().set("Meria", players);
            main.plugin.saveConfig();
			return;
		}
		if(main.SQL.getPlayerRabota(e.getPlayer().getName()).getFraction().getName().contains("Больница")) {
			hospital.add(e.getPlayer());
            List<String> players = main.plugin.getConfig().getStringList("Hospital");
            players.add(e.getPlayer().getName());
            main.plugin.getConfig().set("Hospital", players);
            main.plugin.saveConfig();
			return;
		}
	}
	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		if(main.SQL.getPlayerRabota(e.getPlayer().getName()).getFraction().getName().contains("Полиция")) {
			police.remove(e.getPlayer());
            List<String> players = main.plugin.getConfig().getStringList("Police");
            players.remove(e.getPlayer().getName());
            main.plugin.getConfig().set("Police", players);
            main.plugin.saveConfig();
			return;
		}
		if(main.SQL.getPlayerRabota(e.getPlayer().getName()).getFraction().getName().contains("Мэрия")) {
			meria.remove(e.getPlayer());
            List<String> players = main.plugin.getConfig().getStringList("Meria");
            players.remove(e.getPlayer().getName());
            main.plugin.getConfig().set("Meria", players);
            main.plugin.saveConfig();
			return;
		}
		if(main.SQL.getPlayerRabota(e.getPlayer().getName()).getFraction().getName().contains("Больница")) {
			hospital.remove(e.getPlayer());
            List<String> players = main.plugin.getConfig().getStringList("Hospita;");
            players.remove(e.getPlayer().getName());
            main.plugin.getConfig().set("Hospital", players);
            main.plugin.saveConfig();
			return;
		}
	}
	@EventHandler
	public void onQuit(PlayerKickEvent e) {
		if(main.SQL.getPlayerRabota(e.getPlayer().getName()).getFraction().getName().contains("Полиция")) {
			police.remove(e.getPlayer());
            List<String> players = main.plugin.getConfig().getStringList("Police");
            players.remove(e.getPlayer().getName());
            main.plugin.getConfig().set("Police", players);
            main.plugin.saveConfig();
			return;
		}
		if(main.SQL.getPlayerRabota(e.getPlayer().getName()).getFraction().getName().contains("Мэрия")) {
			meria.remove(e.getPlayer());
            List<String> players = main.plugin.getConfig().getStringList("Meria");
            players.remove(e.getPlayer().getName());
            main.plugin.getConfig().set("Meria", players);
            main.plugin.saveConfig();
			return;
		}
		if(main.SQL.getPlayerRabota(e.getPlayer().getName()).getFraction().getName().contains("Больница")) {
			hospital.remove(e.getPlayer());
            List<String> players = main.plugin.getConfig().getStringList("Hospita;");
            players.remove(e.getPlayer().getName());
            main.plugin.getConfig().set("Hospital", players);
            main.plugin.saveConfig();
			return;
		}
	}
}
