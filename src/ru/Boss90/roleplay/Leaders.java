package ru.Boss90.roleplay;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import net.md_5.bungee.api.ChatColor;

public class Leaders implements CommandExecutor {
    Plugin plugin;
    String forall;
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public Leaders() {
        this.plugin = (Plugin)main.getPlugin((Class)main.class);
        this.forall = this.plugin.getConfig().getString("nachalo").replace("&", "�");;
    }
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String str, String[] args) {
		Player p = (Player) sender;
        p.sendMessage(this.forall + ChatColor.WHITE + "������ ���� �������: ");
        p.sendMessage(ChatColor.YELLOW + "����� " + ChatColor.WHITE + "- " + this.plugin.getConfig().getString("mer").replace("-", ChatColor.RED+"������ ���.") + (this.isOnline(this.plugin.getConfig().getString("mer")) ? " �a[ON] �e�.*****" : " �c[OFF] �e�.*****"));
        p.sendMessage(ChatColor.BLUE + "������� " + ChatColor.WHITE + "- " + this.plugin.getConfig().getString("sherif").replace("-", ChatColor.RED+"������ ���.") + (this.isOnline(this.plugin.getConfig().getString("sherif")) ? " �a[ON] �e�.*****" : " �c[OFF] �e�.*****"));
        p.sendMessage(ChatColor.RED + "�������� " + ChatColor.WHITE + "- " + this.plugin.getConfig().getString("glvrach").replace("-", ChatColor.RED+"������ ���.") + (this.isOnline(this.plugin.getConfig().getString("glvrach")) ? " �a[ON] �e�.*****" : " �c[OFF] �e�.*****"));
		return true;
	}
    public boolean isOnline(final String playerName) {
        return Bukkit.getPlayerExact(playerName) != null;
    }
}
