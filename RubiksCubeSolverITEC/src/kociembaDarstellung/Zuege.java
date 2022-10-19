package kociembaDarstellung;

import representation.CubieWuerfel;

public class Zuege {
	/**
	 * Definition der Züge nach Kociemba
	 */
	public static final int U1 = 0;
	public static final int U2 = 1;
	public static final int U3 = 2;
	public static final int R1 = 3;
	public static final int R2 = 4;
	public static final int R3 = 5;
	public static final int F1 = 6;
	public static final int F2 = 7;
	public static final int F3 = 8;
	public static final int D1 = 9;
	public static final int D2 = 10;
	public static final int D3 = 11;
	public static final int L1 = 12;
	public static final int L2 = 13;
	public static final int L3 = 14;
	public static final int B1 = 15;
	public static final int B2 = 16;
	public static final int B3 = 17;

	public static final CubieWuerfel[] grundZuege = {
			new CubieWuerfel(CubieWuerfel.epU, CubieWuerfel.eoU, CubieWuerfel.kpU, CubieWuerfel.koU),
			new CubieWuerfel(CubieWuerfel.epR, CubieWuerfel.eoR, CubieWuerfel.kpR, CubieWuerfel.koR),
			new CubieWuerfel(CubieWuerfel.epF, CubieWuerfel.eoF, CubieWuerfel.kpF, CubieWuerfel.koF),
			new CubieWuerfel(CubieWuerfel.epD, CubieWuerfel.eoD, CubieWuerfel.kpD, CubieWuerfel.koD),
			new CubieWuerfel(CubieWuerfel.epL, CubieWuerfel.eoL, CubieWuerfel.kpL, CubieWuerfel.koL),
			new CubieWuerfel(CubieWuerfel.epB, CubieWuerfel.eoB, CubieWuerfel.kpB, CubieWuerfel.koB)};
	
	public static final CubieWuerfel[] alleZuege = new CubieWuerfel[18];
	static {
		for(int f = 0; f < 6; f++) { // f ist Farbe
			CubieWuerfel cw = new CubieWuerfel();
			for(int zugArt = 0; zugArt < 3; zugArt++) {
				cw.mul(grundZuege[f]);
				alleZuege[3 * f + zugArt] = new CubieWuerfel(cw.getEp(), cw.getEo(), cw.getKp(), cw.getKo());
			}
		}
	}

}
