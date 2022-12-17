package loeser;

import representation.Wuerfel;

import java.util.Arrays;

public class CubeNode {

    private final Wuerfel wuerfel;
    private final int[] moves;

    public CubeNode(Wuerfel w, int[] m){
        this.wuerfel = w;
        this.moves = m;
    }

    public Wuerfel getWuerfel(){
        return this.wuerfel;
    }

    public int[] getMoves(){
        return this.moves;
    }

    public CubeNode applyMoveToCopyAndReturn(int zug) {
        int[] nMoves = Arrays.copyOf(moves, moves.length + 1);
        nMoves[nMoves.length - 1] = zug;
        CubeNode n = new CubeNode(new Wuerfel(wuerfel.getSeiten(), zug), nMoves);
        return n;
    }
}
