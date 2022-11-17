package representation;

import loeser.CFOP;
import loeser.Database;
import loeser.GenerateCornerDatabase;
import loeser.ZweiMalZwei;

import java.util.BitSet;

public class Starter {

	public static void main(String[] args) {

		Util.compare(100);

		/*Wuerfel a = new Wuerfel();
		a.dreheZugsequenz("B U L F' U2 U2 B R' U' F F' L2 B2 R' B2 D B' D2 D2 R' U' U' D R' U2 L2");
		Util.printArr(a.getCornerCubies()[0]);
		Util.printArr(a.getCornerCubies()[1]);*/

		//GenerateCornerDatabase.permutationToIndex(new int[]{0, 1, 8, 4, 3, 11, 6, 5, 2, 10, 9, 7});

		/*Wuerfel a = new Wuerfel();
		a.verdrehe(25, true);
		CFOP b = new CFOP(a, 1);

		b.start();*/

		/*Database corners = new Database("cornerData", false);
		corners.writeDatabase(new byte[]{0, 1, 15, 2, 3, 10, 11, 12, 13, 14});
		corners.load();
		System.out.println(corners.readfromDatabase(6));*/

	}


}