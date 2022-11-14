package representation;

import loeser.CFOP;

public class Starter {

	public static void main(String[] args) {

		Wuerfel a = new Wuerfel();
		a.verdrehe(25, true);

		CFOP b = new CFOP(a);

		b.start();

	}


}