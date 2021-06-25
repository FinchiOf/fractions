package ru.Boss90.roleplay.work.meria;

import ru.Boss90.roleplay.interfaces.*;
import ru.Boss90.roleplay.enums.*;

public class Mer implements Work
{
    @Override
    public int getNumber() {
        return 8;
    }
    
    @Override
    public int getNext() {
        return -1;
    }
    
    @Override
    public String getName() {
        return "Ìýð";
    }
    
    @Override
    public AccessLevel[] getAccess() {
        return new AccessLevel[] { AccessLevel.MERIA, AccessLevel.SEKRETARUTILS, AccessLevel.UNARREST, AccessLevel.KICKMERIA, AccessLevel.UPMERIA, AccessLevel.PASSPORTINFO, AccessLevel.SETSALARY, AccessLevel.UNKICKABLE, AccessLevel.PASSPORTGIVE, AccessLevel.KAZNA };
    }
    
    @Override
    public Education getEducation() {
        return Education.HIGH;
    }
    
    @Override
    public Fraction getFraction() {
        return Fraction.MERIA;
    }
    
    @Override
    public int getCode() {
        return 8;
    }
}
