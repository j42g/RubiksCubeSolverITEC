package representation;

public class Starter {

	public static void main(String[] args) {
			
		Wuerfel a = new Wuerfel();
		
		a.makeSolved();
		a.veraendereEinzeln(0, 7, 5);
		a.dreheZugsequenz("U"); // "R F' D' F' U2 B D2 L2 B' R2 F L2 R2 B U L' B U B' U2 B"
		a.wuerfelAusgeben();

	}


}