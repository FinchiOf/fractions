package ru.Boss90.roleplay;

import org.bukkit.plugin.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.*;
import ru.Boss90.roleplay.enums.*;
import ru.Boss90.roleplay.interfaces.*;
import ru.Boss90.roleplay.work.*;

public class CommandWork implements CommandExecutor {
	String da;
	Plugin plugin;
	static String nachalo;

	@SuppressWarnings({ "unchecked", "rawtypes", "static-access" })
	public CommandWork() {
		this.da = "кек";
		this.plugin = (Plugin) main.getPlugin((Class) main.class);
		this.nachalo = new StringBuilder().append(ChatColor.AQUA).append(ChatColor.BOLD).append("Работы > ").toString();
	}

	@SuppressWarnings("static-access")
	public boolean onCommand(final CommandSender sender, final Command cmd, final String commandlabel,
			final String[] args) {
		if (cmd.getName().equalsIgnoreCase("work")) {
			if (args.length == 0) {
				sender.sendMessage(this.nachalo + ChatColor.WHITE + "/work select - Посмотреть существующие фракции.");
				sender.sendMessage(this.nachalo + ChatColor.WHITE + "/work select [Название] - выбрать фракцию.");
				sender.sendMessage(this.nachalo + ChatColor.WHITE + "/work leave - Уйти с работы.");
				final Work work = main.SQL.getPlayerRabota(sender.getName());
				final AccessLevel[] levels = work.getAccess();
				final boolean f1 = AccessLevel.contains(levels, AccessLevel.KICKHOSPITAL)
						|| AccessLevel.contains(levels, AccessLevel.KICKMERIA)
						|| AccessLevel.contains(levels, AccessLevel.KICKPOLICE) || sender.isOp();
				final boolean f2 = AccessLevel.contains(levels, AccessLevel.UPHOSPITAL)
						|| AccessLevel.contains(levels, AccessLevel.UPMERIA)
						|| AccessLevel.contains(levels, AccessLevel.UPPOLICE) || sender.isOp();
				if (f1) {
					sender.sendMessage(this.nachalo + ChatColor.WHITE + "/work kick [Ник] - Уволить игрока с работы.");
				}
				if (f2) {
					sender.sendMessage(this.nachalo + ChatColor.WHITE + "/work up [Ник] - Поднять игрока в должности.");
				}
				@SuppressWarnings("unused")
				final Player p = (Player) sender;
				sender.sendMessage(this.nachalo + ChatColor.WHITE + "Сейчас вы " + ChatColor.AQUA + work.getName()
						+ ChatColor.WHITE + " во фракции " + ChatColor.AQUA + work.getFraction().getName()
						+ ChatColor.WHITE + ".");
				sender.sendMessage("   ");
				sender.sendMessage(ChatColor.WHITE + "Ваши права:");
				for (final AccessLevel ac : work.getAccess()) {
					sender.sendMessage(ChatColor.AQUA + ac.getName());
				}
				String a = this.nachalo + ChatColor.WHITE + "Следующий ранг: " + ChatColor.AQUA;
				switch (work.getNext()) {
				case -1: {
					a += "Вы на максимальном/нулевом ранге";
					break;
				}
				default: {
					a += WorkFactory.loadedWorks.get(work.getNext()).getName();
					break;
				}
				}
				sender.sendMessage("    ");
				sender.sendMessage(a);
				return true;
			} else {
				if (args.length == 1 && args[0].equalsIgnoreCase("select")) {
					for (final Fraction fraction : Fraction.values()) {
						if (!fraction.equals(Fraction.NONE)) {
							sender.sendMessage(this.nachalo + ChatColor.WHITE + fraction.getName());
						}
					}
					return true;
				}
				if (args.length == 1 && args[0].equalsIgnoreCase("list")) {
					sender.sendMessage(main.SQL.getAllWorkers().toString());
					return true;
				}
				if (args.length == 1 && args[0].equalsIgnoreCase("leave")) {
					if (main.SQL.getPlayerRabota(sender.getName()).getCode() == 0) {
						sender.sendMessage(this.nachalo + ChatColor.WHITE + "У вас нет работы!");
						return true;
					}
					if (main.SQL.getPlayerRabota(sender.getName()).getFraction().equals(Fraction.POLICE)) {
						RaEvents.police
								.forEach(p3 -> p3.sendMessage(this.nachalo + ChatColor.WHITE + "[Полиция: Информация] "
										+ "Работник " + sender.getName() + " ушёл по С/Ж с нашей фракции!"));
						RaEvents.police.remove(((Player) sender));
					}
					if (main.SQL.getPlayerRabota(sender.getName()).getFraction().equals(Fraction.MERIA)) {
						RaEvents.meria
								.forEach(p3 -> p3.sendMessage(this.nachalo + ChatColor.WHITE + "[Мэрия: Информация] "
										+ "Работник " + sender.getName() + " ушёл по С/Ж с нашей фракции!"));
						RaEvents.meria.remove(((Player) sender));
					}
					if (main.SQL.getPlayerRabota(sender.getName()).getFraction().equals(Fraction.HOSPITAL)) {
						RaEvents.hospital
								.forEach(p3 -> p3.sendMessage(this.nachalo + ChatColor.WHITE + "[Больница: Информация] "
										+ "Работник " + sender.getName() + " ушёл по С/Ж с нашей фракции!"));
						RaEvents.hospital.remove(((Player) sender));
					}
					if (main.SQL.getPlayerRabota(sender.getName()).getFraction().equals(Fraction.ARMIA)) {
						RaEvents.armia
								.forEach(p3 -> p3.sendMessage(this.nachalo + ChatColor.WHITE + "[Армия: Информация] "
										+ "Работник " + sender.getName() + " ушёл по С/Ж с нашей фракции!"));
						RaEvents.armia.remove(((Player) sender));
					}
					main.SQL.setrabota(sender.getName(), WorkFactory.loadedWorks.get(0));
					sender.sendMessage(this.nachalo + ChatColor.WHITE + "Вы ушли с работы!");
					return true;
				} else {
					if (args.length == 2 && args[0].equalsIgnoreCase("up")) {
						Player p = (Player) sender;
						final String kogo = args[1];
						final Work work2 = main.SQL.getPlayerRabota(sender.getName());
						final AccessLevel[] levels2 = work2.getAccess();
						final Work work3 = main.SQL.getPlayerRabota(kogo);
						final Work wrk = WorkFactory.loadedWorks.get(work3.getNext());
						final boolean f2 = AccessLevel.contains(levels2, AccessLevel.UPHOSPITAL)
								|| AccessLevel.contains(levels2, AccessLevel.UPMERIA)
								|| AccessLevel.contains(levels2, AccessLevel.UPPOLICE)
								|| AccessLevel.contains(levels2, AccessLevel.UPARMIA) || sender.isOp();
						if (!f2) {
							sender.sendMessage(this.nachalo + ChatColor.WHITE + "Нет прав!");
							return true;
						}
						if (kogo.equalsIgnoreCase(sender.getName())) {
							sender.sendMessage(this.nachalo + ChatColor.WHITE + "Вы не можете повысить себя!");
							return true;
						}
						if (work3.getNext() == -1) {
							sender.sendMessage(this.nachalo + ChatColor.WHITE + "Этого игрока нельзя повысить!");
							return true;
						}
						if (sender.isOp()) {
							onMessageUp(work2, sender, kogo, p, wrk);
							main.SQL.setrabota(kogo, WorkFactory.loadedWorks.get(work3.getNext()));
							return true;
						}
						if (!work3.getFraction().equals(work2.getFraction())) {
							sender.sendMessage(this.nachalo + ChatColor.WHITE + "Этого игрока нельзя повысить!");
							return true;
						}
						if (wrk.getEducation().getCode() > main.SQL.getPlayerEducation(kogo).getCode()) {
							sender.sendMessage(this.nachalo + ChatColor.WHITE
									+ "У игрока недостаточное образование чтобы вступить на эту должность!");
							return true;
						}
						onMessageUp(work2, sender, kogo, p, wrk);
						main.SQL.setrabota(kogo, wrk);
						if (Bukkit.getPlayer(kogo) != null) {
							Bukkit.getPlayer(kogo).sendMessage(this.nachalo + ChatColor.WHITE + "Вы повышены!");
						}
					}
					if (args[0].equalsIgnoreCase("kick")) {
						StringBuilder sb = new StringBuilder();
						for (int i = 2; i < args.length; i++)
							sb.append(args[i]).append(' ');
						String text = sb.toString();
						final String kogo = args[1];
						final Work work2 = main.SQL.getPlayerRabota(sender.getName());
						final AccessLevel[] levels2 = work2.getAccess();
						final boolean f2 = AccessLevel.contains(levels2, AccessLevel.KICKHOSPITAL)
								|| AccessLevel.contains(levels2, AccessLevel.KICKMERIA)
								|| AccessLevel.contains(levels2, AccessLevel.KICKPOLICE)
								|| AccessLevel.contains(levels2, AccessLevel.KICKARMIA) || sender.isOp();
						if (!f2) {
							sender.sendMessage(this.nachalo + ChatColor.WHITE + "Нет прав!");
							return true;
						}
						if (!main.SQL.hasValue(kogo)) {
							sender.sendMessage(this.nachalo + ChatColor.WHITE + "Этого игрока нет!");
							return true;
						}
						if (kogo.equalsIgnoreCase(sender.getName())) {
							sender.sendMessage(this.nachalo + ChatColor.WHITE + "Вы не можете кикнуть себя!");
							return true;
						}
						final Work work3 = main.SQL.getPlayerRabota(kogo);
						if (!work3.getFraction().equals(work2.getFraction())) {
							sender.sendMessage(this.nachalo + ChatColor.WHITE + "Этого игрока нельзя уволить!");
							return true;
						}
						if (AccessLevel.contains(work3.getAccess(), AccessLevel.UNKICKABLE)) {
							sender.sendMessage(this.nachalo + ChatColor.WHITE + "Этого игрока нельзя уволить!");
							return true;
						}
						if (sender.isOp()) {
							onMessageKick(work2, sender, kogo, text);
							sender.sendMessage(this.nachalo + ChatColor.WHITE + "Игрок уволен!");
							main.SQL.setrabota(kogo, WorkFactory.loadedWorks.get(0));
							return true;
						}
						onMessageKick(work2, sender, kogo, text);
						sender.sendMessage(this.nachalo + ChatColor.WHITE + "Игрок уволен!");
						main.SQL.setrabota(kogo, WorkFactory.loadedWorks.get(0));
						Bukkit.getPlayer(kogo).sendMessage(
								this.nachalo + ChatColor.WHITE + "Вас уволил(а) " + sender.getName() + " за: " + text);
						if (Bukkit.getPlayer(kogo) != null) {
							Bukkit.getPlayer(kogo).sendMessage(this.nachalo + ChatColor.WHITE + "Вы уволены!");
						}
					}
					if (args[0].equalsIgnoreCase("select")) {
						Player p = (Player) sender;
						try {
							ItemStack book = p.getInventory().getItemInMainHand();
							if (book.getType() != Material.WRITTEN_BOOK) {
								sender.sendMessage(nachalo + ChatColor.WHITE
										+ "Вы должны приобрести паспорт! Если он у вас есть, возьмите его в руки.");
								return true;
							}
							BookMeta bm = (BookMeta) book.getItemMeta();
							if (!p.getInventory().getItemInMainHand().getType().equals(Material.WRITTEN_BOOK)) {
								sender.sendMessage(nachalo + ChatColor.WHITE
										+ "Вы должны приобрести паспорт! Если он у вас есть, возьмите его в руки.");
								return true;
							}
							if (!bm.getTitle().equalsIgnoreCase(
									ChatColor.GOLD + "Паспорт " + ChatColor.YELLOW + sender.getName())) {
								sender.sendMessage(nachalo + ChatColor.WHITE
										+ "Вы должны приобрести паспорт! Если он у вас есть, возьмите его в руки.");
								return true;
							}
						} catch (ClassCastException ignored) {
							sender.sendMessage(nachalo + ChatColor.WHITE
									+ "Вы должны приобрести паспорт! Если он у вас есть, возьмите его в руки.");
							ignored.printStackTrace();
							return true;
						} catch (NullPointerException ignored) {
							sender.sendMessage(nachalo + ChatColor.WHITE
									+ "Вы должны приобрести паспорт! Если он у вас есть, возьмите его в руки.");
							ignored.printStackTrace();
							return true;
						}
						if (main.SQL.getMedCart(sender.getName()).equals(Med.NONE)) {
							sender.sendMessage(
									nachalo + ChatColor.WHITE + "У вас нету мед.карты, заведите её у доктора!");
							return true;
						}
						if (main.SQL.getMedCart(sender.getName()).equals(Med.NO)) {
							sender.sendMessage(nachalo + ChatColor.WHITE + "Вы болен!");
							return true;
						}
						final String n = args[1].toLowerCase();
						final Work work2 = main.SQL.getPlayerRabota(sender.getName());
						if (work2.getCode() != 0) {
							sender.sendMessage(this.nachalo + ChatColor.WHITE + "Вы уже состоите на работе!");
							return true;
						}
						final String s = n;
						switch (s) {
						case "мэрия": {
							if (plugin.getConfig().getStringList("BlackListMeria").contains(sender.getName())) {
								sender.sendMessage(nachalo + ChatColor.WHITE + "Вы в ЧС данной фракции!");
								return true;
							}
							final Work wrk2 = WorkFactory.loadedWorks.get(Fraction.MERIA.getDefaultwork());
							if (wrk2.getEducation().getCode() > main.SQL.getPlayerEducation(sender.getName())
									.getCode()) {
								sender.sendMessage(this.nachalo + ChatColor.WHITE
										+ "Недостаточное образование для вступления на работу!");
								return true;
							}
							main.SQL.setrabota(sender.getName(), wrk2);
							((Player) sender).kickPlayer(nachalo + ChatColor.WHITE
									+ "Пожалуйста, перезайдите для прогрузки вашего нового звания..");
							sender.sendMessage(this.nachalo + ChatColor.WHITE + "Вы устроились на работу в "
									+ Fraction.MERIA.getName() + "!");
							RaEvents.meria.forEach(p4 -> p4
									.sendMessage(this.nachalo + ChatColor.WHITE + "[Мэрия: Информация] " + "Игрок "
											+ sender.getName() + " вступил(а) в нашу фракцию! Пожелаем ему удачи."));
							return true;
						}
						case "полиция": {
							if (plugin.getConfig().getStringList("BlackListPolice").contains(sender.getName())) {
								sender.sendMessage(nachalo + ChatColor.WHITE + "Вы в ЧС данной фракции!");
								return true;
							}
							if (!main.SQL.getBilet(p.getName()).getName().contains("Есть")) {
								sender.sendMessage(nachalo + ChatColor.WHITE
										+ "Вы должны отслужить в армии для вступлении в полицию!");
								return true;
							}
							final Work wrk3 = WorkFactory.loadedWorks.get(Fraction.POLICE.getDefaultwork());
							if (wrk3.getEducation().getCode() > main.SQL.getPlayerEducation(sender.getName())
									.getCode()) {
								sender.sendMessage(this.nachalo + ChatColor.WHITE
										+ "Недостаточное образование для вступления на работу!");
								return true;
							}
							main.SQL.setrabota(sender.getName(), wrk3);
							((Player) sender).kickPlayer(nachalo + ChatColor.WHITE
									+ "Пожалуйста, перезайдите для прогрузки вашего нового звания..");
							RaEvents.police.forEach(p4 -> p4
									.sendMessage(this.nachalo + ChatColor.WHITE + "[Полиция: Информация] " + "Игрок "
											+ sender.getName() + " вступил(а) в нашу фракцию! Пожелаем ему удачи."));
							sender.sendMessage(this.nachalo + ChatColor.WHITE + "Вы устроились на работу в "
									+ Fraction.POLICE.getName() + "!");
							return true;
						}
						case "больница": {
							if (plugin.getConfig().getStringList("BlackListHospital").contains(sender.getName())) {
								sender.sendMessage(nachalo + ChatColor.WHITE + "Вы в ЧС данной фракции!");
								return true;
							}
							final Work wrk4 = WorkFactory.loadedWorks.get(Fraction.HOSPITAL.getDefaultwork());
							if (wrk4.getEducation().getCode() > main.SQL.getPlayerEducation(sender.getName())
									.getCode()) {
								sender.sendMessage(this.nachalo + ChatColor.WHITE
										+ "Недостаточное образование для вступления на работу!");
								return true;
							}
							main.SQL.setrabota(sender.getName(), wrk4);
							sender.sendMessage(this.nachalo + ChatColor.WHITE + "Вы устроились на работу в "
									+ Fraction.HOSPITAL.getName() + "!");
							((Player) sender).kickPlayer(nachalo + ChatColor.WHITE
									+ "Пожалуйста, перезайдите для прогрузки вашего нового звания..");
							RaEvents.hospital.forEach(p4 -> p4
									.sendMessage(this.nachalo + ChatColor.WHITE + "[Больница: Информация] " + "Игрок "
											+ sender.getName() + " вступил(а) в нашу фракцию! Пожелаем ему удачи."));
							return true;
						}
						case "армия": {
							if (plugin.getConfig().getStringList("BlackListArmia").contains(sender.getName())) {
								sender.sendMessage(nachalo + ChatColor.WHITE + "Вы в ЧС данной фракции!");
								return true;
							}
							final Work wrk4 = WorkFactory.loadedWorks.get(Fraction.ARMIA.getDefaultwork());
							if (wrk4.getEducation().getCode() > main.SQL.getPlayerEducation(sender.getName())
									.getCode()) {
								sender.sendMessage(this.nachalo + ChatColor.WHITE
										+ "Недостаточное образование для вступления на работу!");
								return true;
							}
							main.SQL.setrabota(sender.getName(), wrk4);
							sender.sendMessage(this.nachalo + ChatColor.WHITE + "Вы устроились на работу в "
									+ Fraction.ARMIA.getName() + "!");
							((Player) sender).kickPlayer(nachalo + ChatColor.WHITE
									+ "Пожалуйста, перезайдите для прогрузки вашего нового звания..");
							RaEvents.armia.forEach(p4 -> p4
									.sendMessage(this.nachalo + ChatColor.WHITE + "[Армия: Информация] " + "Игрок "
											+ sender.getName() + " вступил(а) в нашу фракцию! Пожелаем ему удачи."));
							return true;
						}
						default: {
							sender.sendMessage(this.nachalo + ChatColor.WHITE + "Неверно указана фракция!");
							return true;
						}
						}
					}
				}
			}
		}
		return true;
	}

