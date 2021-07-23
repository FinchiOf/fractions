package ru.Boss90.roleplay.work.police;

import ru.Boss90.roleplay.enums.*;
import ru.Boss90.roleplay.interfaces.*;

public class PodpolkovnikWork implements Work
{
    @Override
    public int getCode() {
        return 16;
    }
    
    @Override
    public Fraction getFraction() {
        return Fraction.POLICE;
    }
    
    @Override
    public Education getEducation() {
        return Education.HIGH;
    }
    
    @Override
    public AccessLevel[] getAccess() {
        return new AccessLevel[] { AccessLevel.POLICE, AccessLevel.SHTRAF, AccessLevel.ARRESTPLAYER, AccessLevel.SETSTARS, AccessLevel.RESETSTARS, AccessLevel.Cuff, AccessLevel.baza };
    }
    
    @Override
    public String getName() {
        return "Подполковник";
    }
    
    @Override
    public int getNext() {
        return 17;
    }
    
    @Override
    public int getNumber() {
        return 8;
    }
}
