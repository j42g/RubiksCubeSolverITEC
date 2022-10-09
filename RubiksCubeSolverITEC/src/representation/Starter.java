package representation;

public class Starter {

	public static void main(String[] args) {
			
		Wuerfel a = new Wuerfel();
		
		a.makeSolved(); 
		
		a.dreheUhr(1);
		a.dreheGUhr(0);
		
		a.wuerfelAusgeben();
		a.dreheZugsequenz("R2 F2 B2");

	}


}