	@SuppressWarnings("static-access")
	protected void onMessageKick(Work work2, CommandSender sender, String kogo, String text) {
		if (main.SQL.getPlayerRabota(sender.getName()).getFraction().equals(Fraction.POLICE)) {
			RaEvents.police.forEach(p -> p.sendMessage(this.nachalo + ChatColor.WHITE + "[Полиция: Информация] "
					+ work2.getName() + " " + sender.getName() + " уволил работника " + kogo + " по причине: " + text));
			return;
		}
		if (main.SQL.getPlayerRabota(sender.getName()).getFraction().equals(Fraction.MERIA)) {
			RaEvents.meria.forEach(p -> p.sendMessage(this.nachalo + ChatColor.WHITE + "[Мэрия: Информация] "
					+ work2.getName() + " " + sender.getName() + " уволил работника " + kogo + " по причине: " + text));
			return;
		}
		if (main.SQL.getPlayerRabota(sender.getName()).getFraction().equals(Fraction.HOSPITAL)) {
			RaEvents.hospital.forEach(p -> p.sendMessage(this.nachalo + ChatColor.WHITE + "[Больница: Информация] "
					+ work2.getName() + " " + sender.getName() + " уволил работника " + kogo + " по причине: " + text));
			return;
		}
		if (main.SQL.getPlayerRabota(sender.getName()).getFraction().equals(Fraction.ARMIA)) {
			RaEvents.armia.forEach(p -> p.sendMessage(this.nachalo + ChatColor.WHITE + "[Армия: Информация] "
					+ work2.getName() + " " + sender.getName() + " уволил работника " + kogo + " по причине: " + text));
			return;
		}
	}

