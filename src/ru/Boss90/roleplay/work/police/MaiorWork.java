package ru.Boss90.roleplay.work.police;

import ru.Boss90.roleplay.enums.*;
import ru.Boss90.roleplay.interfaces.*;

public class MaiorWork implements Work
{
    @Override
    public int getCode() {
        return 15;
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
        return new AccessLevel[] { AccessLevel.POLICE, AccessLevel.SHTRAF, AccessLevel.ARRESTPLAYER, AccessLevel.SETSTARS, AccessLevel.RESETSTARS };
    }
    
    @Override
    public String getName() {
        return "Майор";
    }
    
    @Override
    public int getNext() {
        return 16;
    }
    
    @Override
    public int getNumber() {
        return 7;
    }
}
