package representation;

import loeser.*;
import graphic.renderer.ScreenThread;
import kociembaDarstellung.*;


public class Starter {

	public static void main(String[] args) {
		
		
		FaceletWuerfel a = new FaceletWuerfel("BFFDUUDDRFRUBRULBBBLUFFRDFDFRFBDDBLLDRLULLULRLDRFBBUUR", true);
		CubieWuerfel b = a.toCubieWuerfel();
		Util.printBinArr(b.symmetries());
		
	}

}