package ru.Boss90.roleplay;

import org.bukkit.plugin.java.*;
import org.bukkit.scheduler.*;

import me.clip.placeholderapi.PlaceholderAPI;
import ru.Boss90.roleplay.enums.*;
import ru.Boss90.roleplay.interfaces.*;
import ru.Boss90.roleplay.work.*;

import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.bukkit.command.*;
import java.util.*;
import net.milkbowl.vault.economy.*;
import org.bukkit.*;
import org.bukkit.ChatColor;
import org.bukkit.plugin.*;

public class main extends JavaPlugin {
	public static main plugin;
	public static SQL SQL;
	BukkitTask task;
	String forall;
	private HashMap<String, Integer> playersScore;
	private static main instance;

	public main() {
		this.task = null;
		this.forall = this.getConfig().getString("nachalo");
	}

	public void onEnable() {
		plugin = this;
		main.instance = this;
		main.SQL = new SQL(this.getConfig().getString("mysql.host"), this.getConfig().getString("mysql.base"),
				this.getConfig().getString("mysql.user"), this.getConfig().getString("mysql.password"));
		this.playersScore = new HashMap<String, Integer>();
		WorkFactory.loadWorks();
		final Map<String, Object> defaults = new HashMap<String, Object>();
		for (int da = 1; da < 11; ++da) {
			defaults.put("skins.skin" + da + ".owner", "glebloh");
			defaults.put("skins.skin" + da + ".name", "\u0413\u043b\u0435\u0431 \u043b\u043e\u0445");
			defaults.put("skins.skin" + da + ".price", 50);
		}
		this.saveDefaultConfig();
		Bukkit.getScheduler().runTaskTimer((Plugin) this, (Runnable) new Runnable() {
			@Override
			public void run() {
				for (final Player alss : Bukkit.getOnlinePlayers()) {
					if (main.SQL.getPlayerStars(alss.getName()) != 0) {
						main.SQL.minusstars(alss.getName(), 1);
					}
				}
			}
		}, 24000L, 24000L);
		if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")){
		    new Placeholders().register();
		}
		Bukkit.getScheduler().runTaskTimer((Plugin) this, (Runnable) new Runnable() {
			@Override
			public void run() {
				if (Bukkit.getOnlinePlayers().isEmpty()) {
					return;
				}
			}
		}, (long) (20 * this.getConfig().getInt("boardupdate")), (long) (20 * this.getConfig().getInt("boardupdate")));
		System.out.println(
				"\u0428\u0415\u0414\u0423\u041b\u0415\u0420 \u041d\u0410 \u0412\u042b\u0414\u0410\u0427\u0423 \u0417\u0410\u0420\u041f\u041b\u0410\u0422 \u0420\u0410\u0417 \u0412 \u0427\u0410\u0421 \u041d\u0410\u0427\u0410\u041b\u0421\u042f");
		this.forall = this.forall.replace("&", "§");
		this.task = Bukkit.getScheduler().runTaskTimer((Plugin) this, (Runnable) new Runnable() {
			@SuppressWarnings("incomplete-switch")
			@Override
			public void run() {
				final ArrayList<String> workers = main.SQL.getAllWorkers();
				for (int da = 0; da < workers.size(); ++da) {
					if (Bukkit.getPlayer((String) workers.get(da)) != null) {
						int summa = 0;
						final Work work = main.SQL.getPlayerRabota(workers.get(da));
						String d = "";
						switch (work.getFraction()) {
						case MERIA: {
							d = "meria";
							break;
						}
						case POLICE: {
							d = "police";
							break;
						}
						case HOSPITAL: {
							d = "hospital";
							break;
						}
						}
						summa = main.this.getConfig().getInt(d + work.getNumber());
						main.this.givezp(Bukkit.getPlayer((String) workers.get(da)), summa);
						TownMoneyApi.withdraw(summa);
						Bukkit.getPlayer((String) workers.get(da)).sendMessage(main.this.forall + ChatColor.WHITE
								+ "\u0412\u0430\u043c \u0432\u044b\u0434\u0430\u043d\u0430 \u0437\u0430\u0440\u043f\u043b\u0430\u0442\u0430 \u0432 \u0440\u0430\u0437\u043c\u0435\u0440\u0435 "
								+ summa + "$.");
					}
				}
				Bukkit.broadcastMessage(main.this.forall + ChatColor.WHITE
						+ "\u0417\u0430\u0440\u043f\u043b\u0430\u0442\u044b \u0432\u0441\u0435\u043c \u0440\u0430\u0431\u043e\u0442\u043d\u0438\u043a\u0430\u043c \u0443\u0441\u043f\u0435\u0448\u043d\u043e \u0432\u044b\u0434\u0430\u043d\u044b!");
			}
		}, 72000L, 72000L);
		System.out.println(
				"\u0428\u0415\u0414\u0423\u041b\u0415\u0420 \u041d\u0410 \u041e\u0421\u0412\u041e\u0411\u041e\u0416\u0414\u0415\u041d\u0418\u0415 \u0422\u042e\u0420\u0415\u041c\u041d\u0418\u041a\u041e\u0412 \u041d\u0410\u0427\u0410\u041b\u0421\u042f");
		Bukkit.getScheduler().runTaskTimer((Plugin) this, (Runnable) new Runnable() {
			@Override
			public void run() {
				final ArrayList<String> players = main.SQL.getFuckinTyuremshiki();
				for (final String s : players) {
					main.SQL.setTimeInPrison(s, main.SQL.getPlayerTimeinPris(s) - 1);
					if (Bukkit.getPlayer(s) != null && main.SQL.getPlayerInPris(s)
							&& main.SQL.getPlayerTimeinPris(s) <= 0) {
						main.SQL.vishelPris(s);
						Bukkit.getPlayer(s).sendMessage(ChatColor.AQUA
								+ "\u0412\u044b \u0443\u0441\u043f\u0435\u0448\u043d\u043e \u0432\u044b\u0448\u043b\u0438 \u0438\u0437 \u0442\u044e\u0440\u044c\u043c\u044b!");
						final Location locs = new Location(Bukkit.getPlayer(s).getWorld(),
								main.this.getConfig().getDouble("xprisafter"),
								main.this.getConfig().getDouble("yprisafter"),
								main.this.getConfig().getDouble("zprisafter"));
						Bukkit.getPlayer(s).teleport(locs);
					}
				}
			}
		}, 1200L, 1200L);
		Bukkit.getScheduler().runTaskTimer((Plugin) this, (Runnable) new Runnable() {
			@SuppressWarnings({ "unchecked", "rawtypes" })
			@Override
			public void run() {
				if (Bukkit.getOnlinePlayers().isEmpty()) {
					return;
				}
				for (final Player peha : Bukkit.getOnlinePlayers()) {
					if (main.SQL.getPlayerRabota(peha.getName()).getFraction().equals(Fraction.POLICE)) {
						final ItemStack palka = new ItemStack(Material.STICK, 1);
						final ItemMeta palkameta = palka.getItemMeta();
						palkameta.setDisplayName(ChatColor.AQUA
								+ "\u041f\u0430\u043b\u043a\u0430 \u043f\u043e\u043b\u0438\u0446\u0435\u0439\u0441\u043a\u043e\u0433\u043e");
						final ArrayList<String> listos = new ArrayList<String>();
						listos.add(ChatColor.GRAY
								+ "\u0417\u0430\u043c\u043e\u0440\u0430\u0436\u0438\u0432\u0430\u0435\u0442 \u043f\u0440\u0435\u0441\u0442\u0443\u043f\u043d\u0438\u043a\u0430 \u043d\u0430 10 \u0441\u0435\u043a\u0443\u043d\u0434.");
						palkameta.setLore((List) listos);
						palka.setItemMeta(palkameta);
						peha.getInventory().setItem(0, palka);
					}
				}
			}
		}, 3000L, 3000L);
		this.saveDefaultConfig();
		Bukkit.getPluginManager().registerEvents((Listener) new JoinListener(), (Plugin) this);
		Bukkit.getPluginManager().registerEvents((Listener) new JoinPris(), (Plugin) this);
		Bukkit.getPluginManager().registerEvents((Listener) new ArrestEvents(), (Plugin) this);
		Bukkit.getPluginManager().registerEvents((Listener) new StarsEvents(), (Plugin) this);
		Bukkit.getPluginManager().registerEvents((Listener) new RaEvents(), (Plugin) this);
		Bukkit.getPluginManager().registerEvents((Listener) new ArrestCmd(), (Plugin) this);
		this.getCommand("weekreset").setExecutor((CommandExecutor) new WeekResetCmd());
		this.getCommand("marklist").setExecutor((CommandExecutor) new MarkCmd());
		this.getCommand("cuff").setExecutor((CommandExecutor) new cuff());
		this.getCommand("givemed").setExecutor((CommandExecutor) new GiveMed());
		this.getCommand("ra").setExecutor((CommandExecutor) new ra());
		this.getCommand("setbase").setExecutor((CommandExecutor) new setbase());
		this.getCommand("base").setExecutor((CommandExecutor) new setbase());
		this.getCommand("block").setExecutor((CommandExecutor) new block());
		this.getCommand("unblock").setExecutor((CommandExecutor) new unblock());
		this.getCommand("setrank").setExecutor((CommandExecutor) new SetRang());
		this.getCommand("leaders").setExecutor((CommandExecutor) new Leaders());
		this.getCommand("boss90").setExecutor((CommandExecutor) new Boss90());
		this.getCommand("helpme").setExecutor((CommandExecutor) new SekretarCmd());
		this.getCommand("answer").setExecutor((CommandExecutor) new SekretarCmd());
		this.getCommand("mark").setExecutor((CommandExecutor) new MarkCmd());
		this.getCommand("givemilitary").setExecutor((CommandExecutor) new GiviBilet());
		this.getCommand("givepassport").setExecutor((CommandExecutor) new BookPasport());
		this.getCommand("gender").setExecutor((CommandExecutor) new Gender());
		this.getCommand("townmoney").setExecutor((CommandExecutor) new TownMoneyHandler());
		this.getCommand("withdrawtown").setExecutor((CommandExecutor) new TownMoneyHandler());
		this.getCommand("holotownmoney").setExecutor((CommandExecutor) new TownMoneyHandler());
		this.getCommand("towndonate").setExecutor((CommandExecutor) new TownMoneyHandler());
		this.getCommand("work").setExecutor((CommandExecutor) new CommandWork());
		this.getCommand("arrest").setExecutor((CommandExecutor) new ArrestCmd());
		this.getCommand("shtraf").setExecutor((CommandExecutor) new ShtrafCmdHandler());
		this.getCommand("unarrest").setExecutor((CommandExecutor) new UnArrestCmd());
		this.getCommand("makeleader").setExecutor((CommandExecutor) new SetMerCmd());
		this.getCommand("prison").setExecutor((CommandExecutor) new PrisonCmd());
		this.getCommand("setsalary").setExecutor((CommandExecutor) new SetSalaryCmd());
		this.getCommand("givestudy").setExecutor((CommandExecutor) new GiveStudyCmd());
		this.getCommand("resetstars").setExecutor((CommandExecutor) new AdminCmd());
		this.getCommand("setstars").setExecutor((CommandExecutor) new AdminCmd());
		this.getCommand("hp").setExecutor((CommandExecutor) new HPcmdHandler(this));
	}

	public void onDisable() {
		this.task.cancel();
		System.out.println(
				"\u0428\u0435\u0434\u0443\u043b\u0435\u0440 \u0432\u044b\u0434\u0430\u0447\u0438 \u0437\u0430\u0440\u043f\u043b\u0430\u0442\u044b \u0432\u044b\u043a\u043b\u044e\u0447\u0435\u043d!");
		this.playersScore = null;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void givezp(final Player p, final int zp) {
		final RegisteredServiceProvider<Economy> rsp = (RegisteredServiceProvider<Economy>) Bukkit.getServicesManager()
				.getRegistration((Class) Economy.class);
		if (rsp == null) {
			return;
		}
		final Economy econ = (Economy) rsp.getProvider();
		econ.depositPlayer((OfflinePlayer) p, (double) zp);
	}

	public HashMap<String, Integer> getPlayersScore() {
		return this.playersScore;
	}

	public static main getInstance() {
		return main.instance;
	}
}
