package ru.Boss90.roleplay.work.meria;

import ru.Boss90.roleplay.interfaces.*;
import ru.Boss90.roleplay.enums.*;

public class CarWork implements Work
{
    @Override
    public int getNumber() {
        return 2;
    }
    
    @Override
    public int getNext() {
        return 3;
    }
    
    @Override
    public String getName() {
        return "\u0412\u043e\u0434\u0438\u0442\u0435\u043b\u044c";
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
        return 2;
    }
}
