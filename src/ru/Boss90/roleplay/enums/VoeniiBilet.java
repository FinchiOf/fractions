package ru.Boss90.roleplay.enums;

public enum VoeniiBilet{
    NONE("Нету", 0), 
    YES("Есть", 1);
    
    private String name;
    private int code;
    
    private VoeniiBilet(final String name, final int code) {
        this.name = name;
        this.code = code;
    }
    
    public String getName() {
        return this.name;
    }
    
    public int getCode() {
        return this.code;
    }
    
    public static VoeniiBilet fromCode(final int code) {
        for (final VoeniiBilet  Bilet : values()) {
            if (Bilet.getCode() == code) {
                return Bilet;
            }
        }
        return null;
    }
}
