package representation;

public class Starter {

	public static void main(String[] args) {
			
		Wuerfel a = new Wuerfel();
		a.makeSolved();
		a.veraendereEinzeln(3, 2, 4);
		a.printFace(3);

	}

}
