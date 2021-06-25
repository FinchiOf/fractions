package ru.Boss90.roleplay; 

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

import ru.Boss90.roleplay.work.WorkFactory;

public class SetRang implements CommandExecutor {
    Plugin plugin;
    String nachalo;
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public SetRang() {
        this.plugin = (Plugin)main.getPlugin((Class)main.class);
        this.nachalo = new StringBuilder().append(ChatColor.AQUA).append(ChatColor.BOLD).append("������ > ").toString();
    }
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String str, String[] args) {
		if(!sender.isOp()) {
			sender.sendMessage(ChatColor.RED+"��� �� ���������, ������ �� ����� ��� �������. ��� ���������, ����� �� � ��������, �� ��� �� ��������?");
			return true;
		}
		if(args.length == 0) {
			sender.sendMessage(nachalo + ChatColor.WHITE+"������� ���!");
			return true;
		}
		if(args.length == 1) {
			sender.sendMessage(nachalo + ChatColor.WHITE+"������� ����!");
			return true;
		}
		try {
		main.SQL.setrabota(args[0], WorkFactory.loadedWorks.get(Integer.valueOf(args[1])));
		sender.sendMessage(nachalo + ChatColor.WHITE+"�� ������� ���� "+WorkFactory.loadedWorks.get(Integer.valueOf(args[1])).getName()+" ������ "+ args[0]);
		}catch(NullPointerException e) {
			sender.sendMessage(nachalo + ChatColor.WHITE+"������ ������ �� ����������!");
			e.printStackTrace();
		}
		return true;
	}
}
