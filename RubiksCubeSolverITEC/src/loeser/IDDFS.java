package loeser;

import java.util.Stack;
import representation.Wuerfel;
import java.util.Arrays;

public class IDDFS {
	
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
	private int[] startPos;
	// zielPosition muss an den Stellen, an denen es egal ist, 1111 haben.
	private int[] zielPos;
	
	
	/**
	 * Konstruktor
	 * @param _startPos
	 * @param _zielPos
	 */
	public IDDFS(int[] _startPos, int[] _zielPos) {
		this.startPos = _startPos;
		this.zielPos = _zielPos;
	}
	
	/**
	 * Starte IDDFS
	 * @return
	 */
	public int[] start() { 
		int tiefe = 0;
		while(tiefe < 2) {
			DLS(new int[] {0xF}, tiefe);
			tiefe++;
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
			(new Wuerfel(startPos, aktuelleZuege)).wuerfelAusgeben();
			if((new Wuerfel(startPos, aktuelleZuege)).isMaskSolved(this.zielPos)) {
				System.out.println("POOOGGERS");
				this.gefunden = true;
				this.loesung = aktuelleZuege;
				return;
			}
			this.genChildMoves(aktuelleZuege, tiefe);
		}
	}
	
	/**
	 * Generiert alle m�glichen 1-Zug fortsetzungen von move f�gt sie dem Stack hinzu.
	 * Falls die Tiefe gleich der Anzahl der Z
	 * @param move bisherige Z�ge
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
		// �berpr�fen ob das rekursionsende erreicht ist
		if(intIndex + 8 * moveIndex - 1 >= tiefe) {
			return;
		}
		// neues Ende deklarieren
		if(intIndex == 7) {
			move[moveIndex + 1] |= 0xF ;
		} else {
			move[moveIndex] |= 0xF << ((intIndex + 1) << 2);
		}
		
		for(int i = 0; i < 6; i++) {
			int[] a = Arrays.copyOf(move, stackArrayLaenge);
			a[moveIndex] |= i << ((intIndex) << 2);
			pos.push(a);
		}
		for(int i = 8; i < 14; i++) {
			int[] a = Arrays.copyOf(move, stackArrayLaenge);
			a[moveIndex] |= i << ((intIndex) << 2);
			pos.push(a);
		}
		
	}
	
}