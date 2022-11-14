package representation;

import loeser.CFOP;
import loeser.Database;

public class Starter {

	public static void main(String[] args) {

		/*Wuerfel a = new Wuerfel();
		a.verdrehe(25, true);

		CFOP b = new CFOP(a);

		b.start();*/

		// Database.writeDatabase(new byte[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15});
		System.out.println(Database.readfromDatabase(16));

	}


}