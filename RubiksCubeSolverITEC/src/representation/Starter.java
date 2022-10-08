package representation;

public class Starter {

	public static void main(String[] args) {
			
		Wuerfel a = new Wuerfel();
		a.makeSolved();
		a.dreheZug(0b1000);
		/*a.veraendereEinzeln(0, 1, 5);
		a.veraendereEinzeln(4, 1, 1);
		a.veraendereEinzeln(2, 3, 5);
		a.veraendereEinzeln(3, 4, 2);
		a.veraendereEinzeln(1, 0, 0);*/
		a.wuerfelAusgeben();
		

	}


}