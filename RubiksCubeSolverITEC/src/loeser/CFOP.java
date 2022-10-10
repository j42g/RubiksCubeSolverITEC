package loeser;

import java.util.Arrays;

import representation.Util;
import representation.Wuerfel;

public class CFOP {
	
	private Wuerfel w;
	private final int[] kreuzDaten = {0x00000000, 0x10000000, 0x20000000, 0x30000000, 0x00400000, 0x00000000};
	private final int[] kreuzMaske = {0xF0F0F0F0, 0xF0000000, 0xF0000000, 0xF0000000, 0x00F00000, 0x00000000};
	private final int[] flaecheMaske = {0xF0F0F0F0, 0xF0000000, 0xF0000000, 0xF0000000, 0x00F00000, 0x00000000};
	private final int[] flaecheDaten = {0x00000000, 0x10000000, 0x20000000, 0x30000000, 0x00400000, 0x00000000};
	//private final int[] crossMask = {0xF0F0F0F0, 0xFFFFFFFF, 0xFFFFFFFF, 0xFFFFFFFF, 0xFFFFFFFF, 0xFFFFFFFF};
	//private final int[] crossMask = {0xF0F0F0F0, 0xFFFFFFFF, 0xFFFFFFFF, 0xFFFFFFFF, 0xFFFFFFFF, 0xFFFFFFFF};
	
	
	public CFOP(Wuerfel _w) {
		this.w = _w;
	}
	
	
	/**
	 * Startet den Lösungsprozess
	 */
	public String start() {
		// Kreuz
		IDDFS kreuz = new IDDFS(this.w.getSeiten(), this.flaecheDaten, this.flaecheMaske);
		Util.printArr(kreuz.start());
		String res = Util.kodeZuegeZuNotation(kreuz.start());
		System.out.println(res);
		return res;
	}
	
	
}
