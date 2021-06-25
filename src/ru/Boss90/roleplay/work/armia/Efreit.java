package ru.Boss90.roleplay.work.armia;

import ru.Boss90.roleplay.enums.*;
import ru.Boss90.roleplay.interfaces.*;

public class Efreit implements Work
{
    @Override
    public String getName() {
        return "Åפנויעמנ";
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
        return 28;
    }
    
    @Override
    public int getNext() {
        return 29;
    }
    
    @Override
    public int getNumber() {
        return 2;
    }
}
