package ru.Boss90.roleplay.enums;

public enum Med {
    NONE("Нету", 0),
    YES("Здоровый", 1),
    NO("Болен", 2);

    private String name;
    private int code;

    private Med(String name, int code) {
        this.name = name;
        this.code = code;
    }

    public String getName() {
        return this.name;
    }

    public int getCode() {
        return this.code;
    }

    public static Med fromCode(int code) {
        Med[] var4;
        int var3 = (var4 = values()).length;

        for(int var2 = 0; var2 < var3; ++var2) {
            Med Med = var4[var2];
            if (Med.getCode() == code) {
                return Med;
            }
        }

        return null;
    }
}
