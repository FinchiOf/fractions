package ru.Boss90.roleplay.enums;

public enum Education
{
    NONE("�� �������", 0), 
    LOW("������", 1), 
    MEDIUM("�������", 2), 
    HIGH("������", 3);
    
    private String name;
    private int code;
    
    private Education(final String name, final int code) {
        this.name = name;
        this.code = code;
    }
    
    public String getName() {
        return this.name;
    }
    
    public int getCode() {
        return this.code;
    }
    
    public static Education fromCode(final int code) {
        for (final Education educ : values()) {
            if (educ.getCode() == code) {
                return educ;
            }
        }
        return null;
    }
}
