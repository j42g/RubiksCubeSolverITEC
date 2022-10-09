package loeser;

import representation.Util;
import representation.Wuerfel;

public class CFOP {
	
	private Wuerfel w;
	private final int[] crossMask = {0x0F0F0F0F, 0x00000000, 0x00000000, 0x00000000, 0x00000000, 0x00000000};
	//private final int[] crossMask = {0xF0F0F0F0, 0xFFFFFFFF, 0xFFFFFFFF, 0xFFFFFFFF, 0xFFFFFFFF, 0xFFFFFFFF};
	//private final int[] crossMask = {0xF0F0F0F0, 0xFFFFFFFF, 0xFFFFFFFF, 0xFFFFFFFF, 0xFFFFFFFF, 0xFFFFFFFF};
	
	
	public CFOP(Wuerfel _w) {
		this.w = _w;
	}
	
	
	/**
	 * Startet den Lösungsprozess
	 */
	public void start() {
		// Kreuz
		IDDFS kreuz = new IDDFS(this.w.seiten, crossMask);
		Util.printArr(kreuz.start());
	}
	
	
}
