package loeser;

import java.util.Stack;

import representation.Wuerfel;
import representation.Zuege;

import java.util.Arrays;

public class IDDFS {

	private static final int[] oppFace = {5, 3, 4, 1, 2, 0};
	
	/**
	 * Stack für IDDFS
	 */
	private Stack<int[]> pos;
	private boolean gefunden = false;
	/**
	 * Wuerfel den man haben wollen (0xF heißt beliebig).
	 */
	private int[] loesung;
	/**
	 * Wuerfel mit dem man anfängt.
	 */
	private final int[] startPos;
	private final int[] zielPos;
	private final int[] zielMaske;
	
	/**
	 * Zuge in Zugkode, welche gemacht werden sollen
	 */
	private final int[] zuege;
	/**
	 * Debug
	 */
	private final int debug;

	public IDDFS(int[] _startPos, int[] _zielPos, int[] _zielMaske, int[] _zuege, int debug) {
		this.startPos = _startPos;
		this.zielPos = _zielPos;
		this.zielMaske = _zielMaske;
		this.zuege = _zuege;
		this.debug = debug;
	}
	
	public IDDFS(int[] _startPos, int[] _zielPos, int[] _zielMaske, int debug) {
		this.startPos = _startPos;
		this.zielPos = _zielPos;
		this.zielMaske = _zielMaske;
		this.zuege = Zuege.alleZuege;
		this.debug = debug;
	}

	public int[] loese() { 
		int tiefe = 1;
		while(!this.gefunden) {
			long time = System.currentTimeMillis();
			DLS(new int[]{}, tiefe);
			tiefe++;
			if(debug >= 1)System.out.println(tiefe + " " + (System.currentTimeMillis() - time));
		}
		return loesung;
	}
	
	/**
	 * Funkion, die man für IDDFS brauch (keine Ahnung wie das genau funkioniert,
	 * obwohl eigentlich schon, aber ist cracked mit Wuerfeln)
	 * @param startZuege
	 * @param tiefe
	 */
	private void DLS(int[] startZuege, int tiefe) {
		this.pos = new Stack<int[]>();
		
		this.pos.push(startZuege);
		
		while(!this.pos.empty()) {
			int[] aktuelleZuege = this.pos.pop();
			if((new Wuerfel(startPos, aktuelleZuege)).isMaskSolved(this.zielPos, this.zielMaske)) {
				this.gefunden = true;
				this.loesung = aktuelleZuege;
				return;
			}

			this.genChildMoves(aktuelleZuege, tiefe);
		}
	}
	
	/**
	 * Generiert alle möglichen 1-Zug fortsetzungen von moves fügt sie dem Stack hinzu.
	 * Falls die Tiefe gleich der Anzahl der Z.
	 * @param moves bisherige Züge
	 * TODO Man kann hier mehr branches wegschmeißen
	 */
	private void genChildMoves(int[] moves, int tiefe){
		if(tiefe < moves.length){
			return;
		}
		if (moves.length > 1){  // adv pruning
			for (int zug : this.zuege) {
				if (oppFace[moves[moves.length - 1] / 3] == moves[moves.length - 2] / 3) { // last the moves commute
					if (zug / 3 != moves[moves.length - 1] / 3
							&& zug / 3 != moves[moves.length - 2] / 3) { // dont move the same side as last 2 moves
						int[] a = Arrays.copyOf(moves, moves.length + 1);
						a[moves.length] = zug;
						pos.push(a);
					}
				} else {
					if (zug / 3 != moves[moves.length - 1] / 3) { // dont move the same side as last move
						int[] a = Arrays.copyOf(moves, moves.length + 1);
						a[moves.length] = zug;
						pos.push(a);
					}
				}
			}
		} else if (moves.length == 1) { // simple move pruning
			for (int zug : this.zuege) {
				if(zug / 3 != moves[moves.length - 1] / 3){ // dont move the same side as last move
					int[] a = Arrays.copyOf(moves, moves.length + 1);
					a[moves.length] = zug;
					pos.push(a);
				}
			}
		} else { // erster Durchgang, es gibt keinen letzten Zug
			for (int zug : this.zuege) {
				int[] a = Arrays.copyOf(moves, moves.length + 1);
				a[moves.length] = zug;
				pos.push(a);
			}
		}
	}

}
