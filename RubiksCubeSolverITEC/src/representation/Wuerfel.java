package representation;

import java.util.Arrays;

public class Wuerfel {


    private static final int[][][] eckenFacelet = {{{0, 4}, {4, 4}, {3, 0}}, {{0, 6}, {3, 6}, {2, 0}}, {{0, 0}, {2, 6}, {1, 0}}, {{0, 2}, {1, 6}, {4, 6}},
            {{5, 2}, {3, 2}, {4, 2}}, {{5, 0}, {2, 2}, {3, 4}}, {{5, 6}, {1, 2}, {2, 4}}, {{5, 4}, {4, 0}, {1, 5}}};


    private static final int[][] eckenFarbe = {{0, 4, 3}, {0, 3, 2},
            {0, 2, 1}, {0, 1, 4}, {5, 3, 4},
            {5, 2, 3}, {5, 1, 2}, {5, 4, 1}};


    /**
     * Im Array sind die 6 Seiten gespeicht. Die i-te Seite hat die i-te Farbe:
     * <p>
     * 0: Weiß; 1: Blau; 2: Orange; 3: Grün; 4: Rot; 5: Gelb
     * <p>
     * Die Binïärdarstellungen dieser Zahlen bilden die 32-bit Zahlen, welche die
     * Seiten wiefolgt darstellen:
     * <p>
     * 0 1 2 7 M 3 6 5 4
     * <p>
     * Die 1 "zeigt" dabei immer auf die nächste Fläche, basierend auf der
     * Reihenfolge der Farben. Gelb zeigt auf Grün.
     * <p>
     * Die Züge funktionieren so wie in der Klasse Zuege definiert.
     * Zugsequenzen sind int-Arrays mit je einem Zug pro int.
     */
    private final int[] seiten;

    private static final int[][][] aussenIndex = {
            // U (Blau, Rot, Grün, Orange)
            {{1, 6}, {4, 4}, {3, 6}, {2, 6}},
            // B (Weiß, Orange, Gelb, Rot)
            {{0, 0}, {2, 4}, {5, 4}, {4, 6}},
            // L (Weiß, Grün, Gelb, Blau)
            {{0, 6}, {3, 4}, {5, 6}, {1, 0}},
            // F (Weiß, Rot, Gelb, Orange)
            {{0, 4}, {4, 2}, {5, 0}, {2, 0}},
            // R (Weiß, Blau, Gelb, Grün)
            {{0, 2}, {1, 4}, {5, 2}, {3, 0}},
            // D (Blau, Orange, Grün, Rot)
            {{1, 2}, {2, 2}, {3, 2}, {4, 0}}};

    public Wuerfel() {
        this.seiten = new int[]{0x00000000, 0x11111111, 0x22222222, 0x33333333, 0x44444444, 0x55555555};
    }

    public Wuerfel(int[] seiten) {
        this.seiten = Arrays.copyOf(seiten, 6);
    }

    public Wuerfel(int[] pos, int move) {
        this.seiten = Arrays.copyOf(pos, 6);
        this.drehe(move);
    }

    public Wuerfel(int[] pos, int[] moves) {
        this.seiten = Arrays.copyOf(pos, 6);
        this.dreheZugsequenz(moves);
    }

	public void makeSolved() {
		this.seiten[0] = 0x00000000;
		this.seiten[1] = 0x11111111;
		this.seiten[2] = 0x22222222;
		this.seiten[3] = 0x33333333;
		this.seiten[4] = 0x44444444;
		this.seiten[5] = 0x55555555;
	}

	public boolean isSolved() {
		return seiten[0] == 0x00000000 && seiten[1] == 0x11111111 && seiten[2] == 0x22222222 && seiten[3] == 0x33333333
				&& seiten[4] == 0x44444444 && seiten[5] == 0x55555555;
	}

	public boolean isMaskSolved(int[] daten, int[] maske) {
		if (daten[5] != (this.seiten[5] & maske[5])) {
			return false;
		} else if (daten[4] != (this.seiten[4] & maske[4])) {
			return false;
		} else if (daten[3] != (this.seiten[3] & maske[3])) {
			return false;
		} else if (daten[2] != (this.seiten[2] & maske[2])) {
			return false;
		} else if (daten[1] != (this.seiten[1] & maske[1])) {
			return false;
		} else return daten[0] == (this.seiten[0] & maske[0]);
	}

