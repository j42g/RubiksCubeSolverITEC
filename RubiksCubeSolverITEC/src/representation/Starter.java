package representation;

import loeser.*;

public class Starter {

	public static void main(String[] args) {
			
		Wuerfel a = new Wuerfel();
		a.makeSolved();															  // Hab hier ich
		a.dreheZugsequenz("R F' D' F' U2 B D2 L2 B' R2 F L2 R2 B U L' B U B' U2 B F L2 F L B' U R' U");
		CFOP solver = new CFOP(a);
		
		
		//System.out.println(solver.hatKreuz());
		
		
		/*a.veraendereEinzeln(0, 7, 5);
		a.veraendereEinzeln(1, 6, 5);
		a.veraendereEinzeln(2, 7, 5);
		a.veraendereEinzeln(3, 7, 5);
		a.veraendereEinzeln(4, 6, 5);
		a.veraendereEinzeln(5, 7, 1);*/
		
		
		/*a.wuerfelAusgeben();
		 // 
		a.wuerfelAusgeben();*/

	}


}