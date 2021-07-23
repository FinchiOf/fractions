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
	String da = "���";
	Plugin plugin = main.getPlugin(main.class);
	static String nachalo;

	public CommandWork() {
		nachalo = "" + ChatColor.AQUA + ChatColor.BOLD + "������ > ";
	}

	public boolean onCommand(CommandSender sender, Command cmd, String commandlabel, String[] args) {
		if (cmd.getName().equalsIgnoreCase("work")) {
			if (args.length == 1 && args[0].equalsIgnoreCase("online")) {
				if (main.SQL.getPlayerRabota(sender.getName()).getFraction() == Fraction.NONE) {
					sender.sendMessage(nachalo + ChatColor.WHITE+"�� �� ��������� ���.����������");
					return true;
				}
				sender.sendMessage(nachalo + ChatColor.WHITE+"��������� ������:");
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
				sender.sendMessage(nachalo + ChatColor.WHITE + "/work select - ���������� ������������ �������.");
				sender.sendMessage(nachalo + ChatColor.WHITE + "/work select [��������] - ������� �������.");
				sender.sendMessage(nachalo + ChatColor.WHITE + "/work leave - ���� � ������.");
				sender.sendMessage(nachalo + ChatColor.WHITE + "/work online - ���������� ���� ����������� ������.");
				Work work = main.SQL.getPlayerRabota(sender.getName());
				AccessLevel[] levels = work.getAccess();
				boolean f1 = AccessLevel.contains(levels, AccessLevel.KICKHOSPITAL)
						|| AccessLevel.contains(levels, AccessLevel.KICKMERIA)
						|| AccessLevel.contains(levels, AccessLevel.KICKPOLICE) || sender.isOp();
				boolean f2 = AccessLevel.contains(levels, AccessLevel.UPHOSPITAL)
						|| AccessLevel.contains(levels, AccessLevel.UPMERIA)
						|| AccessLevel.contains(levels, AccessLevel.UPPOLICE) || sender.isOp();
				if (f1) {
					sender.sendMessage(nachalo + ChatColor.WHITE + "/work kick [���] - ������� ������ � ������.");
				}

				if (f2) {
					sender.sendMessage(nachalo + ChatColor.WHITE + "/work promote [���] - ������� ������ � ���������.");
				}

				Player p = (Player) sender;
				sender.sendMessage(nachalo + ChatColor.WHITE + "������ �� " + ChatColor.AQUA + work.getName()
						+ ChatColor.WHITE + " �� ������� " + ChatColor.AQUA + work.getFraction().getName()
						+ ChatColor.WHITE + ".");
				sender.sendMessage("   ");
				sender.sendMessage(ChatColor.WHITE + "���� �����:");
				AccessLevel[] var41;
				int var39 = (var41 = work.getAccess()).length;

				for (int var40 = 0; var40 < var39; ++var40) {
					AccessLevel ac = var41[var40];
					sender.sendMessage(ChatColor.AQUA + ac.getName());
				}

				String a = nachalo + ChatColor.WHITE + "��������� ����: " + ChatColor.AQUA;
				switch (work.getNext()) {
				case -1:
					a = a + "�� �� ������������/������� �����";
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
					sender.sendMessage(nachalo + ChatColor.WHITE + "� ��� ��� ������!");
					return true;
				}

				if (main.SQL.getPlayerRabota(sender.getName()).getFraction().equals(Fraction.POLICE)) {
					RaEvents.police.forEach((p3) -> {
						p3.sendMessage(nachalo + ChatColor.WHITE + "[�������: ����������] " + "�������� "
								+ sender.getName() + " ���� �� �/� � ����� �������!");
					});
					RaEvents.police.remove((Player) sender);
				}

				if (main.SQL.getPlayerRabota(sender.getName()).getFraction().equals(Fraction.MERIA)) {
					RaEvents.meria.forEach((p3) -> {
						p3.sendMessage(nachalo + ChatColor.WHITE + "[�����: ����������] " + "�������� "
								+ sender.getName() + " ���� �� �/� � ����� �������!");
					});
					RaEvents.meria.remove((Player) sender);
				}

				if (main.SQL.getPlayerRabota(sender.getName()).getFraction().equals(Fraction.HOSPITAL)) {
					RaEvents.hospital.forEach((p3) -> {
						p3.sendMessage(nachalo + ChatColor.WHITE + "[��������: ����������] " + "�������� "
								+ sender.getName() + " ���� �� �/� � ����� �������!");
					});
					RaEvents.hospital.remove((Player) sender);
				}

				main.SQL.setrabota(sender.getName(), (Work) WorkFactory.loadedWorks.get(0));
				sender.sendMessage(nachalo + ChatColor.WHITE + "�� ���� � ������!");
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
					sender.sendMessage(nachalo + ChatColor.WHITE + "��� ����!");
					return true;
				}

				if (!main.SQL.hasValue(text)) {
					sender.sendMessage(nachalo + ChatColor.WHITE + "����� ������ ���!");
					return true;
				}

				if (text.equalsIgnoreCase(sender.getName())) {
					sender.sendMessage(nachalo + ChatColor.WHITE + "�� �� ������ �������� ����!");
					return true;
				}

				if (work3.getNext() == -1) {
					sender.sendMessage(nachalo + ChatColor.WHITE + "����� ������ ������ ��������!");
					return true;
				}

				if (sender.isOp()) {
					this.onMessageUp(work2, sender, text, p, wrk);
					main.SQL.setrabota(text, (Work) WorkFactory.loadedWorks.get(work3.getNext()));
					return true;
				}

				if (!work3.getFraction().equals(work2.getFraction())) {
					sender.sendMessage(nachalo + ChatColor.WHITE + "����� ������ ������ ��������!");
					return true;
				}

				if (wrk.getEducation().getCode() > main.SQL.getPlayerEducation(text).getCode()) {
					sender.sendMessage(nachalo + ChatColor.WHITE
							+ "� ������ ������������� ����������� ����� �������� �� ��� ���������!");
					return true;
				}

				this.onMessageUp(work2, sender, text, p, wrk);
				main.SQL.setrabota(text, wrk);
				if (Bukkit.getPlayer(text) != null) {
					Bukkit.getPlayer(text).sendMessage(nachalo + ChatColor.WHITE + "�� ��������!");
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
					sender.sendMessage(nachalo + ChatColor.WHITE + "��� ����!");
					return true;
				}

				if (!main.SQL.hasValue(n)) {
					sender.sendMessage(nachalo + ChatColor.WHITE + "����� ������ ���!");
					return true;
				}

				if (n.equalsIgnoreCase(sender.getName())) {
					sender.sendMessage(nachalo + ChatColor.WHITE + "�� �� ������ ������� ����!");
					return true;
				}

				wrk4 = main.SQL.getPlayerRabota(n);
				if (!wrk4.getFraction().equals(work2.getFraction())) {
					sender.sendMessage(nachalo + ChatColor.WHITE + "����� ������ ������ �������!");
					return true;
				}

				if (AccessLevel.contains(wrk4.getAccess(), AccessLevel.UNKICKABLE)) {
					sender.sendMessage(nachalo + ChatColor.WHITE + "����� ������ ������ �������!");
					return true;
				}

				if (sender.isOp()) {
					this.onMessageKick(work2, sender, n, text);
					sender.sendMessage(nachalo + ChatColor.WHITE + "����� ������!");
					main.SQL.setrabota(n, (Work) WorkFactory.loadedWorks.get(0));
					return true;
				}

				this.onMessageKick(work2, sender, n, text);
				sender.sendMessage(nachalo + ChatColor.WHITE + "����� ������!");
				main.SQL.setrabota(n, (Work) WorkFactory.loadedWorks.get(0));
				Bukkit.getPlayer(n)
						.sendMessage(nachalo + ChatColor.WHITE + "��� ������(�) " + sender.getName() + " ��: " + text);
				if (Bukkit.getPlayer(n) != null) {
					Bukkit.getPlayer(n).sendMessage(nachalo + ChatColor.WHITE + "�� �������!");
				}
			}

			if (args[0].equalsIgnoreCase("select")) {
            	p = (Player) sender;
        		try {
        		ItemStack book = p.getInventory().getItemInMainHand();
        		if(book.getType() != Material.WRITTEN_BOOK) {
            		sender.sendMessage(nachalo + ChatColor.WHITE + "�� ������ ���������� �������! ���� �� � ��� ����, �������� ��� � ����.");
        		    return true;
        		}
        		BookMeta bm = (BookMeta) book.getItemMeta();
            	if(!p.getInventory().getItemInMainHand().getType().equals(Material.WRITTEN_BOOK)) {
            		sender.sendMessage(nachalo + ChatColor.WHITE + "�� ������ ���������� �������! ���� �� � ��� ����, �������� ��� � ����.");
        		    return true;
            	}
               if(!bm.getTitle().equalsIgnoreCase(ChatColor.GOLD + "������� " + ChatColor.YELLOW + sender.getName())) {
            		sender.sendMessage(nachalo + ChatColor.WHITE + "�� ������ ���������� �������! ���� �� � ��� ����, �������� ��� � ����.");
            		return true;
            	}
        		}catch(ClassCastException ignored) {
            		sender.sendMessage(nachalo + ChatColor.WHITE + "�� ������ ���������� �������! ���� �� � ��� ����, �������� ��� � ����.");
        			ignored.printStackTrace();
            		return true;
        		}catch(NullPointerException ignored) {                			
            		sender.sendMessage(nachalo + ChatColor.WHITE + "�� ������ ���������� �������! ���� �� � ��� ����, �������� ��� � ����.");
        			ignored.printStackTrace();
            		return true;
        		}

				if (main.SQL.getMedCart(sender.getName()).equals(Med.NONE)) {
					sender.sendMessage(nachalo + ChatColor.WHITE + "� ��� ���� ���.�����, �������� � � �������!");
					return true;
				}

				if (main.SQL.getMedCart(sender.getName()).equals(Med.NO)) {
					sender.sendMessage(nachalo + ChatColor.WHITE + "�� �����!");
					return true;
				}

				n = args[1].toLowerCase();
				work2 = main.SQL.getPlayerRabota(sender.getName());
				if (work2.getCode() != 0) {
					sender.sendMessage(nachalo + ChatColor.WHITE + "�� ��� �������� �� ������!");
					return true;
				}

				Player peha;
				Iterator var17;
				switch (n.hashCode()) {
				case 408780001:
					if (n.equals("�������")) {
						if (this.plugin.getConfig().getStringList("BlackListPolice").contains(sender.getName())) {
							sender.sendMessage(nachalo + ChatColor.WHITE + "�� � �� ������ �������!");
							return true;
						}

						wrk4 = (Work) WorkFactory.loadedWorks.get(Fraction.POLICE.getDefaultwork());
						if (wrk4.getEducation().getCode() > main.SQL.getPlayerEducation(sender.getName()).getCode()) {
							sender.sendMessage(
									nachalo + ChatColor.WHITE + "������������� ����������� ��� ���������� �� ������!");
							return true;
						}

						main.SQL.setrabota(sender.getName(), wrk4);
						((Player) sender).kickPlayer(nachalo + ChatColor.WHITE
								+ "����������, ����������� ��� ��������� ������ ������ ������..");
						RaEvents.police.forEach((p4) -> {
							p4.sendMessage(nachalo + ChatColor.WHITE + "[�������: ����������] " + "����� "
									+ sender.getName() + " �������(�) � ���� �������! �������� ��� �����.");
						});
						sender.sendMessage(nachalo + ChatColor.WHITE + "�� ���������� �� ������ � "
								+ Fraction.POLICE.getName() + "!");
						var17 = Bukkit.getOnlinePlayers().iterator();

						while (var17.hasNext()) {
							peha = (Player) var17.next();
							if (main.SQL.getPlayerRabota(peha.getName()).getFraction().equals(Fraction.POLICE)) {
								peha.sendMessage(nachalo + ChatColor.WHITE + "[�������: ����������] " + "����� "
										+ sender.getName() + " �������(�) � ���� �������! �������� ��� �����.");
							}
						}

						return true;
					}
					break;
				case 1034976806:
					if (n.equals("�����")) {
						if (this.plugin.getConfig().getStringList("BlackListMeria").contains(sender.getName())) {
							sender.sendMessage(nachalo + ChatColor.WHITE + "�� � �� ������ �������!");
							return true;
						}

						wrk4 = (Work) WorkFactory.loadedWorks.get(Fraction.MERIA.getDefaultwork());
						if (wrk4.getEducation().getCode() > main.SQL.getPlayerEducation(sender.getName()).getCode()) {
							sender.sendMessage(
									nachalo + ChatColor.WHITE + "������������� ����������� ��� ���������� �� ������!");
							return true;
						}

						main.SQL.setrabota(sender.getName(), wrk4);
						((Player) sender).kickPlayer(nachalo + ChatColor.WHITE
								+ "����������, ����������� ��� ��������� ������ ������ ������..");
						sender.sendMessage(nachalo + ChatColor.WHITE + "�� ���������� �� ������ � "
								+ Fraction.MERIA.getName() + "!");
						RaEvents.meria.forEach((p4) -> {
							p4.sendMessage(nachalo + ChatColor.WHITE + "[�����: ����������] " + "����� "
									+ sender.getName() + " �������(�) � ���� �������! �������� ��� �����.");
						});
						return true;
					}
					break;
				case 1175940323:
					if (n.equals("��������")) {
						if (this.plugin.getConfig().getStringList("BlackListHospital").contains(sender.getName())) {
							sender.sendMessage(nachalo + ChatColor.WHITE + "�� � �� ������ �������!");
							return true;
						}

						wrk4 = (Work) WorkFactory.loadedWorks.get(Fraction.HOSPITAL.getDefaultwork());
						if (wrk4.getEducation().getCode() > main.SQL.getPlayerEducation(sender.getName()).getCode()) {
							sender.sendMessage(
									nachalo + ChatColor.WHITE + "������������� ����������� ��� ���������� �� ������!");
							return true;
						}

						main.SQL.setrabota(sender.getName(), wrk4);
						sender.sendMessage(nachalo + ChatColor.WHITE + "�� ���������� �� ������ � "
								+ Fraction.HOSPITAL.getName() + "!");
						((Player) sender).kickPlayer(nachalo + ChatColor.WHITE
								+ "����������, ����������� ��� ��������� ������ ������ ������..");
						RaEvents.hospital.forEach((p4) -> {
							p4.sendMessage(nachalo + ChatColor.WHITE + "[��������: ����������] " + "����� "
									+ sender.getName() + " �������(�) � ���� �������! �������� ��� �����.");
						});
						var17 = Bukkit.getOnlinePlayers().iterator();

						while (var17.hasNext()) {
							peha = (Player) var17.next();
							if (main.SQL.getPlayerRabota(peha.getName()).getFraction().equals(Fraction.HOSPITAL)) {
								peha.sendMessage(nachalo + ChatColor.WHITE + "[��������: ����������] " + "����� "
										+ sender.getName() + " �������(�) � ���� �������! �������� ��� �����.");
							}
						}

						return true;
					}
				}

				sender.sendMessage(nachalo + ChatColor.WHITE + "������� ������� �������!");
				return true;
			}
		}

		return true;
	}

	protected void onMessageKick(Work work2, CommandSender sender, String kogo, String text) {
		if (main.SQL.getPlayerRabota(sender.getName()).getFraction().equals(Fraction.POLICE)) {
			RaEvents.police.forEach((p) -> {
				p.sendMessage(nachalo + ChatColor.WHITE + "[�������: ����������] " + work2.getName() + " "
						+ sender.getName() + " ������ ��������� " + kogo + " �� �������: " + text);
			});
		} else if (main.SQL.getPlayerRabota(sender.getName()).getFraction().equals(Fraction.MERIA)) {
			RaEvents.meria.forEach((p) -> {
				p.sendMessage(nachalo + ChatColor.WHITE + "[�����: ����������] " + work2.getName() + " "
						+ sender.getName() + " ������ ��������� " + kogo + " �� �������: " + text);
			});
		} else if (main.SQL.getPlayerRabota(sender.getName()).getFraction().equals(Fraction.HOSPITAL)) {
			RaEvents.hospital.forEach((p) -> {
				p.sendMessage(nachalo + ChatColor.WHITE + "[��������: ����������] " + work2.getName() + " "
						+ sender.getName() + " ������ ��������� " + kogo + " �� �������: " + text);
			});
		}
	}

	protected void onMessageUp(Work work2, CommandSender sender, String kogo, Player p, Work wrk) {
		if (main.SQL.getPlayerRabota(sender.getName()).getFraction().equals(Fraction.POLICE)) {
			RaEvents.police.forEach((p2) -> {
				p2.sendMessage(nachalo + ChatColor.WHITE + "[�������: ����������] " + work2.getName() + " "
						+ p.getName() + " �������(�) �� ������ " + wrk.getName() + " ��������� " + kogo);
			});
		} else if (main.SQL.getPlayerRabota(sender.getName()).getFraction().equals(Fraction.MERIA)) {
			RaEvents.meria.forEach((p2) -> {
				p2.sendMessage(nachalo + ChatColor.WHITE + "[�����: ����������] " + work2.getName() + " " + p.getName()
						+ " �������(�) �� ������ " + wrk.getName() + " ��������� " + kogo);
			});
		} else if (main.SQL.getPlayerRabota(sender.getName()).getFraction().equals(Fraction.HOSPITAL)) {
			RaEvents.hospital.forEach((p2) -> {
				p2.sendMessage(nachalo + ChatColor.WHITE + "[��������: ����������] " + work2.getName() + " "
						+ p.getName() + " �������(�) �� ������ " + wrk.getName() + " ��������� " + kogo);
			});
		}
	}
}
