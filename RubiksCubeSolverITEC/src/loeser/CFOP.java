package loeser;

import representation.Wuerfel;
import representation.Util;
import representation.Zuege;

public class CFOP extends Thread {

	private static final int[] kreuzDaten = { 0x00000000, 0x10000000, 0x20000000, 0x30000000, 0x00400000, 0x00000000 };
	private static final int[] kreuzMaske = { 0xF0F0F0F0, 0xF0000000, 0xF0000000, 0xF0000000, 0x00F00000, 0x00000000 };
	// Orange Green F2L Corner
	private static final int[] F2LOG1Maske = { 0xFFF0F0F0, 0xF0000000, 0xF000000F, 0xFF000000, 0x00F00000, 0x00000000 };
	private static final int[] F2LOG1Daten = { 0x00000000, 0x10000000, 0x20000002, 0x33000000, 0x00400000, 0x00000000 };
	// Orange Green F2L Full
	private static final int[] F2LOG2Maske = { 0xFFF0F0F0, 0xF0000000, 0xF00000FF, 0xFFF00000, 0x00F00000, 0x00000000 };
	private static final int[] F2LOG2Daten = { 0x00000000, 0x10000000, 0x20000022, 0x33300000, 0x00400000, 0x00000000 };
	// Green Red F2L Corner
	private static final int[] F2LGR1Maske = { 0xFFFFF0F0, 0xF0000000, 0xF00000FF, 0xFFF0000F, 0x00FF0000, 0x00000000 };
	private static final int[] F2LGR1Daten = { 0x00000000, 0x10000000, 0x20000022, 0x33300003, 0x00440000, 0x00000000 };
	// Green Red F2L Full
	private static final int[] F2LGR2Maske = { 0xFFFFF0F0, 0xF0000000, 0xF00000FF, 0xFFF000FF, 0x00FFF000, 0x00000000 };
	private static final int[] F2LGR2Daten = { 0x00000000, 0x10000000, 0x20000022, 0x33300033, 0x00444000, 0x00000000 };
	// Red Blue F2L Corner
	private static final int[] F2LRB1Maske = { 0xFFFFFFF0, 0xFF000000, 0xF00000FF, 0xFFF000FF, 0x0FFFF000, 0x00000000 };
	private static final int[] F2LRB1Daten = { 0x00000000, 0x11000000, 0x20000022, 0x33300033, 0x04444000, 0x00000000 };
	// Red Blue F2L Full
	private static final int[] F2LRB2Maske = { 0xFFFFFFF0, 0xFFF00000, 0xF00000FF, 0xFFF000FF, 0xFFFFF000, 0x00000000 };
	private static final int[] F2LRB2Daten = { 0x00000000, 0x11100000, 0x20000022, 0x33300033, 0x44444000, 0x00000000 };
	// Blue Orange Corner
	private static final int[] F2LBO1Maske = { 0xFFFFFFFF, 0xFFF0000F, 0xFF0000FF, 0xFFF000FF, 0xFFFFF000, 0x00000000 };
	private static final int[] F2LBO1Daten = { 0x00000000, 0x11100001, 0x22000022, 0x33300033, 0x44444000, 0x00000000 };
	// Blue Orange F2L
	private static final int[] F2LBO2Maske = { 0xFFFFFFFF, 0xFFF000FF, 0xFFF000FF, 0xFFF000FF, 0xFFFFF000, 0x00000000 };
	private static final int[] F2LBO2Daten = { 0x00000000, 0x11100011, 0x22200022, 0x33300033, 0x44444000, 0x00000000 };


	// OLL
	private static final int[] OLLMaske = {0xFFFFFFFF, 0xFFF000FF, 0xFFF000FF, 0xFFF000FF, 0xFFFFF000, 0xFFFFFFFF};
	private static final int[] OLLDaten = {0x00000000, 0x11100011, 0x22200022, 0x33300033, 0x44444000, 0x55555555};
	private static final int[][] OLLzuege = {
			{Zuege.D1}, // drehen
			{Zuege.D2},
			{Zuege.D3},
			{3, 12, 15, 14, 17, 5, 9, 15, 6, 17, 8, 11}, // Dot
			{3, 12, 15, 14, 17, 5}, // I-Shape
			{3, 15, 12, 17, 14, 5}, // L-Shape
			{12, 16, 14, 17, 12, 17, 14}, // Anti-Sune
			{12, 15, 14, 15, 12, 16, 14}, // Sune
			{12, 15, 14, 15, 12, 17, 14, 15, 12, 16, 14}, // H
			{3, 14, 5, 6, 3, 12, 5, 8}, // L
			{12, 16, 13, 17, 13, 17, 13, 16, 12}, // PI
			{6, 3, 14, 5, 8, 3, 12, 5}, // T
			{13, 0, 14, 16, 12, 2, 14, 16, 14} // U


	};

