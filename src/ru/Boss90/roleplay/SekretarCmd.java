package ru.Boss90.roleplay;

import org.bukkit.plugin.*;

import ru.Boss90.roleplay.enums.*;

import org.bukkit.command.*;
import org.bukkit.entity.*;
import org.bukkit.*;
import org.bukkit.ChatColor;

public class SekretarCmd implements CommandExecutor
{
    Plugin plugin;
    String forall;
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public SekretarCmd() {
        this.plugin = (Plugin)main.getPlugin((Class)main.class);
        this.forall = this.plugin.getConfig().getString("nachalo");
    }
    
    public boolean onCommand(final CommandSender sender, final Command cmd, final String commandlabel, final String[] args) {
        this.forall = this.forall.replace("&", "§");
        if (cmd.getName().equalsIgnoreCase("helpme")) {
            if (args.length == 0) {
                sender.sendMessage(this.forall + ChatColor.WHITE + "Чтобы вам помогли, введите /helpme Сообщение. Секретари ответят вам и помогут.");
                return true;
            }
            if (args.length > 0 && sender instanceof Player) {
                final Player pe = (Player)sender;
                String helpmemes = "";
                for (final String s : args) {
                    helpmemes = helpmemes + s + " ";
                }
                for (final Player p : Bukkit.getOnlinePlayers()) {
                    if (AccessLevel.contains(main.SQL.getPlayerRabota(p.getName()).getAccess(), AccessLevel.SEKRETARUTILS) || p.hasPermission("atlantworld.seehelpme")) {
                        p.sendMessage(new StringBuilder().append(ChatColor.GREEN).append(ChatColor.BOLD).append("HelpMe > ").append(ChatColor.RED).append(pe.getName()).append(": ").append(ChatColor.WHITE).append(helpmemes).toString());
                    }
                }
                sender.sendMessage(this.forall + ChatColor.WHITE + "Ваш запрос отправлен! Ждите ответа.");
                return true;
            }
        }
        if (cmd.getName().equalsIgnoreCase("answer") && sender instanceof Player) {
            final Player peka = (Player)sender;
            if (!AccessLevel.contains(main.SQL.getPlayerRabota(peka.getName()).getAccess(), AccessLevel.SEKRETARUTILS) && !peka.isOp() && !sender.hasPermission("atlantworld.seehelpme")) {
                peka.sendMessage(this.forall + ChatColor.WHITE + "Вы не можете воспользоваться данной командой!");
                return true;
            }
            if (args.length == 0 || args.length == 1) {
                sender.sendMessage(this.forall + ChatColor.WHITE + "/answer [Игрок] [Ответ] - Ответить игроку на вопрос, который он задал.");
                sender.sendMessage(this.forall + ChatColor.RED + ChatColor.BOLD + "Внимание! Все ответы проверяются модерацией проекта, вы будете наказаны за нарушение правил проекта в ответе игроку, или же за неправильный ответ!");
                return true;
            }
            if (args.length >= 2 && (sender.hasPermission("atlantworld.seehelpme") || sender.isOp() || AccessLevel.contains(main.SQL.getPlayerRabota(peka.getName()).getAccess(), AccessLevel.SEKRETARUTILS))) {
                final Player papa = Bukkit.getPlayer(args[0]);
                if (Bukkit.getOnlinePlayers().contains(papa)) {
                    String fullmka = "";
                    for (final String sas : args) {
                        fullmka = fullmka + sas + " ";
                    }
                    fullmka = fullmka.replaceFirst(papa.getName(), "");
                    fullmka = fullmka.replaceFirst(" ", "");
                    for (final Player paha : Bukkit.getOnlinePlayers()) {
                        if (paha.hasPermission("atlantworld.seehelpme") || paha.isOp() || AccessLevel.contains(main.SQL.getPlayerRabota(peka.getName()).getAccess(), AccessLevel.SEKRETARUTILS)) {
                            paha.sendMessage(new StringBuilder().append(ChatColor.GREEN).append(ChatColor.BOLD).append("Support > ").append(peka.getName()).append(" > ").append(ChatColor.WHITE).append(fullmka).toString());
                        }
                    }
                    papa.sendMessage(new StringBuilder().append(ChatColor.GREEN).append(ChatColor.BOLD).append("Support > ").append(peka.getName()).append(" > ").append(ChatColor.WHITE).append(fullmka).toString());
                    sender.sendMessage(this.forall + ChatColor.WHITE + "Отправлено!");
                    return true;
                }
                sender.sendMessage(this.forall + ChatColor.WHITE + "Игрока нет на сервере!");
                return true;
            }
        }
        return false;
    }
}
