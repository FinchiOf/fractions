package ru.Boss90.roleplay;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import java.util.Iterator;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.plugin.Plugin;
import ru.Boss90.roleplay.enums.AccessLevel;
import ru.Boss90.roleplay.enums.Fraction;
import ru.Boss90.roleplay.enums.Med;
import ru.Boss90.roleplay.interfaces.Work;
import ru.Boss90.roleplay.work.WorkFactory;

public class CommandWork implements CommandExecutor {
	String da = "кек";
	Plugin plugin = main.getPlugin(main.class);
	static String nachalo;

	public CommandWork() {
		nachalo = "" + ChatColor.AQUA + ChatColor.BOLD + "Работы > ";
	}

	public boolean onCommand(CommandSender sender, Command cmd, String commandlabel, String[] args) {
		if (cmd.getName().equalsIgnoreCase("work")) {
			if (args.length == 1 && args[0].equalsIgnoreCase("online")) {
				if (main.SQL.getPlayerRabota(sender.getName()).getFraction() == Fraction.NONE) {
					sender.sendMessage(nachalo + ChatColor.WHITE+"Вы не являетесь гос.работником");
					return true;
				}
				sender.sendMessage(nachalo + ChatColor.WHITE+"Работники онлайн:");
				if (main.SQL.getPlayerRabota(sender.getName()).getFraction() == Fraction.POLICE) {
					for (int i2 = 0; i2 < main.plugin.getConfig().getStringList("Police").size(); i2++)
						sender.sendMessage(ChatColor.AQUA+"- "+ChatColor.WHITE+main.SQL.getPlayerRabota(main.plugin.getConfig().getStringList("Police").remove(i2)).getName() +" "+main.plugin.getConfig().getStringList("Police").remove(i2));
					return true;
				}
				if (main.SQL.getPlayerRabota(sender.getName()).getFraction() == Fraction.HOSPITAL) {
					for (int i3 = 0; i3 < main.plugin.getConfig().getStringList("Hospital").size(); i3++)
						sender.sendMessage(ChatColor.AQUA+"- "+ChatColor.WHITE+main.SQL.getPlayerRabota(main.plugin.getConfig().getStringList("Hospital").remove(i3)).getName() +" "+main.plugin.getConfig().getStringList("Hospital").remove(i3));
					return true;
				}
				if (main.SQL.getPlayerRabota(sender.getName()).getFraction() == Fraction.MERIA) {
					for (int i4 = 0; i4 < main.plugin.getConfig().getStringList("Meria").size(); i4++)
						sender.sendMessage(ChatColor.AQUA+"- "+ChatColor.WHITE+main.SQL.getPlayerRabota(main.plugin.getConfig().getStringList("Meria").remove(i4)).getName() +" "+main.plugin.getConfig().getStringList("Meria").remove(i4));
					return true;
				}
				return true;
			}

			if (args.length == 0) {
				sender.sendMessage(nachalo + ChatColor.WHITE + "/work select - Посмотреть существующие фракции.");
				sender.sendMessage(nachalo + ChatColor.WHITE + "/work select [Название] - выбрать фракцию.");
				sender.sendMessage(nachalo + ChatColor.WHITE + "/work leave - Уйти с работы.");
				sender.sendMessage(nachalo + ChatColor.WHITE + "/work online - Посмотреть всех сотрудников онлайн.");
				Work work = main.SQL.getPlayerRabota(sender.getName());
				AccessLevel[] levels = work.getAccess();
				boolean f1 = AccessLevel.contains(levels, AccessLevel.KICKHOSPITAL)
						|| AccessLevel.contains(levels, AccessLevel.KICKMERIA)
						|| AccessLevel.contains(levels, AccessLevel.KICKPOLICE) || sender.isOp();
				boolean f2 = AccessLevel.contains(levels, AccessLevel.UPHOSPITAL)
						|| AccessLevel.contains(levels, AccessLevel.UPMERIA)
						|| AccessLevel.contains(levels, AccessLevel.UPPOLICE) || sender.isOp();
				if (f1) {
					sender.sendMessage(nachalo + ChatColor.WHITE + "/work kick [Ник] - Уволить игрока с работы.");
				}

				if (f2) {
					sender.sendMessage(nachalo + ChatColor.WHITE + "/work promote [Ник] - Поднять игрока в должности.");
				}

				Player p = (Player) sender;
				sender.sendMessage(nachalo + ChatColor.WHITE + "Сейчас вы " + ChatColor.AQUA + work.getName()
						+ ChatColor.WHITE + " во фракции " + ChatColor.AQUA + work.getFraction().getName()
						+ ChatColor.WHITE + ".");
				sender.sendMessage("   ");
				sender.sendMessage(ChatColor.WHITE + "Ваши права:");
				AccessLevel[] var41;
				int var39 = (var41 = work.getAccess()).length;

				for (int var40 = 0; var40 < var39; ++var40) {
					AccessLevel ac = var41[var40];
					sender.sendMessage(ChatColor.AQUA + ac.getName());
				}

				String a = nachalo + ChatColor.WHITE + "Следующий ранг: " + ChatColor.AQUA;
				switch (work.getNext()) {
				case -1:
					a = a + "Вы на максимальном/нулевом ранге";
					break;
				default:
					a = a + ((Work) WorkFactory.loadedWorks.get(work.getNext())).getName();
				}

				sender.sendMessage("    ");
				sender.sendMessage(a);
				return true;
			}

			int i;
			if (args.length == 1 && args[0].equalsIgnoreCase("select")) {
				Fraction[] var31;
				int var28 = (var31 = Fraction.values()).length;

				for (i = 0; i < var28; ++i) {
					Fraction fraction = var31[i];
					if (!fraction.equals(Fraction.NONE)) {
						sender.sendMessage(nachalo + ChatColor.WHITE + fraction.getName());
					}
				}

				return true;
			}

			if (args.length == 1 && args[0].equalsIgnoreCase("leave")) {
				if (main.SQL.getPlayerRabota(sender.getName()).getCode() == 0) {
					sender.sendMessage(nachalo + ChatColor.WHITE + "У вас нет работы!");
					return true;
				}

				if (main.SQL.getPlayerRabota(sender.getName()).getFraction().equals(Fraction.POLICE)) {
					RaEvents.police.forEach((p3) -> {
						p3.sendMessage(nachalo + ChatColor.WHITE + "[Полиция: Информация] " + "Работник "
								+ sender.getName() + " ушёл по С/Ж с нашей фракции!");
					});
					RaEvents.police.remove((Player) sender);
				}

				if (main.SQL.getPlayerRabota(sender.getName()).getFraction().equals(Fraction.MERIA)) {
					RaEvents.meria.forEach((p3) -> {
						p3.sendMessage(nachalo + ChatColor.WHITE + "[Мэрия: Информация] " + "Работник "
								+ sender.getName() + " ушёл по С/Ж с нашей фракции!");
					});
					RaEvents.meria.remove((Player) sender);
				}

				if (main.SQL.getPlayerRabota(sender.getName()).getFraction().equals(Fraction.HOSPITAL)) {
					RaEvents.hospital.forEach((p3) -> {
						p3.sendMessage(nachalo + ChatColor.WHITE + "[Больница: Информация] " + "Работник "
								+ sender.getName() + " ушёл по С/Ж с нашей фракции!");
					});
					RaEvents.hospital.remove((Player) sender);
				}

				main.SQL.setrabota(sender.getName(), (Work) WorkFactory.loadedWorks.get(0));
				sender.sendMessage(nachalo + ChatColor.WHITE + "Вы ушли с работы!");
				return true;
			}

			Player p;
			String text;
			if (args.length == 2 && args[0].equalsIgnoreCase("promote")) {
				p = (Player) sender;
				text = args[1];
				Work work2 = main.SQL.getPlayerRabota(sender.getName());
				AccessLevel[] levels2 = work2.getAccess();
				Work work3 = main.SQL.getPlayerRabota(text);
				Work wrk = (Work) WorkFactory.loadedWorks.get(work3.getNext());
				boolean f2 = AccessLevel.contains(levels2, AccessLevel.UPHOSPITAL)
						|| AccessLevel.contains(levels2, AccessLevel.UPMERIA)
						|| AccessLevel.contains(levels2, AccessLevel.UPPOLICE) || sender.isOp();
				if (!f2) {
					sender.sendMessage(nachalo + ChatColor.WHITE + "Нет прав!");
					return true;
				}

				if (!main.SQL.hasValue(text)) {
					sender.sendMessage(nachalo + ChatColor.WHITE + "Этого игрока нет!");
					return true;
				}

				if (text.equalsIgnoreCase(sender.getName())) {
					sender.sendMessage(nachalo + ChatColor.WHITE + "Вы не можете повысить себя!");
					return true;
				}

				if (work3.getNext() == -1) {
					sender.sendMessage(nachalo + ChatColor.WHITE + "Этого игрока нельзя повысить!");
					return true;
				}

				if (sender.isOp()) {
					this.onMessageUp(work2, sender, text, p, wrk);
					main.SQL.setrabota(text, (Work) WorkFactory.loadedWorks.get(work3.getNext()));
					return true;
				}

				if (!work3.getFraction().equals(work2.getFraction())) {
					sender.sendMessage(nachalo + ChatColor.WHITE + "Этого игрока нельзя повысить!");
					return true;
				}

				if (wrk.getEducation().getCode() > main.SQL.getPlayerEducation(text).getCode()) {
					sender.sendMessage(nachalo + ChatColor.WHITE
							+ "У игрока недостаточное образование чтобы вступить на эту должность!");
					return true;
				}

				this.onMessageUp(work2, sender, text, p, wrk);
				main.SQL.setrabota(text, wrk);
				if (Bukkit.getPlayer(text) != null) {
					Bukkit.getPlayer(text).sendMessage(nachalo + ChatColor.WHITE + "Вы повышены!");
				}
			}

			String n;
			Work work2;
			Work wrk4;
			if (args[0].equalsIgnoreCase("kick")) {
				StringBuilder sb = new StringBuilder();

				for (i = 2; i < args.length; ++i) {
					sb.append(args[i]).append(' ');
				}

				text = sb.toString();
				n = args[1];
				work2 = main.SQL.getPlayerRabota(sender.getName());
				AccessLevel[] levels2 = work2.getAccess();
				boolean f2 = AccessLevel.contains(levels2, AccessLevel.KICKHOSPITAL)
						|| AccessLevel.contains(levels2, AccessLevel.KICKMERIA)
						|| AccessLevel.contains(levels2, AccessLevel.KICKPOLICE) || sender.isOp();
				if (!f2) {
					sender.sendMessage(nachalo + ChatColor.WHITE + "Нет прав!");
					return true;
				}

				if (!main.SQL.hasValue(n)) {
					sender.sendMessage(nachalo + ChatColor.WHITE + "Этого игрока нет!");
					return true;
				}

				if (n.equalsIgnoreCase(sender.getName())) {
					sender.sendMessage(nachalo + ChatColor.WHITE + "Вы не можете кикнуть себя!");
					return true;
				}

				wrk4 = main.SQL.getPlayerRabota(n);
				if (!wrk4.getFraction().equals(work2.getFraction())) {
					sender.sendMessage(nachalo + ChatColor.WHITE + "Этого игрока нельзя уволить!");
					return true;
				}

				if (AccessLevel.contains(wrk4.getAccess(), AccessLevel.UNKICKABLE)) {
					sender.sendMessage(nachalo + ChatColor.WHITE + "Этого игрока нельзя уволить!");
					return true;
				}

				if (sender.isOp()) {
					this.onMessageKick(work2, sender, n, text);
					sender.sendMessage(nachalo + ChatColor.WHITE + "Игрок уволен!");
					main.SQL.setrabota(n, (Work) WorkFactory.loadedWorks.get(0));
					return true;
				}

				this.onMessageKick(work2, sender, n, text);
				sender.sendMessage(nachalo + ChatColor.WHITE + "Игрок уволен!");
				main.SQL.setrabota(n, (Work) WorkFactory.loadedWorks.get(0));
				Bukkit.getPlayer(n)
						.sendMessage(nachalo + ChatColor.WHITE + "Вас уволил(а) " + sender.getName() + " за: " + text);
				if (Bukkit.getPlayer(n) != null) {
					Bukkit.getPlayer(n).sendMessage(nachalo + ChatColor.WHITE + "Вы уволены!");
				}
			}

			if (args[0].equalsIgnoreCase("select")) {
            	p = (Player) sender;
        		try {
        		ItemStack book = p.getInventory().getItemInMainHand();
        		if(book.getType() != Material.WRITTEN_BOOK) {
            		sender.sendMessage(nachalo + ChatColor.WHITE + "Вы должны приобрести паспорт! Если он у вас есть, возьмите его в руки.");
        		    return true;
        		}
        		BookMeta bm = (BookMeta) book.getItemMeta();
            	if(!p.getInventory().getItemInMainHand().getType().equals(Material.WRITTEN_BOOK)) {
            		sender.sendMessage(nachalo + ChatColor.WHITE + "Вы должны приобрести паспорт! Если он у вас есть, возьмите его в руки.");
        		    return true;
            	}
               if(!bm.getTitle().equalsIgnoreCase(ChatColor.GOLD + "Паспорт " + ChatColor.YELLOW + sender.getName())) {
            		sender.sendMessage(nachalo + ChatColor.WHITE + "Вы должны приобрести паспорт! Если он у вас есть, возьмите его в руки.");
            		return true;
            	}
        		}catch(ClassCastException ignored) {
            		sender.sendMessage(nachalo + ChatColor.WHITE + "Вы должны приобрести паспорт! Если он у вас есть, возьмите его в руки.");
        			ignored.printStackTrace();
            		return true;
        		}catch(NullPointerException ignored) {                			
            		sender.sendMessage(nachalo + ChatColor.WHITE + "Вы должны приобрести паспорт! Если он у вас есть, возьмите его в руки.");
        			ignored.printStackTrace();
            		return true;
        		}

				if (main.SQL.getMedCart(sender.getName()).equals(Med.NONE)) {
					sender.sendMessage(nachalo + ChatColor.WHITE + "У вас нету мед.карты, заведите её у доктора!");
					return true;
				}

				if (main.SQL.getMedCart(sender.getName()).equals(Med.NO)) {
					sender.sendMessage(nachalo + ChatColor.WHITE + "Вы болен!");
					return true;
				}

				n = args[1].toLowerCase();
				work2 = main.SQL.getPlayerRabota(sender.getName());
				if (work2.getCode() != 0) {
					sender.sendMessage(nachalo + ChatColor.WHITE + "Вы уже состоите на работе!");
					return true;
				}

				Player peha;
				Iterator var17;
				switch (n.hashCode()) {
				case 408780001:
					if (n.equals("полиция")) {
						if (this.plugin.getConfig().getStringList("BlackListPolice").contains(sender.getName())) {
							sender.sendMessage(nachalo + ChatColor.WHITE + "Вы в ЧС данной фракции!");
							return true;
						}

						wrk4 = (Work) WorkFactory.loadedWorks.get(Fraction.POLICE.getDefaultwork());
						if (wrk4.getEducation().getCode() > main.SQL.getPlayerEducation(sender.getName()).getCode()) {
							sender.sendMessage(
									nachalo + ChatColor.WHITE + "Недостаточное образование для вступления на работу!");
							return true;
						}

						main.SQL.setrabota(sender.getName(), wrk4);
						((Player) sender).kickPlayer(nachalo + ChatColor.WHITE
								+ "Пожалуйста, перезайдите для прогрузки вашего нового звания..");
						RaEvents.police.forEach((p4) -> {
							p4.sendMessage(nachalo + ChatColor.WHITE + "[Полиция: Информация] " + "Игрок "
									+ sender.getName() + " вступил(а) в нашу фракцию! Пожелаем ему удачи.");
						});
						sender.sendMessage(nachalo + ChatColor.WHITE + "Вы устроились на работу в "
								+ Fraction.POLICE.getName() + "!");
						var17 = Bukkit.getOnlinePlayers().iterator();

						while (var17.hasNext()) {
							peha = (Player) var17.next();
							if (main.SQL.getPlayerRabota(peha.getName()).getFraction().equals(Fraction.POLICE)) {
								peha.sendMessage(nachalo + ChatColor.WHITE + "[Полиция: Информация] " + "Игрок "
										+ sender.getName() + " вступил(а) в нашу фракцию! Пожелаем ему удачи.");
							}
						}

						return true;
					}
					break;
				case 1034976806:
					if (n.equals("мэрия")) {
						if (this.plugin.getConfig().getStringList("BlackListMeria").contains(sender.getName())) {
							sender.sendMessage(nachalo + ChatColor.WHITE + "Вы в ЧС данной фракции!");
							return true;
						}

						wrk4 = (Work) WorkFactory.loadedWorks.get(Fraction.MERIA.getDefaultwork());
						if (wrk4.getEducation().getCode() > main.SQL.getPlayerEducation(sender.getName()).getCode()) {
							sender.sendMessage(
									nachalo + ChatColor.WHITE + "Недостаточное образование для вступления на работу!");
							return true;
						}

						main.SQL.setrabota(sender.getName(), wrk4);
						((Player) sender).kickPlayer(nachalo + ChatColor.WHITE
								+ "Пожалуйста, перезайдите для прогрузки вашего нового звания..");
						sender.sendMessage(nachalo + ChatColor.WHITE + "Вы устроились на работу в "
								+ Fraction.MERIA.getName() + "!");
						RaEvents.meria.forEach((p4) -> {
							p4.sendMessage(nachalo + ChatColor.WHITE + "[Мэрия: Информация] " + "Игрок "
									+ sender.getName() + " вступил(а) в нашу фракцию! Пожелаем ему удачи.");
						});
						return true;
					}
					break;
				case 1175940323:
					if (n.equals("больница")) {
						if (this.plugin.getConfig().getStringList("BlackListHospital").contains(sender.getName())) {
							sender.sendMessage(nachalo + ChatColor.WHITE + "Вы в ЧС данной фракции!");
							return true;
						}

						wrk4 = (Work) WorkFactory.loadedWorks.get(Fraction.HOSPITAL.getDefaultwork());
						if (wrk4.getEducation().getCode() > main.SQL.getPlayerEducation(sender.getName()).getCode()) {
							sender.sendMessage(
									nachalo + ChatColor.WHITE + "Недостаточное образование для вступления на работу!");
							return true;
						}

						main.SQL.setrabota(sender.getName(), wrk4);
						sender.sendMessage(nachalo + ChatColor.WHITE + "Вы устроились на работу в "
								+ Fraction.HOSPITAL.getName() + "!");
						((Player) sender).kickPlayer(nachalo + ChatColor.WHITE
								+ "Пожалуйста, перезайдите для прогрузки вашего нового звания..");
						RaEvents.hospital.forEach((p4) -> {
							p4.sendMessage(nachalo + ChatColor.WHITE + "[Больница: Информация] " + "Игрок "
									+ sender.getName() + " вступил(а) в нашу фракцию! Пожелаем ему удачи.");
						});
						var17 = Bukkit.getOnlinePlayers().iterator();

						while (var17.hasNext()) {
							peha = (Player) var17.next();
							if (main.SQL.getPlayerRabota(peha.getName()).getFraction().equals(Fraction.HOSPITAL)) {
								peha.sendMessage(nachalo + ChatColor.WHITE + "[Больница: Информация] " + "Игрок "
										+ sender.getName() + " вступил(а) в нашу фракцию! Пожелаем ему удачи.");
							}
						}

						return true;
					}
				}

				sender.sendMessage(nachalo + ChatColor.WHITE + "Неверно указана фракция!");
				return true;
			}
		}

		return true;
	}

