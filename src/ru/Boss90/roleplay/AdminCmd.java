package ru.Boss90.roleplay;

import org.bukkit.plugin.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;
import org.bukkit.*;
import ru.Boss90.roleplay.interfaces.*;

public class AdminCmd implements CommandExecutor {
	Plugin plugin;
	String forall;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public AdminCmd() {
		this.plugin = (Plugin) main.getPlugin((Class) main.class);
		this.forall = this.plugin.getConfig().getString("nachalo");
	}

	public boolean onCommand(final CommandSender sender, final Command cmd, final String commandlabel,
			final String[] args) {
		this.forall = this.forall.replace("&", "�");
		final Work work = main.SQL.getPlayerRabota(sender.getName());
		if (cmd.getName().equalsIgnoreCase("resetstars")) {
			if (work.getNumber() < 6) {
				sender.sendMessage(String.valueOf(String.valueOf(this.forall)) + ChatColor.WHITE + "��� ����!");
				return true;
			}
			if (args.length == 0) {
				sender.sendMessage(String.valueOf(String.valueOf(this.forall)) + ChatColor.WHITE
						+ "/resetstars [�����] - ������ ������ � ������(������)");
				return true;
			}
			if (args.length == 1) {
				final Player peha = Bukkit.getPlayer(args[0]);
				if (!main.SQL.hasValue(peha.getName())) {
					main.SQL.writeToRoleplay(peha.getName());
				}
				main.SQL.setstars(peha.getName(), 0);
				sender.sendMessage(String.valueOf(String.valueOf(this.forall)) + ChatColor.WHITE + "���������!");
				return true;
			}
		}
		if (cmd.getName().equalsIgnoreCase("setstars")) {
			String message = " ";
			if (work.getNumber() < 6) {
				sender.sendMessage(String.valueOf(String.valueOf(this.forall)) + ChatColor.WHITE + "��� ����!");
				return true;
			}
			if (args.length <= 1) {
				sender.sendMessage(String.valueOf(String.valueOf(this.forall)) + ChatColor.WHITE
						+ "/setstars [�����] [���������� ����] - ���������� ���������� ����(������) ������.");
				return true;
			}
			if (args.length >= 2) {
				int stars = 1;
				try {
					stars = Integer.valueOf(args[1]);
					if (stars > 5 || stars < 1) {
						sender.sendMessage(String.valueOf(String.valueOf(this.forall)) + ChatColor.WHITE
								+ "����� ������ ���� �� 1 �� 5!");
						stars = 1;
						return true;
					}
				} catch (NumberFormatException nfe) {
					sender.sendMessage(
							String.valueOf(String.valueOf(this.forall)) + ChatColor.WHITE + "������������ �����!");
					return true;
				}
				final Player pevuha = Bukkit.getPlayer(args[0]);
				if (!Bukkit.getOnlinePlayers().contains(pevuha)) {
					sender.sendMessage(
							String.valueOf(String.valueOf(this.forall)) + ChatColor.WHITE + "����� �� ������!");
					stars = 0;
					return true;
				}
				if (args.length >= 3) {
					for (int i = 2; i < args.length; ++i) {
						message = String.valueOf(String.valueOf(message)) + args[i] + " ";
					}
                    StringBuilder sb = new StringBuilder();
                    for (int i = 2; i < args.length; i++) sb.append(args[i]).append(' ');
               	    String text = sb.toString();
					if(stars == 1) {
						RaEvents.police.forEach(p4 -> p4.sendMessage(CommandWork.nachalo + ChatColor.WHITE+"����� " + sender.getName() + " ������� � ������ " + args[0] + " ��: " + text));
						return true;
					}
					main.SQL.setstars(pevuha.getName(), stars);
					Bukkit.broadcastMessage("�f[�6�l*�f] �����: �6" + pevuha.getName()
							+ " �f�������� � ������. ��� ���������� �������� � �������!");
					Bukkit.broadcastMessage(
							"�f[�6�l*�f] � ������ �������: �6" + work.getName() + " " + sender.getName());
					Bukkit.broadcastMessage("�f[�6�l*�f] �������:�6" + message + "�f.");
					sender.sendMessage(String.valueOf(this.forall) + ChatColor.WHITE + "������ � ������ "
							+ pevuha.getName() + " " + stars + " ����!");
					return true;
				}
			}
		}
		return false;
	}
}