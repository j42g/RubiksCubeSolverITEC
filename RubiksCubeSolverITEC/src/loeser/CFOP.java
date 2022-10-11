package loeser;

import java.util.Arrays;

import representation.Util;
import representation.Wuerfel;

public class CFOP {

	private Wuerfel w;
	private final int[] kreuzDaten = { 0x00000000, 0x10000000, 0x20000000, 0x30000000, 0x00400000, 0x00000000 };
	private final int[] kreuzMaske = { 0xF0F0F0F0, 0xF0000000, 0xF0000000, 0xF0000000, 0x00F00000, 0x00000000 };
	// Orange Green F2L Corner
	private final int[] F2LOG1Maske = { 0xFFF0F0F0, 0xF0000000, 0xF000000F, 0xFF000000, 0x00F00000, 0x00000000 };
	private final int[] F2LOG1Daten = { 0x00000000, 0x10000000, 0x20000002, 0x33000000, 0x00400000, 0x00000000 };
	// Orange Green F2L Full
	private final int[] F2LOG2Maske = { 0xFFF0F0F0, 0xF0000000, 0xF00000FF, 0xFFF00000, 0x00F00000, 0x00000000 };
	private final int[] F2LOG2Daten = { 0x00000000, 0x10000000, 0x20000022, 0x33300000, 0x00400000, 0x00000000 };
	// Green Red F2L Corner
	private final int[] F2LGR1Maske = { 0xFFFFF0F0, 0xF0000000, 0xF00000FF, 0xFFF0000F, 0x00FF0000, 0x00000000 };
	private final int[] F2LGR1Daten = { 0x00000000, 0x10000000, 0x20000022, 0x33300003, 0x00440000, 0x00000000 };
	// Green Red F2L Full
	private final int[] F2LGR2Maske = { 0xFFFFF0F0, 0xF0000000, 0xF00000FF, 0xFFF000FF, 0x00FFF000, 0x00000000 };
	private final int[] F2LGR2Daten = { 0x00000000, 0x10000000, 0x20000022, 0x33300033, 0x00444000, 0x00000000 };
	// Red Blue F2L Corner
	private final int[] F2LRB1Maske = { 0xFFFFFFF0, 0xFF000000, 0xF00000FF, 0xFFF000FF, 0x0FFFF000, 0x00000000 };
	private final int[] F2LRB1Daten = { 0x00000000, 0x11000000, 0x20000022, 0x33300033, 0x04444000, 0x00000000 };
	// Red Blue F2L Full
	private final int[] F2LRB2Maske = { 0xFFFFFFF0, 0xFFF00000, 0xF00000FF, 0xFFF000FF, 0xFFFFF000, 0x00000000 };
	private final int[] F2LRB2Daten = { 0x00000000, 0x11100000, 0x20000022, 0x33300033, 0x44444000, 0x00000000 };
	// Blue Orange Corner
	private final int[] F2LBO1Maske = { 0xFFFFFFFF, 0xFFF0000F, 0xFF0000FF, 0xFFF000FF, 0xFFFFF000, 0x00000000 };
	private final int[] F2LBO1Daten = { 0x00000000, 0x11100001, 0x22000022, 0x33300033, 0x44444000, 0x00000000 };
	// Blue Orange F2L
	private final int[] F2LBO2Maske = { 0xFFFFFFFF, 0xFFF000FF, 0xFFF000FF, 0xFFF000FF, 0xFFFFF000, 0x00000000 };
	private final int[] F2LBO2Daten = { 0x00000000, 0x11100011, 0x22200022, 0x33300033, 0x44444000, 0x00000000 };
	// Yellow Cross no edges
	private final int[] YNEMaske = { 0xFFFFFFFF, 0xFFF000FF, 0xFFF000FF, 0xFFF000FF, 0xFFFFF000, 0xF0F0F0F0 };
	private final int[] YNEDaten = { 0x00000000, 0x11100011, 0x22200022, 0x33300033, 0x44444000, 0x50505050 };
	/*
	 * // Yellow Cross all edges private final int[] YEMaske = { 0xFFFFFFFF,
	 * 0xFFF0F0FF, 0xFFF0F0FF, 0xFFF0F0FF, 0xFFFFF0F0, 0xF0F0F0F0 }; private final
	 * int[] YEDaten = { 0x00000000, 0x11101011, 0x22202022, 0x33303033, 0x44444040,
	 * 0x50505050 };
	 */
	// Finished
	private final int[] YMaske = { 0xFFFFFFFF, 0xFFFFFFFF, 0xFFFFFFFF, 0xFFFFFFFF, 0xFFFFFFFF, 0xFFFFFFFF };
	private final int[] YDaten = { 0x00000000, 0x11111111, 0x22222222, 0x33333333, 0x44444444, 0x55555555 };

