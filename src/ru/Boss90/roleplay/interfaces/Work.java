package ru.Boss90.roleplay.interfaces;

import ru.Boss90.roleplay.enums.*;

public interface Work
{
    String getName();
    
    Fraction getFraction();
    
    AccessLevel[] getAccess();
    
    int getNext();
    
    Education getEducation();
    
    int getCode();
    
    int getNumber();
}
