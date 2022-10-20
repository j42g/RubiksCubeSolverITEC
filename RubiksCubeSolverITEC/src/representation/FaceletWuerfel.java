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
				s += 'W';
			}
		}
		return s;
	}
}
