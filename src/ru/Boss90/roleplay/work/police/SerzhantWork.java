package ru.Boss90.roleplay.work.police;

import ru.Boss90.roleplay.enums.*;
import ru.Boss90.roleplay.interfaces.*;

public class SerzhantWork implements Work
{
    @Override
    public int getCode() {
        return 11;
    }
    
    @Override
    public Fraction getFraction() {
        return Fraction.POLICE;
    }
    
    @Override
    public Education getEducation() {
        return Education.MEDIUM;
    }
    
    @Override
    public AccessLevel[] getAccess() {
        return new AccessLevel[] { AccessLevel.POLICE, AccessLevel.SHTRAF, AccessLevel.ARRESTPLAYER };
    }
    
    @Override
    public String getName() {
        return "Сержант";
    }
    
    @Override
    public int getNext() {
        return 12;
    }
    
    @Override
    public int getNumber() {
        return 3;
    }
}
