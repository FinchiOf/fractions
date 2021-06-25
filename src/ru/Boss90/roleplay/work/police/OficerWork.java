package ru.Boss90.roleplay.work.police;

import ru.Boss90.roleplay.enums.*;
import ru.Boss90.roleplay.interfaces.*;

public class OficerWork implements Work
{
    @Override
    public int getCode() {
        return 10;
    }
    
    @Override
    public Fraction getFraction() {
        return Fraction.POLICE;
    }
    
    @Override
    public Education getEducation() {
        return Education.LOW;
    }
    
    @Override
    public AccessLevel[] getAccess() {
        return new AccessLevel[] { AccessLevel.POLICE };
    }
    
    @Override
    public String getName() {
        return "ќфицер";
    }
    
    @Override
    public int getNext() {
        return 11;
    }
    
    @Override
    public int getNumber() {
        return 2;
    }
}
