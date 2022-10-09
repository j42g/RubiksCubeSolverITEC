package loeser;

import java.util.Stack;
import representation.Wuerfel;
import java.util.Arrays;

public class IDDFS {
	
	private Stack<int[]> pos = new Stack<int[]>();
	private static int stackArrayLaenge = 2;
	private boolean found = false;
	private int[] loesung;
	private int[] startPos;
	// zielPosition muss an den Stellen, an denen es egal ist, 1111 haben.
	private int[] zielPos;
	
	public IDDFS(int[] _startPos, int[] _zielPos) {
		this.startPos = _startPos;
		this.zielPos = _zielPos;
	}
	
	public int[] start() { 
		int tiefe = 0;
		while(!found) {
			DLS(tiefe);
			tiefe++;
		}
		return loesung;
	}
	
	private boolean DLS(int tiefe) {
		if(tiefe == 0 && (new Wuerfel(startPos)).isMaskSolved(this.zielPos)) {
			this.loesung = new int[] {0xF};
			return true;
		}
		int[] curr;
		if(tiefe > 0) {
			genChildMoves(this.startPos);
			for()
			if((new Wuerfel(this.startPos, curr)).isMaskSolved(this.zielPos)) {
				this.loesung = curr;
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Generiert alle möglichen 1-Zug fortsetzungen von move fügt sie dem Stack hinzu
	 * @param move bisherige Züge
	 */
	private void genChildMoves(int[] move){
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
