package loeser;

import representation.Util;
import representation.Wuerfel;

public class ZweiMalZwei {
	
	private Wuerfel w;
	private String solveSequenz;
	
	//private final int[] ersteEckeDaten = { 0x00000000, 0x01000001, 0x02000002, 0x03000003, 0x04040000, 0x00000000 };
	//private final int[] ersteEckeMasken = { 0x0F0F0F0F, 0x0F00000F, 0x0F00000F, 0x0F00000F, 0x0F0F0000, 0x00000000 };
	
	private final int[] ersteSeiteDaten = { 0x00000000, 0x00000000, 0x00000000, 0x00000003, 0x00040000, 0x00000000 };
	private final int[] ersteSeiteMasken = { 0x000F0000, 0x00000000, 0x00000000, 0x0000000F, 0x000F0000, 0x00000000 };
	
	private final int[] loeseDaten = { 0x00000000, 0x01010101, 0x02020202, 0x03030303, 0x04040404, 0x05050505 };
	private final int[] loeseMasken = { 0x0F0F0F0F, 0x0F0F0F0F, 0x0F0F0F0F, 0x0F0F0F0F, 0x0F0F0F0F, 0x0F0F0F0F };
	private final int[] loeseZuege = {12, 11, 8, 4, 3, 0};
	
	public ZweiMalZwei(Wuerfel _w) {
		this.w = _w;
		this.solveSequenz = "";
	}
	
	public void loese() {
		/*IDDFS pattern = new IDDFS(this.w.getSeiten(), this.ersteEckeDaten, this.ersteEckeMasken);
		w.dreheZugsequenz(pattern.start());
		this.solveSequenz += Util.kodeZuegeZuNotation(pattern.start());
		
		this.w.wuerfelAusgeben();*/
		
		IDDFS pattern = new IDDFS(this.w.getSeiten(), this.loeseDaten, this.loeseMasken, this.loeseZuege);
		w.dreheZugsequenz(pattern.loese());
		this.solveSequenz += Util.kodeZuegeZuNotation(pattern.loese());
		
		System.out.println("Gelöst mit: " + this.solveSequenz);
	}
	
}
