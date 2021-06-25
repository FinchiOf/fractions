package ru.Boss90.roleplay.work.meria;

import ru.Boss90.roleplay.interfaces.*;
import ru.Boss90.roleplay.enums.*;

public class BodyProtecterWork implements Work
{
    @Override
    public int getNumber() {
        return 4;
    }
    
    @Override
    public int getNext() {
        return 5;
    }
    
    @Override
    public String getName() {
        return "Телохранитель";
    }
    
    @Override
    public AccessLevel[] getAccess() {
        return new AccessLevel[] { AccessLevel.MERIA, AccessLevel.PASSPORTGIVE };
    }
    
    @Override
    public Education getEducation() {
        return Education.MEDIUM;
    }
    
    @Override
    public Fraction getFraction() {
        return Fraction.MERIA;
    }
    
    @Override
    public int getCode() {
        return 4;
    }
}
