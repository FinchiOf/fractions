package ru.Boss90.roleplay.work.meria;

import ru.Boss90.roleplay.interfaces.*;
import ru.Boss90.roleplay.enums.*;

public class ProtecterWork implements Work
{
    @Override
    public int getNumber() {
        return 3;
    }
    
    @Override
    public int getNext() {
        return 4;
    }
    
    @Override
    public String getName() {
        return "Охранник";
    }
    
    @Override
    public AccessLevel[] getAccess() {
        return new AccessLevel[] { AccessLevel.MERIA, AccessLevel.SEKRETARUTILS  };
    }
    
    @Override
    public Education getEducation() {
        return Education.LOW;
    }
    
    @Override
    public Fraction getFraction() {
        return Fraction.MERIA;
    }
    
    @Override
    public int getCode() {
        return 3;
    }
}
