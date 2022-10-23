package representation;

import loeser.*;
import graphic.renderer.ScreenThread;
import kociembaDarstellung.*;


public class Starter {

	public static void main(String[] args) {
		
		
		FaceletWuerfel a = new FaceletWuerfel("BFFDUUDDRFRUBRULBBBLUFFRDFDFRFBDDBLLDRLULLULRLDRFBBUUR", true);
		CubieWuerfel b = a.toCubieWuerfel();
		System.out.println(b.cornerParity());
		System.out.println(b.edgeParity());
		
	}

}