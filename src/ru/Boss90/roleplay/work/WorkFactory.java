package ru.Boss90.roleplay.work;

import java.util.*;

import ru.Boss90.roleplay.interfaces.*;
import ru.Boss90.roleplay.work.hospital.*;
import ru.Boss90.roleplay.work.meria.*;
import ru.Boss90.roleplay.work.police.*;
import ru.Boss90.roleplay.work.armia.*;

public class WorkFactory
{
    public static HashMap<Integer, Work> loadedWorks;
    
    @SuppressWarnings("unused")
	public static void loadWorks() {
        WorkFactory.loadedWorks = new HashMap<Integer, Work>();
        final Work[] array;
        final Work[] works = array = new Work[] { new Cledyashii(), new CledyashiiHospital(), new CledyashiiPolice(), new NoneWork(), new InternWork(), new SanitarWork(), new SaverWork(), new DoctorWork(), new PsyhWork(), new HirurgWork(), new ZamDoctorWork(), new GlDoctorWork(), new StazherWork(), new CarWork(), new ProtecterWork(), new BodyProtecterWork(), new SecretarWork(), new AdvocatWork(), new ZamMer(), new Mer(), new KadetWork(), new OficerWork(), new SerzhantWork(), new PraporWork(), new LeytenantWork(), new CaptainWork(), new MaiorWork(), new PodpolkovnikWork(), new PolkovnikWork(), new SherifWork(), new Ryadovoi(), new Efreit(), new MlSersh(), new Sershant(), new Prapor(), new Maior(), new Podpolk(), new Polk(), new General()};
        for (final Work work : array) {
            WorkFactory.loadedWorks.put(work.getCode(), work);
        }
    }
}
