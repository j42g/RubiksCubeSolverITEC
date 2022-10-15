package representation;

import java.util.Arrays;

import kociembaDarstellung.Zuege;

public class Wuerfel {

	/**
	 * 
	 * Im Array sind die 6 Seiten gespeicht. Die i-te Seite hat die i-te Farbe:
	 * 
	 * 0: Weiß; 1: Blau; 2: Orange; 3: Grün; 4: Rot; 5: Gelb
	 * 
	 * Die Binïärdarstellungen dieser Zahlen bilden die 32-bit Zahlen, welche die
	 * Seiten wiefolgt darstellen:
	 * 
	 * 0 1 2 7 M 3 6 5 4
	 * 
	 * Die 1 "zeigt" dabei immer auf die nächste Fläche, basierend auf der
	 * Reihenfolge der Farben. Gelb zeigt auf Grün.
	 * 
	 * Züge sind cool. Keine Ahnung. Ab jetzt Int-Arrays, mir egal.
	 *
	 */
	private int[] seiten = new int[6];

	private static final int[][][] aussenIndex = {
			// U (Blau, Rot, Grün, Orange)
			{ { 1, 6 }, { 4, 4 }, { 3, 6 }, { 2, 6 } },
			// B (Weiß, Orange, Gelb, Rot)
			{ { 0, 0 }, { 2, 4 }, { 5, 4 }, { 4, 6 } },
			// L (Weiß, Grün, Gelb, Blau)
			{ { 0, 6 }, { 3, 4 }, { 5, 6 }, { 1, 0 } },
			// F (Weiß, Rot, Gelb, Orange)
			{ { 0, 4 }, { 4, 2 }, { 5, 0 }, { 2, 0 } },
			// R (Weiß, Blau, Gelb, Grün)
			{ { 0, 2 }, { 1, 4 }, { 5, 2 }, { 3, 0 } },
			// D (Blau, Orange, Grün, Rot)
			{ { 1, 2 }, { 2, 2 }, { 3, 2 }, { 4, 0 } } };

	/**
	 * Temporäres Übersetzungsarray, damit ich nicht assenIndex ändern muss
	 */
	private final int[] kociembaZuUns = { 0, 4, 3, 5, 2, 1 };

	/**
	 * Geneiert gelösten Würfel.
	 */
	public Wuerfel() {
		this.makeSolved();
	}

	/**
	 * Generiert Würfel mit seiten pos.
	 * 
	 * @param Würfelkonfiguration
	 */
	public Wuerfel(int[] pos) {
		this.seiten = pos;
	}

	/**
	 * Generiert Würfel mit pos und dreht moves.
	 * 
	 * @param pos   Würfelkonfiguration
	 * @param moves Zugabfolge
	 */
	public Wuerfel(int[] pos, int[] moves) {
		this.seiten = Arrays.copyOf(pos, 6);
		this.dreheZugsequenz(moves);
	}

	/**
	 * Dreht Zugsequenz.
	 * 
	 * @param zug Züge als Kode
	 */
	public void dreheZugsequenz(int[] zuege) {
		for (int zug : zuege) {
			this.drehe(zug);
		}

	}

	/**
	 * Übersetzt und ruft dreheZugSquenz auf.
	 * 
	 * @param zuegeNot Züge in Notation
	 */
	public void dreheZugsequenz(String zuegeNot) {
		// Länge bestimmen
		int len = 0;
		for (char i : zuegeNot) {
			if ("URFDLB".indexOf(i) != -1) {
				len++;
			}
		}
		// Züge in Array schreiben
		int[] zuege = new int[len];
		for (int i = 0; i < len; i++) {
			if ("URFDLB".indexOf(zuegeNot.charAt(i)) != -1) { // Anfang eines Zuges gefunden
				
			}
		}
	}

	/**
	 * Ändert an der Fläche "seitenIndex" an den "feldIndex"-ten Position die Farbe
	 * zu "farbe".
	 * 
	 * @param seitenIndex Index der Seite.
	 * @param feldIndex   Index der Seite.
	 * @param farbe       Farbe als Binärkode.
	 */
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

	/**
	 * Ersetzt den Wärfel, durch einen gelästen Wärfel.
	 */
	public void makeSolved() {
		seiten[0] = 0x00000000;
		seiten[1] = 0x11111111;
		seiten[2] = 0x22222222;
		seiten[3] = 0x33333333;
		seiten[4] = 0x44444444;
		seiten[5] = 0x55555555;
	}

	/**
	 * mach "laenge" zufällig Züge. An sich braucht man nie mehr als 26.
	 * 
	 * @param laenge   Anzahl der Züge
	 * @param ausgeben Anzeigen der Züge
	 */
	public void verdrehe(int laenge, boolean ausgeben) {
		final int[] a = { 0, 1, 2, 3, 4, 5, 8, 9, 10, 11, 12, 13 }; // alle Züge
		int zug;
		String zuege = "";
		for (int i = 0; i < laenge; i++) {
			zug = a[(int) (12 * Math.random())];
			this.drehe(zug);
			zuege += this.lookupMove(zug) + " ";
		}
		if (ausgeben) {
			System.out.println("Verdreht mit: " + zuege);
		}
	}

	/**
	 * Redundant? DAS MCAHST DU NICK
	 * 
	 * @param face
	 * @param pos  Komplement aus Seite und Stipindex
	 * @return
	 */
	public int extractStrip(int[] pos) {
		return (Integer.rotateRight(this.seiten[pos[0]], (pos[1] << 2))) & (0xFFF);
	}

