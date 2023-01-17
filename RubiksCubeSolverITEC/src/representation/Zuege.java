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
        return switch (code) {
            case 0 -> "U";
            case 1 -> "U2";
            case 2 -> "U'";
            case 3 -> "B";
            case 4 -> "B2";
            case 5 -> "B'";
            case 6 -> "L";
            case 7 -> "L2";
            case 8 -> "L'";
            case 9 -> "F";
            case 10 -> "F2";
            case 11 -> "F'";
            case 12 -> "R";
            case 13 -> "R2";
            case 14 -> "R'";
            case 15 -> "D";
            case 16 -> "D2";
            case 17 -> "D'";
            default -> "Unknown";
        };
    }

    public static int lookupZug(String zug){
        return switch (zug) {
            case "U" -> 0;
            case "U2" -> 1;
            case "U'" -> 2;
            case "B" -> 3;
            case "B2" -> 4;
            case "B'" -> 5;
            case "L" -> 6;
            case "L2" -> 7;
            case "L'" -> 8;
            case "F" -> 9;
            case "F2" -> 10;
            case "F'" -> 11;
            case "R" -> 12;
            case "R2" -> 13;
            case "R'" -> 14;
            case "D" -> 15;
            case "D2" -> 16;
            case "D'" -> 17;
            default -> -1;
        };
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

    public static int[] invZuege(int[] zuege){
        int[] invZuege = new int[zuege.length];
        int currMove;
        for(int i = 0; i < invZuege.length; i++){
            invZuege[i] = invZug[zuege[i]];

        }
        return invZuege;
    }


}
