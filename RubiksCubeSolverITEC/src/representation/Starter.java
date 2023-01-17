package representation;

import loeser.CFOP;

public class Starter {

    public static void main(String[] args) {

        //Util.testLauf(100);

        /*Wuerfel w = new Wuerfel();
        w.dreheZugsequenz("D2 R' F' B' F D2 F L F U D L' D F' U' B' L U' L' F' L2 B' L D U B'");
        CFOP s = new CFOP(w, 10);
        s.starteLoesen();*/

        String zuege = "R U F' D' L2 F' D B2 D2 L' U2 B2 D' R2 U R2 L2 D' R2 F2";
        Wuerfel w = new Wuerfel();
        w.dreheZugsequenz(Zuege.invZuege(Zuege.lookupZugseq(zuege)));


    }
}