	/**
	 * Übersetzt Kociemba Züge zu unseren
	 */
	public void drehe(int zug) {
		int zugTyp = zug % 3;
		if (zugTyp == 2) {
			this.dreheGUhr(this.kociembaZuUns[zug / 3]);
		} else if (zugTyp == 1) {
			this.dreheUhr(this.kociembaZuUns[zug / 3]);
			this.dreheUhr(this.kociembaZuUns[zug / 3]);
		} else {
			this.dreheUhr(this.kociembaZuUns[zug / 3]);
		}
	}

	/**
	 * Redundant? DAS MCAHST DU NICK
	 * 
	 * @param face Index der Seite
	 */
	private void dreheUhr(int face) {
		seiten[face] = Integer.rotateLeft(seiten[face], 8);
		long aussen = 0;
		for (int i = 0; i < 4; i++) {
			aussen |= ((long) this.extractStrip(aussenIndex[face][i])) << (i * 16);
		}
		aussen = Long.rotateLeft(aussen, 16);
		for (int i = 0; i < 4; i++) {
			overwriteStrip((int) (aussen >>> i * 16) & 0xFFF, aussenIndex[face][i]);
		}
	}

	private void dreheGUhr(int face) {
		seiten[face] = Integer.rotateRight(seiten[face], 8);
		long aussen = 0;
		for (int i = 0; i < 4; i++) {
			aussen |= ((long) this.extractStrip(aussenIndex[face][i])) << (i * 16);
		}
		aussen = Long.rotateRight(aussen, 16);
		for (int i = 0; i < 4; i++) {
			overwriteStrip((int) (aussen >>> i * 16) & 0xFFF, aussenIndex[face][i]);
		}
	}

	private void overwriteStrip(int a, int[] pos) {
		a = Integer.rotateLeft(a, pos[1] << 2);
		seiten[pos[0]] &= ~(Integer.rotateLeft(0xFFF, (pos[1] << 2)));
		seiten[pos[0]] |= a;
	}

	/**
	 * Überprüft ob mask = seiten, mit der Einschränkung, dass bei den Felder wo in
	 * der Maske F steht alles sein darf.
	 * 
	 * @param mask Maske
	 * @return true wenn gleich sonst false.
	 */
	public boolean isMaskSolved(int[] daten, int[] maske) {
		if (daten[0] != (this.seiten[0] & maske[0])) {
			return false;
		} else if (daten[1] != (this.seiten[1] & maske[1])) {
			return false;
		} else if (daten[2] != (this.seiten[2] & maske[2])) {
			return false;
		} else if (daten[3] != (this.seiten[3] & maske[3])) {
			return false;
		} else if (daten[4] != (this.seiten[4] & maske[4])) {
			return false;
		} else if (daten[5] != (this.seiten[5] & maske[5])) {
			return false;
		}
		return true;
	}

	/**
	 * überpräft ob der Würfel gelöst ist.
	 * 
	 * @return true wenn gelöst, sonst false.
	 */
	public boolean isSolved() {
		if (seiten[0] == 0x00000000 && seiten[1] == 0x11111111 && seiten[2] == 0x22222222 && seiten[3] == 0x33333333
				&& seiten[4] == 0x44444444 && seiten[5] == 0x55555555) {
			return true;
		}
		return false;
	}

	/**
	 * Gibt zu einer Fläche an einem Stelle die Farbe in Binär zurück.
	 * 
	 * @param face  Index der Seite.
	 * @param index Index in der Seite.
	 * @return Die Farbe in Binär kodiert.
	 */
	private int extractColor(int face, int index) {
		return (this.seiten[face] >>> (index * 4)) & (0xF);
	}

	/**
	 * Gibt den "index"-ten Zug in seq zurück.
	 * 
	 * @param seq   Sequenz der Züge.
	 * @param index Index in der Sequenz 0-7.
	 * @return Zug in "seq" bei "index".
	 */
	private int extractMove(int seq, int index) {
		return (seq >>> (index << 2)) & (0xF);
	}

	/**
	 * Gibt zu gegebenem Binärkode die Farbe zuräck. Bei einem ungültigen Kode, wird
	 * 'X' zurückgegeben.
	 * 
	 * @param Farbe in Binär kodiert.
	 * @return Farbe als Buchstabe.
	 */
	private char lookupColor(int code) {
		switch (code) {
		case 0:
			return 'W';
		case 1:
			return 'B';
		case 2:
			return 'O';
		case 3:
			return 'G';
		case 4:
			return 'R';
		case 5:
			return 'Y';
		}
		return 'X';
	}

	/**
	 * Gibt zu gegebenem Binärkode den Zug zuräck. Bei einem ungültigen Kode, wird
	 * 'X' zurückgegeben.
	 * 
	 * @param code in Binär kodiert.
	 * @return Move als Buchstabe.
	 */
	private String lookupMove(int code) {
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
	 * Gibt die Seite "index" aus, sodass 1 nach oben zeigt.
	 * 
	 * @param index Index der Seite, welche ausgegeben werden soll.
	 */
	public void printFace(int index) {
		for (int i = 0; i < 9; i++) {
			if (i < 3) {
				System.out.print(lookupColor(extractColor(index, i)) + " ");
			} else if (i == 3) {
				System.out.print("\n" + lookupColor(extractColor(index, 7)) + " ");
			} else if (i == 4) {
				System.out.print(lookupColor(index) + " ");
			} else if (i == 5) {
				System.out.print(lookupColor(extractColor(index, i)) + "\n");
			} else {
				System.out.print(lookupColor(extractColor(index, 12 - i)) + " ");
			}
		}
	}

	/**
	 * Gibt den Würfel aus
	 */
	public void wuerfelAusgeben() {
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

	/**
	 * Getter für Seiten.
	 * 
	 * @return seiten
	 */
	public int[] getSeiten() {
		return seiten;
	}
}