package loeser;

import java.util.Arrays;

import representation.Util;
import representation.Wuerfel;

public class CFOP {
	
	private Wuerfel w;
	private final int[] kreuzDaten = {0x00000000, 0x10000000, 0x20000000, 0x30000000, 0x00400000, 0x00000000};
	private final int[] kreuzMaske = {0xF0F0F0F0, 0xF0000000, 0xF0000000, 0xF0000000, 0x00F00000, 0x00000000};
	//Orange Green F2L
	private final int[] F2LOGMaske = {0xFFF0F0F0, 0xF0000000, 0xF00000FF, 0xFFF00000, 0x00F00000, 0x00000000};
	private final int[] F2LOGDaten = {0x00000000, 0x10000000, 0x20000022, 0x33300000, 0x00400000, 0x00000000};
	//Green Red F2L
	private final int[] F2LGRMaske = {0xFFFFF0F0, 0xF0000000, 0xF00000FF, 0xFFF000FF, 0x00FFF000, 0x00000000};
	private final int[] F2LGRDaten = {0x00000000, 0x10000000, 0x20000022, 0x33300033, 0x00444000, 0x00000000};
	//Red Blue F2L
	private final int[] F2LRBMaske = {0xFFFFFFF0, 0xFFF00000, 0xF00000FF, 0xFFF000FF, 0xFFFFF000, 0x00000000};
	private final int[] F2LRBDaten = {0x00000000, 0x11100000, 0x20000022, 0x33300033, 0x44444000, 0x00000000};
	//Blue Orange F2L
	private final int[] F2LBOMaske = {0xFFFFFFFF, 0xFFF000FF, 0xFFF000FF, 0xFFF000FF, 0xFFFFF000, 0x00000000};
	private final int[] F2LBODaten = {0x00000000, 0x11100011, 0x22200022, 0x33300033, 0x44444000, 0x00000000};
	
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
		long time1 = System.currentTimeMillis();
		IDDFS pattern = new IDDFS(this.w.getSeiten(), this.kreuzDaten, this.kreuzMaske);
		w.dreheZugsequenz(pattern.start());
		System.out.println(Util.kodeZuegeZuNotation(pattern.start()));
		System.out.println(System.currentTimeMillis()-time1);
		w.wuerfelAusgeben();
		pattern = new IDDFS(this.w.getSeiten(), this.F2LOGDaten, this.F2LOGMaske);
		w.dreheZugsequenz(pattern.start());
		System.out.println(Util.kodeZuegeZuNotation(pattern.start()));
		System.out.println(System.currentTimeMillis()-time1);
		w.wuerfelAusgeben();
		pattern = new IDDFS(this.w.getSeiten(), this.F2LGRDaten, this.F2LGRMaske);
		w.dreheZugsequenz(pattern.start());
		System.out.println(Util.kodeZuegeZuNotation(pattern.start()));
		System.out.println(System.currentTimeMillis()-time1);
		w.wuerfelAusgeben();
		pattern = new IDDFS(this.w.getSeiten(), this.F2LRBDaten, this.F2LRBMaske);
		w.dreheZugsequenz(pattern.start());
		System.out.println(Util.kodeZuegeZuNotation(pattern.start()));
		System.out.println(System.currentTimeMillis()-time1);
		w.wuerfelAusgeben();
		pattern = new IDDFS(this.w.getSeiten(), this.F2LBODaten, this.F2LBOMaske);
		w.dreheZugsequenz(pattern.start());
		System.out.println(Util.kodeZuegeZuNotation(pattern.start()));
		System.out.println(System.currentTimeMillis()-time1);
		w.wuerfelAusgeben();
		
		
		;
	}
	
	
}
