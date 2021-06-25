package ru.Boss90.roleplay;

import org.bukkit.plugin.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;

import net.md_5.bungee.api.*;
import ru.Boss90.roleplay.enums.*;

public class SetSalaryCmd implements CommandExecutor
{
    Plugin plugin;
    String forall;
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public SetSalaryCmd() {
        this.plugin = (Plugin)main.getPlugin((Class)main.class);
        this.forall = this.plugin.getConfig().getString("nachalo");
    }
    
    public boolean onCommand(final CommandSender sender, final Command cmd, final String commandlabel, final String[] args) {
        if (cmd.getName().equalsIgnoreCase("setsalary")) {
            this.forall = this.forall.replace("&", "§");
            if (sender instanceof Player) {
                final Player p = (Player)sender;
                if (!AccessLevel.contains(main.SQL.getPlayerRabota(p.getName()).getAccess(), AccessLevel.SETSALARY) && !sender.isOp()) {
                    sender.sendMessage(this.forall + ChatColor.WHITE + "Нет прав.");
                    return true;
                }
                if (args.length == 0) {
                    sender.sendMessage(this.forall + ChatColor.WHITE + "/setsalary [Фракция] [Сумма от 400 до 700] - Установить зарплату ПЕРВОМУ рангу фракции.");
                    sender.sendMessage(this.forall + ChatColor.WHITE + "/setsalary list - Список фракций, которым можно установить ЗП");
                    return true;
                }
                if (args.length == 1 && args[0].equalsIgnoreCase("list")) {
                    sender.sendMessage(this.forall + ChatColor.WHITE + "Мэрия");
                    sender.sendMessage(this.forall + ChatColor.WHITE + "Полиция");
                    sender.sendMessage(this.forall + ChatColor.WHITE + "Больница");
                    return true;
                }
                if (args.length == 2) {
                    final String h = args[0].toLowerCase();
                    if (!h.equalsIgnoreCase("мэрия") && !h.equalsIgnoreCase("полиция") && !h.equalsIgnoreCase("больница")) {
                        sender.sendMessage(this.forall + ChatColor.WHITE + "Неверная работа!");
                        return true;
                    }
                    if (h.equalsIgnoreCase("мэрия")) {
                        int zp = 0;
                        try {
                            zp = Integer.valueOf(args[1]);
                        }
                        catch (NumberFormatException e) {
                            sender.sendMessage(this.forall + ChatColor.WHITE + "Неправильное число!");
                            return true;
                        }
                        if (zp < 400 || zp > 700) {
                            sender.sendMessage(this.forall + ChatColor.WHITE + "Зарплата должна быть от 400 до 700$!");
                            zp = 0;
                            return true;
                        }
                        this.plugin.getConfig().set("meria1", (Object)zp);
                        sender.sendMessage(this.forall + ChatColor.WHITE + "Зарплата Первому рангу мэрии теперь составляет " + zp + "!");
                        return true;
                    }
                    else if (h.equalsIgnoreCase("полиция")) {
                        int zp = 0;
                        try {
                            zp = Integer.valueOf(args[1]);
                        }
                        catch (NumberFormatException e) {
                            sender.sendMessage(this.forall + ChatColor.WHITE + "Неправильное число!");
                            return true;
                        }
                        if (zp < 400 || zp > 700) {
                            sender.sendMessage(this.forall + ChatColor.WHITE + "Зарплата должна быть от 400 до 700$!");
                            zp = 0;
                            return true;
                        }
                        this.plugin.getConfig().set("police1", (Object)zp);
                        sender.sendMessage(this.forall + ChatColor.WHITE + "Зарплата Первому рангу полиции теперь составляет " + zp + "!");
                        return true;
                    }
                    else if (args[0].equalsIgnoreCase("больница")) {
                        int zp = 0;
                        try {
                            zp = Integer.valueOf(args[1]);
                        }
                        catch (NumberFormatException e) {
                            sender.sendMessage(this.forall + ChatColor.WHITE + "Неправильное число!");
                            return true;
                        }
                        if (zp < 400 || zp > 700) {
                            sender.sendMessage(this.forall + ChatColor.WHITE + "Зарплата должна быть от 400 до 700$!");
                            zp = 0;
                            return true;
                        }
                        this.plugin.getConfig().set("bol1", (Object)zp);
                        sender.sendMessage(this.forall + ChatColor.WHITE + "Зарплата Первому рангу больницы теперь составляет " + zp + "!");
                        return true;
                    }
                }
            }
        }
        return true;
    }
}
