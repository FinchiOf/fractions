package ru.Boss90.roleplay;

import org.bukkit.plugin.*;
import org.bukkit.Bukkit;
import org.bukkit.command.*;
import net.md_5.bungee.api.*;
import ru.Boss90.roleplay.work.*;

public class SetMerCmd implements CommandExecutor {
	Plugin plugin;
	String forall;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public SetMerCmd() {
		this.plugin = (Plugin) main.getPlugin((Class) main.class);
		this.forall = this.plugin.getConfig().getString("nachalo").replace("&", "§");
	}

	public boolean onCommand(final CommandSender sender, final Command cmd, final String commandlabel,
			final String[] args) {
		if(args.length == 0) {
			sender.sendMessage(forall + ChatColor.WHITE+"Напишите фракцию.");
			return true;
		}
			switch (args[0]) {
			case ("hospital"):
				if (!sender.isOp()) {
					sender.sendMessage(this.forall + ChatColor.WHITE + "Нет прав!");
					return true;
				}
				if (args.length == 0) {
					sender.sendMessage(this.forall + "/makeleader hospital [ник] - выдать лидерку больницы.");
					return true;
				}
				if (args.length == 1) {
					sender.sendMessage(this.forall + ChatColor.WHITE + "Введите ник игрока!");
					return true;
				}
				final String oldglvr = this.plugin.getConfig().getString("glvrach");
				final String newglvr = args[1].toString();
				if (args[1].contains("-")) {
					this.plugin.getConfig().set("glvrach", "-");
					main.SQL.setrabota(oldglvr, WorkFactory.loadedWorks.get(0));
					Bukkit.broadcastMessage(" ");
					Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&',
							"[&6&l*&f] Лидер фракции &eБольницы &fбыл снят!"));
					Bukkit.broadcastMessage(" ");
					return true;
				}
				main.SQL.setrabota(oldglvr, WorkFactory.loadedWorks.get(0));
				main.SQL.setrabota(newglvr, WorkFactory.loadedWorks.get(26));
				this.plugin.getConfig().set("glvrach", (Object) newglvr);
				this.plugin.saveConfig();
				Bukkit.broadcastMessage(" ");
				Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&',
						"[&6*&f] На сервере появился новый лидер фракции &eБольницы!"));
				Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&fЕго ник: &e" + newglvr + "!"));
				Bukkit.broadcastMessage(" ");
				break;
			case ("police"):
				if (!sender.isOp()) {
					sender.sendMessage(this.forall + ChatColor.WHITE + "Нет прав!");
					return true;
				}
				if (args.length == 0) {
					sender.sendMessage(this.forall + "/makeleader police [ник] - выдать лидерку полиции.");
					return true;
				}
				if (args.length == 1) {
					sender.sendMessage(this.forall + ChatColor.WHITE + "Введите ник игрока!");
					return true;
				}
					final String oldsherif = this.plugin.getConfig().getString("sherif");
					final String newsherif = args[1].toString();
					if (args[1].contains("-")) {
						this.plugin.getConfig().set("sherif", "-");
						main.SQL.setrabota(oldsherif, WorkFactory.loadedWorks.get(0));
						Bukkit.broadcastMessage(" ");
						Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&',
								"[&6&l*&f] Лидер фракции &eПолиции &fбыл снят!"));
						Bukkit.broadcastMessage(" ");
						return true;
					}
					main.SQL.setrabota(oldsherif, WorkFactory.loadedWorks.get(0));
					main.SQL.setrabota(newsherif, WorkFactory.loadedWorks.get(18));
					this.plugin.getConfig().set("sherif", (Object) newsherif);
					this.plugin.saveConfig();
					Bukkit.broadcastMessage(" ");
					Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&',
							"[&6&l*&f] На сервере появился новый лидер фракции &eПолиции!"));
					Bukkit.broadcastMessage(
							ChatColor.translateAlternateColorCodes('&', "&fЕго ник: &e" + newsherif + "!"));
					Bukkit.broadcastMessage(" ");
				break;
			case("meria"):
				if (!sender.isOp()) {
					sender.sendMessage(this.forall + ChatColor.WHITE + "Нет прав!");
					return true;
				}
				if (args.length == 0) {
					sender.sendMessage(this.forall + "/makeleader meria  [ник] - выдать лидерку мэрии.");
					return true;
				}
				if (args.length == 1) {
					sender.sendMessage(this.forall + ChatColor.WHITE + "Введите ник игрока!");
					return true;
				}
					final String oldmer = this.plugin.getConfig().getString("mer");
					if (args[1].contains("-")) {
						this.plugin.getConfig().set("mer", "-");
						main.SQL.setrabota(oldmer, WorkFactory.loadedWorks.get(0));
						Bukkit.broadcastMessage(" ");
						Bukkit.broadcastMessage(
								ChatColor.translateAlternateColorCodes('&', "[&6&l*&f] Лидер фракции &eМэрии &fбыл снят!"));
						Bukkit.broadcastMessage(" ");
						return true;
					}
					final String newmer = args[1].toString();
					main.SQL.setrabota(oldmer, WorkFactory.loadedWorks.get(0));
					main.SQL.setrabota(newmer, WorkFactory.loadedWorks.get(8));
					this.plugin.getConfig().set("mer", (Object) newmer);
					this.plugin.saveConfig();
					Bukkit.broadcastMessage(" ");
					Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&',
							"[&6&l*&f] На сервере появился новый лидер фракции &eМэрии!"));
					Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&fЕго ник: &e" + newmer + "!"));
					Bukkit.broadcastMessage(" ");
				break;
			case("armia"):
				if (!sender.isOp()) {
					sender.sendMessage(this.forall + ChatColor.WHITE + "Нет прав!");
					return true;
				}
				if (args.length == 0) {
					sender.sendMessage(this.forall + "/makeleader armia  [ник] - выдать лидерку мэрии.");
					return true;
				}
				if (args.length == 1) {
					sender.sendMessage(this.forall + ChatColor.WHITE + "Введите ник игрока!");
					return true;
				}
					final String oldarmia = this.plugin.getConfig().getString("general");
					if (args[1].contains("-")) {
						this.plugin.getConfig().set("general", "-");
						main.SQL.setrabota(oldarmia, WorkFactory.loadedWorks.get(0));
						Bukkit.broadcastMessage(" ");
						Bukkit.broadcastMessage(
								ChatColor.translateAlternateColorCodes('&', "[&6&l*&f] Лидер фракции &eАрмия &fбыл снят!"));
						Bukkit.broadcastMessage(" ");
						return true;
					}
					final String newarmia = args[1].toString();
					main.SQL.setrabota(oldarmia, WorkFactory.loadedWorks.get(0));
					main.SQL.setrabota(newarmia, WorkFactory.loadedWorks.get(35));
					this.plugin.getConfig().set("general", (Object) newarmia);
					this.plugin.saveConfig();
					Bukkit.broadcastMessage(" ");
					Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&',
							"[&6&l*&f] На сервере появился новый лидер фракции &eАрмия!"));
					Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&fЕго ник: &e" + newarmia + "!"));
					Bukkit.broadcastMessage(" ");
				break;
				
				default:
					sender.sendMessage(this.forall + ChatColor.WHITE + "Список команд:");
					sender.sendMessage("/makeleader meria [ник] - выдать лидерку мэрии.");
					sender.sendMessage("/makeleader police [ник] - выдать лидерку полиции.");
					sender.sendMessage("/makeleader hospital [ник] - выдать лидерку больницы.");
					sender.sendMessage("/makeleader armia [ник] - выдать лидерку армия.");
			}
		return true;
	}
}
