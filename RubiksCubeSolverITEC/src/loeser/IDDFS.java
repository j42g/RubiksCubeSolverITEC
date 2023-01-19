package loeser;

import java.util.ArrayList;
import java.util.Arrays;

import representation.Wuerfel;
import representation.Zuege;

public class IDDFS {

	private static final int[] oppFace = {5, 3, 4, 1, 2, 0};

	private final Wuerfel w;
	private final ArrayList<Integer> zugSequenz;

	private boolean gefunden = false;
	private int[] loesung;

	private final int[] startPos;
	private final int[] zielPos;
	private final int[] zielMaske;

	private final int[] zuege;

	private final int debug;

	public IDDFS(int[] _startPos, int[] _zielPos, int[] _zielMaske, int[] _zuege, int debug) {
		// kekw
		if ((new Wuerfel(_startPos)).isMaskSolved(_zielPos, _zielMaske)) {
			this.gefunden = true;
			this.loesung = new int[]{};
		}

		this.startPos = _startPos;
		this.zielPos = _zielPos;
		this.zielMaske = _zielMaske;
		this.zuege = _zuege;
		this.debug = debug;
		this.w = new Wuerfel(startPos);
		this.zugSequenz = new ArrayList<Integer>(16);


	}
	
	public IDDFS(int[] _startPos, int[] _zielPos, int[] _zielMaske, int debug) {
		// kekw
		if ((new Wuerfel(_startPos)).isMaskSolved(_zielPos, _zielMaske)) {
			this.gefunden = true;
			this.loesung = new int[]{};
		}

		this.startPos = _startPos;
		this.zielPos = _zielPos;
		this.zielMaske = _zielMaske;
		this.zuege = Zuege.alleZuege;
		this.debug = debug;
		this.w = new Wuerfel(startPos);
		this.zugSequenz = new ArrayList<Integer>(16);
	}

	public int[] loese() { 
		int tiefe = 1;
		while(!this.gefunden) {
			long time = System.currentTimeMillis();
			DFS(tiefe);
			tiefe++;
			if(debug >= 1)System.out.println(tiefe + " " + (System.currentTimeMillis() - time));
		}
		return loesung;
	}
	

	private void DFS(int tiefe) {
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
				DFS(tiefe - 1);
				w.drehe(Zuege.invZug[move]);
				zugSequenz.remove(zugSequenz.size() - 1);
		}
	}

	private ArrayList<Integer> genChildMoves(){
		ArrayList<Integer> childMoves;
		int mvlen = this.zugSequenz.size();
		if (mvlen > 1) {  // adv pruning
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
		} else if (mvlen == 1) { // simple move pruning
			childMoves = new ArrayList<Integer>(15);
			for (int zug : this.zuege) {
				if(zug / 3 != zugSequenz.get(0) / 3){ // dont move the same side as last move
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
