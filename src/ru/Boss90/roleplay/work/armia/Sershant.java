package ru.Boss90.roleplay.work.armia;

import ru.Boss90.roleplay.enums.*;
import ru.Boss90.roleplay.interfaces.*;

public class Sershant implements Work
{
    @Override
    public String getName() {
        return "В.Сержант";
    }
    
    @Override
    public AccessLevel[] getAccess() {
        return new AccessLevel[] { AccessLevel.ARMIA, AccessLevel.ARRESTPLAYER };
    }
    
    @Override
    public Education getEducation() {
        return Education.LOW;
    }
    
    @Override
    public Fraction getFraction() {
        return Fraction.ARMIA;
    }
    
    @Override
    public int getCode() {
        return 30;
    }
    
    @Override
    public int getNext() {
        return 31;
    }
    
    @Override
    public int getNumber() {
        return 4;
    }
}
