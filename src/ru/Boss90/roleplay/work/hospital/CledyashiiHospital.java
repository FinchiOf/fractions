package ru.Boss90.roleplay.work.hospital;

import ru.Boss90.roleplay.enums.*;
import ru.Boss90.roleplay.interfaces.*;

public class CledyashiiHospital implements Work
{
    @Override
    public String getName() {
        return "Следящий: Больница";
    }
    
    @Override
    public AccessLevel[] getAccess() {
        return new AccessLevel[] { AccessLevel.HOSPITAL, AccessLevel.KICKHOSPITAL, AccessLevel.UPHOSPITAL, AccessLevel.UNKICKABLE, AccessLevel.HEALPLAYERS, AccessLevel.SETSALARY, AccessLevel.MEDCARTGIVE };
    }
    
    @Override
    public Education getEducation() {
        return Education.HIGH;
    }
    
    @Override
    public Fraction getFraction() {
        return Fraction.HOSPITAL;
    }
    
    @Override
    public int getCode() {
        return 51;
    }
    
    @Override
    public int getNext() {
        return -1;
    }
    
    @Override
    public int getNumber() {
        return 51;
    }
}
