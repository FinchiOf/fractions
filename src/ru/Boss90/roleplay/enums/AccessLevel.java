package ru.Boss90.roleplay.enums;

public enum AccessLevel
{
    NONE("Не имеется"), 
    MERIA("Мэрия"), 
    POLICE("Полиция"), 
    ARMIA("Армия"), 
    HOSPITAL("Больница"), 
    KICKHOSPITAL("Увольнение (Больница)"), 
    KICKMERIA("Увольнение (Мэрия)"), 
    KICKPOLICE("Увольнение (Полиция)"), 
    UPHOSPITAL("Повышение (Больница)"), 
    KICKARMIA("Увольнение (Армия)"), 
    UPARMIA("Повышение (Армия)"), 
    UPMERIA("Повышение (Мэрия)"), 
    UPPOLICE("Повышение (Полиция)"), 
    ARRESTPLAYER("Сажать в тюрьму"), 
    UNKICKABLE("Нельзя уволить"), 
    HEALPLAYERS("Лечение игроков"), 
    PASSPORTINFO("Смотреть паспорты без запроса"), 
    SEKRETARUTILS("Работа секретарём"), 
    SETSALARY("Установка зарплаты"), 
    SHTRAF("Выписывание штрафов"), 
    UNARREST("Освобождение преступников"),
    PASSPORTGIVE("Выдача паспорта"),
    SETSTARS("Выдача розыска"),
    RESETSTARS("Снятие розыска"),
    KAZNA("Полный доступ к казне города"),
    GIVEBILET("Выдача военного билета"),
    MEDCARTGIVE("Выдача мед.карты");
    
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
