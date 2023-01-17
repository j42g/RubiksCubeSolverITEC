package loeser;

import java.util.ArrayList;
import java.util.Stack;

import representation.Util;
import representation.Wuerfel;
import representation.Zuege;

import java.util.Arrays;

public class IDDFS {

	private static final int[] oppFace = {5, 3, 4, 1, 2, 0};
	
	/**
	 * Stack für IDDFS
	 */
	private final Wuerfel w;
	private final ArrayList<Integer> zugSequenz;
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
		this.w = new Wuerfel(startPos);
		this.zugSequenz = new ArrayList<Integer>();
	}
	
	public IDDFS(int[] _startPos, int[] _zielPos, int[] _zielMaske, int debug) {
		this.startPos = _startPos;
		this.zielPos = _zielPos;
		this.zielMaske = _zielMaske;
		this.zuege = Zuege.alleZuege;
		this.debug = debug;
		this.w = new Wuerfel(startPos);
		this.zugSequenz = new ArrayList<Integer>();
	}

	public int[] loese() { 
		int tiefe = 1;
		while(!this.gefunden) {
			long time = System.currentTimeMillis();
			DLS(tiefe);
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
	private void DLS(int tiefe) {
		if (tiefe == -1 || this.gefunden) {
			return;
		}
		for (int move : this.genChildMoves()) {
				w.drehe(move);
				zugSequenz.add(move);
				if (w.isMaskSolved(zielPos, zielMaske) && !gefunden) {
					this.gefunden = true;
					this.loesung = new int[zugSequenz.size()];
					for (int i = 0; i < loesung.length; i++) {
						loesung[i] = zugSequenz.get(i);
					}
					return;
				}
				DLS(tiefe - 1);
				w.drehe(Zuege.invZug[move]);
				zugSequenz.remove(zugSequenz.size() - 1);
		}
	}
	
	/**
	 * Generiert alle möglichen 1-Zug fortsetzungen von moves fügt sie dem Stack hinzu.
	 * Falls die Tiefe gleich der Anzahl der Z.
	 * @param moves bisherige Züge
	 * TODO Man kann hier mehr branches wegschmeißen
	 */
	private ArrayList<Integer> genChildMoves(){
		ArrayList<Integer> childMoves;
		int mvlen = this.zugSequenz.size();
		if (zugSequenz.size() > 1) {  // adv pruning
			childMoves = new ArrayList<Integer>(15);
			for (int zug : this.zuege) {
				if (oppFace[zugSequenz.get(mvlen - 1) / 3] == zugSequenz.get(mvlen - 2) / 3) { // last the moves commute
					if (zug / 3 != zugSequenz.get(mvlen - 1) / 3
							&& zug / 3 != zugSequenz.get(mvlen - 2) / 3) { // dont move the same side as last 2 moves
						childMoves.add(zug);
					}
				} else {
					if (zug / 3 != zugSequenz.get(mvlen - 1) / 3) { // dont move the same side as last move
						childMoves.add(zug);
					}
				}
			}
		} else if (zugSequenz.size() == 1) { // simple move pruning
			childMoves = new ArrayList<Integer>(15);
			for (int zug : this.zuege) {
				if(zug / 3 != zugSequenz.get(mvlen - 1) / 3){ // dont move the same side as last move
					childMoves.add(zug);
				}
			}
		} else { // erster Durchgang, es gibt keinen letzten Zug
			childMoves = new ArrayList<Integer>(18);
			for (int zug : this.zuege) {
				childMoves.add(zug);
			}
		}
		return childMoves;
	}

}
