package loeser;

import representation.Wuerfel;
import representation.Zuege;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

public class IDAStar extends Thread { // https://en.wikipedia.org/wiki/Iterative_deepening_A*

    private static final int[] oppFace = {5, 3, 4, 1, 2, 0};

    private final int[] startPos;
    private final int[] zielPos;
    private final int[] zielMaske;
    private final int[] zuege;
    private final Stack<CubeNode> path;
    private int[] loesung;

    public IDAStar(int[] _startPos, int[] _zielPos, int[] _zielMaske, int[] _zuege) {
        this.path = new Stack<CubeNode>();
        this.startPos = _startPos;
        this.zielPos = _zielPos;
        this.zielMaske = _zielMaske;
        this.zuege = _zuege;
    }

    public IDAStar(int[] _startPos, int[] _zielPos, int[] _zielMaske) {
        this.path = new Stack<CubeNode>();
        this.startPos = _startPos;
        this.zielPos = _zielPos;
        this.zielMaske = _zielMaske;
        this.zuege = Zuege.alleZuege;
    }

    public void run() {
        this.starten();
    }

    private int[] starten() {
        CubeNode root = new CubeNode(new Wuerfel(this.startPos, new int[]{}), new int[]{});
        int bound = h(root);
        this.path.push(root);
        int t;
        while (true) {
            t = search(0, bound);
            if (t == -1) return this.loesung;
            bound = t;
        }
    }

    private int search(int g, int bound) {
        CubeNode node = path.pop();
        int f = g + h(node);
        if (f > bound) {
            return f;
        } else if (node.getWuerfel().isMaskSolved(this.zielPos, this.zielMaske)) {
            this.loesung = node.getMoves();
            return -1;
        }
        int min = Integer.MAX_VALUE;
        int t;
        for (CubeNode succ : this.genChilds(node)) {
            if (!this.path.contains(succ)) {
                path.push(succ);
                t = search(g + 1, bound); // cost = 1, weil man immer einen Zug hinzufügt (denke ich)
                if (t == -1) return -1;
                if (t < min) min = t;
                path.pop();
            }
        }
        return min;
    }

    public ArrayList<CubeNode> genChilds(CubeNode node) {
        ArrayList<CubeNode> children = new ArrayList<CubeNode>();
        int[] nm = node.getMoves(); // nm = nodeMoves
        if (nm.length > 1){  // adv pruning
            for (int zug : this.zuege) {
                if (oppFace[nm[nm.length - 1] / 3] == nm[nm.length - 2] / 3) { // last the moves commute
                    if (zug / 3 != nm[nm.length - 1] / 3
                            && zug / 3 != nm[nm.length - 2] / 3) { // dont move the same side as last 2 moves
                        children.add(node.applyMoveToCopyAndReturn(zug));
                    }
                } else {
                    if (zug / 3 != nm[nm.length - 1] / 3) { // dont move the same side as last move
                        children.add(node.applyMoveToCopyAndReturn(zug));
                    }
                }
            }
        } else if (nm.length == 1) { // simple move pruning
            for (int zug : this.zuege) {
                if(zug / 3 != nm[nm.length - 1] / 3){ // dont move the same side as last move
                    children.add(node.applyMoveToCopyAndReturn(zug));
                }
            }
        } else {
            for (int zug : this.zuege) {
                children.add(node.applyMoveToCopyAndReturn(zug));
            }
        }
        return children;
    }

    // https://stackoverflow.com/questions/60130124/heuristic-function-for-rubiks-cube-in-a-algorithm-artificial-intelligence
    // https://www.cs.princeton.edu/courses/archive/fall06/cos402/papers/korfrubik.pdf
    private static int h(CubeNode node) {
        return 0; // TODO Implementieren
    }
}
