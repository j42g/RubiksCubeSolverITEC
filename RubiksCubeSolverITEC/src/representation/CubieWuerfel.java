package representation;

import java.util.Arrays;

import kociembaDarstellung.*;

public class CubieWuerfel {

	private int[] ep;
	private int[] eo;
	private int[] kp;
	private int[] ko;

	/**
	 * Diese Klasse stellt einen Würfel auf dem "Cubie"-Level da
	 */
	public CubieWuerfel(int[] _ep, int[] _eo, int[] _kp, int[] _ko) {
		this.ep = Arrays.copyOf(_ep, _ep.length);
		this.eo = Arrays.copyOf(_eo, _eo.length);
		this.kp = Arrays.copyOf(_kp, _kp.length);
		this.ko = Arrays.copyOf(_ko, _ko.length);

	}

	public CubieWuerfel() {
		this(
				new int[]{0, 1, 2, 3, 4, 5, 6, 7},
				new int[]{0, 0, 0, 0, 0, 0, 0, 0},
				new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11},
				new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0});
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
		for (int k = 0; k < 12; k++) {
			this.kp[k] = kantenPerm[k];
			this.ko[k] = kantenOri[k];
		}
	}

	public void mul(CubieWuerfel b) {
		this.eckenMul(b);
		this.kantenMul(b);
	}
	
	public FaceletWuerfel toFaceletWuerfel() {
		FaceletWuerfel faceletW = new FaceletWuerfel();
		int j;
		int ori;
		for(int e = 0; e < 8; e++) { // Ecken
			j = this.ep[e];
			ori = this.eo[e];
			for(int k = 0; k < 3; k++) {
				faceletW.setzeFacelet(Facelet.eckenFacelet[e][(k + ori) % 3], Facelet.eckenFarbe[j][k]);
			}
		}
		for(int k = 0; k < 12; k++) { // Kanten
			j = this.kp[k];
			ori = this.ko[k];
			for(int i = 0; i < 2; i++) {
				faceletW.setzeFacelet(Facelet.kantenFacelet[k][(i + ori) % 2], Facelet.kantenFarbe[j][i]);
			}
		}
		return faceletW;
	}
	
	
	public void invCubieWuerfel(CubieWuerfel b) { // speichert den inv Wüerfel zu this in d
		for(int k = 0; k < 12; k++) { // Kanten
			b.kp[this.kp[k]] = k;
		}
		for(int k = 0; k < 12; k++) {
			b.ko[k] = this.ko[b.kp[k]];
		}
		
		for(int e = 0; e < 8; e++) { // Ecken
			b.ep[this.ep[e]] = e;
		}
		int ori;
		for(int e = 0; e < 8; e++) {
			ori = this.eo[b.ep[e]];
			if(ori > 2) {
				b.eo[e] = ori;
			} else {
				b.eo[e] = -ori;
				if(b.eo[e] < 0) {
					b.eo[e] += 3;
				}
			}
		}
	}
	
	public int cornerParity() {
		int s = 0;
		for(int i = 7; i > -1; i--) { // Rückwarts durch die Ecken
			for(int j = i - 1; j > -1; j--) { // Von der Ecke i bis zur ersten Ecke
				if(this.ep[j] > this.ep[i]) {
					s++;
				}
			}
		}
		return s % 2;
	}
	
	public int edgeParity() {
		int s = 0;
		for(int i = 11; i > -1; i--) { // Rückwarts durch die Kanten
			for(int j = i - 1; j > -1; j--) { // Von der Kante i bis zur ersten Kante
				if(this.kp[j] > this.kp[i]) {
					s++;
				}
			}
		}
		return s % 2;
	}

	
	public int[] symmetries() {
		
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
