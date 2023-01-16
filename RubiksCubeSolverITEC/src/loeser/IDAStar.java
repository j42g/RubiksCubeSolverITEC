package loeser;

import com.sun.jdi.ArrayReference;
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
    private int[] currMoves;
    private int currMovesLen;

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
        } else if (node.getWuerfel().isSolved()) {
            this.loesung = node.getMoves();
            return -1;
        }
        int min = Integer.MAX_VALUE;
        int t;
        for (CubeNode succ : this.genChilds(node, g)) {
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

    public ArrayList<CubeNode> genChilds(CubeNode node, int costToNode) {
        ArrayList<CubeNode> children = new ArrayList<CubeNode>();
        currMoves = node.getMoves();
        currMovesLen = currMoves.length;
        // Moves
        if (currMovesLen > 1) {  // adv pruning
            for (int zug : Zuege.alleZuege) {
                if (currMoves[currMovesLen - 1] < 9) { // is first face
                    if (zug / 3 != currMoves[currMovesLen - 1] / 3) { // dont move the same side as last move
                        children.add(node.applyMoveToCopyAndReturn(zug));
                    }
                } else { // is second face
                    if (zug / 3 != currMoves[currMovesLen - 1] / 3
                            && zug / 3 != oppFace[currMoves[currMovesLen - 1] / 3]) { // dont move the same side as last move and opp
                        children.add(node.applyMoveToCopyAndReturn(zug));
                    }
                }
            }
        } else if (currMovesLen == 1) { // simple move pruning
            for (int zug : Zuege.alleZuege) {
                if (zug / 3 != currMoves[currMovesLen - 1] / 3) { // dont move the same side as last move
                    children.add(node.applyMoveToCopyAndReturn(zug));
                }
            }
        } else {
            for (int zug : Zuege.alleZuege) {
                children.add(node.applyMoveToCopyAndReturn(zug));
            }
        }
        return children; // TODO MÜSSEN SORT WERDEN SONST IST A* NUTZLOS
    }

    // https://stackoverflow.com/questions/60130124/heuristic-function-for-rubiks-cube-in-a-algorithm-artificial-intelligence
    // https://www.cs.princeton.edu/courses/archive/fall06/cos402/papers/korfrubik.pdf
    private int h(CubeNode node) {
        // make into index
        int index = 0;

        return 0; // TODO Implementieren
    }
}
