package representation;

import java.util.ArrayList;
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

	public void kantenMul(CubieWuerfel b) {
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

	public void dreheZugsequenz(int[] zuege){
		for(int zug : zuege) {
			this.mul(Zuege.alleZuege[zug]);
		}
	}

	public void dreheZugsequenz(String seq){
		String[] zuege = seq.split(" ");
		for(String zug : zuege) {
			this.mul(Zuege.alleZuege[Zuege.lookupZug(zug)]);
		}
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
		ArrayList<Integer> s = new ArrayList<Integer>();
		CubieWuerfel c;
		CubieWuerfel d = new CubieWuerfel();
		for(int j = 0; j < Andere.N_SYM; j++) {
			c = new CubieWuerfel(Symmetrie.alleSym[j].ep, Symmetrie.alleSym[j].eo, Symmetrie.alleSym[j].kp, Symmetrie.alleSym[j].ko);
			c.mul(this);
			c.mul(Symmetrie.alleSym[Symmetrie.inv_idx[j]]);
			if(this.equals(c)) {
				s.add(j);
			}
			c.invCubieWuerfel(d);
			if(this.equals(d)) {
				s.add(j + Andere.N_SYM);
			}
		}
		
		
		int[] arr = new int[s.size()];
		for(int i = 0; i < arr.length; i++) {
			arr[i] = s.get(i);
		}
		return arr;
	}

	public boolean equals(Object b) {
		if(b instanceof CubieWuerfel c) return this.equals((CubieWuerfel) c);
		return false;
	}
	
	public boolean equals(CubieWuerfel b) {
		for(int i = 0; i < 8; i++) { // Ecken
			if(this.ep[i] != b.ep[i] || this.eo[i] != b.eo[i]) {
				return false;
			}
		}
		for(int i = 0; i < 12; i++) { // Kanten
			if(this.kp[i] != b.kp[i] || this.ko[i] != b.ko[i]) {
				return false;
			}
		}
		return true;
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

	// ------------------ Methoden für KoordWuerfel-------------
	public int getTwist() {
		int twist = 0;
		for(int e = 0; e < 7; e++){ // für alle Ecken (außer letzte?)
			twist = 3 * twist + this.eo[e];
		}
		return twist;
	}

	public void setTwist(int twist){
		int twistparity	= 0;
		for(int e = 6; e > -1; e--){ // alle Ecken außer DRB
			this.eo[e] = twist % 3;
			twistparity += this.eo[e];
			twist /= 3;
		}
		this.eo[7] = ((3 - twistparity % 3) % 3);
	}

	public int getFlip(){
		int flip = 0;
		for(int k = 0; k < 11; k++){ // für alle Ecken (außer letzte)
			flip = flip * 2 + this.ko[k];
		}
		return flip;
	}

	public void setFlip(int flip){
		int flipparity = 0;
		for(int i = 10; i > -1; i--) { // Ecken
			this.eo[i] = flip % 2;
			flipparity += this.eo[i];
			flip /= 2;
			this.eo[11] = ((2 - flipparity % 2) % 2);
		}
	}

	public int getSlice(){
		int a = 0;
		int x = 0;
		for(int j = 11; j > -1; j--){ // Kanten
			if(Kanten.FR <= this.kp[j] && this.kp[j] <= Kanten.BR){
				a += Util.cnk(11 - j, ++x);
			}
		}
		return a;
	}

	public void setSlice(int idx){
		int[] sliceEdge = new int[]{8, 9, 10, 11};
		int[] otherEdge = new int[]{0, 1, 2, 3, 4, 5, 6, 7};
		int a = idx;
		for(int k = 0; k < 12; k++){
			this.kp[k] = -1;
		}
		int x = 4;
		for(int k = 0; k < 12; k++){
			if(a - Util.cnk(11 - k, x) > -1){
				this.kp[k] = sliceEdge[4 - x];
				a -= Util.cnk(11 - k, x--);
			}
		}
		x = 0;
		for(int k = 0; k < 12; k++){
			if(this.ep[k] == -1){
				this.ep[k] = otherEdge[x++];
			}
		}
	}
}
