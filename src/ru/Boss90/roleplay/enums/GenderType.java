package ru.Boss90.roleplay.enums;

public enum GenderType {
    NONE("Скрыт", 3),
    MAN("Мужской", 1),
    ZEN("Женский", 0);

    private String name;
    private int code;

    private GenderType(String name, int code) {
        this.name = name;
        this.code = code;
    }

    public String getName() {
        return this.name;
    }

    public int getCode() {
        return this.code;
    }

    public static GenderType fromCode(int code) {
        GenderType[] var4;
        int var3 = (var4 = values()).length;

        for(int var2 = 0; var2 < var3; ++var2) {
            GenderType gender = var4[var2];
            if (gender.getCode() == code) {
                return gender;
            }
        }

        return null;
    }
}
