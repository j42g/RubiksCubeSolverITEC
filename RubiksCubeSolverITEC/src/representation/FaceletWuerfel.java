package representation;

import kociembaDarstellung.Farbe;

public class FaceletWuerfel {
	
	private int[] facelets;
	 
	public FaceletWuerfel() {
		this.facelets = new int[54];
		for(int c = 0; c < Farbe.farben.length; c++) {
			for(int f = 0; f < 9; f++) {
				facelets[9 * c + f] = Farbe.farben[c];
			}
		}
	}
	
	public FaceletWuerfel(int[] _facelets) {
		this();
		this.facelets = _facelets;
	}
	
	public FaceletWuerfel(String s) {
		char[] cs = s.toCharArray();
		for(int i = 0; i < 54; i++) {
			switch (cs[i]) {
			case 'W':
				this.facelets[i] = Farbe.U;
				break;
			case 'R':
				this.facelets[i] = Farbe.R;
				break;
			case 'G':
				this.facelets[i] = Farbe.F;
				break;
			case 'Y':
				this.facelets[i] = Farbe.D;
				break;
			case 'O':
				this.facelets[i] = Farbe.L;
				break;
			case 'B':
				this.facelets[i] = Farbe.B;
				break;
			}
		}
	}
	
	public void setzeFacelet(int index, int wert) {
		this.facelets[index] = wert;
	}
	
	public String toString() {
		String s = "";
		for(int f = 0; f < this.facelets.length; f++) {
			if(this.facelets[f] == Farbe.U) {
				s += 'W';
			} else if(this.facelets[f] == Farbe.R) {
				s += 'R';
			} else if(this.facelets[f] == Farbe.F) {
				s += 'G';
			} else if(this.facelets[f] == Farbe.D) {
				s += 'Y';
			} else if(this.facelets[f] == Farbe.L) {
				s += 'O';
			} else if(this.facelets[f] == Farbe.B) {
				s += 'B';
			}
		}
		return s;
	}
	
	public void ausgeben() {
		char[] s = this.toString().toCharArray();
		// U
		System.out.println("    " + s[0] + s[1] + s[2]);
		System.out.println("    " + s[3] + s[4] + s[5]);
		System.out.println("    " + s[6] + s[7] + s[8]);
		// L F R B
		System.out.println("" + s[36] + s[37] + s[38] + " " + s[18] + s[19] + s[20] + " " + s[9]  + s[10] + s[11] + " " + s[45] + s[46] + s[47]);
		System.out.println("" + s[39] + s[40] + s[41] + " " + s[21] + s[22] + s[23] + " " + s[12] + s[13] + s[14] + " " + s[48] + s[49] + s[50]);
		System.out.println("" + s[42] + s[43] + s[44] + " " + s[24] + s[25] + s[26] + " " + s[15] + s[16] + s[17] + " " + s[51] + s[52] + s[53]);
		// B
		System.out.println("    " + s[27] + s[28] + s[29]);
		System.out.println("    " + s[30] + s[31] + s[32]);
		System.out.println("    " + s[33] + s[34] + s[35]);
		
	}
}
