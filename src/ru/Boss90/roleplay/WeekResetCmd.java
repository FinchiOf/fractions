package ru.Boss90.roleplay;

import org.bukkit.plugin.*;
import org.bukkit.command.*;
import net.md_5.bungee.api.*;
import ru.Boss90.roleplay.work.*;

import java.util.*;

public class WeekResetCmd implements CommandExecutor
{
    Plugin plugin;
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public WeekResetCmd() {
        this.plugin = (Plugin)main.getPlugin((Class)main.class);
    }
    
    public boolean onCommand(final CommandSender sender, final Command cmd, final String commandlabel, final String[] args) {
        if (cmd.getName().equalsIgnoreCase("weekreset")) {
            if (!sender.isOp()) {
                return true;
            }
            final boolean f = true;
            if (f) {
                sender.sendMessage(ChatColor.RED + "¬ременно выключено");
                return true;
            }
            final ArrayList<String> uvolennie = new ArrayList<String>();
            if (args.length == 0) {
                final ArrayList<String> uvln = main.SQL.getAllWorkers();
                for (int da = 0; da < uvln.size(); ++da) {
                    final int lasttime = 55234;
                    final int gega = lasttime / 1000 / 60 / 60;
                    if (gega < this.plugin.getConfig().getInt("weekhours")) {
                        uvolennie.add(uvln.get(da));
                        main.SQL.setrabota(uvln.get(da), WorkFactory.loadedWorks.get(0));
                    }
                }
                String agashka = "";
                for (int da2 = 0; da2 < uvolennie.size(); ++da2) {
                    agashka = agashka + uvolennie.get(da2) + ", ";
                }
                sender.sendMessage("Ёволенные: " + agashka);
                return true;
            }
        }
        return false;
    }
}
