package representation;

import loeser.*;
import graphic.renderer.ScreenThread;
import kociembaDarstellung.*;


public class Starter {

	public static void main(String[] args) {
		
		ScreenThread a = new ScreenThread();
		a.start();
		
		/*CubieWuerfel cubie = new CubieWuerfel();
		cubie.mul(Zuege.alleZuege[Zuege.F1]);
		cubie.zuFaceletWuerfel().ausgeben();*/
		
		Zuege.alleZuege[Zuege.U2].zuFaceletWuerfel().ausgeben();
	}

}