package representation;

import kociembaDarstellung.*;

public class CubieWuerfel {

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
	
	// 
	private int[] ep;
	private int[] eo;
	private int[] kp;
	private int[] ko;

	/**
	 * Diese Klasse stellt einen Würfel auf dem "Cubie"-Level da
	 */
	public CubieWuerfel(int[] _ep, int[] _eo, int[] _kp, int[] _ko) {
		this.ep = _ep;
		this.eo = _eo;
		this.kp = _kp;
		this.ko = _ko;

	}

	public CubieWuerfel() {
		this(new int[8], new int[8], new int[12], new int[12]);
	}

	private void eckenMul(CubieWuerfel b) { // http://kociemba.org/cube.htm
		int[] eckenPerm = new int[8];
		int[] eckenOri = new int[8];
		int ori = 0;
		int ori_a;
		int ori_b;
		for (int e = 0; e < 8; e++) { // 0-7 sind die Ecken in kociembaDarstellung
			eckenPerm[e] = this.ep[b.ep[e]];
			ori_a = this.eo[b.ep[e]];
			ori_b = b.eo[e];
			// die Fälle check ich nicht, ist aber so (anscheinend)
			if (ori_a < 3 && ori_b < 3) { // beide normal
				ori = ori_a + ori_b;
				if (ori > 2) {
					ori -= 3;
				}
			} else if (ori_a < 3 && ori_b > 2) { // b gespiegelt
				ori = ori_a + ori_b;
				if (ori > 5) {
					ori -= 3;
				}
			} else if (ori_a > 2 && ori_b < 3) { // a gespiegelt
				ori = ori_a - ori_b;
				if (ori < 3) {
					ori += 3;
				}
			} else if (ori_a > 2 && ori_b > 2) { // beide gespiegelt
				ori = ori_a - ori_b;
				if (ori < 0) {
					ori += 3;
				}
			}
			eckenOri[e] = ori;
		}
		// in Cube schreiben
		for (int e = 0; e < 8; e++) {
			this.ep[e] = eckenPerm[e];
			this.eo[e] = eckenOri[e];
		}
	}

	private void kantenMul(CubieWuerfel b) {
		int[] kantenPerm = new int[12];
		int[] kantenOri = new int[12];
		for (int k = 0; k < 12; k++) {
			kantenPerm[k] = this.kp[b.kp[k]];
			kantenOri[k] = (this.ko[b.kp[k]] + b.ko[k]) % 2;
		}
		// in Cube schreiben
		for (int e = 0; e < 12; e++) {
			this.kp[e] = kantenPerm[e];
			this.ko[e] = kantenOri[e];
		}
	}

	public void mul(CubieWuerfel b) {
		this.eckenMul(b);
		this.kantenMul(b);
	}
	
	public void zuWuerfel() {
		
	}

	public boolean equals(CubieWuerfel b) {
		if (this.ep == b.ep && this.eo == b.eo && this.kp == b.kp && this.ko == b.ko) {
			return true;
		}
		return false;
	}

	public int[] getEp() {
		return ep;
	}

	public int[] getEo() {
		return eo;
	}

	public int[] getKp() {
		return kp;
	}

	public int[] getKo() {
		return ko;
	}

}
