package ru.Boss90.roleplay.work.hospital;

import ru.Boss90.roleplay.enums.*;
import ru.Boss90.roleplay.interfaces.*;

public class InternWork implements Work
{
    @Override
    public String getName() {
        return "Интерн";
    }
    
    @Override
    public AccessLevel[] getAccess() {
        return new AccessLevel[] { AccessLevel.HOSPITAL };
    }
    
    @Override
    public Education getEducation() {
        return Education.LOW;
    }
    
    @Override
    public Fraction getFraction() {
        return Fraction.HOSPITAL;
    }
    
    @Override
    public int getCode() {
        return 19;
    }
    
    @Override
    public int getNext() {
        return 20;
    }
    
    @Override
    public int getNumber() {
        return 1;
    }
}