	protected void onMessageKick(Work work2, CommandSender sender, String kogo, String text) {
		if (main.SQL.getPlayerRabota(sender.getName()).getFraction().equals(Fraction.POLICE)) {
			RaEvents.police.forEach((p) -> {
				p.sendMessage(nachalo + ChatColor.WHITE + "[Полиция: Информация] " + work2.getName() + " "
						+ sender.getName() + " уволил работника " + kogo + " по причине: " + text);
			});
		} else if (main.SQL.getPlayerRabota(sender.getName()).getFraction().equals(Fraction.MERIA)) {
			RaEvents.meria.forEach((p) -> {
				p.sendMessage(nachalo + ChatColor.WHITE + "[Мэрия: Информация] " + work2.getName() + " "
						+ sender.getName() + " уволил работника " + kogo + " по причине: " + text);
			});
		} else if (main.SQL.getPlayerRabota(sender.getName()).getFraction().equals(Fraction.HOSPITAL)) {
			RaEvents.hospital.forEach((p) -> {
				p.sendMessage(nachalo + ChatColor.WHITE + "[Больница: Информация] " + work2.getName() + " "
						+ sender.getName() + " уволил работника " + kogo + " по причине: " + text);
			});
		}
	}

	protected void onMessageUp(Work work2, CommandSender sender, String kogo, Player p, Work wrk) {
		if (main.SQL.getPlayerRabota(sender.getName()).getFraction().equals(Fraction.POLICE)) {
			RaEvents.police.forEach((p2) -> {
				p2.sendMessage(nachalo + ChatColor.WHITE + "[Полиция: Информация] " + work2.getName() + " "
						+ p.getName() + " повысил(а) до звания " + wrk.getName() + " работника " + kogo);
			});
		} else if (main.SQL.getPlayerRabota(sender.getName()).getFraction().equals(Fraction.MERIA)) {
			RaEvents.meria.forEach((p2) -> {
				p2.sendMessage(nachalo + ChatColor.WHITE + "[Мэрия: Информация] " + work2.getName() + " " + p.getName()
						+ " повысил(а) до звания " + wrk.getName() + " работника " + kogo);
			});
		} else if (main.SQL.getPlayerRabota(sender.getName()).getFraction().equals(Fraction.HOSPITAL)) {
			RaEvents.hospital.forEach((p2) -> {
				p2.sendMessage(nachalo + ChatColor.WHITE + "[Больница: Информация] " + work2.getName() + " "
						+ p.getName() + " повысил(а) до звания " + wrk.getName() + " работника " + kogo);
			});
		}
	}
}
