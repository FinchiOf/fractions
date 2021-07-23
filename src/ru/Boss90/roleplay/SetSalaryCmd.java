package ru.Boss90.roleplay;

import org.bukkit.plugin.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;

import net.md_5.bungee.api.*;
import ru.Boss90.roleplay.enums.*;
import ru.Boss90.roleplay.work.WorkFactory;

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
                if (args.length == 3) {
                    final String h = args[0].toLowerCase();
                    if (!h.equalsIgnoreCase("мэрия") && !h.equalsIgnoreCase("полиция") && !h.equalsIgnoreCase("больница")) {
                        sender.sendMessage(this.forall + ChatColor.WHITE + "Неверная работа!");
                        return true;
                    }
                    if (h.equalsIgnoreCase("мэрия")) {
                        int zp = 0;
                        try {
                            zp = Integer.valueOf(args[2]);
                        }
                        catch (NumberFormatException e) {
                            sender.sendMessage(this.forall + ChatColor.WHITE + "Неправильное число!");
                            return true;
                        }
                        if (zp < 0 || zp > 100000) {
                            sender.sendMessage(this.forall + ChatColor.WHITE + "Зарплата должна быть от 0 до 100000$!");
                            zp = 0;
                            return true;
                        }
                        if(args[1].contains("1")) {
                        this.plugin.getConfig().set("meria1", (Object)zp);
                        sender.sendMessage(this.forall + ChatColor.WHITE +"Зарплата рангу Стажёр теперь составляет " + zp + "!");
                        }
                        if(args[1].contains("2")) {
                        this.plugin.getConfig().set("meria2", (Object)zp);
                        sender.sendMessage(this.forall + ChatColor.WHITE +"Зарплата рангу Водитель теперь составляет " + zp + "!");
                        }
                        if(args[1].contains("3")) {
                        this.plugin.getConfig().set("meria3", (Object)zp);
                        sender.sendMessage(this.forall + ChatColor.WHITE +"Зарплата рангу Водитель теперь составляет " + zp + "!");
                        }
                        if(args[1].contains("4")) {
                        this.plugin.getConfig().set("meria4", (Object)zp);
                        sender.sendMessage(this.forall + ChatColor.WHITE +"Зарплата рангу Телохранитель теперь составляет " + zp + "!");
                        }
                        if(args[1].contains("5")) {
                        this.plugin.getConfig().set("meria5", (Object)zp);
                        sender.sendMessage(this.forall + ChatColor.WHITE +"Зарплата рангу Секретарь теперь составляет " + zp + "!");
                        }
                        if(args[1].contains("6")) {
                        this.plugin.getConfig().set("meria6", (Object)zp);
                        sender.sendMessage(this.forall + ChatColor.WHITE +"Зарплата рангу Адвокат теперь составляет " + zp + "!");
                        }
                        if(args[1].contains("7")) {
                        this.plugin.getConfig().set("meria7", (Object)zp);
                        sender.sendMessage(this.forall + ChatColor.WHITE +"Зарплата рангу Зам.Мэра теперь составляет " + zp + "!");
                        }
                        if(args[1].contains("8")) {
                        this.plugin.getConfig().set("meria8", (Object)zp);
                        sender.sendMessage(this.forall + ChatColor.WHITE +"Зарплата рангу Мэр теперь составляет " + zp + "!");
                        }
                        this.plugin.saveConfig();
                        return true;
                    }
                    else if (h.equalsIgnoreCase("полиция")) {
                        int zp = 0;
                        try {
                            zp = Integer.valueOf(args[2]);
                        }
                        catch (NumberFormatException e) {
                            sender.sendMessage(this.forall + ChatColor.WHITE + "Неправильное число!");
                            return true;
                        }
                        if (zp < 0 || zp > 100000) {
                            sender.sendMessage(this.forall + ChatColor.WHITE + "Зарплата должна быть от 0 до 100000$!");
                            zp = 0;
                            return true;
                        }
                        if(args[1].contains("1")) {
                        this.plugin.getConfig().set("police1", (Object)zp);
                        sender.sendMessage(this.forall + ChatColor.WHITE +"Зарплата рангу Кадет теперь составляет " + zp + "!");
                        }
                        if(args[1].contains("2")) {
                        this.plugin.getConfig().set("police2", (Object)zp);
                        sender.sendMessage(this.forall + ChatColor.WHITE +"Зарплата рангу Офицер теперь составляет " + zp + "!");
                        }
                        if(args[1].contains("3")) {
                        this.plugin.getConfig().set("police3", (Object)zp);
                        sender.sendMessage(this.forall + ChatColor.WHITE +"Зарплата рангу Сержант теперь составляет " + zp + "!");
                        }
                        if(args[1].contains("4")) {
                        this.plugin.getConfig().set("police4", (Object)zp);
                        sender.sendMessage(this.forall + ChatColor.WHITE +"Зарплата рангу Прапорщик теперь составляет " + zp + "!");
                        }
                        if(args[1].contains("5")) {
                        this.plugin.getConfig().set("police5", (Object)zp);
                        sender.sendMessage(this.forall + ChatColor.WHITE +"Зарплата рангу Лейтенант теперь составляет " + zp + "!");
                        }
                        if(args[1].contains("6")) {
                        this.plugin.getConfig().set("police6", (Object)zp);
                        sender.sendMessage(this.forall + ChatColor.WHITE +"Зарплата рангу Капитан теперь составляет " + zp + "!");
                        }
                        if(args[1].contains("7")) {
                        this.plugin.getConfig().set("police7", (Object)zp);
                        sender.sendMessage(this.forall + ChatColor.WHITE +"Зарплата рангу Майор теперь составляет " + zp + "!");
                        }
                        if(args[1].contains("8")) {
                        this.plugin.getConfig().set("police8", (Object)zp);
                        sender.sendMessage(this.forall + ChatColor.WHITE +"Зарплата рангу ПодПолковник теперь составляет " + zp + "!");
                        }
                        if(args[1].contains("9")) {
                        this.plugin.getConfig().set("police9", (Object)zp);
                        sender.sendMessage(this.forall + ChatColor.WHITE +"Зарплата рангу Полковник теперь составляет " + zp + "!");
                        }
                        if(args[1].contains("10")) {
                        this.plugin.getConfig().set("police10", (Object)zp);
                        sender.sendMessage(this.forall + ChatColor.WHITE +"Зарплата рангу Шериф теперь составляет " + zp + "!");
                        }
                        this.plugin.saveConfig();
                        return true;
                    }
                    else if (args[0].equalsIgnoreCase("больница")) {
                        int zp = 0;
                        try {
                            zp = Integer.valueOf(args[2]);
                        }
                        catch (NumberFormatException e) {
                            sender.sendMessage(this.forall + ChatColor.WHITE + "Неправильное число!");
                            return true;
                        }
                        if (zp < 0 || zp > 100000) {
                            sender.sendMessage(this.forall + ChatColor.WHITE + "Зарплата должна быть от 0 до 100000$!");
                            zp = 0;
                            return true;
                        }
                        if(args[1].contains("1")) {
                        this.plugin.getConfig().set("hospita1", (Object)zp);
                        sender.sendMessage(this.forall + ChatColor.WHITE +"Зарплата рангу Интерн теперь составляет " + zp + "!");
                        }
                        if(args[1].contains("2")) {
                        this.plugin.getConfig().set("hospita2", (Object)zp);
                        sender.sendMessage(this.forall + ChatColor.WHITE +"Зарплата рангу Санитар теперь составляет " + zp + "!");
                        }
                        if(args[1].contains("3")) {
                        this.plugin.getConfig().set("hospita3", (Object)zp);
                        sender.sendMessage(this.forall + ChatColor.WHITE +"Зарплата рангу Спасатель теперь составляет " + zp + "!");
                        }
                        if(args[1].contains("4")) {
                        this.plugin.getConfig().set("hospita4", (Object)zp);
                        sender.sendMessage(this.forall + ChatColor.WHITE +"Зарплата рангу Доктор теперь составляет " + zp + "!");
                        }
                        if(args[1].contains("5")) {
                        this.plugin.getConfig().set("hospita5", (Object)zp);
                        sender.sendMessage(this.forall + ChatColor.WHITE +"Зарплата рангу Психиатор теперь составляет " + zp + "!");
                        }
                        if(args[1].contains("6")) {
                        this.plugin.getConfig().set("hospita6", (Object)zp);
                        sender.sendMessage(this.forall + ChatColor.WHITE +"Зарплата рангу Хирурнг теперь составляет " + zp + "!");
                        }
                        if(args[1].contains("7")) {
                        this.plugin.getConfig().set("hospita7", (Object)zp);
                        sender.sendMessage(this.forall + ChatColor.WHITE +"Зарплата рангу Зам.Гл.Доктора теперь составляет " + zp + "!");
                        }
                        if(args[1].contains("8")) {
                        this.plugin.getConfig().set("hospita8", (Object)zp);
                        sender.sendMessage(this.forall + ChatColor.WHITE +"Зарплата рангу Доктор теперь составляет " + zp + "!");
                        }
                        this.plugin.saveConfig();
                        return true;
                    }
                }
            }
        }
        return true;
    }
}
