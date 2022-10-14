package loeser;

import representation.Wuerfel;
import representation.Util;

public class CFOP {

	private Wuerfel w;
	
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
	
	// ------------------------------------------Patterns-for-Y-Layer------------------------------------------
	private final int[] DotMaske = { 0xFFFFFFFF, 0xFFF0F0FF, 0xFFF0F0FF, 0xFFF0F0FF, 0xFFFFF0F0, 0x00000000 };
	private final int[] DotDaten = { 0x00000000, 0x11105011, 0x22205022, 0x33305033, 0x44444050, 0x00000000 };

	private final int[] IShapeMaske = { 0xFFFFFFFF, 0xFFF0F0FF, 0xFFF000FF, 0xFFF0F0FF, 0xFFFFF000, 0xF000F000 };
	private final int[] IShapeDaten = { 0x00000000, 0x11105011, 0x22200022, 0x33305033, 0x44444000, 0x50005000 };

	private final int[] LShapeMaske = { 0xFFFFFFFF, 0xFFF000FF, 0xFFF0F0FF, 0xFFF0F0FF, 0xFFFFF000, 0x00F0F000 };
	private final int[] LShapeDaten = { 0x00000000, 0x11100011, 0x22205022, 0x33305033, 0x44444000, 0x00505000 };

	private final int[] AntisuneMaske = { 0xFFFFFFFF, 0xFFF00FFF, 0xFFF00FFF, 0xFFF000FF, 0xFFFFF00F, 0xF0F0FFF0 };
	private final int[] AntisuneDaten = { 0x00000000, 0x11100511, 0x22200522, 0x33300033, 0x44444005, 0x50505550 };

	private final int[] HMaske = { 0xFFFFFFFF, 0xFFF000FF, 0xFFFF0FFF, 0xFFF000FF, 0xFFFFFF0F, 0xF0F0F0F0 };
	private final int[] HDaten = { 0x00000000, 0x11100011, 0x22250522, 0x33300033, 0x44444505, 0x50505050 };

	private final int[] LMaske = { 0xFFFFFFFF, 0xFFF00FFF, 0xFFF000FF, 0xFFF000FF, 0xFFFFFF00, 0xF0FFF0FF };
	private final int[] LDaten = { 0x00000000, 0x11100511, 0x22200022, 0x33300033, 0x44444500, 0x50555055 };

	private final int[] PiMaske = { 0xFFFFFFFF, 0xFFFF00FF, 0xFFFF0FFF, 0xFFF00FFF, 0xFFFFF000, 0xF0F0F0F0 };
	private final int[] PiDaten = { 0x00000000, 0x11150011, 0x22250522, 0x33300533, 0x44444000, 0x50505050 };

	private final int[] SuneMaske = { 0xFFFFFFFF, 0xFFFF00FF, 0xFFF000FF, 0xFFFF00FF, 0xFFFFFF00, 0xFFF0F0F0 };
	private final int[] SuneDaten = { 0x00000000, 0x11150011, 0x22200022, 0x33350033, 0x44444500, 0x55505050 };

	private final int[] TMaske = { 0xFFFFFFFF, 0xFFF00FFF, 0xFFF000FF, 0xFFFF00FF, 0xFFFFF000, 0xF0FFFFF0 };
	private final int[] TDaten = { 0x00000000, 0x11100511, 0x22200022, 0x33350033, 0x44444000, 0x50555550 };

	private final int[] UMaske = { 0xFFFFFFFF, 0xFFFF0FFF, 0xFFF000FF, 0xFFF000FF, 0xFFFFF000, 0xF0F0FFFF };
	private final int[] UDaten = { 0x00000000, 0x11150511, 0x22200022, 0x33300033, 0x44444000, 0x50505555 };
	
	private final int[] OLLMaske = { 0xFFFFFFFF, 0xFFF000FF, 0xFFF000FF, 0xFFF000FF, 0xFFFFF000, 0xFFFFFFFF };
	private final int[] OLLDaten = { 0x00000000, 0x11100011, 0x22200022, 0x33300033, 0x44444000, 0x55555555 };
	
	//----------------------------------------------------------------------------------------------------------

	public CFOP(Wuerfel _w, int _debug) {
		this.w = _w;
		this.debug = _debug;
		this.solveSequenz = "";
	}
	
	
	public CFOP(Wuerfel _w) {
		this(_w, 0);
	}
	
	private void Dot() {
		w.dreheZugsequenz("B R D R' D' B' F D L D' L' F'");
		this.solveSequenz += "B R D R' D' B' F D L D' L' F' ";
	}

	private void IShape() {
		w.dreheZugsequenz("B R D R' D' B'");
		this.solveSequenz += "B R D R' D' B' ";
	}

	private void LShape() {
		w.dreheZugsequenz("B D R D' R' B'");
		this.solveSequenz += "B D R D' R' B' ";
		//new int[]{0xFBA8203}
	}
	
	private void Antisune() {
		w.dreheZugsequenz(new int[]{0xCD4DC554,0xF});
		this.solveSequenz += Util.kodeZuegeZuNotation(new int[]{0xCD4DC554,0xF});
	}

	
	private void H() {
		w.dreheZugsequenz(new int[]{0x5CD45C54,0xFC554});
		this.solveSequenz += Util.kodeZuegeZuNotation(new int[]{0x5CD45C54,0xFC554});
	}

