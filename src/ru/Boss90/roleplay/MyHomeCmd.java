package ru.Boss90.roleplay;

import org.bukkit.configuration.file.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;
import org.bukkit.configuration.*;
import net.milkbowl.vault.economy.*;
import org.bukkit.*;
import org.bukkit.ChatColor;
import org.bukkit.plugin.*;
import java.io.*;

public class MyHomeCmd implements CommandExecutor
{
    Plugin plugin;
    String forall;
    File homesyml;
    FileConfiguration homes;
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public MyHomeCmd() {
        this.plugin = (Plugin)main.getPlugin((Class)main.class);
        this.forall = this.plugin.getConfig().getString("nachalo").replace("&", "§");
        this.homesyml = new File(this.plugin.getDataFolder() + "/Homes.yml");
        this.homes = (FileConfiguration)YamlConfiguration.loadConfiguration(this.homesyml);
    }
    
    @SuppressWarnings("null")
	public boolean onCommand(final CommandSender sender, final Command cmd, final String commandlabel, final String[] args) {
        if (cmd.getName().equalsIgnoreCase("myhome") && sender instanceof Player) {
            if (args.length == 0) {
                sender.sendMessage(this.forall + ChatColor.WHITE + "Стоимость вызова такси составляет " + ChatColor.AQUA + this.plugin.getConfig().getInt("Taksi") + ChatColor.WHITE + "$");
                sender.sendMessage(this.forall + ChatColor.WHITE + "Чтобы вызвать такси напишите " + ChatColor.AQUA + "/myhome call" + ChatColor.WHITE + ".");
                return true;
            }
            if (args.length == 1 && args[0].equalsIgnoreCase("call")) {
                final Player p = (Player)sender;
                try {
                    if (!this.haveHome(p)) {
                        p.sendMessage(this.forall + ChatColor.WHITE + "Вы не имеете дома! Введите " + ChatColor.AQUA + "/setmyhome " + ChatColor.WHITE + "чтобы установить его!");
                        return true;
                    }
                }
                catch (IOException | InvalidConfigurationException ex2) {
                    final Exception ex = null;
                    final Exception e = ex;
                    e.printStackTrace();
                }
                if (this.getmoney(p) < this.plugin.getConfig().getInt("Taksi")) {
                    p.sendMessage(this.forall + ChatColor.WHITE + "У вас недостаточно средств на балансе!");
                    return true;
                }
                sender.sendMessage(this.forall + ChatColor.WHITE + "Такси прибудет в течении минуты!");
                Bukkit.getScheduler().runTaskLater(this.plugin, (Runnable)new Runnable() {
                    @Override
                    public void run() {
                        if (Bukkit.getOnlinePlayers().contains(p)) {
                            if (MyHomeCmd.this.getmoney(p) < MyHomeCmd.this.plugin.getConfig().getInt("Taksi")) {
                                p.sendMessage(MyHomeCmd.this.forall + ChatColor.WHITE + "Недостаточно средств на счету!");
                                return;
                            }
                            MyHomeCmd.this.withdrawp(p, MyHomeCmd.this.plugin.getConfig().getInt("Taksi"));
                            p.teleport(new Location(p.getWorld(), MyHomeCmd.this.homes.getDouble("Homes." + p.getName() + ".posX"), MyHomeCmd.this.homes.getDouble("Homes." + p.getName() + ".posY"), MyHomeCmd.this.homes.getDouble("Homes." + p.getName() + ".posZ")));
                            p.sendMessage(MyHomeCmd.this.forall + ChatColor.WHITE + "Удачного дня!");
                        }
                    }
                }, 1200L);
                return true;
            }
        }
        if (cmd.getName().equalsIgnoreCase("setmyhome") && sender instanceof Player) {
            final Player p = (Player)sender;
            this.homes.set("Homes." + p.getName() + ".posX", (Object)p.getLocation().getX());
            this.homes.set("Homes." + p.getName() + ".posY", (Object)p.getLocation().getY());
            this.homes.set("Homes." + p.getName() + ".posZ", (Object)p.getLocation().getZ());
            try {
                this.homes.save(this.homesyml);
            }
            catch (IOException e2) {
                e2.printStackTrace();
            }
            p.sendMessage(this.forall + ChatColor.WHITE + "Новая точка дома установлена!");
            return true;
        }
        return false;
    }
    
    @SuppressWarnings("rawtypes")
	public double getmoney(final Player p) {
        @SuppressWarnings("unchecked")
		final RegisteredServiceProvider<Economy> rsp = (RegisteredServiceProvider<Economy>)Bukkit.getServicesManager().getRegistration((Class)Economy.class);
        if (rsp == null) {
            return 0.0;
        }
        final Economy econ = (Economy)rsp.getProvider();
        return econ.getBalance((OfflinePlayer)p);
    }
    
    @SuppressWarnings("unchecked")
	public void withdrawp(final Player p, final int da) {
        @SuppressWarnings("rawtypes")
		final RegisteredServiceProvider<Economy> rsp = (RegisteredServiceProvider<Economy>)Bukkit.getServicesManager().getRegistration((Class)Economy.class);
        if (rsp == null) {
            return;
        }
        final Economy econ = (Economy)rsp.getProvider();
        econ.withdrawPlayer((OfflinePlayer)p, (double)da);
    }
    
    private boolean haveHome(final Player p) throws FileNotFoundException, IOException, InvalidConfigurationException {
        this.homes.load(this.homesyml);
        return this.homes.contains("Homes." + p.getName());
    }
}
