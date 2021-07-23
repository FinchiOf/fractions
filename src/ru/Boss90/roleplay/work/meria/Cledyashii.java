package ru.Boss90.roleplay.work.meria;

import ru.Boss90.roleplay.enums.AccessLevel;
import ru.Boss90.roleplay.enums.Education;
import ru.Boss90.roleplay.enums.Fraction;
import ru.Boss90.roleplay.interfaces.Work;

public class Cledyashii implements Work {
	@Override
	public int getNumber() {
		return 50;
	}

	@Override
	public int getNext() {
		return -1;
	}

	@Override
	public String getName() {
		return "Следящий: Мэрия";
	}

	@Override
	public AccessLevel[] getAccess() {
		return new AccessLevel[] { AccessLevel.MERIA, AccessLevel.SEKRETARUTILS, AccessLevel.UNARREST,
				AccessLevel.KICKMERIA, AccessLevel.UPMERIA, AccessLevel.PASSPORTINFO, AccessLevel.SETSALARY, AccessLevel.PASSPORTGIVE, AccessLevel.baza };
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
		return 50;
	}
}
