package representation;

import loeser.*;

public class Starter {

	public static void main(String[] args) {
			
		
		Wuerfel a = new Wuerfel();
		a.verdrehe(26, true);
		a.wuerfelAusgeben();
		ZweiMalZwei loeser = new ZweiMalZwei(a);
		loeser.loese();
		a.wuerfelAusgeben();
		
		/*System.out.println("KEKW:" + Util.kuerzen("F F"));
		System.out.println(Util.kuerzen("U R' U F' B' R R' L D' L' R F R F' F' R' F' R' D' R D' U R' D' R U' D' R D R' B L B' L' B' D' B D' B' B D R D' R' B' B R D R' D' B' F D L D' L' F' DR D R' D R D' R' D R D D R'"));
		
		Wuerfel a = new Wuerfel();
		a.makeSolved();
		a.dreheZugsequenz("R F' D' F' U2 B D2 L2 B' R2 F L2 R2 B U L' B U B' U2 B");
		a.wuerfelAusgeben();
		CFOP solver = new CFOP(a, 1);
		long time1 = System.currentTimeMillis();
		solver.start();
		long time2 = System.currentTimeMillis();
		System.out.println(time2-time1);
		a.wuerfelAusgeben();*/
		
		
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