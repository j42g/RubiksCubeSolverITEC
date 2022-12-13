package loeser.Database;

import loeser.CubeNode;
import representation.Util;
import representation.Wuerfel;
import representation.Zuege;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Stack;

public class GenerateCornerDatabase {

    private static int[] fac; // pre-computed factorials
    private static int[] bitCount; // pre-computed bit-count
    private static Stack<CubeNode> path;


    public static void start(int len){
        precompute(len);
        DFS();

    }

    private static void DFS(){
        byte[] file = new byte[88179840]; // 8! * 3^7
        path = new Stack<CubeNode>();
        path.push(new CubeNode(new Wuerfel(), new int[]{}));
        CubeNode curr;
        int[][] permOriCurr;
        int index;
        while(!path.empty()){
            curr = path.pop();
            permOriCurr = curr.getWuerfel().cubieOP();
            index = totalIndex(permOriCurr[0], permOriCurr[1]);
            if(index % 2 == 0){
                file[index / 2] = (byte)curr.getMoves().length;
            } else {
                file[index / 2] |= (byte)(curr.getMoves().length << 4);
            }
            genChilds(curr);
        }
        Database a = new Database("cornerData", false);
        a.writeDatabase(file);
    }

    private static void genChilds(CubeNode node) {
        int[] nm = node.getMoves(); // nm = nodeMoves
        if (nm.length == 11) {
            return;
        } else if (nm.length == 0) { // first move
            for (int zug : Zuege.alleZuege) {
                path.add(node.applyMoveToCopyAndReturn(zug));
            }
        } else { // simple pruning
            for (int zug : Zuege.alleZuege) {
                if (zug / 3 != nm[nm.length - 1] / 3) { // dont move the same side as last move
                    path.add(node.applyMoveToCopyAndReturn(zug));
                }
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



}
