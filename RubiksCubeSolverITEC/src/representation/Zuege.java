package representation;

public class Zuege {

    public static final int U1 = 0;
    public static final int U2 = 1;
    public static final int U3 = 2;

    public static final int B1 = 3;
    public static final int B2 = 4;
    public static final int B3 = 5;

    public static final int L1 = 6;
    public static final int L2 = 7;
    public static final int L3 = 8;

    public static final int F1 = 9;
    public static final int F2 = 10;
    public static final int F3 = 11;

    public static final int R1 = 12;
    public static final int R2 = 13;
    public static final int R3 = 14;

    public static final int D1 = 15;
    public static final int D2 = 16;
    public static final int D3 = 17;

    public static final int[] grundZuege = new int[]{0, 2, 3, 5, 6, 8, 9, 11, 12, 14, 15, 17};
    public static final int[] alleZuege = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17};

    // Zug am Index i, macht den zug i rückgängig
    public static final int[] invZug = new int[]{2, 1, 0, 5, 4, 3, 8, 7, 6, 11, 10, 9, 14, 13, 12, 17, 16, 15};

    public static String lookupZug(int code){
        switch (code){
            case 0: return "U";
            case 1: return "U2";
            case 2: return "U'";
            case 3: return "B";
            case 4: return "B2";
            case 5: return "B'";
            case 6: return "L";
            case 7: return "L2";
            case 8: return "L'";
            case 9: return "F";
            case 10: return "F2";
            case 11: return "F'";
            case 12: return "R";
            case 13: return "R2";
            case 14: return "R'";
            case 15: return "D";
            case 16: return "D2";
            case 17: return "D'";
            default: return "Unknown";
        }
    }

    public static int lookupZug(String zug){
        switch (zug){
            case "U": return 0;
            case "U2": return 1;
            case "U'": return 2;
            case "B": return 3;
            case "B2": return 4;
            case "B'": return 5;
            case "L": return 6;
            case "L2": return 7;
            case "L'": return 8;
            case "F": return 9;
            case "F2": return 10;
            case "F'": return 11;
            case "R": return 12;
            case "R2": return 13;
            case "R'": return 14;
            case "D": return 15;
            case "D2": return 16;
            case "D'": return 17;
            default: return -1;
        }
    }

    public static String lookupZugseq(int[] zuege){
        StringBuilder a = new StringBuilder();
        for(int zug : zuege){
            a.append(Zuege.lookupZug(zug));
            a.append(" ");
        }
        return a.toString();
    }

    public static int[] lookupZugseq(String seq){
        String[] z = seq.split(" ");
        int[] result = new int[z.length];
        for(int i = 0; i < z.length; i++){
            result[i] = Zuege.lookupZug(z[i]);
        }
        return result;
    }



}
