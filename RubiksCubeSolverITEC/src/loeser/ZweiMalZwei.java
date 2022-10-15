package loeser;

import representation.Util;
import representation.Wuerfel;

public class ZweiMalZwei {
	
	private Wuerfel w;
	private String solveSequenz;
	
	//private final int[] ersteEckeDaten = { 0x00000000, 0x01000001, 0x02000002, 0x03000003, 0x04040000, 0x00000000 };
	//private final int[] ersteEckeMasken = { 0x0F0F0F0F, 0x0F00000F, 0x0F00000F, 0x0F00000F, 0x0F0F0000, 0x00000000 };
	
	private final int[] DBLEckeDaten =  { 0x00000000, 0x00000100, 0x00020000, 0x00000000, 0x00000000, 0x05000000 };
	private final int[] DBLEckeMasken = { 0x00000000, 0x00000F00, 0x000F0000, 0x00000000, 0x00000000, 0x0F000000 };
	
	private final int[] loeseDaten = { 0x00000000, 0x01010101, 0x02020202, 0x03030303, 0x04040404, 0x05050505 };
	private final int[] loeseMasken = { 0x0F0F0F0F, 0x0F0F0F0F, 0x0F0F0F0F, 0x0F0F0F0F, 0x0F0F0F0F, 0x0F0F0F0F };
	private final int[] loeseZuege = {12, 11, 8, 4, 3, 0};
	
	public ZweiMalZwei(Wuerfel _w) {
		this.w = _w;
		this.solveSequenz = "";
	}
	
	public void loese() {
		
		// DBL-Ecke lösen
		
		IDDFS pattern = new IDDFS(this.w.getSeiten(), this.DBLEckeDaten, this.DBLEckeMasken);
		w.dreheZugsequenz(pattern.loese());
		//this.solveSequenz += Util.kodeZuegeZuNotation(pattern.loese());
		
		//w.wuerfelAusgeben();
		
		
		pattern = new IDDFS(this.w.getSeiten(), this.loeseDaten, this.loeseMasken, this.loeseZuege);
		w.dreheZugsequenz(pattern.loese());
		//this.solveSequenz += Util.kodeZuegeZuNotation(pattern.loese());
		
		//System.out.println("Gelöst mit: " + this.solveSequenz);
	}
	
}