	// PLL
	private static final int[] PLLMaske = {0xFFFFFFFF, 0xFFFFFFFF, 0xFFFFFFFF, 0xFFFFFFFF, 0xFFFFFFFF, 0xFFFFFFFF};
	private static final int[] PLLDaten = {0x00000000, 0x11111111, 0x22222222, 0x33333333, 0x44444444, 0x55555555};
	private static final int[][] PLLzuege = {
			{Zuege.D1}, // drehen
			{Zuege.D2},
			{Zuege.D3},
			{3, 12, 17, 14, 17, 12, 15, 14, 5, 12, 15, 14, 17, 14, 3, 12, 5}, // Diagonal
			{12, 15, 14, 17, 14, 3, 13, 17, 14, 17, 12, 15, 14, 5}, // Headlights
			{13, 16, 12, 16, 13, 16, 13, 16, 12, 16, 13}, // H
			{13, 17, 14, 17, 12, 15, 12, 15, 12, 17, 12}, // Ua
			{14, 15, 14, 17, 14, 17, 14, 15, 12, 15, 13}, // Ub
			{14, 17, 13, 15, 12, 15, 14, 17, 12, 15, 12, 17, 12, 17, 14} // Z
	};

	private final Wuerfel w;
	private final int debug;
	private String solveSequenz;

	public CFOP(Wuerfel _w, int _debug) {
		this.w = _w;
		this.debug = _debug;
		this.solveSequenz = "";
	}

	public CFOP(Wuerfel _w) {
		this(_w, 0);
	}

	public void run() {
		beginne();
	}

	public void beginne() {
		// Kreuz
		IDDFS pattern = new IDDFS(this.w.getSeiten(), kreuzDaten, kreuzMaske);
		w.dreheZugsequenz(pattern.loese());
		this.solveSequenz += Zuege.lookupZugseq(pattern.loese());
		if (this.debug == 1)
			w.ausgeben();

		pattern = new IDDFS(this.w.getSeiten(), F2LOG1Daten, F2LOG1Maske);
		w.dreheZugsequenz(pattern.loese());
		this.solveSequenz += Zuege.lookupZugseq(pattern.loese());
		if (this.debug == 1)
			w.ausgeben();

		pattern = new IDDFS(this.w.getSeiten(), F2LOG2Daten, F2LOG2Maske);
		w.dreheZugsequenz(pattern.loese());
		this.solveSequenz += Zuege.lookupZugseq(pattern.loese());
		if (this.debug == 1)
			w.ausgeben();

		pattern = new IDDFS(this.w.getSeiten(), F2LGR1Daten, F2LGR1Maske);
		w.dreheZugsequenz(pattern.loese());
		this.solveSequenz += Zuege.lookupZugseq(pattern.loese());
		if (this.debug == 1)
			w.ausgeben();

		pattern = new IDDFS(this.w.getSeiten(), F2LGR2Daten, F2LGR2Maske);
		w.dreheZugsequenz(pattern.loese());
		this.solveSequenz += Zuege.lookupZugseq(pattern.loese());
		if (this.debug == 1)
			w.ausgeben();

		pattern = new IDDFS(this.w.getSeiten(), F2LRB1Daten, F2LRB1Maske);
		w.dreheZugsequenz(pattern.loese());
		this.solveSequenz += Zuege.lookupZugseq(pattern.loese());
		if (this.debug == 1)
			w.ausgeben();

		pattern = new IDDFS(this.w.getSeiten(), F2LRB2Daten, F2LRB2Maske);
		w.dreheZugsequenz(pattern.loese());
		this.solveSequenz += Zuege.lookupZugseq(pattern.loese());
		if (this.debug == 1)
			w.ausgeben();

		pattern = new IDDFS(this.w.getSeiten(), F2LBO1Daten, F2LBO1Maske);
		w.dreheZugsequenz(pattern.loese());
		this.solveSequenz += Zuege.lookupZugseq(pattern.loese());
		if (this.debug == 1)
			w.ausgeben();

		pattern = new IDDFS(this.w.getSeiten(), F2LBO2Daten, F2LBO2Maske);
		w.dreheZugsequenz(pattern.loese());
		this.solveSequenz += Zuege.lookupZugseq(pattern.loese());
		if (this.debug == 1)
			w.ausgeben();

		// OLL
		IDDFSSeqs oll = new IDDFSSeqs(this.w.getSeiten(), OLLDaten, OLLMaske, OLLzuege);
		w.dreheZugsequenz(oll.loese());
		this.solveSequenz += Zuege.lookupZugseq(oll.loese());
		if (this.debug == 1) w.ausgeben();

		// PLL
		IDDFSSeqs pll = new IDDFSSeqs(this.w.getSeiten(), PLLDaten, PLLMaske, PLLzuege);
		w.dreheZugsequenz(pll.loese());
		this.solveSequenz += Zuege.lookupZugseq(pll.loese());
		if (this.debug == 1) w.ausgeben();


		System.out.println("Gel�st mit (L�nge" + this.solveSequenz.split(" ").length + "): " + this.solveSequenz);
		System.out.println("Gel�st mit (L�nge" + Util.kuerzen(this.solveSequenz).split(" ").length + "): " + Util.kuerzen(this.solveSequenz));

	}

}
