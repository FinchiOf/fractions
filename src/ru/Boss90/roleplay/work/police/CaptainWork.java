package ru.Boss90.roleplay.work.police;

import ru.Boss90.roleplay.enums.*;
import ru.Boss90.roleplay.interfaces.*;

public class CaptainWork implements Work
{
    @Override
    public int getCode() {
        return 14;
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
        return new AccessLevel[] { AccessLevel.POLICE, AccessLevel.SHTRAF, AccessLevel.ARRESTPLAYER, AccessLevel.SETSTARS, AccessLevel.RESETSTARS};
    }
    
    @Override
    public String getName() {
        return "Капитан";
    }
    
    @Override
    public int getNext() {
        return 15;
    }
    
    @Override
    public int getNumber() {
        return 6;
    }
}
