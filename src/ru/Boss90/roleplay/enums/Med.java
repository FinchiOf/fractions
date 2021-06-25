package ru.Boss90.roleplay.enums;

public enum Med {
    NONE("����", 0), 
    YES("��������", 1), 
    NO("�����", 2);
    
    private String name;
    private int code;
    
    private Med(final String name, final int code) {
        this.name = name;
        this.code = code;
    }
    
    public String getName() {
        return this.name;
    }
    
    public int getCode() {
        return this.code;
    }
    
    public static Med fromCode(final int code) {
        for (final Med  Med : values()) {
            if (Med.getCode() == code) {
                return Med;
            }
        }
        return null;
    }
}
