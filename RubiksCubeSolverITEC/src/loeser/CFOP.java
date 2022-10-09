package loeser;

import representation.Util;
import representation.Wuerfel;

public class CFOP {
	
	private Wuerfel w;
	private final int[] kreuzDaten = {0xF0F0F0F0, 0x10000000, 0x20000000, 0x30000000, 0x00500000, 0x00000000};
	private final int[] kreuzMaske = {0xF0F0F0F0, 0xF0000000, 0xF0000000, 0xF0000000, 0x00F00000, 0x00000000};
	//private final int[] crossMask = {0xF0F0F0F0, 0xFFFFFFFF, 0xFFFFFFFF, 0xFFFFFFFF, 0xFFFFFFFF, 0xFFFFFFFF};
	//private final int[] crossMask = {0xF0F0F0F0, 0xFFFFFFFF, 0xFFFFFFFF, 0xFFFFFFFF, 0xFFFFFFFF, 0xFFFFFFFF};
	
	
	public CFOP(Wuerfel _w) {
		this.w = _w;
	}
	
	
	/**
	 * Startet den L�sungsprozess
	 */
	public void start() {
		// Kreuz
		IDDFS kreuz = new IDDFS(this.w.seiten, this.kreuzDaten, this.kreuzMaske);
		Util.printArr(kreuz.start());
	}
	
	
}
