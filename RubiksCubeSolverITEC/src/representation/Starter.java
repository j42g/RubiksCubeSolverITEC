package representation;

public class Starter {

	public static void main(String[] args) {
			
		Wuerfel a = new Wuerfel();
		a.makeSolved();
		a.veraendereEinzeln(0, 1, 5);
		a.wuerfelAusgeben();
		//a.dreheZug(0);
		a.veraendereEinzeln(4, 1, 1);
		a.veraendereEinzeln(2, 3, 5);
		a.veraendereEinzeln(2, 3, 5);
		a.veraendereEinzeln(2, 3, 5);
		a.wuerfelAusgeben();
		

	}


}