package ru.Boss90.roleplay;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;

public class Placeholders extends PlaceholderExpansion {

	@Override
	public String getAuthor() {
		return "Boss90";
	}

	@Override
	public String getIdentifier() {
        return "Fraction";
	}

	@Override
	public String getVersion() {
        return "1.0.0";
	}
    @Override
    public String onPlaceholderRequest(Player player, String identifier) {
        if(identifier.equalsIgnoreCase("fraction")){
            return main.SQL.getPlayerRabota(player.getName()).getFraction().getName();
        }
        if(identifier.equalsIgnoreCase("rank")){
            return main.SQL.getPlayerRabota(player.getName()).getName();
        }
        if(identifier.equalsIgnoreCase("stars")){
            final int st = main.SQL.getPlayerStars(player.getName());
            final String star = main.getInstance().getConfig().getString("starsymbol");
            if (st <= 0) {
                return new StringBuilder().append(ChatColor.GRAY).append(ChatColor.BOLD).append(bld(star, 5)).toString();
            }
            if (st == 1) {
                return new StringBuilder().append(ChatColor.YELLOW).append(ChatColor.BOLD).append(star).append(ChatColor.GRAY).append(ChatColor.BOLD).append(bld(star, 4)).toString();
            }
            if (st == 2) {
                return new StringBuilder().append(ChatColor.YELLOW).append(ChatColor.BOLD).append(bld(star, 2)).append(ChatColor.GRAY).append(ChatColor.BOLD).append(bld(star, 3)).toString();
            }
            if (st == 3) {
                return new StringBuilder().append(ChatColor.YELLOW).append(ChatColor.BOLD).append(bld(star, 3)).append(ChatColor.GRAY).append(ChatColor.BOLD).append(bld(star, 2)).toString();
            }
            if (st == 4) {
                return new StringBuilder().append(ChatColor.YELLOW).append(ChatColor.BOLD).append(bld(star, 4)).append(ChatColor.GRAY).append(ChatColor.BOLD).append(star).toString();
            }
            if (st >= 5) {
                return new StringBuilder().append(ChatColor.YELLOW).append(ChatColor.BOLD).append(bld(star, 5)).toString();
            }
            if (player.hasPermission("MSStudio.freestars")) {
                return new StringBuilder().append(ChatColor.YELLOW).append(ChatColor.BOLD).append(bld(star, 5)).toString();
            }
            return bld(star, 5);
        }
        if(identifier.equalsIgnoreCase("stude")){
            return main.SQL.getPlayerEducation(player.getName()).getName();
        }
        if(identifier.equalsIgnoreCase("medcart")){
            return main.SQL.getMedCart(player.getName()).getName();
        }
        if(identifier.equalsIgnoreCase("bilet")){
            return main.SQL.getBilet(player.getName()).getName();
        }
    	return null;
    }
    protected static String bld(final String s, final int f) {
        String d = "";
        for (int j = 0; j < f; ++j) {
            d += s;
        }
        return d;
    }
}
