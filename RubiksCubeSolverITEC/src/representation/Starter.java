package representation;


import kociembaDarstellung.*;


public class Starter {

	public static void main(String[] args) {

		FaceletWuerfel a = new FaceletWuerfel("DUUBULDBFRBFRRULLLBRDFFFBLURDBFDFDRFRULBLUFDURRBLBDUDL", true);
		CubieWuerfel b = a.toCubieWuerfel();
		System.out.println(b.getFlip());
		
		
	}

}