package loeser;

import java.util.Stack;

import representation.Util;
import representation.Wuerfel;
import java.util.Arrays;

public class TIDDFS extends Thread {
	
	/**
	 * Stack f�r IDDFS
	 */
	private Stack<int[]> pos;
	
	/**
	 * L�nge der Z�gespeicher (2*8 = 16 Z�ge).
	 */
	private static int stackArrayLaenge = 2;
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
	public TIDDFS(int[] _startPos, int[] _zielPos, int[] _zielMaske, int[] _zuege) {
		startPos = null;
		zielPos = null;
		zielMaske = null;
		zuege = null;
	}
	
	/**
	 * Starte IDDFS
	 * @return
	 */
	public int[] loese() { 
		int tiefe = 0;
		while(!this.gefunden) {
			long time = System.currentTimeMillis();
			DLS(new int[] {0xF}, tiefe);
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
	 * @return
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
	 * Generiert alle m�glichen 1-Zug fortsetzungen von move f�gt sie dem Stack hinzu.
	 * Falls die Tiefe gleich der Anzahl der Z.
	 * @param move bisherige Z�ge
	 */
	private void genChildMoves(int[] move, int tiefe){
		// ist move leer? (wird f�r lastmove gebraucht)
		boolean nleer = true;
		if(move[0] == 0xF) {
			move[0] &= ~(0xF);
			nleer = false;
		}
		int intIndex = 0;
		int moveIndex = 0;
		//letzer Zug in move
		int invLastMove = -1;
		// Gehe zum letzten Zug
		while(nleer) {
			if(((move[moveIndex] >>> (intIndex << 2)) & (0xF)) == 0xF) {
				move[moveIndex] &= ~(0xF << (intIndex << 2));
				// invLastZug bestimmen
				invLastMove = ((move[moveIndex] >>> ((intIndex - 1) << 2)) & (0xF)) ^ 0b1000;
				break;
			}
			if(intIndex == 7) {
				moveIndex++;
				intIndex = -1;
			}
			intIndex++;
		}
		// �berpr�fen ob das rekursionsende erreicht ist
		if(intIndex + (moveIndex << 3) - 1 >= tiefe) {
			return;
		}
		// neues Ende deklarieren
		if(intIndex == 7) {
			move[moveIndex + 1] |= 0xF ;
		} else {
			move[moveIndex] |= 0xF << ((intIndex + 1) << 2);
		}
		// Zuege adden
		intIndex = (intIndex) << 2; // reduziert Operationen
		for(int zug : this.zuege) {
			if(zug == invLastMove) {
				continue;
			}
			int[] a = Arrays.copyOf(move, stackArrayLaenge);
			a[moveIndex] |= zug << intIndex;
			pos.push(a);
		}
	}
	
}
