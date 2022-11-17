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
	
	
	// U
	public static final int[] epU = { Ecken.UBR, Ecken.URF, Ecken.UFL, Ecken.ULB, Ecken.DFR, Ecken.DLF, Ecken.DBL,Ecken.DRB };
	public static final int[] eoU = { 0, 0, 0, 0, 0, 0, 0, 0 };
	public static final int[] kpU = { Kanten.UB, Kanten.UR, Kanten.UF, Kanten.UL, Kanten.DR, Kanten.DF, Kanten.DL, Kanten.DB,Kanten.FR, Kanten.FL, Kanten.BL, Kanten.BR };
	public static final int[] koU = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
	
	// R
	public static final int[] epR = { Ecken.DFR, Ecken.UFL, Ecken.ULB, Ecken.URF, Ecken.DRB, Ecken.DLF, Ecken.DBL,Ecken.UBR };
	public static final int[] eoR = { 2, 0, 0, 1, 1, 0, 0, 2 };
	public static final int[] kpR = { Kanten.FR, Kanten.UF, Kanten.UL, Kanten.UB, Kanten.BR, Kanten.DF, Kanten.DL, Kanten.DB,Kanten.DR, Kanten.FL, Kanten.BL, Kanten.UR };
	public static final int[] koR = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };

	// F
	public static final int[] epF = { Ecken.UFL, Ecken.DLF, Ecken.ULB, Ecken.UBR, Ecken.URF, Ecken.DFR, Ecken.DBL, Ecken.DRB };
	public static final int[] eoF = { 1, 2, 0, 0, 2, 1, 0, 0 };
	public static final int[] kpF = { Kanten.UR, Kanten.FL, Kanten.UL, Kanten.UB, Kanten.DR, Kanten.FR, Kanten.DL, Kanten.DB, Kanten.UF, Kanten.DF, Kanten.BL, Kanten.BR };
	public static final int[] koF = { 0, 1, 0, 0, 0, 1, 0, 0, 1, 1, 0, 0 };
	
	// D
	public static final int[] epD = { Ecken.URF, Ecken.UFL, Ecken.ULB, Ecken.UBR, Ecken.DLF, Ecken.DBL, Ecken.DRB, Ecken.DFR };
	public static final int[] eoD = { 0, 0, 0, 0, 0, 0, 0, 0 };
	public static final int[] kpD = { Kanten.UR, Kanten.UF, Kanten.UL, Kanten.UB, Kanten.DF, Kanten.DL, Kanten.DB, Kanten.DR, Kanten.FR, Kanten.FL, Kanten.BL, Kanten.BR };
	public static final int[] koD = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
	
	// L
	public static final int[] epL = { Ecken.URF, Ecken.ULB, Ecken.DBL, Ecken.UBR, Ecken.DFR, Ecken.UFL, Ecken.DLF, Ecken.DRB };
	public static final int[] eoL = { 0, 1, 2, 0, 0, 2, 1, 0 };
	public static final int[] kpL = { Kanten.UR, Kanten.UF, Kanten.BL, Kanten.UB, Kanten.DR, Kanten.DF, Kanten.FL, Kanten.DB, Kanten.FR, Kanten.UL, Kanten.DL, Kanten.BR };
	public static final int[] koL = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		
	// B
	public static final int[] epB = { Ecken.URF, Ecken.UFL, Ecken.UBR, Ecken.DRB, Ecken.DFR, Ecken.DLF, Ecken.ULB, Ecken.DBL };
	public static final int[] eoB = { 0, 0, 1, 2, 0, 0, 2, 1 };
	public static final int[] kpB = { Kanten.UR, Kanten.UF, Kanten.UL, Kanten.BR, Kanten.DR, Kanten.DF, Kanten.DL, Kanten.BL, Kanten.FR, Kanten.FL, Kanten.UB, Kanten.DB };
	public static final int[] koB = { 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 1, 1 };
	
	// Züge
	public static final CubieWuerfel[] grundZuege = {
			new CubieWuerfel(epU, eoU, kpU, koU),
			new CubieWuerfel(epR, eoR, kpR, koR),
			new CubieWuerfel(epF, eoF, kpF, koF),
			new CubieWuerfel(epD, eoD, kpD, koD),
			new CubieWuerfel(epL, eoL, kpL, koL),
			new CubieWuerfel(epB, eoB, kpB, koB)};
	
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

	public static String lookupZug(int code){
		switch (code){
			case 0: return "U";
			case 1: return "U2";
			case 2: return "U'";
			case 3: return "R";
			case 4: return "R2";
			case 5: return "R'";
			case 6: return "F";
			case 7: return "F2";
			case 8: return "F'";
			case 9: return "D";
			case 10: return "D2";
			case 11: return "D'";
			case 12: return "L";
			case 13: return "L2";
			case 14: return "L'";
			case 15: return "B";
			case 16: return "B2";
			case 17: return "B'";
			default: return "Unknown";
		}
	}

	public static int lookupZug(String zug){
		switch (zug){
			case "U": return 0;
			case "U2": return 1;
			case "U'": return 2;
			case "R": return 3;
			case "R2": return 4;
			case "R'": return 5;
			case "F": return 6;
			case "F2": return 7;
			case "F'": return 8;
			case "D": return 9;
			case "D2": return 10;
			case "D'": return 11;
			case "L": return 12;
			case "L2": return 13;
			case "L'": return 14;
			case "B": return 15;
			case "B2": return 16;
			case "B'": return 17;
			default: return -1;
		}
	}

}
