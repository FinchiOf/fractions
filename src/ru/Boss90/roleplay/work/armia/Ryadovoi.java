package ru.Boss90.roleplay.work.armia;

import ru.Boss90.roleplay.enums.*;
import ru.Boss90.roleplay.interfaces.*;

public class Ryadovoi implements Work
{
    @Override
    public String getName() {
        return "Рядовой";
    }
    
    @Override
    public AccessLevel[] getAccess() {
        return new AccessLevel[] { AccessLevel.ARMIA };
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
        return 27;
    }
    
    @Override
    public int getNext() {
        return 28;
    }
    
    @Override
    public int getNumber() {
        return 1;
    }
}
