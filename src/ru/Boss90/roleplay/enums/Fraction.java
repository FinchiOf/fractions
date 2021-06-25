package ru.Boss90.roleplay.enums;

public enum Fraction
{
    NONE("�� �������", 0), 
    MERIA("�����", 1), 
    POLICE("�������", 9), 
    HOSPITAL("��������", 19),
    ARMIA("�����", 27);
    
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
