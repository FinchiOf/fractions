package ru.Boss90.roleplay.enums;

public enum AccessLevel
{
    NONE("�� �������"), 
    MERIA("�����"), 
    POLICE("�������"), 
    ARMIA("�����"), 
    HOSPITAL("��������"), 
    KICKHOSPITAL("���������� (��������)"), 
    KICKMERIA("���������� (�����)"), 
    KICKPOLICE("���������� (�������)"), 
    UPHOSPITAL("��������� (��������)"), 
    KICKARMIA("���������� (�����)"), 
    UPARMIA("��������� (�����)"), 
    UPMERIA("��������� (�����)"), 
    UPPOLICE("��������� (�������)"), 
    ARRESTPLAYER("������ � ������"), 
    UNKICKABLE("������ �������"), 
    HEALPLAYERS("������� �������"), 
    PASSPORTINFO("�������� �������� ��� �������"), 
    SEKRETARUTILS("������ ���������"), 
    SETSALARY("��������� ��������"), 
    SHTRAF("����������� �������"), 
    UNARREST("������������ ������������"),
    PASSPORTGIVE("������ ��������"),
    SETSTARS("������ �������"),
    RESETSTARS("������ �������"),
    KAZNA("������ ������ � ����� ������"),
    GIVEBILET("������ �������� ������"),
    MEDCARTGIVE("������ ���.�����");
    
    private String name;
    
    private AccessLevel(final String name) {
        this.name = name;
    }
    
    public String getName() {
        return this.name;
    }
    
    public static boolean contains(final AccessLevel[] mas, final AccessLevel level) {
        for (final AccessLevel l : mas) {
            if (l.equals(level)) {
                return true;
            }
        }
        return false;
    }
}
