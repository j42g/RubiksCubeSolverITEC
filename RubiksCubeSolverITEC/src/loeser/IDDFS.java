package loeser;

import java.util.Stack;

import representation.Util;
import representation.Wuerfel;
import java.util.Arrays;

public class IDDFS {
	
	/**
	 * Stack für IDDFS
	 */
	private Stack<int[]> pos;
	
	/**
	 * Länge der Zügespeicher (2*8 = 16 Züge).
	 */
	private static int stackArrayLaenge = 2;
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
	 * Konstruktor
	 * @param _startPos
	 * @param _zielPos
	 */
	public IDDFS(int[] _startPos, int[] _zielPos, int[] _zielMaske) {
		this.startPos = _startPos;
		this.zielPos = _zielPos;
		this.zielMaske = _zielMaske;
	}
	
	/**
	 * Starte IDDFS
	 * @return
	 */
	public int[] start() { 
		int tiefe = 0;
		
		while(!this.gefunden) {
			long time1 = System.currentTimeMillis();
			DLS(new int[] {0xF}, tiefe);
			tiefe++;
			System.out.println(tiefe+" " + (System.currentTimeMillis()-time1));
		} 
		return loesung;
	}
	
	/**
	 * Funkion, die man für IDDFS brauch (keine Ahnung wie das genau funkioniert,
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
				Util.printArr(aktuelleZuege);
				this.gefunden = true;
				this.loesung = aktuelleZuege;
				return;
			}

			this.genChildMoves(aktuelleZuege, tiefe);
		}
	}
	
	/**
	 * Generiert alle möglichen 1-Zug fortsetzungen von move fügt sie dem Stack hinzu.
	 * Falls die Tiefe gleich der Anzahl der Z
	 * @param move bisherige Züge
	 */
	private void genChildMoves(int[] move, int tiefe){
		int intIndex = 0;
		int moveIndex = 0;
		// Gehe zum letzten Zug
		while(true) {
			if(((move[moveIndex] >>> (intIndex << 2)) & (0xF)) == 0xF) {
				move[moveIndex] &= ~(0xF << (intIndex << 2));
				break;
			}
			if(intIndex == 7) {
				moveIndex++;
				intIndex = -1;
			}
			intIndex++;
		}
		// überprüfen ob das rekursionsende erreicht ist
		if(intIndex + 8 * moveIndex - 1 >= tiefe) {
			return;
		}
		// neues Ende deklarieren
		if(intIndex == 7) {
			move[moveIndex + 1] |= 0xF ;
		} else {
			move[moveIndex] |= 0xF << ((intIndex + 1) << 2);
		}
		//System.out.println("IN GEN:");
		for(int i = 0; i < 6; i++) {
			int[] a = Arrays.copyOf(move, stackArrayLaenge);
			a[moveIndex] |= i << ((intIndex) << 2);
			//Util.printArr(a);
			pos.push(a);
		}
		for(int i = 8; i < 14; i++) {
			int[] a = Arrays.copyOf(move, stackArrayLaenge);
			a[moveIndex] |= i << ((intIndex) << 2);
			//Util.printArr(a);
			pos.push(a);
		}
		
	}
	
}