	private void L() {
		w.dreheZugsequenz("B R' B' L B R B' L'");
		this.solveSequenz += "B R' B' L B R B' L' ";
	}

	private void Pi() {
		w.dreheZugsequenz(new int[]{0x55D44554, 0xF45544D});
		this.solveSequenz += Util.kodeZuegeZuNotation(new int[]{0x55D44554, 0xF45544D});
	}

	private void Sune() {
		w.dreheZugsequenz(new int[]{0xC5545C54, 0xF});
		this.solveSequenz += Util.kodeZuegeZuNotation(new int[]{0xC5545C54, 0xF});
	}

	private void T() {
		w.dreheZugsequenz("L B R' B' L' B R B'");
		this.solveSequenz += "L B R' B' L' B R B' ";
	}

	private void U() {
		w.dreheZugsequenz(new int[]{0x8455C044, 0xFC55C});
		this.solveSequenz += Util.kodeZuegeZuNotation(new int[]{0x8455C044, 0xFC55C});
	}

	/**
	 * Startet den Lösungsprozess
	 * @throws Exception 
	 */
	public void start() {
		// Kreuz
		IDDFS pattern = new IDDFS(this.w.getSeiten(), this.kreuzDaten, this.kreuzMaske);
		w.dreheZugsequenz(pattern.start());
		this.solveSequenz += Util.kodeZuegeZuNotation(pattern.start());
		if(this.debug == 1) w.wuerfelAusgeben();
		
		pattern = new IDDFS(this.w.getSeiten(), this.F2LOG1Daten, this.F2LOG1Maske);
		w.dreheZugsequenz(pattern.start());
		this.solveSequenz += Util.kodeZuegeZuNotation(pattern.start());
		if(this.debug == 1) w.wuerfelAusgeben();
		
		pattern = new IDDFS(this.w.getSeiten(), this.F2LOG2Daten, this.F2LOG2Maske);
		w.dreheZugsequenz(pattern.start());
		this.solveSequenz += Util.kodeZuegeZuNotation(pattern.start());
		if(this.debug == 1) w.wuerfelAusgeben();
		
		pattern = new IDDFS(this.w.getSeiten(), this.F2LGR1Daten, this.F2LGR1Maske);
		w.dreheZugsequenz(pattern.start());
		this.solveSequenz += Util.kodeZuegeZuNotation(pattern.start());
		if(this.debug == 1) w.wuerfelAusgeben();
		
		pattern = new IDDFS(this.w.getSeiten(), this.F2LGR2Daten, this.F2LGR2Maske);
		w.dreheZugsequenz(pattern.start());
		this.solveSequenz += Util.kodeZuegeZuNotation(pattern.start());
		if(this.debug == 1) w.wuerfelAusgeben();
		
		pattern = new IDDFS(this.w.getSeiten(), this.F2LRB1Daten, this.F2LRB1Maske);
		w.dreheZugsequenz(pattern.start());
		this.solveSequenz += Util.kodeZuegeZuNotation(pattern.start());
		if(this.debug == 1) w.wuerfelAusgeben();
		
		pattern = new IDDFS(this.w.getSeiten(), this.F2LRB2Daten, this.F2LRB2Maske);
		w.dreheZugsequenz(pattern.start());
		this.solveSequenz += Util.kodeZuegeZuNotation(pattern.start());
		if(this.debug == 1) w.wuerfelAusgeben();
		
		pattern = new IDDFS(this.w.getSeiten(), this.F2LBO1Daten, this.F2LBO1Maske);
		w.dreheZugsequenz(pattern.start());
		this.solveSequenz += Util.kodeZuegeZuNotation(pattern.start());
		if(this.debug == 1) w.wuerfelAusgeben();
		
		pattern = new IDDFS(this.w.getSeiten(), this.F2LBO2Daten, this.F2LBO2Maske);
		w.dreheZugsequenz(pattern.start());
		this.solveSequenz += Util.kodeZuegeZuNotation(pattern.start());
		if(this.debug == 1) w.wuerfelAusgeben();
		
		while(!mask(OLLDaten, OLLMaske)) {
			if (mask(UDaten, UMaske)) {
				U();
				break;
			} else if (mask(TMaske, TDaten)) {
				T();
				break;
			} else if (mask(SuneMaske, SuneDaten)) {
				Sune();
				break;
			} else if (mask(PiMaske, PiDaten)) {
				Pi();
				break;
			} else if (mask(LMaske, LDaten)) {
				L();
				break;
			} else if(mask(HMaske, HDaten)) {
				H();
				break;
			} else if(mask(AntisuneMaske, AntisuneDaten)) {
				Antisune();
				break;
			} else if(mask(LShapeMaske, LShapeDaten)) {
				LShape();
				w.wuerfelAusgeben();
				continue;
			} else if(mask(IShapeMaske, IShapeDaten)) {
				IShape();
				continue;
			} else if(mask(DotMaske, DotDaten)) {
				Dot();
				w.wuerfelAusgeben();
				continue;
			}
			if(this.debug == 1) w.wuerfelAusgeben();
			w.drehe(5);
			this.solveSequenz += "D ";
		}
		//this.solveSequenz = Util.kuerzen(this.solveSequenz);
		System.out.println("Gelöst mit: " + this.solveSequenz);
		System.out.println("Gelöst mit: " + Util.kuerzen(this.solveSequenz));
		
	}

	private boolean mask(int[] maske, int[] daten) {
		return w.isMaskSolved(daten, maske);
	}
}
