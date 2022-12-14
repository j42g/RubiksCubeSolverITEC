package loeser.Database;

import loeser.CubeNode;
import representation.Util;
import representation.Wuerfel;
import representation.Zuege;

import java.util.Arrays;
import java.util.Stack;

public class GenerateCornerDatabase implements Runnable {

    private static final int[] oppFace = {5, 3, 4, 1, 2, 0};

    private static int[] fac; // pre-computed factorials
    private static int[] bitCount; // pre-computed bit-count
    private static Stack<int[]> path; // graph
    private static final byte[] file = new byte[88179840 / 2]; // 8! * 3^7. 2 Pro byte
    static {
        precompute(8);
    }


    public static void starte(int len){
        precompute(len);
        DFS();

    }

    private static void DFS(){
        path = new Stack<int[]>();
        path.push(new int[0]);
        int[] curr;
        int[][] permOriCurr;
        int index;
        int indexD2;
        Wuerfel w = new Wuerfel();
        while(!path.empty()){
            curr = path.pop();
            w.makeSolved();
            w.dreheZugsequenz(curr);
            permOriCurr = w.cubieOP();
            index = totalIndex(permOriCurr[0], permOriCurr[1]);
            indexD2 = index / 2;
            if(index % 2 == 0){
                if((file[indexD2] & 0xF) > curr.length || (file[indexD2] & 0xF) == 0){
                    file[indexD2] &= 0xF0;
                    file[indexD2] |= (byte)curr.length;
                }
            } else {
                if((file[indexD2] >>> 4) > curr.length || ((file[indexD2] >>> 4) & 0xF) == 0){
                    file[indexD2] &= 0x0F;
                    file[indexD2] |= (byte)(curr.length << 4);
                }
            }
            genChilds(curr);
        }
        Database a = new Database("test1", false);
        a.writeDatabase(file);
    }

    private static void genChilds(int[] node) {// node = nodeMoves
        if (node.length == 7) {
            return;
        } else if (node.length > 1){  // adv pruning
            for (int zug : Zuege.alleZuege) {
                if (node[node.length - 1] < 9) { // is first face
                    if (zug / 3 != node[node.length - 1] / 3) { // dont move the same side as last move
                        int[] a = Arrays.copyOf(node, node.length + 1);
                        a[node.length] = zug;
                        path.push(a);
                    }
                } else { // is second face
                    if (zug / 3 != node[node.length - 1] / 3
                            && zug / 3 != oppFace[node[node.length - 1] / 3]) { // dont move the same side as last move and opp
                        int[] a = Arrays.copyOf(node, node.length + 1);
                        a[node.length] = zug;
                        path.push(a);
                    }
                }
            }
        } else if (node.length == 1) { // simple move pruning
            for (int zug : Zuege.alleZuege) {
                if(zug / 3 != node[node.length - 1] / 3){ // dont move the same side as last move
                    int[] a = Arrays.copyOf(node, node.length + 1);
                    a[node.length] = zug;
                    path.push(a);
                }
            }
        } else {
            for (int zug : Zuege.alleZuege) {
                int[] a = Arrays.copyOf(node, node.length + 1);
                a[node.length] = zug;
                path.push(a);
            }
        }
    }

    public static int totalIndex(int[] p, int[] o){
        return permutationToIndex(p) * 2187 + oriantationToIndex(o);
    }

    // https://medium.com/@benjamin.botto/sequentially-indexing-permutations-a-linear-algorithm-for-computing-lexicographic-rank-a22220ffd6e3
    public static int permutationToIndex(int[] p){
        // Lehmer Code
        int[] lehmer = new int[p.length];
        int seen = 0; // acts as anx array
        lehmer[0] = p[0];
        seen |= 0b1 << (p.length - 1 - p[0]);
        for(int i = 1; i < p.length - 1; i++){
            seen |= 0b1 << (p.length - 1 - p[i]);
            lehmer[i] = p[i] - bitCount[seen >>> (p.length - p[i])];
        }
        //System.out.println(Arrays.toString(lehmer));
        // Index
        int index = 0;
        for(int i = 0; i < lehmer.length; i++){
            index += lehmer[i]*fac[i];
        }
        //System.out.println(index);
        return index;
    }

    public static int oriantationToIndex(int[] o){
        int sum = 0;
        for(int i = 0; i < o.length - 1; i++){
            sum = 3 * sum + o[i];
        }
        return sum;
    }

    public static void precompute(int len){
        fac = new int[len];
        for(int i = 0; i < len; i++){
            fac[i] = Util.factorial(len - 1- i);
        }
        bitCount = new int[1 << len];
        int n;
        for(int i = 0; i < bitCount.length  ; i++){
            // count binary digits in n
            n = i;
            while(n != 0){
                bitCount[i] += n % 2;
                n /= 2;
            }
        }
    }

    public static byte[] getBytes(){
        return file;
    }

    public static int[] getStackTop(){
        return path.peek();
    }

    @Override
    public void run() {
        GenerateCornerDatabase.starte(8);
    }
}
