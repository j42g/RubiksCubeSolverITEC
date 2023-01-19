package loeser.Database;

import loeser.CubeNode;
import representation.Util;
import representation.Wuerfel;
import representation.Zuege;

import java.util.ArrayList;
import java.util.Stack;

public class GenerateCornerDatabase implements Runnable {

    private static final int[] oppFace = {5, 3, 4, 1, 2, 0};

    private static int[] fac; // pre-computed factorials
    private static int[] bitCount; // pre-computed bit-count
    private static Stack<CubeNode> path; // graph
    private static final byte[] file = new byte[88179840 / 2]; // 8! * 3^7. 2 Pro byte
    private static int currMovesLen;
    private static int[] currMoves;


    // DFS Better
    private static final Wuerfel w = new Wuerfel();
    private static final ArrayList<Integer> zugSequenz = new ArrayList<>(11);
    private static int[][] permOriCurr;
    private static int index;
    private static int indexD2;


    public static void starte() {
        DFSBetterStart();
    }

    private static void forwardSearch() {
        precompute(8);
        DFSforward();
    }

    private static void backwardSearch() {
        precompute(8);
        DFSbackward();
    }

    private static void DFSforward() {
        path = new Stack<CubeNode>();
        path.push(new CubeNode(new Wuerfel(), new int[]{}));
        CubeNode curr;
        int[][] permOriCurr;
        int index;
        int indexD2;
        int x = 0;
        while (!path.empty()) {
            curr = path.pop();
            permOriCurr = curr.getWuerfel().getCornerCubies();
            index = totalIndex(permOriCurr[0], permOriCurr[1]);
            indexD2 = index / 2;
            if (index % 2 == 0) {
                if ((file[indexD2] & 0xF) > curr.getMoves().length || (file[indexD2] & 0xF) == 0) {
                    file[indexD2] &= 0xF0;
                    file[indexD2] |= curr.getMoves().length;
                }
            } else {
                if ((file[indexD2] >>> 4) > curr.getMoves().length || (file[indexD2] >>> 4) == 0) {
                    file[indexD2] &= 0x0F;
                    file[indexD2] |= curr.getMoves().length << 4;
                }
            }
            genChilds(curr);
        }
        Database a = new Database("test1", false);
        file[0] &= 0xF0; // ersten Wert schreibe, weil er mit der Bedingung oben file(i) == 0 abgefangen wird
        a.writeDatabase(file);
    }

    private static void DFSBetterStart() {
        precompute(8);
        DFSBetter(6);
        Database a = new Database("test1", false);
        file[0] &= 0xF0; // ersten Wert schreibe, weil er mit der Bedingung oben file(i) == 0 abgefangen wird
        a.writeDatabase(file);
    }

    private static void DFSBetter(int tiefe) {
        if (tiefe == -1) {
            return;
        }
        for (int move : genChildMoves()) {
            w.drehe(move);
            zugSequenz.add(move);
            permOriCurr = w.getCornerCubies();
            index = totalIndex(permOriCurr[0], permOriCurr[1]);
            indexD2 = index / 2;
            if (index % 2 == 0) {
                if ((file[indexD2] & 0xF) > zugSequenz.size() || (file[indexD2] & 0xF) == 0) {
                    file[indexD2] &= 0xF0;
                    file[indexD2] |= zugSequenz.size();
                }
            } else {
                if ((file[indexD2] >>> 4) > zugSequenz.size() || (file[indexD2] >>> 4) == 0) {
                    file[indexD2] &= 0x0F;
                    file[indexD2] |= zugSequenz.size() << 4;
                }
            }
            GenerateCornerDatabase.DFSBetter(tiefe - 1);
            zugSequenz.remove(zugSequenz.size() - 1);
            w.drehe(Zuege.invZug[move]);
        }
    }

    private static void DFSbackward() {
        Database db = new Database("test1", true);
        for (int index = 1; index < 88179840; index++) {
            if (db.readfromDatabase(index) == 0) { // found empty entry
                System.out.println(index); // TODO index -> perm + ori
            }
        }
    }