	public void drehe(int zug) {
		if (zug % 3 == 2) { // prime
			this.dreheGUhr(zug / 3);
		} else {
			this.dreheUhr(zug / 3); // hier muss es entweder normal oder doppelt sein
			if (zug % 3 == 1) { // falls doppelt
				this.dreheUhr(zug / 3);
			}
		}
	}

	private void dreheUhr(int face) {
		seiten[face] = Integer.rotateLeft(seiten[face], 8);
		long aussen = 0;
		for (int i = 0; i < 4; i++) {
			aussen |= ((long) this.extractStrip(aussenIndex[face][i])) << (i << 4);
		}
		aussen = Long.rotateLeft(aussen, 16);
		for (int i = 0; i < 4; i++) {
			overwriteStrip((int) (aussen >>> (i << 4)) & 0xFFF, aussenIndex[face][i]);
		}
	}

	private void dreheGUhr(int face) {
		seiten[face] = Integer.rotateRight(seiten[face], 8);
		long aussen = 0;
		for (int i = 0; i < 4; i++) {
			aussen |= ((long) this.extractStrip(aussenIndex[face][i])) << (i << 4);
		}
		aussen = Long.rotateRight(aussen, 16);
		for (int i = 0; i < 4; i++) {
			overwriteStrip((int) (aussen >>> (i << 4)) & 0xFFF, aussenIndex[face][i]);
		}
	}

	public void dreheZugsequenz(int[] zuege) {
		for (int i = 0; i < zuege.length; i++) {
			this.drehe(zuege[i]);
		}
	}

	public void dreheZugsequenz(String zuege) {
		String[] z = zuege.split(" ");
		for (int i = 0; i < z.length; i++) {
			this.drehe(Zuege.lookupZug(z[i]));
		}
	}

	public void verdrehe(int laenge, boolean ausgeben) {
		int currZug;
		String s = "";
		currZug = Zuege.alleZuege[(int) (Zuege.alleZuege.length * Math.random())];
		s += Zuege.lookupZug(currZug);
		do {
			s += " ";
			currZug = Zuege.alleZuege[(int) (Zuege.alleZuege.length * Math.random())];
			s += Zuege.lookupZug(currZug);
			s = Util.kuerzen(s);
		} while (s.split(" ").length < laenge);
		this.dreheZugsequenz(s);
		if (ausgeben) {
			System.out.println("Verdreht mit: " + s);
		}
	}

	public Wuerfel WuerfelFromCubies(int[] eckenPerm, int[] eckenOri) { // NUR ECKEN
		Wuerfel w = new Wuerfel();
		int j;
		int ori;
		for (int e = 0; e < 8; e++) { // Ecken
			j = eckenPerm[e];
			ori = eckenOri[e];
			for (int k = 0; k < 3; k++) {
				w.veraendereEinzeln(eckenFacelet[e][(k + ori) % 3][0], eckenFacelet[e][(k + ori) % 3][1], eckenFarbe[j][k]);
			}
		}
		return w;
	}

	public void veraendereEinzeln(int seitenIndex, int feldIndex, int farbe) {
		// feldIndex * 4 in schnell
		feldIndex = feldIndex << 2;
		// Bit magic
		for (int i = 0; i < 4; i++) {
			if (((farbe >>> i) & 1) == 1) {
				this.seiten[seitenIndex] |= (0b1 << (feldIndex + i));
			} else {
				this.seiten[seitenIndex] &= ~(0b1 << (feldIndex + i));
			}
		}
	}

	// --------------------- Bit Manipulation ------------------
    private int extractStrip(int[] pos) {
        return (Integer.rotateRight(this.seiten[pos[0]], (pos[1] << 2))) & (0xFFF);
    }

    private void overwriteStrip(int a, int[] pos) {
        a = Integer.rotateLeft(a, pos[1] << 2);
        seiten[pos[0]] &= ~(Integer.rotateLeft(0xFFF, (pos[1] << 2)));
        seiten[pos[0]] |= a;
    }

    private int extractColor(int face, int index) {
        return (this.seiten[face] >>> (index << 2)) & (0xF);
    }

