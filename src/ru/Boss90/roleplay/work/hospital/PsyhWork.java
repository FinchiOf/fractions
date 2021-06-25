package ru.Boss90.roleplay.work.hospital;

import ru.Boss90.roleplay.enums.*;
import ru.Boss90.roleplay.interfaces.*;

public class PsyhWork implements Work
{
    @Override
    public String getName() {
        return "ѕсихолог";
    }
    
    @Override
    public AccessLevel[] getAccess() {
        return new AccessLevel[] { AccessLevel.HOSPITAL, AccessLevel.HEALPLAYERS, AccessLevel.MEDCARTGIVE };
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
        return 23;
    }
    
    @Override
    public int getNext() {
        return 24;
    }
    
    @Override
    public int getNumber() {
        return 5;
    }
}
