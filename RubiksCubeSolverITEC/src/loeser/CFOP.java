package loeser;

import representation.Wuerfel;
import representation.Util;
import representation.Zuege;

public class CFOP extends Thread {

	private final Wuerfel w;

	/**
	 * 0: kein Debug; 1: Würfel nach jeden Schritt ausgeben
	 */
	private final int debug;

	private String solveSequenz;

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


	// OLL
	private final int[] OLLMaske = {0xFFFFFFFF, 0xFFF000FF, 0xFFF000FF, 0xFFF000FF, 0xFFFFF000, 0xFFFFFFFF};
	private final int[] OLLDaten = {0x00000000, 0x11100011, 0x22200022, 0x33300033, 0x44444000, 0x55555555};
	private final int[][] OLLzuege = {
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
	private final int[] PLLMaske = {0xFFFFFFFF, 0xFFFFFFFF, 0xFFFFFFFF, 0xFFFFFFFF, 0xFFFFFFFF, 0xFFFFFFFF};
	private final int[] PLLDaten = {0x00000000, 0x11111111, 0x22222222, 0x33333333, 0x44444444, 0x55555555};
	private final int[][] PLLzuege = {
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
		IDDFS pattern = new IDDFS(this.w.getSeiten(), this.kreuzDaten, this.kreuzMaske);
		w.dreheZugsequenz(pattern.loese());
		this.solveSequenz += Zuege.lookupZugseq(pattern.loese());
		if (this.debug == 1)
			w.ausgeben();

		pattern = new IDDFS(this.w.getSeiten(), this.F2LOG1Daten, this.F2LOG1Maske);
		w.dreheZugsequenz(pattern.loese());
		this.solveSequenz += Zuege.lookupZugseq(pattern.loese());
		if (this.debug == 1)
			w.ausgeben();

		pattern = new IDDFS(this.w.getSeiten(), this.F2LOG2Daten, this.F2LOG2Maske);
		w.dreheZugsequenz(pattern.loese());
		this.solveSequenz += Zuege.lookupZugseq(pattern.loese());
		if (this.debug == 1)
			w.ausgeben();

		pattern = new IDDFS(this.w.getSeiten(), this.F2LGR1Daten, this.F2LGR1Maske);
		w.dreheZugsequenz(pattern.loese());
		this.solveSequenz += Zuege.lookupZugseq(pattern.loese());
		if (this.debug == 1)
			w.ausgeben();

		pattern = new IDDFS(this.w.getSeiten(), this.F2LGR2Daten, this.F2LGR2Maske);
		w.dreheZugsequenz(pattern.loese());
		this.solveSequenz += Zuege.lookupZugseq(pattern.loese());
		if (this.debug == 1)
			w.ausgeben();

		pattern = new IDDFS(this.w.getSeiten(), this.F2LRB1Daten, this.F2LRB1Maske);
		w.dreheZugsequenz(pattern.loese());
		this.solveSequenz += Zuege.lookupZugseq(pattern.loese());
		if (this.debug == 1)
			w.ausgeben();

		pattern = new IDDFS(this.w.getSeiten(), this.F2LRB2Daten, this.F2LRB2Maske);
		w.dreheZugsequenz(pattern.loese());
		this.solveSequenz += Zuege.lookupZugseq(pattern.loese());
		if (this.debug == 1)
			w.ausgeben();

		pattern = new IDDFS(this.w.getSeiten(), this.F2LBO1Daten, this.F2LBO1Maske);
		w.dreheZugsequenz(pattern.loese());
		this.solveSequenz += Zuege.lookupZugseq(pattern.loese());
		if (this.debug == 1)
			w.ausgeben();

		pattern = new IDDFS(this.w.getSeiten(), this.F2LBO2Daten, this.F2LBO2Maske);
		w.dreheZugsequenz(pattern.loese());
		this.solveSequenz += Zuege.lookupZugseq(pattern.loese());
		if (this.debug == 1)
			w.ausgeben();

		// OLL
		IDDFSSeqs oll = new IDDFSSeqs(this.w.getSeiten(), this.OLLDaten, this.OLLMaske, this.OLLzuege);
		w.dreheZugsequenz(oll.loese());
		this.solveSequenz += Zuege.lookupZugseq(oll.loese());
		if (this.debug == 1) w.ausgeben();

		// PLL
		IDDFSSeqs pll = new IDDFSSeqs(this.w.getSeiten(), this.PLLDaten, this.PLLMaske, this.PLLzuege);
		w.dreheZugsequenz(pll.loese());
		this.solveSequenz += Zuege.lookupZugseq(pll.loese());
		if (this.debug == 1) w.ausgeben();


		System.out.println("Gelöst mit (Länge" + this.solveSequenz.split(" ").length + "): " + this.solveSequenz);
		System.out.println("Gelöst mit (Länge" + Util.kuerzen(this.solveSequenz).split(" ").length + "): " + Util.kuerzen(this.solveSequenz));

	}

}
