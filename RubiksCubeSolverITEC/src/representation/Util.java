package representation;

import loeser.CFOP;

public class Util {

    public static void printArr(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            if (i == arr.length - 1) {
                System.out.print(arr[i] + "\n");
            } else {
                System.out.print(arr[i] + ", ");
            }
        }
    }

    public static String lookupMove(int code) {
        switch (code) {
            case 0:
                return "U";
            case 1:
                return "B";
            case 2:
                return "L";
            case 3:
                return "F";
            case 4:
                return "R";
            case 5:
                return "D";
            case 8:
                return "U'";
            case 9:
                return "B'";
            case 10:
                return "L'";
            case 11:
                return "F'";
            case 12:
                return "R'";
            case 13:
                return "D'";
        }
        return "Invalid";
    }

    /**
     * @param zuege Zugsequenz
     * @return Zugsequenz gekürzt
     */
    public static String kuerzen(String zuege) {
        zuege = zuege.trim();
        if (zuege.length() < 3) {
            return zuege;
        }
        boolean changed = true;
        if (!zuege.contains(" ")) {
            return zuege;
        }
        while (changed) {
            changed = false;
            String[] s = zuege.split(" ");
            StringBuilder bessereZuege = new StringBuilder();
            for (int i = 0; i < s.length - 1; i++) {
                if (s[i].equals(s[i + 1]) && s[i].length() == 1) { // D + D => D2
                    bessereZuege.append(s[i].charAt(0)).append("2");
                    bessereZuege.append(" ");
                    i++;
                    changed = true;
                } else if (s[i].equals(s[i + 1] + "'")) { // D + D'
                    i++;
                    changed = true;
                } else if ((s[i] + "'").equals(s[i + 1])) { // D' + D
                    i++;
                    changed = true;
                } else if (s[i].charAt(0) == s[i + 1].charAt(0)) { // D* + D*
                    if (!(s[i].charAt(0) + "2").equals(s[i]) || !(s[i + 1].charAt(0) + "2").equals(s[i + 1])) {
                        if ((s[i].charAt(0) + "2").equals(s[i])) {
                            if ((s[i + 1].charAt(0) + "'").equals(s[i + 1])) {
                                bessereZuege.append(s[i].charAt(0));
                            } else {
                                bessereZuege.append(s[i].charAt(0)).append("'");
                            }
                        } else {
                            if ((s[i].charAt(0) + "'").equals(s[i])) {
                                bessereZuege.append(s[i].charAt(0));
                            } else {
                                bessereZuege.append(s[i].charAt(0)).append("'");
                            }
                        }
                        bessereZuege.append(" ");
                    }
                    changed = true;
                    i++;
                } else {
                    bessereZuege.append(s[i]);
                    bessereZuege.append(" ");
                }
                if (i == s.length - 2) {
                    bessereZuege.append(s[s.length - 1]);
                }
            }
            if (changed) {
                zuege = bessereZuege.toString();
            }

        }
        return zuege.trim();
    }

    public static void testLauf(int durchgaenge) {
        Wuerfel w = new Wuerfel();
        CFOP s;
        int moveSum = 0;
        int maxMoveCount = 0;
        int minMoveCount = Integer.MAX_VALUE;
        long timeSum = 0;
        long startTime;
        long endTime;
        long time;
        long maxTime = 0;
        long minTime = Long.MAX_VALUE;
        for (int i = 0; i < durchgaenge; i++) {
            w.makeSolved();
            w.verdrehe(26, false);
            s = new CFOP(w, 0);
            // Zeiten
            startTime = System.nanoTime();
            s.run();
            endTime = System.nanoTime();
            time = endTime - startTime;
            timeSum += time;
            if (time < minTime) {
                minTime = time;
            }
            if (time > maxTime) {
                maxTime = time;
            }
            // Züge
            moveSum += s.getZugAnzahl();
            if (s.getZugAnzahl() < minMoveCount) {
                minMoveCount = s.getZugAnzahl();
            }
            if (s.getZugAnzahl() > maxMoveCount) {
                maxMoveCount = s.getZugAnzahl();
            }
            System.out.print("\r" + (i + 1) + "/" + durchgaenge + " fertig.");
        }
        // Bilde Durchschnitt
        System.out.println("Durchschnitt: " + timeSum / (durchgaenge * 1000000d) + "\tMax Time:\t" + maxTime / 1000000d + "ms\tMin Time:\t" + minTime / 1000000d + "ms");
        System.out.println("Züge:\t Durchschnitt:\t" + moveSum / durchgaenge + "Max Züge:\t" + maxMoveCount + "\tMin Züge:\t" + minMoveCount);
    }

    public static int factorial(int n) {
        int f = 1;
        for (int i = 2; i < n + 1; i++) {
            f *= i;
        }
        return f;
    }

    public static void compare(int num) {
        long start;
        long total1 = 0;
        long total2 = 0;
        int[][] temp;
        Wuerfel a;
        for (int i = 0; i < num; i++) {
            a = new Wuerfel();
            a.verdrehe(26, false);
            start = System.currentTimeMillis();
            temp = a.getCornerCubies();
            total1 += System.currentTimeMillis() - start;
            start = System.currentTimeMillis();
            temp = a.getCornerCubies();
            total2 += System.currentTimeMillis() - start;
        }
        System.out.println("Bit:\t" + total1 / 1000d + "\nKoc:\t" + total2 / 1000d);
    }

}
