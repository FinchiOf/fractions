package ru.Boss90.roleplay.work.armia;

import ru.Boss90.roleplay.enums.*;
import ru.Boss90.roleplay.interfaces.*;

public class General implements Work
{
    @Override
    public String getName() {
        return "Генерал";
    }
    
    @Override
    public AccessLevel[] getAccess() {
        return new AccessLevel[] { AccessLevel.ARMIA, AccessLevel.KICKARMIA, AccessLevel.UPARMIA, AccessLevel.UNKICKABLE, AccessLevel.GIVEBILET, AccessLevel.ARRESTPLAYER };
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
        return 35;
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
