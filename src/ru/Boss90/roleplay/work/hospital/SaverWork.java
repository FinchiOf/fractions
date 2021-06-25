package ru.Boss90.roleplay.work.hospital;

import ru.Boss90.roleplay.enums.*;
import ru.Boss90.roleplay.interfaces.*;

public class SaverWork implements Work
{
    @Override
    public String getName() {
        return "Спасатель";
    }
    
    @Override
    public AccessLevel[] getAccess() {
        return new AccessLevel[] { AccessLevel.HOSPITAL, AccessLevel.HEALPLAYERS };
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
        return 21;
    }
    
    @Override
    public int getNext() {
        return 22;
    }
    
    @Override
    public int getNumber() {
        return 3;
    }
}
