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
    POLIC("Политика"), 
    KICKPOLIC("Увольнение (Политика)"), 
    UPPOLIC("Повышение (Политика)"), 
    HEALPLAYERS("Лечение игроков (/hp)"), 
    PASSPORTINFO("Смотреть паспорты без запроса"), 
    SEKRETARUTILS("Отвечать на вопросы граждан"), 
    SETSALARY("Установка зарплаты"), 
    SHTRAF("Выписывание штрафов"), 
    UNARREST("Освобождение преступников"),
    PASSPORTGIVE("Выдача паспорта"),
    SETSTARS("Выдача розыска"),
    RESETSTARS("Снятие розыска"),
    KAZNA("Полный доступ к казне города"),
    GIVEBILET("Выдача военного билета"),
    MEDCARTGIVE("Выдача мед.карты"),
    Cuff("Заковывать в наручники (/cuff)"),
    baza("Просмотр базы данных города [СКОРО..]");
    
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
