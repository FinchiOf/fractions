package ru.Boss90.roleplay;

import ru.Boss90.roleplay.interfaces.*;
import ru.Boss90.roleplay.work.*;
import ru.Boss90.roleplay.enums.*;
import java.util.*;
import java.sql.*;

public class SQL
{
    private static Connection connection;
    private static Statement statement;
    
    public SQL(final String host, final String base, final String user, final String password) {
        try {
            SQL.connection = DriverManager.getConnection("jdbc:mysql://" + host + ":3306/" + base, user, password);
            this.update("CREATE TABLE IF NOT EXISTS `roleplay` (`player` varchar(50) NOT NULL,`work` int(11) NOT NULL,`education` int(11) NOT NULL,`stars` int(11) NOT NULL,`inprison` boolean NOT NULL,`time` int(11) NOT NULL,`sroki` int(11) NOT NULL,`gender` int(11) NOT NULL,`medcart` int(11) NOT NULL,`bilet` int(11) NOT NULL) ENGINE=InnoDB DEFAULT CHARSET=utf8");
        }
        catch (Exception e) {
            e.printStackTrace();
        }	
    }
    
    public ResultSet query(final String query) {
        try {
            SQL.statement = SQL.connection.createStatement();
            final ResultSet set = SQL.statement.executeQuery(query);
            set.next();
            return set;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public void update(final String... lines) {
        try {
            for (final String string : lines) {
                (SQL.statement = SQL.connection.createStatement()).executeUpdate(string);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void writeToRoleplay(final String player) {
        this.update("INSERT INTO roleplay (`player`,`work`,`education`,`stars`,`inprison`,`time`,`sroki`,`gender`,`medcart`,`bilet`) VALUES ('" + player + "',0,0,0,'0',0,0,3,0,0);");
    }
    
    public void setrabota(final String player, final Work rabota) {
        this.update("UPDATE `roleplay` SET `work` = " + rabota.getCode() + " WHERE `player`='" + player + "';");
    }
    
    public void plusstars(final String player, final int stars) {
        this.update("UPDATE `roleplay` SET `stars` = stars + '" + stars + "' WHERE `player` = '" + player + "';");
    }
    
    public void minusstars(final String player, final int stars) {
        this.update("UPDATE `roleplay` SET `stars` = stars - '" + stars + "' WHERE `player` = '" + player + "';");
    }
    
    public void setbilet(final String player, int bilet) {
        this.update("UPDATE `roleplay` SET `bilet` = '" + bilet + "' WHERE `player` = '" + player + "';");
    }
    
    public void setstars(final String player, final int stars) {
        this.update("UPDATE `roleplay` SET `stars` = '" + stars + "' WHERE `player` = '" + player + "';");
    }
    
    public void setgender(final String player, final int gender) {
        this.update("UPDATE `roleplay` SET `gender` = '" + gender + "' WHERE `player` = '" + player + "';");
    }
    
    public void setMedCart(final String player, final Med educ) {
        this.update("UPDATE `roleplay` SET `medcart` = " + educ.getCode() + " WHERE `player` = '" + player + "';");
    }
    
    public void setInPrison(final String player, final int time) {
        this.update("UPDATE `roleplay` SET `inprison` = '1' WHERE `player` = '" + player + "';", "UPDATE `roleplay` SET `stars` = 0 WHERE player = '" + player + "';", "UPDATE `roleplay` SET `time` = " + time + " WHERE `player` = '" + player + "';");
    }
    
    public void vishelPris(final String player) {
        this.update("UPDATE `roleplay` SET `inprison` = '0' WHERE `player` = '" + player + "';", "UPDATE `roleplay` SET `time` = 0 WHERE `player` = '" + player + "';", "UPDATE `roleplay` SET `work` = 0 WHERE `player` = '" + player + "';", "UPDATE `roleplay` SET `sroki` = sroki+1 WHERE `player` = '" + player + "';");
    }
    
    public int getPlayerTimeinPris(final String player) {
        try {
            final ResultSet set = this.query("SELECT `time` FROM `roleplay` WHERE `player` = '" + player + "';");
            return set.getInt("time");
        }
        catch (Exception e) {
            System.out.println("88 line, proeb");
            return 0;
        }
    }
    
    
    public int getPlayerSroki(final String player) {
        try {
            final ResultSet set = this.query("SELECT `sroki` FROM `roleplay` WHERE `player` = '" + player + "';");
            return set.getInt("sroki");
        }
        catch (Exception e) {
            System.out.println("98 line, proeb");
            return 0;
        }
    }
    
    public GenderType getGender(final String player) {
        try {
            final ResultSet set = this.query("SELECT `gender` FROM `roleplay` WHERE `player` = '" + player + "';");
            final int code = set.getInt("gender");
            return GenderType.fromCode(code);
        }
        catch (Exception e) {
            System.out.println("161 line, proeb");
            return GenderType.NONE;
        }
    }
    
    public VoeniiBilet getBilet(final String player) {
        try {
            final ResultSet set = this.query("SELECT `bilet` FROM `roleplay` WHERE `player` = '" + player + "';");
            final int code = set.getInt("bilet");
            return VoeniiBilet.fromCode(code);
        }
        catch (Exception e) {
            System.out.println("161 line, proeb");
            return VoeniiBilet.NONE;
        }
    }
    
    public Med getMedCart(final String player) {
        try {
            final ResultSet set = this.query("SELECT `medcart` FROM `roleplay` WHERE `player` = '" + player + "';");
            final int code = set.getInt("medcart");
            return Med.fromCode(code);
        }
        catch (Exception e) {
            System.out.println("161 line, proeb");
            return Med.NONE;
        }
    }
    
    public boolean getPlayerInPris(final String player) {
        try {
            final ResultSet set = this.query("SELECT `inprison` FROM `roleplay` WHERE `player` = '" + player + "';");
            return set.getBoolean("inprison");
        }
        catch (Exception e) {
            System.out.println("123 line, proeb");
            return false;
        }
    }
    
    public int getPlayerStars(final String player) {
        try {
            final ResultSet set = this.query("SELECT `stars` FROM `roleplay` WHERE `player` = '" + player + "';");
            return set.getInt("stars");
        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println("133 line, proeb");
            return 0;
        }
    }
    
    public Work getPlayerRabota(final String player) {
        try {
            final ResultSet set = this.query("SELECT `work` FROM `roleplay` WHERE `player` = '" + player + "';");
            final int code = set.getInt("work");
            return WorkFactory.loadedWorks.get(code);
        }
        catch (Exception e) {
            System.out.println("143 line, proeb");
            return null;
        }
    }
    
    public void setEducation(final String player, final Education educ) {
        this.update("UPDATE `roleplay` SET `education` = " + educ.getCode() + " WHERE `player` = '" + player + "';");
    }
    
    public void setTimeInPrison(final String player, final int time) {
        this.update("UPDATE `roleplay` SET `time` = " + time + " WHERE `player` = '" + player + "';");
    }
    
    public Education getPlayerEducation(final String player) {
        try {
            final ResultSet set = this.query("SELECT `education` FROM `roleplay` WHERE `player` = '" + player + "';");
            final int code = set.getInt("education");
            return Education.fromCode(code);
        }
        catch (Exception e) {
            System.out.println("161 line, proeb");
            return Education.NONE;
        }
    }
    
    public boolean hasValue(final String player) {
        try {
            final ResultSet set = this.query("SELECT * FROM `roleplay` WHERE `player` = '" + player + "';");
            set.next();
            return set.previous();
        }
        catch (Exception e) {
            System.out.println("172 line, proeb");
            return false;
        }
    }
    
    public void resetAllPlayersRabota() {
        this.update("UPDATE `roleplay` SET `work` = 0;");
    }
    
    public void resetAllPlayersEducation() {
        this.update("UPDATE `roleplay` SET `education` = 0;");
    }
    
    public void resetPrison() {
        this.update("UPDATE `roleplay` SET `time` = 0;", "UPDATE `roleplay` SET `inprison` = '0';");
    }
    
    public ArrayList<String> getAllWorkers() {
        final ArrayList<String> list = new ArrayList<String>();
        try {
            final ResultSet set = this.query("SELECT `player` FROM `roleplay` WHERE `work` > 0;");
            while (set.next()) {
                list.add(set.getString("player"));
            }
            return list;
        }
        catch (Exception e) {
            System.out.println("195 line, proeb");
            return list;
        }
    }
    
    public ArrayList<String> getFivePlayers() {
        final ArrayList<String> list = new ArrayList<String>();
        try {
            final ResultSet set = this.query("SELECT `player` FROM `roleplay` WHERE `stars` >= 4;");
            while (set.next()) {
                list.add(set.getString("player"));
            }
            return list;
        }
        catch (Exception e) {
            System.out.println("213 line, proeb");
            return list;
        }
    }
    
    public ArrayList<String> getFuckinTyuremshiki() {
        final ArrayList<String> list = new ArrayList<String>();
        try {
            final ResultSet set = this.query("SELECT `player` FROM `roleplay` WHERE `inprison` = '1';");
            while (set.next()) {
                list.add(set.getString("player"));
            }
            return list;
        }
        catch (Exception e) {
            System.out.println("229 line, proeb");
            return list;
        }
    }
    
    public static void closeSQLiteDBConnection() throws SQLException {
        SQL.connection.close();
        SQL.statement.close();
    }
}
