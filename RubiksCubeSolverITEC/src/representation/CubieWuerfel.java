package representation;

import kociembaDarstellung.*;

public class CubieWuerfel {

	// U
	private final int[] epU = { Ecken.UBR, Ecken.URF, Ecken.UFL, Ecken.ULB, Ecken.DFR, Ecken.DLF, Ecken.DBL,Ecken.DRB };
	private final int[] eoU = { 0, 0, 0, 0, 0, 0, 0, 0 };
	private final int[] kpU = { Kanten.UB, Kanten.UR, Kanten.UF, Kanten.UL, Kanten.DR, Kanten.DF, Kanten.DL, Kanten.DB,Kanten.FR, Kanten.FL, Kanten.BL, Kanten.BR };
	private final int[] koU = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };

	// R
	private final int[] epR = { Ecken.DFR, Ecken.UFL, Ecken.ULB, Ecken.URF, Ecken.DRB, Ecken.DLF, Ecken.DBL,Ecken.UBR };
	private final int[] eoR = { 2, 0, 0, 1, 1, 0, 0, 2 };
	private final int[] kpR = { Kanten.FR, Kanten.UF, Kanten.UL, Kanten.UB, Kanten.BR, Kanten.DF, Kanten.DL, Kanten.DB,Kanten.DR, Kanten.FL, Kanten.BL, Kanten.UR };
	private final int[] koR = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };

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

	public void eckenMul(CubieWuerfel b) { // http://kociemba.org/cube.htm
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

	public boolean equals(CubieWuerfel b) {
		if (this.ep == b.ep && this.eo == b.eo && this.kp == b.kp && this.ko == b.ko) {
			return true;
		}
		return false;
	}

}