    private static void genChilds(CubeNode node) {// node = nodeMoves
        currMoves = node.getMoves();
        currMovesLen = currMoves.length;
        if (currMovesLen == 9) {
            return;
        } else if (currMovesLen > 1) {  // adv pruning
            for (int zug : Zuege.alleZuege) {
                if (currMoves[currMovesLen - 1] < 9) { // is first face
                    if (zug / 3 != currMoves[currMovesLen - 1] / 3) { // dont move the same side as last move
                        path.push(node.applyMoveToCopyAndReturn(zug));
                    }
                } else { // is second face
                    if (zug / 3 != currMoves[currMovesLen - 1] / 3
                            && zug / 3 != oppFace[currMoves[currMovesLen - 1] / 3]) { // dont move the same side as last move and opp
                        path.push(node.applyMoveToCopyAndReturn(zug));
                    }
                }
            }
        } else if (currMovesLen == 1) { // simple move pruning
            for (int zug : Zuege.alleZuege) {
                if (zug / 3 != currMoves[currMovesLen - 1] / 3) { // dont move the same side as last move
                    path.push(node.applyMoveToCopyAndReturn(zug));
                }
            }
        } else {
            for (int zug : Zuege.alleZuege) {
                path.push(node.applyMoveToCopyAndReturn(zug));
            }
        }
    }

    private static ArrayList<Integer> genChildMoves() {
        ArrayList<Integer> childMoves;
        int mvlen = zugSequenz.size();
        if (mvlen > 1) {  // adv pruning
            childMoves = new ArrayList<Integer>(15);
            for (int zug : Zuege.alleZuege) {
                if (oppFace[zugSequenz.get(mvlen - 1) / 3] == zugSequenz.get(mvlen - 2) / 3) { // last the moves commute
                    if (zug / 3 != zugSequenz.get(mvlen - 1) / 3
                            && zug / 3 != zugSequenz.get(mvlen - 2) / 3) { // dont move the same side as last 2 moves
                        childMoves.add(zug);
                    }
                } else {
                    if (zug / 3 != zugSequenz.get(mvlen - 1) / 3) { // dont move the same side as last move
                        childMoves.add(zug);
                    }
                }
            }
        } else if (mvlen == 1) { // simple move pruning
            childMoves = new ArrayList<Integer>(15);
            for (int zug : Zuege.alleZuege) {
                if (zug / 3 != zugSequenz.get(0) / 3) { // dont move the same side as last move
                    childMoves.add(zug);
                }
            }
        } else { // erster Durchgang, es gibt keinen letzten Zug
            childMoves = new ArrayList<Integer>(18);
            for (int zug : Zuege.alleZuege) {
                childMoves.add(zug);
            }
        }
        return childMoves;
    }

    public static int totalIndex(int[] p, int[] o) {
        return permutationToIndex(p) * 2187 + oriantationToIndex(o);
    }

    // https://medium.com/@benjamin.botto/sequentially-indexing-permutations-a-linear-algorithm-for-computing-lexicographic-rank-a22220ffd6e3
    public static int permutationToIndex(int[] p) {
        // Lehmer Code
        int[] lehmer = new int[p.length];
        int seen = 0; // acts as anx array
        lehmer[0] = p[0];
        seen |= 0b1 << (p.length - 1 - p[0]);
        for (int i = 1; i < p.length - 1; i++) {
            seen |= 0b1 << (p.length - 1 - p[i]);
            lehmer[i] = p[i] - bitCount[seen >>> (p.length - p[i])];
        }
        //System.out.println(Arrays.toString(lehmer));
        // Index
        int index = 0;
        for (int i = 0; i < lehmer.length; i++) {
            index += lehmer[i] * fac[i];
        }
        //System.out.println(index);
        return index;
    }

    public static int oriantationToIndex(int[] o) {
        int sum = 0;
        for (int i = 0; i < o.length - 1; i++) {
            sum = 3 * sum + o[i];
        }
        return sum;
    }

    public static void precompute(int len) {
        fac = new int[len];
        for (int i = 0; i < len; i++) {
            fac[i] = Util.factorial(len - 1 - i);
        }
        bitCount = new int[1 << len];
        int n;
        for (int i = 0; i < bitCount.length; i++) {
            // count binary digits in n
            n = i;
            while (n != 0) {
                bitCount[i] += n % 2;
                n /= 2;
            }
        }
    }

    public static byte[] getBytes() {
        return file;
    }

    public static CubeNode getStackTop() {
        return path.peek();
    }

    public static int[] getZuege() {
        int[] zuege = new int[zugSequenz.size()];
        try {
            for (int i = 0; i < zuege.length; i++) {
                zuege[i] = zugSequenz.get(i);
            }
            return zuege;
        } catch (Exception ex) {
            return zuege;
        }

    }

    @Override
    public void run() {
        long start = System.nanoTime();
        GenerateCornerDatabase.starte();
        System.out.println((System.nanoTime() - start) / 1000000000d + "s");
    }
}
