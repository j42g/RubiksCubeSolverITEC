package loeser;

import java.util.Stack;

import representation.Wuerfel;
import representation.Zuege;

import java.util.Arrays;

public class IDDFS {
	
	/**
	 * Stack f�r IDDFS
	 */
	private Stack<int[]> pos;
	private boolean gefunden = false;
	/**
	 * Wuerfel den man haben wollen (0xF hei�t beliebig).
	 */
	private int[] loesung;
	/**
	 * Wuerfel mit dem man anf�ngt.
	 */
	private final int[] startPos;
	private final int[] zielPos;
	private final int[] zielMaske;
	
	/**
	 * Zuge in Zugkode, welche gemacht werden sollen
	 */
	private final int[] zuege;
	
	
	/**
	 * Konstruktor
	 * @param _startPos
	 * @param _zielPos
	 */
	public IDDFS(int[] _startPos, int[] _zielPos, int[] _zielMaske, int[] _zuege) {
		this.startPos = _startPos;
		this.zielPos = _zielPos;
		this.zielMaske = _zielMaske;
		this.zuege = _zuege;
	}
	
	public IDDFS(int[] _startPos, int[] _zielPos, int[] _zielMaske) {
		this.startPos = _startPos;
		this.zielPos = _zielPos;
		this.zielMaske = _zielMaske;
		this.zuege = Zuege.grundZuege;
	}
	
	/**
	 * Starte IDDFS
	 * @return
	 */
	public int[] loese() { 
		int tiefe = 1;
		while(!this.gefunden) {
			long time = System.currentTimeMillis();
			DLS(new int[]{}, tiefe);
			tiefe++;
			System.out.println(tiefe + " " + (System.currentTimeMillis() - time));
		}
		return loesung;
	}
	
	/**
	 * Funkion, die man f�r IDDFS brauch (keine Ahnung wie das genau funkioniert,
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
	 * Generiert alle m�glichen 1-Zug fortsetzungen von moves f�gt sie dem Stack hinzu.
	 * Falls die Tiefe gleich der Anzahl der Z.
	 * @param moves bisherige Z�ge
	 */
	private void genChildMoves(int[] moves, int tiefe){
		if(tiefe < moves.length){
			return;
		}
		if(moves.length == 0){ // erster Durchgang, es gibt keinen letzten Zug
			for (int zug : this.zuege) {
				int[] a = Arrays.copyOf(moves, moves.length + 1);
				a[moves.length] = zug;
				pos.push(a);
			}
		} else {
			int invLastMove = Zuege.invZug[moves[moves.length - 1]]; // umkehrzug vom letzten Zug
			for (int zug : this.zuege) {
				if (zug == invLastMove) { // w�rde den letzten Zug r�ckg�ngig machen
					continue;
				}
				int[] a = Arrays.copyOf(moves, moves.length + 1);
				a[moves.length] = zug;
				pos.push(a);
			}
		}
	}

}
