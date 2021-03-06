package ru.Boss90.roleplay.work.meria;

import ru.Boss90.roleplay.interfaces.*;
import ru.Boss90.roleplay.enums.*;

public class SecretarWork implements Work
{
    @Override
    public int getNumber() {
        return 5;
    }
    
    @Override
    public int getNext() {
        return 6;
    }
    
    @Override
    public String getName() {
        return "Секретарь";
    }
    
    @Override
    public AccessLevel[] getAccess() {
        return new AccessLevel[] { AccessLevel.MERIA, AccessLevel.SEKRETARUTILS, AccessLevel.PASSPORTGIVE };
    }
    
    @Override
    public Education getEducation() {
        return Education.HIGH;
    }
    
    @Override
    public Fraction getFraction() {
        return Fraction.MERIA;
    }
    
    @Override
    public int getCode() {
        return 5;
    }
}
