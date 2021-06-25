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
		if (!main.SQL.getPlayerRabota(p.getName()).getFraction().getName().contains("Мэрия")) {
			p.sendMessage(CommandWork.nachalo + ChatColor.WHITE + "Вы не работаете в мэрии!");
			return true;
		}
		if (main.SQL.getPlayerRabota(p.getName()).getNumber() < 4) {
			p.sendMessage(CommandWork.nachalo + ChatColor.WHITE + "Отказ в доступе.");
			return true;
		}
		if (args.length == 0) {
			p.sendMessage(CommandWork.nachalo + ChatColor.WHITE + "Введите кому хотите дать паспорт!");
			return true;
		}
		if (Bukkit.getPlayer(args[0]) == null) {
			p.sendMessage(CommandWork.nachalo + ChatColor.WHITE + "В данный момент игрок не в сети.");
			return true;
		}
		if (VaultManager.getmoney(Bukkit.getPlayer(args[0])) < 500) {
			sender.sendMessage(CommandWork.nachalo + "У игрока недостаточно денег на счету");
			return true;
		}
		Player pe = Bukkit.getPlayer(args[0]);
		VaultManager.deposite(p, 200);
		VaultManager.withdraw(Bukkit.getPlayer(args[0]), 500);
		TownMoneyApi.deposite(300);
		ItemStack book = new ItemStack(Material.WRITTEN_BOOK, 1);
		book.getItemMeta().setDisplayName(ChatColor.GOLD + "Паспорт " + ChatColor.YELLOW + args[0]);
		BookMeta bm = (BookMeta) book.getItemMeta();
		SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd");
		bm.setAuthor("Мэрия | Дата выдачи: " + format.format(Calendar.getInstance().getTime()));
		bm.setTitle(ChatColor.GOLD + "Паспорт " + ChatColor.YELLOW + args[0]);
		ArrayList<String> pages = new ArrayList<String>();
		pages.add("•———————————————————————• \n \n Гражданин: " + pe.getName() + " \n Фракция: "
				+ main.SQL.getPlayerRabota(pe.getName()).getFraction().getName() + "\n Звания: "
				+ main.SQL.getPlayerRabota(pe.getName()).getName() + "\n \n Был в тюрьме: "
				+ main.SQL.getPlayerSroki(pe.getName()) + " раз \n \n Пол: "
				+ main.SQL.getGender(pe.getName()).getName() + " \n Диплом: "
				+ main.SQL.getPlayerEducation(pe.getName()).getName().replace("Высшее", ChatColor.RED+"Высшее") + ChatColor.BLACK+" \n Мед.карта: "
				+ main.SQL.getMedCart(pe.getName()).getName() + " \n Военный билет: " + main.SQL.getBilet(pe.getName()).getName() + " \n \n •———————————————————————•");
		sender.sendMessage(ChatColor.GRAY + p.getName() + ": минуточку, собираю данные о вас..");
		sender.sendMessage(ChatColor.GRAY + p.getName() + ": ваша роспись пожалуйста..");
		for (Player player2 : Bukkit.getOnlinePlayers()) {
			if (p.getLocation().distance(player2.getLocation()) <= 15)
				player2.sendMessage(ChatColor.translateAlternateColorCodes('&',
						"&5* " + args[0] + " поставил роспись на паспорте."));
		}
		sender.sendMessage(ChatColor.GRAY + p.getName() + ": Ваш паспорт оформлен и является официальным! Держите.");
		for (Player player2 : Bukkit.getOnlinePlayers()) {
			if (p.getLocation().distance(player2.getLocation()) <= 15) {
				player2.sendMessage(ChatColor.translateAlternateColorCodes('&',
						"&5* " + p.getName() + " передал паспорт гражданину " + p.getName()));
				player2.sendMessage(
						ChatColor.translateAlternateColorCodes('&', "&5* " + args[0] + " взял паспорт у паспортёра"));
			}
		}
		bm.setPages(pages);
		book.setItemMeta(bm);
		Bukkit.getPlayer(args[0]).getInventory().addItem(book);

		return true;
	}
}
