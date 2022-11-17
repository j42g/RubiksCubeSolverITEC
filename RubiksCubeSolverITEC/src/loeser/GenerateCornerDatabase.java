package loeser;

import representation.Util;

import java.util.Arrays;
import java.util.BitSet;

public class GenerateCornerDatabase {

    private static int[] fac; // pre-computed factorials
    private static int[] bitCount; // pre-computed bit-count


    public static void start(int len){
        precompute(len);
    }

    public static void totalIndex(int[] p, int[] o){
        int index = permutationToIndex(p) * 2187 + oriantationToIndex(o);
    }

    // https://medium.com/@benjamin.botto/sequentially-indexing-permutations-a-linear-algorithm-for-computing-lexicographic-rank-a22220ffd6e3
    public static int permutationToIndex(int[] p){
        precompute(p.length);

        // Lehmer Code
        int[] lehmer = new int[p.length];
        int seen = 0; // acts as anx array
        lehmer[0] = p[0];
        seen |= 0b1 << (p.length - 1 - p[0]);
        for(int i = 1; i < p.length - 1; i++){
            seen |= 0b1 << (p.length - 1 - p[i]);
            lehmer[i] = p[i] - bitCount[seen >>> (p.length - p[i])];
        }
        System.out.println(Arrays.toString(lehmer));
        // Index
        int index = 0;
        for(int i = 0; i < lehmer.length; i++){
            index += lehmer[i]*fac[i];
        }
        System.out.println(index);
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
