package loeser;

import representation.Wuerfel;

import java.util.Arrays;

public class CubeNode {

    private final Wuerfel wuerfel;
    private int[] moves;

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

    public boolean equals(Object o){
        if(o instanceof CubeNode b) {
            if(this.wuerfel == b.wuerfel){
                if(b.moves.length < this.moves.length){ // Falls b der gleiche Würfel, mit aber weniger Zügen ist
                    this.moves = b.moves;
                }
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public CubeNode applyMoveToCopyAndReturn(int zug) {
        int[] nMoves = Arrays.copyOf(moves, moves.length + 1);
        nMoves[nMoves.length - 1] = zug;
        CubeNode n = new CubeNode(new Wuerfel(wuerfel.getSeiten()), nMoves); // Funktioniert nicht mehr
        n.wuerfel.drehe(zug);
        return n;
    }
}