	// ------------------------ Getter ---------------
    public int[][] getCornerCubies() {
        int[][] result = new int[2][8];
        long cache = 0;
        for (int i = 0; i < 4; i++) { //all cornerfaces on white layer
            cache |= ((long) (extractColor(0, ((i * 2) + 4) % 8))) << (8 * i);
        }
        for (int i = 4; i < 8; i++) { //all cornerfaces on yellow layer
            cache |= ((long) (extractColor(5, (((-i * 2) + 2) + 8) % 8))) << (8 * i);
        }
        cache |= ((long) (extractColor(4, 4))) << 4;
        cache |= ((long) (extractColor(3, 6))) << 12;
        cache |= ((long) (extractColor(2, 6))) << 20;
        cache |= ((long) (extractColor(1, 6))) << 28;
        cache |= ((long) (extractColor(3, 2))) << 36;
        cache |= ((long) (extractColor(2, 2))) << 44;
        cache |= ((long) (extractColor(1, 2))) << 52;
        cache |= ((long) (extractColor(4, 0))) << 60;
        for (int i = 0; i < 8; i++) {
            switch ((int) ((cache >>> 8 * i) & 0xFF)) {
                case 0x40 -> {
                    result[0][i] = 0;
                    result[1][i] = 0;
                }
                case 0x03 -> {
                    result[0][i] = 0;
                    result[1][i] = 1;
                }
                case 0x34 -> {
                    result[0][i] = 0;
                    result[1][i] = 2;
                }
                case 0x30 -> {
                    result[0][i] = 1;
                    result[1][i] = 0;
                }
                case 0x02 -> {
                    result[0][i] = 1;
                    result[1][i] = 1;
                }
                case 0x23 -> {
                    result[0][i] = 1;
                    result[1][i] = 2;
                }
                case 0x20 -> {
                    result[0][i] = 2;
                    result[1][i] = 0;
                }
                case 0x01 -> {
                    result[0][i] = 2;
                    result[1][i] = 1;
                }
                case 0x12 -> {
                    result[0][i] = 2;
                    result[1][i] = 2;
                }
                case 0x10 -> {
                    result[0][i] = 3;
                    result[1][i] = 0;
                }
                case 0x04 -> {
                    result[0][i] = 3;
                    result[1][i] = 1;
                }
                case 0x41 -> {
                    result[0][i] = 3;
                    result[1][i] = 2;
                }
                case 0x35 -> {
                    result[0][i] = 4;
                    result[1][i] = 0;
                }
                case 0x54 -> {
                    result[0][i] = 4;
                    result[1][i] = 1;
                }
                case 0x43 -> {
                    result[0][i] = 4;
                    result[1][i] = 2;
                }
                case 0x25 -> {
                    result[0][i] = 5;
                    result[1][i] = 0;
                }
                case 0x53 -> {
                    result[0][i] = 5;
                    result[1][i] = 1;
                }
                case 0x32 -> {
                    result[0][i] = 5;
                    result[1][i] = 2;
                }
                case 0x15 -> {
                    result[0][i] = 6;
                    result[1][i] = 0;
                }
                case 0x52 -> {
                    result[0][i] = 6;
                    result[1][i] = 1;
                }
                case 0x21 -> {
                    result[0][i] = 6;
                    result[1][i] = 2;
                }
                case 0x45 -> {
                    result[0][i] = 7;
                    result[1][i] = 0;
                }
                case 0x51 -> {
                    result[0][i] = 7;
                    result[1][i] = 1;
                }
                case 0x14 -> {
                    result[0][i] = 7;
                    result[1][i] = 2;
                }
            }
        }
        return result;
    }

    public int[] getSeiten() {
        return seiten;
    }

