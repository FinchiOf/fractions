package ru.Boss90.roleplay.work.meria;

import ru.Boss90.roleplay.interfaces.*;
import ru.Boss90.roleplay.enums.*;

public class StazherWork implements Work
{
    @Override
    public int getNumber() {
        return 1;
    }
    
    @Override
    public int getNext() {
        return 2;
    }
    
    @Override
    public String getName() {
        return "Стажер";
    }
    
    @Override
    public AccessLevel[] getAccess() {
        return new AccessLevel[] { AccessLevel.MERIA };
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
        return 1;
    }
}
