package ru.Boss90.roleplay.enums;

public enum GenderType {
	    NONE("Скрыт", 3), 
	    MAN("Мужской", 1), 
	    ZEN("Женский", 0);
	    
	    private String name;
	    private int code;
	    
	    private GenderType(final String name, final int code) {
	        this.name = name;
	        this.code = code;
	    }
	    
	    public String getName() {
	        return this.name;
	    }
	    
	    public int getCode() {
	        return this.code;
	    }
	    
	    public static GenderType fromCode(final int code) {
	        for (final GenderType gender : values()) {
	            if (gender.getCode() == code) {
	                return gender;
	            }
	        }
	        return null;
	    }
	}