	public void ausgeben() {
		// syntax verringern
		char[][] s = new char[6][8];
		for (int i = 0; i < this.seiten.length; i++) {
			for (int j = 0; j < 8; j++) {
				s[i][j] = lookupColor(extractColor(i, j));
			}
		}
		// Abstände definieren
		String la = "        ";
		String md = "   ";
		String sm = " ";
		// Weiße Fläche
		System.out.println(la + s[0][0] + sm + s[0][1] + sm + s[0][2]);
		System.out.println(la + s[0][7] + sm + "W" + sm + s[0][3]);
		System.out.println(la + s[0][6] + sm + s[0][5] + sm + s[0][4]);
		// Orange Grün Rot Blau
		System.out.println(s[2][6] + sm + s[2][7] + sm + s[2][0] + md + s[3][6] + sm + s[3][7] + sm + s[3][0] + md
				+ s[4][4] + sm + s[4][5] + sm + s[4][6] + md + s[1][6] + sm + s[1][7] + sm + s[1][0]);
		System.out.println(s[2][5] + sm + "O" + sm + s[2][1] + md + s[3][5] + sm + "G" + sm + s[3][1] + md + s[4][3]
				+ sm + "R" + sm + s[4][7] + md + s[1][5] + sm + "B" + sm + s[1][1]);
		System.out.println(s[2][4] + sm + s[2][3] + sm + s[2][2] + md + s[3][4] + sm + s[3][3] + sm + s[3][2] + md
				+ s[4][2] + sm + s[4][1] + sm + s[4][0] + md + s[1][4] + sm + s[1][3] + sm + s[1][2]);
		// Gelb
		System.out.println(la + s[5][0] + sm + s[5][1] + sm + s[5][2]);
		System.out.println(la + s[5][7] + sm + "Y" + sm + s[5][3]);
		System.out.println(la + s[5][6] + sm + s[5][5] + sm + s[5][4]);
		System.out.println("\n\n");

	}

    @Override
    public String toString() {
        StringBuilder b = new StringBuilder();
        // syntax verringern
        char[][] s = new char[6][8];
        for (int i = 0; i < this.seiten.length; i++) {
            for (int j = 0; j < 8; j++) {
                s[i][j] = lookupColor(extractColor(i, j));
            }
        }
        // Abstände definieren
        String la = "        ";
        String md = "   ";
        String sm = " ";
        // Weiße Fläche
        b.append(la).append(s[0][0]).append(sm).append(s[0][1]).append(sm).append(s[0][2]).append("\n");
        b.append(la).append(s[0][7]).append(sm).append("W").append(sm).append(s[0][3]).append("\n");
        b.append(la).append(s[0][6]).append(sm).append(s[0][5]).append(sm).append(s[0][4]).append("\n");
        // Orange Grün Rot Blau
        b.append(s[2][6]).append(sm).append(s[2][7]).append(sm).append(s[2][0]).append(md).append(s[3][6]).append(sm).append(s[3][7]).append(sm).append(s[3][0]).append(md).append(s[4][4]).append(sm).append(s[4][5]).append(sm).append(s[4][6]).append(md).append(s[1][6]).append(sm).append(s[1][7]).append(sm).append(s[1][0]).append("\n");
        b.append(s[2][5]).append(sm).append("O").append(sm).append(s[2][1]).append(md).append(s[3][5]).append(sm).append("G").append(sm).append(s[3][1]).append(md).append(s[4][3]).append(sm).append("R").append(sm).append(s[4][7]).append(md).append(s[1][5]).append(sm).append("B").append(sm).append(s[1][1]).append("\n");
        b.append(s[2][4]).append(sm).append(s[2][3]).append(sm).append(s[2][2]).append(md).append(s[3][4]).append(sm).append(s[3][3]).append(sm).append(s[3][2]).append(md).append(s[4][2]).append(sm).append(s[4][1]).append(sm).append(s[4][0]).append(md).append(s[1][4]).append(sm).append(s[1][3]).append(sm).append(s[1][2]).append("\n");
        // Gelb
        b.append(la).append(s[5][0]).append(sm).append(s[5][1]).append(sm).append(s[5][2]).append("\n");
        b.append(la).append(s[5][7]).append(sm).append("Y").append(sm).append(s[5][3]).append("\n");
        b.append(la).append(s[5][6]).append(sm).append(s[5][5]).append(sm).append(s[5][4]);
        return b.toString();
    }

	// --------------------------- Helper ----------------------
	private static char lookupColor(int code) {
		return switch (code) {
			case 0 -> 'W';
			case 1 -> 'B';
			case 2 -> 'O';
			case 3 -> 'G';
			case 4 -> 'R';
			case 5 -> 'Y';
			default -> 'X';
		};
	}

}