	@SuppressWarnings("static-access")
	protected void onMessageUp(Work work2, CommandSender sender, String kogo, Player p, Work wrk) {
		if (main.SQL.getPlayerRabota(sender.getName()).getFraction().equals(Fraction.POLICE)) {
			RaEvents.police.forEach(
					p2 -> p2.sendMessage(this.nachalo + ChatColor.WHITE + "[Полиция: Информация] " + work2.getName()
							+ " " + p.getName() + " повысил(а) до звания " + wrk.getName() + " работника " + kogo));
			return;
		}
		if (main.SQL.getPlayerRabota(sender.getName()).getFraction().equals(Fraction.MERIA)) {
			RaEvents.meria.forEach(
					p2 -> p2.sendMessage(this.nachalo + ChatColor.WHITE + "[Мэрия: Информация] " + work2.getName() + " "
							+ p.getName() + " повысил(а) до звания " + wrk.getName() + " работника " + kogo));
			return;
		}
		if (main.SQL.getPlayerRabota(sender.getName()).getFraction().equals(Fraction.HOSPITAL)) {
			RaEvents.hospital.forEach(
					p2 -> p2.sendMessage(this.nachalo + ChatColor.WHITE + "[Больница: Информация] " + work2.getName()
							+ " " + p.getName() + " повысил(а) до звания " + wrk.getName() + " работника " + kogo));
			return;
		}
		if (main.SQL.getPlayerRabota(sender.getName()).getFraction().equals(Fraction.ARMIA)) {
			RaEvents.armia.forEach(
					p2 -> p2.sendMessage(this.nachalo + ChatColor.WHITE + "[Армия: Информация] " + work2.getName() + " "
							+ p.getName() + " повысил(а) до звания " + wrk.getName() + " работника " + kogo));
			return;
		}
	}
}
