package loeser;

import java.util.ArrayList;
import java.util.Stack;

import representation.Starter;
import representation.Wuerfel;

import java.util.Arrays;

public class IDDFSSeqs {

    /**
     * Ist die Klasse IDDFS, nur für Zugsequenzen, anstatt einzelne Züge
     */

    private Stack<int[]> pos;
    private boolean gefunden = false;
    private int[] loesung;
    private final int[] startPos;
    private final int[] zielPos;
    private final int[] zielMaske;
    private final int[][] seqs;
    private final int debug;


    public IDDFSSeqs(int[] _startPos, int[] _zielPos, int[] _zielMaske, int[][] seqs, int debug) {
        this.startPos = _startPos;
        this.zielPos = _zielPos;
        this.zielMaske = _zielMaske;
        this.seqs = seqs;
        this.debug = debug;
    }

    public int[] loese() {
        int tiefe = 1;
        while (!this.gefunden) {
            long time = System.currentTimeMillis();
            DLS(tiefe);
            tiefe++;
            if (debug >= 1)System.out.println(tiefe + " " + (System.currentTimeMillis() - time));
        }
        return loesung;
    }

    private void DLS(int tiefe) {
        this.pos = new Stack<int[]>();
        this.pos.push(new int[]{});
        while (!pos.empty()) {
            int[] aktuelleZuege = pos.pop();
            if ((new Wuerfel(startPos, aktuelleZuege)).isMaskSolved(this.zielPos, this.zielMaske)) {
                this.gefunden = true;
                this.loesung = aktuelleZuege;
                return;
            }
            this.genChildMoves(aktuelleZuege, tiefe);
        }
    }

    private void genChildMoves(int[] moves, int tiefe) {
        if (tiefe < moves.length) {
            return;
        }
        for (int[] seq : this.seqs) {
            if (moves.length > 0) { // es gibt einen letzten Zug
                if (moves[moves.length - 1] / 3 == 5) { // wenn der letzte Zug D war
                    if (seq.length == 1) { // nicht nochmal D drehen
                        continue;
                    }
                }
            }
            int[] a = Arrays.copyOf(moves, moves.length + seq.length);
            System.arraycopy(seq, 0, a, moves.length, seq.length);
            this.pos.push(a);
        }
    }

}
