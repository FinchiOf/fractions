package ru.Boss90.roleplay;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

public class BookPasport implements CommandExecutor {
	@Override
	public boolean onCommand(final CommandSender sender, final Command cmd, final String strl, final String[] args) {
		Player p = (Player) sender;
		if (!main.SQL.getPlayerRabota(p.getName()).getFraction().getName().contains("�����")) {
			p.sendMessage(CommandWork.nachalo + ChatColor.WHITE + "�� �� ��������� � �����!");
			return true;
		}
		if (main.SQL.getPlayerRabota(p.getName()).getNumber() < 4) {
			p.sendMessage(CommandWork.nachalo + ChatColor.WHITE + "����� � �������.");
			return true;
		}
		if (args.length == 0) {
			p.sendMessage(CommandWork.nachalo + ChatColor.WHITE + "������� ���� ������ ���� �������!");
			return true;
		}
		if (Bukkit.getPlayer(args[0]) == null) {
			p.sendMessage(CommandWork.nachalo + ChatColor.WHITE + "� ������ ������ ����� �� � ����.");
			return true;
		}
		if (VaultManager.getmoney(Bukkit.getPlayer(args[0])) < 500) {
			sender.sendMessage(CommandWork.nachalo + "� ������ ������������ ����� �� �����");
			return true;
		}
		Player pe = Bukkit.getPlayer(args[0]);
		VaultManager.deposite(p, 200);
		VaultManager.withdraw(Bukkit.getPlayer(args[0]), 500);
		TownMoneyApi.deposite(300);
		ItemStack book = new ItemStack(Material.WRITTEN_BOOK, 1);
		book.getItemMeta().setDisplayName(ChatColor.GOLD + "������� " + ChatColor.YELLOW + args[0]);
		BookMeta bm = (BookMeta) book.getItemMeta();
		SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd");
		bm.setAuthor("����� | ���� ������: " + format.format(Calendar.getInstance().getTime()));
		bm.setTitle(ChatColor.GOLD + "������� " + ChatColor.YELLOW + args[0]);
		ArrayList<String> pages = new ArrayList<String>();
		pages.add("������������������������� \n \n ���������: " + pe.getName() + " \n �������: "
				+ main.SQL.getPlayerRabota(pe.getName()).getFraction().getName() + "\n ������: "
				+ main.SQL.getPlayerRabota(pe.getName()).getName() + "\n \n ��� � ������: "
				+ main.SQL.getPlayerSroki(pe.getName()) + " ��� \n \n ���: "
				+ main.SQL.getGender(pe.getName()).getName() + " \n ������: "
				+ main.SQL.getPlayerEducation(pe.getName()).getName().replace("������", ChatColor.RED+"������") + ChatColor.BLACK+" \n ���.�����: "
				+ main.SQL.getMedCart(pe.getName()).getName() + " \n ������� �����: " + main.SQL.getBilet(pe.getName()).getName() + " \n \n �������������������������");
		sender.sendMessage(ChatColor.GRAY + p.getName() + ": ���������, ������� ������ � ���..");
		sender.sendMessage(ChatColor.GRAY + p.getName() + ": ���� ������� ����������..");
		for (Player player2 : Bukkit.getOnlinePlayers()) {
			if (p.getLocation().distance(player2.getLocation()) <= 15)
				player2.sendMessage(ChatColor.translateAlternateColorCodes('&',
						"&5* " + args[0] + " �������� ������� �� ��������."));
		}
		sender.sendMessage(ChatColor.GRAY + p.getName() + ": ��� ������� �������� � �������� �����������! �������.");
		for (Player player2 : Bukkit.getOnlinePlayers()) {
			if (p.getLocation().distance(player2.getLocation()) <= 15) {
				player2.sendMessage(ChatColor.translateAlternateColorCodes('&',
						"&5* " + p.getName() + " ������� ������� ���������� " + p.getName()));
				player2.sendMessage(
						ChatColor.translateAlternateColorCodes('&', "&5* " + args[0] + " ���� ������� � ���������"));
			}
		}
		bm.setPages(pages);
		book.setItemMeta(bm);
		Bukkit.getPlayer(args[0]).getInventory().addItem(book);

		return true;
	}
}
