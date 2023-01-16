package loeser;

import representation.Util;
import representation.Wuerfel;
import representation.Zuege;

public class ZweiMalZwei {
	
	private final Wuerfel w;
	private final int debug;
	private String solveSequenz;
	
	//private final int[] ersteEckeDaten = { 0x00000000, 0x01000001, 0x02000002, 0x03000003, 0x04040000, 0x00000000 };
	//private final int[] ersteEckeMasken = { 0x0F0F0F0F, 0x0F00000F, 0x0F00000F, 0x0F00000F, 0x0F0F0000, 0x00000000 };
	
	private final int[] DBLEckeDaten =  { 0x00000000, 0x00000100, 0x00020000, 0x00000000, 0x00000000, 0x05000000 };
	private final int[] DBLEckeMasken = { 0x00000000, 0x00000F00, 0x000F0000, 0x00000000, 0x00000000, 0x0F000000 };
	
	private final int[] loeseDaten = { 0x00000000, 0x01010101, 0x02020202, 0x03030303, 0x04040404, 0x05050505 };
	private final int[] loeseMasken = { 0x0F0F0F0F, 0x0F0F0F0F, 0x0F0F0F0F, 0x0F0F0F0F, 0x0F0F0F0F, 0x0F0F0F0F };
	private final int[] loeseZuege = { // U R F Züge
			Zuege.U1,
			Zuege.U2,
			Zuege.U3,
			Zuege.R1,
			Zuege.R2,
			Zuege.R3,
			Zuege.F1,
			Zuege.F2,
			Zuege.F3};
	
	public ZweiMalZwei(Wuerfel _w, int debug) {
		this.w = _w;
		this.solveSequenz = "";
		this.debug = debug;
	}

	public ZweiMalZwei(Wuerfel _w) {
		this(_w, 0);
	}
	
	public void loese() {

		if(debug >= 1) w.ausgeben();

		// DBL-Ecke lösen
		IDDFS pattern = new IDDFS(this.w.getSeiten(), this.DBLEckeDaten, this.DBLEckeMasken, debug);
		w.dreheZugsequenz(pattern.loese());
		this.solveSequenz += Zuege.lookupZugseq(pattern.loese());
		
		if(debug >= 1) w.ausgeben();
		
		
		pattern = new IDDFS(this.w.getSeiten(), this.loeseDaten, this.loeseMasken, this.loeseZuege, debug);
		w.dreheZugsequenz(pattern.loese());
		this.solveSequenz += Zuege.lookupZugseq(pattern.loese());

		if(debug >= 1)w.ausgeben();
		
		if (debug < 0) System.out.println("Gelöst mit: " + this.solveSequenz);
	}
	
}
