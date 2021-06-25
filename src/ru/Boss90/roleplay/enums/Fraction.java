package ru.Boss90.roleplay.enums;

public enum Fraction
{
    NONE("Не имеется", 0), 
    MERIA("Мэрия", 1), 
    POLICE("Полиция", 9), 
    HOSPITAL("Больница", 19),
    ARMIA("Армия", 27);
    
    private String name;
    private int defaultwork;
    
    private Fraction(final String name, final int defaultwork) {
        this.name = name;
        this.defaultwork = defaultwork;
    }
    
    public String getName() {
        return this.name;
    }
    
    public int getDefaultwork() {
        return this.defaultwork;
    }
}
