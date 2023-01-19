package representation;

import loeser.CFOP;
import loeser.Database.DatenbankGenerateWatcher;
import loeser.Database.GenerateCornerDatabase;

public class Starter {

    public static void main(String[] args) {


        GenerateCornerDatabase s = new GenerateCornerDatabase();
        Thread sT =  new Thread(s);
        sT.start();

        DatenbankGenerateWatcher w = new DatenbankGenerateWatcher();
        Thread wT = new Thread(w);
        wT.start();


    }
}