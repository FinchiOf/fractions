package ru.Boss90.roleplay.work;

import ru.Boss90.roleplay.enums.*;
import ru.Boss90.roleplay.interfaces.*;

public class NoneWork implements Work
{
    @Override
    public int getCode() {
        return 0;
    }
    
    @Override
    public Fraction getFraction() {
        return Fraction.NONE;
    }
    
    @Override
    public Education getEducation() {
        return Education.NONE;
    }
    
    @Override
    public AccessLevel[] getAccess() {
        return new AccessLevel[] { AccessLevel.NONE };
    }
    
    @Override
    public String getName() {
        return "Безработный";
    }
    
    @Override
    public int getNext() {
        return -1;
    }
    
    @Override
    public int getNumber() {
        return 0;
    }
}
