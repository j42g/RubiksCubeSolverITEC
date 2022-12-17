package representation;

import loeser.Database.GenerateCornerDatabase;

public class Starter {

	public static void main(String[] args) {

		GenerateCornerDatabase a = new GenerateCornerDatabase();
		Thread b = new Thread(a);
		b.setPriority(10);
		b.start();

	}
}