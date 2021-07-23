package ru.Boss90.roleplay.work.police;

import ru.Boss90.roleplay.enums.*;
import ru.Boss90.roleplay.interfaces.*;

public class PraporWork implements Work
{
    @Override
    public int getCode() {
        return 12;
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
        return new AccessLevel[] { AccessLevel.POLICE, AccessLevel.SHTRAF, AccessLevel.ARRESTPLAYER, AccessLevel.Cuff };
    }
    
    @Override
    public String getName() {
        return "���������";
    }
    
    @Override
    public int getNext() {
        return 13;
    }
    
    @Override
    public int getNumber() {
        return 4;
    }
}
