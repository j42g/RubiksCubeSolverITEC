package representation;

import loeser.CFOP;

public class Starter {

    public static void main(String[] args) {

        Wuerfel w = new Wuerfel();
        w.dreheZugsequenz("D2 R' F' B' F D2 F L F U D L' D F' U' B' L U' L' F' L2 B' L D U B'");
        CFOP s = new CFOP(w, 10);
        s.starteLoesen();

    }
}