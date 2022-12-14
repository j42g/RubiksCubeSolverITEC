package representation;

import loeser.CFOP;
import loeser.Database.Database;
import loeser.Database.DatenbankGenerateWatcher;
import loeser.Database.GenerateCornerDatabase;

public class Starter {

	public static void main(String[] args) {

		//Util.compare(100); // Hallo

		/*Wuerfel a = new Wuerfel();
		a.dreheZugsequenz("B U L F' U2 U2 B R' U' F F' L2 B2 R' B2 D B' D2 D2 R' U' U' D R' U2 L2");
		Util.printArr(a.getCornerCubies()[0]);
		Util.printArr(a.getCornerCubies()[1]);*/

		//GenerateCornerDatabase.permutationToIndex(new int[]{0, 1, 8, 4, 3, 11, 6, 5, 2, 10, 9, 7});



		GenerateCornerDatabase a = new GenerateCornerDatabase();
		Thread b = new Thread(a);
		b.setPriority(10);
		b.start();

		/*Database a1 = new Database("test1", true);
		Wuerfel w = new Wuerfel();
		w.drehe(Zuege.D3);
		Util.printArr(w.cubieOP()[0]);
		Util.printArr(w.cubieOP()[1]);*/
		/*System.out.println(GenerateCornerDatabase.totalIndex(w.cubieOP()[0], w.cubieOP()[1]));
		for(int i = -100; i < 101; i++)
			System.out.println(a.readfromDatabase((GenerateCornerDatabase.totalIndex(w.cubieOP()[0], w.cubieOP()[1]) + i)) + ", " + i);*/


		DatenbankGenerateWatcher c = new DatenbankGenerateWatcher();
		Thread d = new Thread(c);
		d.start();


		/*Database corners = new Database("cornerData", false);
		corners.writeDatabase(new byte[]{0, 1, 15, 2, 3, 10, 11, 12, 13, 14});
		corners.load();
		System.out.println(corners.readfromDatabase(6));*/

	}


}