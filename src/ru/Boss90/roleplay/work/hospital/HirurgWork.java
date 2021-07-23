package ru.Boss90.roleplay.work.hospital;

import ru.Boss90.roleplay.enums.*;
import ru.Boss90.roleplay.interfaces.*;

public class HirurgWork implements Work
{
    @Override
    public String getName() {
        return "Хирург";
    }
    
    @Override
    public AccessLevel[] getAccess() {
        return new AccessLevel[] { AccessLevel.HOSPITAL, AccessLevel.HEALPLAYERS, AccessLevel.MEDCARTGIVE, AccessLevel.baza };
    }
    
    @Override
    public Education getEducation() {
        return Education.MEDIUM;
    }
    
    @Override
    public Fraction getFraction() {
        return Fraction.HOSPITAL;
    }
    
    @Override
    public int getCode() {
        return 24;
    }
    
    @Override
    public int getNext() {
        return 25;
    }
    
    @Override
    public int getNumber() {
        return 6;
    }
}
