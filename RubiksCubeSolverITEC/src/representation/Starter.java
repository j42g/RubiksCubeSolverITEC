package representation;

import loeser.CFOP;

public class Starter {

	public static void main(String[] args) {


		Wuerfel a = new Wuerfel();
		a.verdrehe(20, true);

		CFOP b = new CFOP(a, 1);

		b.start();

	}


}