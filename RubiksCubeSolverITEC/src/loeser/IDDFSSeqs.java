package loeser;

import java.util.ArrayList;
import java.util.Stack;

import representation.Wuerfel;

import java.util.Arrays;

public class IDDFSSeqs {

    /**
     * Ist die Klasse IDDFS, nur für Zugsequenzen, anstatt einzelne Züge
     */

    private Stack<int[][]> pos;
    private boolean gefunden = false;
    private int[] loesung;
    private final int[] startPos;
    private final int[] zielPos;
    private final int[] zielMaske;
    private final int[][] seqs;


    public IDDFSSeqs(int[] _startPos, int[] _zielPos, int[] _zielMaske, int[][] seqs) {
        this.startPos = _startPos;
        this.zielPos = _zielPos;
        this.zielMaske = _zielMaske;
        this.seqs = seqs;
    }

    public int[] loese() {
        int tiefe = 1;
        while(!this.gefunden) {
            long time = System.currentTimeMillis();
            DLS(new int[][]{{}}, tiefe);
            tiefe++;
            System.out.println(tiefe + " " + (System.currentTimeMillis() - time));
        }
        return loesung;
    }

    private void DLS(int[][] startZuege, int tiefe) {
        this.pos = new Stack<int[][]>();
        this.pos.push(startZuege);

        while(!this.pos.empty()) {
            int[][] aktuelleZuege = this.pos.pop();
            int[] aktuelleZuegeSeq = IDDFSSeqs.seqofseqstoseq(aktuelleZuege);
            if((new Wuerfel(startPos, aktuelleZuegeSeq)).isMaskSolved(this.zielPos, this.zielMaske)) {
                this.gefunden = true;
                this.loesung = aktuelleZuegeSeq;
                return;
            }
            this.genChildMoves(aktuelleZuege, tiefe);
        }
    }

    private void genChildMoves(int[][] moves, int tiefe){
        if(tiefe < moves.length){
            return;
        }
        for (int[] seq : this.seqs) {
            int[][] a = Arrays.copyOf(moves, moves.length + 1);
            a[moves.length] = seq;
            this.pos.push(a);
        }
    }

    private static int[] seqofseqstoseq(int[][] seqseq){
        ArrayList<Integer> ouputSeq = new ArrayList<Integer>();
        for(int[] seq : seqseq){
            for (int j : seq) {
                ouputSeq.add(j);
            }
        }
        int[] ouputSeqArr = new int[ouputSeq.size()];
        for(int i = 0; i < ouputSeqArr.length; i++){
            ouputSeqArr[i] = ouputSeq.get(i);
        }
        return ouputSeqArr;
    }
}
