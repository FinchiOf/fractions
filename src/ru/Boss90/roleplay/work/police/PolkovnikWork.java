package ru.Boss90.roleplay.work.police;

import ru.Boss90.roleplay.enums.*;
import ru.Boss90.roleplay.interfaces.*;

public class PolkovnikWork implements Work
{
    @Override
    public int getCode() {
        return 17;
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
        return new AccessLevel[] { AccessLevel.POLICE, AccessLevel.SHTRAF, AccessLevel.ARRESTPLAYER, AccessLevel.PASSPORTINFO, AccessLevel.UPPOLICE, AccessLevel.KICKPOLICE, AccessLevel.SETSTARS, AccessLevel.RESETSTARS, AccessLevel.Cuff, AccessLevel.baza };
    }
    
    @Override
    public String getName() {
        return "Полковник";
    }
    
    @Override
    public int getNext() {
        return -1;
    }
    
    @Override
    public int getNumber() {
        return 9;
    }
}
