package loeser;


import representation.Wuerfel;
import representation.Zuege;

import java.util.ArrayList;

public class TIDDFS extends Thread {

	private static final int[] oppFace = {5, 3, 4, 1, 2, 0};

	private final Wuerfel w;
	
	private volatile boolean shouldSearch;
	private boolean gefunden;

	private final TIDDFSSpawner spawner;
	private final int[] startPos;
	private final int[] zielPos;
	private final int[] zielMaske;
	private final int[] zuege;
	private final int debug;
	private final ArrayList<Integer> zugSequenz;

	public TIDDFS(TIDDFSSpawner spawner, int[] startPos, int startZug, int[] zielPos, int[] zielMaske, int[] zuege, int debug) {
		this.spawner = spawner;
		this.zugSequenz = new ArrayList<>(16);
		this.zugSequenz.add(startZug);
		this.startPos = startPos;
		this.zielPos = zielPos;
		this.zielMaske = zielMaske;
		this.zuege = zuege;
		this.debug = debug;

		this.w = new Wuerfel(startPos);
		w.drehe(startZug);

		this.shouldSearch = true;
		this.gefunden = false;
	}

	@Override
	public void run() {
		int tiefe = 1;
		while(!gefunden && this.shouldSearch) {
			long time = System.currentTimeMillis();
			DFS(tiefe);
			tiefe++;
			if(debug >= 1) System.out.println(tiefe + " " + (System.currentTimeMillis() - time));
		}
	}

	private void DFS(int tiefe) {
		if (tiefe == -1 || this.gefunden || !shouldSearch) {
			return;
		}
		for (int move : this.genChildMoves()) {
			w.drehe(move);
			zugSequenz.add(move);
			if (w.isMaskSolved(zielPos, zielMaske) && !gefunden && shouldSearch) {
				this.gefunden = true;
				int[] loesung = new int[zugSequenz.size()];
				for (int i = 0; i < loesung.length; i++) {
					loesung[i] = zugSequenz.get(i);
				}
				this.spawner.updateSolution(loesung);
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

	public void stoppe() {
		this.shouldSearch = false;
	}

	
}