	public CFOP(Wuerfel _w) {
		this.w = _w;
	}

	/**
	 * Startet den Lösungsprozess
	 */
	public void start() {
		// Kreuz
		IDDFS pattern = new IDDFS(this.w.getSeiten(), this.kreuzDaten, this.kreuzMaske);
		w.dreheZugsequenz(pattern.start());
		w.wuerfelAusgeben();
		pattern = new IDDFS(this.w.getSeiten(), this.F2LOG1Daten, this.F2LOG1Maske);
		w.dreheZugsequenz(pattern.start());
		w.wuerfelAusgeben();
		pattern = new IDDFS(this.w.getSeiten(), this.F2LOG2Daten, this.F2LOG2Maske);
		w.dreheZugsequenz(pattern.start());
		w.wuerfelAusgeben();
		pattern = new IDDFS(this.w.getSeiten(), this.F2LGR1Daten, this.F2LGR1Maske);
		w.dreheZugsequenz(pattern.start());
		w.wuerfelAusgeben();
		pattern = new IDDFS(this.w.getSeiten(), this.F2LGR2Daten, this.F2LGR2Maske);
		w.dreheZugsequenz(pattern.start());
		w.wuerfelAusgeben();
		pattern = new IDDFS(this.w.getSeiten(), this.F2LRB1Daten, this.F2LRB1Maske);
		w.dreheZugsequenz(pattern.start());
		w.wuerfelAusgeben();
		pattern = new IDDFS(this.w.getSeiten(), this.F2LRB2Daten, this.F2LRB2Maske);
		w.dreheZugsequenz(pattern.start());
		w.wuerfelAusgeben();
		pattern = new IDDFS(this.w.getSeiten(), this.F2LBO1Daten, this.F2LBO1Maske);
		w.dreheZugsequenz(pattern.start());
		w.wuerfelAusgeben();
		pattern = new IDDFS(this.w.getSeiten(), this.F2LBO2Daten, this.F2LBO2Maske);
		w.dreheZugsequenz(pattern.start());
		w.wuerfelAusgeben();
		/*
		 * pattern = new IDDFS(this.w.getSeiten(), this.YNEDaten, this.YNEMaske);
		 * w.dreheZugsequenz(pattern.start());
		 * System.out.println(Util.kodeZuegeZuNotation(pattern.start()));
		 * w.wuerfelAusgeben();
		 */
		/*
		 * pattern = new IDDFS(this.w.getSeiten(), this.YDaten, this.YMaske);
		 * w.dreheZugsequenz(pattern.start());
		 * System.out.println(Util.kodeZuegeZuNotation(pattern.start()));
		 * w.wuerfelAusgeben();
		 */
		
	}

	/*
	 * first determines in which order the cubies are OB = 0 GO = 1 RG = 2 BR = 3
	 * saves in array that has the corresponding slots as indices and then attemptss
	 * to put them into the right slot finally rotates the cubies to have the right
	 * rotation
	 * 
	 * 
	 * 
	 */
	// the exact cubie is known if two colors in a certain order
	
}
