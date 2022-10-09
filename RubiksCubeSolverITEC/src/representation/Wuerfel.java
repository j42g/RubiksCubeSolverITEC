package representation;

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
	 * 
	 * 
	 * Vielleicht ändern idk:
	 * 
	 * Der Würfel wird "gehalten", sodass Weiß oben und Grün vorne ist. Die Züge
	 * sind dann immer im Uhrzeigersinn, wenn man auf die Seite direkt draufschaut
	 * Die Züge sind dann wiefolgt definiert:
	 * 
	 * 0: U = Weiß; 1: B = Blau; 2: L = Orange; 3: F = Grün; 4: R = Rot; 5: D =
	 * Gelb;
	 * 
	 * Ein Zug sind 4 Bits. Das erste Bit steht dafür, ob es gegen den Uhrzeigersinn
	 * ist.
	 * 
	 * 0100 wäre R. 1011 wäre F'.
	 * 
	 * 1111 ist ein ungültiger Zug und steht für Ende der Zugsquenz.
	 * 
	 * Jeder Zugkode entspricht dem Index der Seite, die er dreht.
	 *
	 */
	public int[] seiten = new int[6];
	private final int[] geloest = {0x00000000,
			0x11111111, 
			0x22222222, 
			0x33333333, 
			0x44444444, 
			0x55555555};

	/**
	 * Erster Index definiert einen Zug 0-5 Zweiter Index definiert einen
	 * Tauschvorgang im Zug (1,6) -> (4,4) -> (3,6) -> (2,6) und (2,6) -> (1,6)
	 * Dritte Index definiert die Vertauschungsreihe Vierter Index 0 -> Index der
	 * Seite und 1 -> Index auf der Seite.
	 */
	private int[][][][] randZuege = {
			// U
			{ { { 1, 6 }, { 4, 4 }, { 3, 6 }, { 2, 6 } }, { { 1, 7 }, { 4, 5 }, { 3, 7 }, { 2, 7 } },
					{ { 1, 0 }, { 4, 6 }, { 3, 0 }, { 2, 0 } } } };

	
	private final int[][][] aussenIndex = {
			// U (Blau, Rot, Grün, Orange)
			{{1,6}, {4,4}, {3,6}, {2,6}},
			// B (Weiß, Orange, Gelb, Rot)
			{{0,0}, {2,4}, {5,4}, {4,6}},
			// L (Weiß, Grün, Gelb, Blau)
			{{0,6}, {3,4}, {5,6}, {1,0}},
			// F (Weiß, Rot, Gelb, Orange)
			{{0,4}, {4,2}, {5,0}, {2,0}},
			// R (Weiß, Blau, Gelb, Grün)
			{{0,2}, {1,4}, {5,2}, {3,0}},
			// D (Blau, Orange, Grün, Rot)
			{{1,2}, {2,2}, {3,2}, {4,0}}
	};
	
	/**
	 * Geneiert gelösten Würfel.
	 */
	public Wuerfel() {
		this.makeSolved();
	}
	
	/**
	 * Generiert Würfel mit seiten pos.
	 * @param Würfelkonfiguration
	 */
	public Wuerfel(int[] pos) {
		this.seiten = pos;
	}
	
	/**
	 * Generiert Würfel mit pos und dreht moves.
	 * @param pos Würfelkonfiguration
	 * @param moves Zugabfolge
	 */
	public Wuerfel(int[] pos, int[] moves) {
		this.seiten = pos;
		this.dreheZugsequenz(moves);
	}

	/**
	 * Dreht Zugsequenz.
	 * 
	 * @param zug Züge als Kode
	 */
	public void dreheZugsequenz(int[] zug) {
		int currInteger = 0;
		int currIndex = 0;
		int currMove;
		while (true) {
			currMove = this.extractMove(zug[currInteger], currIndex);
			if (currMove == 0xF) {
				break;
			}
			if (currIndex == 7) {
				currIndex = 0;
				currInteger++;
			}
			currIndex++;
			this.drehe(currMove);
			
		}
		
	}

	/**
	 * Übersetzt und ruft dreheZugSquenz auf.
	 * 
	 * @param zuege Züge in Notation
	 */
	public void dreheZugsequenz(String zuege) {
		char[] czuege = zuege.toCharArray();
		int movesIndex = 0;
		int intIndex = 0;
		int moveKode;
		int[] moves = new int[czuege.length / 8 + 1]; // rate Länge
		for (int i = 0; i < czuege.length; i++) {
			if(czuege[i] == ' ') {
				continue;
			}
			moveKode = "UBLFRD".indexOf(czuege[i]);
			if (moveKode != -1) { // Valider Zug
				if (i != czuege.length - 1) { // Überprüfen ob es ein Zug wie L' ist und sicherstellen, das man nicht out-of-bounds kommt
					if(czuege[i + 1] == '\'') {
						moveKode |= 0b1000; // ' bit setzen
						i++; // da wir ja 2 Zeichen haben
					} else if (czuege[i + 1] == '2') {
						moves[movesIndex] |= moveKode << (intIndex << 2); // in das Array schieben
						if(intIndex == 7) {
							movesIndex++;
							intIndex = 0;
						}
						intIndex++;
						i++; // da wir ja 2 Zeichen haben
					}
				}
				moves[movesIndex] |= moveKode << (intIndex << 2); // in das Array schieben
				if(intIndex == 7) {
					movesIndex++;
					intIndex = 0;
				}
				intIndex++;

			} else {
				System.out.println("Fehler in dreheZugsequenz.");
			}
		}
		moves[movesIndex] |= 0xF << (intIndex << 2);
		this.dreheZugsequenz(moves);
	}

	/**
	 * Dreht einen Zug
	 * 
	 * @param zug Zug als Kode.
	 */
	public void dreheZug(int zug) {
		// Gegen oder mit Uhrzeigersinn
		int richtungsBit = ((zug >> 3) & 1);
		int seiteDesZuges = zug & ~0b1000; // zug & ~0b1000 stellt den Zugkode da, welcher der Index der Seite ist
		int prev;
		int curr;
		int index;
		if (richtungsBit == 1) {
			for (int i = 2; i > -1; i--) {
				prev = this.extractColor(this.randZuege[seiteDesZuges][i][0][0],
						this.randZuege[seiteDesZuges][i][0][1]);
				for (int j = 3; j > -1; j--) {
					index = ((j + 1) % 4 + 4) % 4; // (a % b + b) % b gibt keine negativen Reste
					curr = this.extractColor(this.randZuege[seiteDesZuges][i][j][0],
							this.randZuege[seiteDesZuges][i][j][1]);
					this.veraendereEinzeln(this.randZuege[seiteDesZuges][i][j][0],
							this.randZuege[seiteDesZuges][i][j][1], prev);
					prev = curr;

				}
			}
			this.seiten[seiteDesZuges] = Integer.rotateRight(this.seiten[seiteDesZuges], 8);
		} else {
			for (int i = 0; i < 3; i++) {
				prev = this.extractColor(this.randZuege[seiteDesZuges][i][3][0],
						this.randZuege[seiteDesZuges][i][3][1]);
				for (int j = 0; j < 4; j++) {
					index = ((j + 1) % 4 + 4) % 4; // (a % b + b) % b gibt keine negativen Reste
					curr = this.extractColor(this.randZuege[seiteDesZuges][i][j][0],
							this.randZuege[seiteDesZuges][i][j][1]);
					this.veraendereEinzeln(this.randZuege[seiteDesZuges][i][j][0],
							this.randZuege[seiteDesZuges][i][j][1], prev);
					prev = curr;

				}
			}
			this.seiten[seiteDesZuges] = Integer.rotateLeft(this.seiten[seiteDesZuges], 8);
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
	 * Redundant? DAS MCAHST DU NICK
	 * 
	 * @param face
	 * @param pos Komplement aus Seite und Stipindex
	 * @return
	 */
	public int extractStrip(int[] pos) {
		return (Integer.rotateRight(this.seiten[pos[0]],(pos[1] << 2))) & (0xFFF);
	}

	/**
	 * Ruft dreheUhr und dreheGUhr auf
	 */
	public void drehe(int zug) {
		if(((zug >> 3) & 1) == 1) {
			this.dreheGUhr(zug & ~0b1000);
		} else {
			this.dreheUhr(zug & ~0b1000);;
		}
	}
	
	/**
	 * Redundant? DAS MCAHST DU NICK
	 * 
	 * @param face Index der Seite
	 */
	public void dreheUhr(int face) {
		seiten[face] = Integer.rotateLeft(seiten[face], 8);
		long aussen = 0;
		for(int i = 0; i < 4; i++) {
			aussen |= ((long)this.extractStrip(aussenIndex[face][i])) << (i*16);
		}
		aussen = Long.rotateLeft(aussen, 16);
		for(int i = 0; i < 4; i++) {
			overwriteStrip((int)(aussen>>>i*16)&0xFFF,aussenIndex[face][i]);
		}
	}
	
	public void dreheGUhr(int face) {
		seiten[face] = Integer.rotateRight(seiten[face], 8);
		long aussen = 0;
		for(int i = 0; i < 4; i++) {
			aussen |= ((long)this.extractStrip(aussenIndex[face][i])) << (i*16);
		}
		aussen = Long.rotateRight(aussen, 16);
		for(int i = 0; i < 4; i++) {
			overwriteStrip((int)(aussen>>>i*16)&0xFFF,aussenIndex[face][i]);
		}
	}
	
	public void overwriteStrip(int a, int[] pos) {
		a = Integer.rotateLeft(a, pos[1] << 2) ;
		seiten[pos[0]] &= ~(Integer.rotateLeft(0xFFF, (pos[1] << 2)));
		seiten[pos[0]] |= a;
	}
		
	/**
	 * Überprüft ob mask = seiten, mit der Einschränkung, dass bei den Felder
	 * wo in der Maske F steht alles sein darf.
	 * @param mask Maske
	 * @return true wenn gleich sonst false.
	 */
	public boolean isMaskSolved(int[] mask) {
		for(int i = 0; i < 6; i++) {
			for(int j = 0; j < 8; j++) {
				if(this.extractMove(mask[i], j) == 0xF) { // extractMove wird missbraucht um mir die 4 bits rauszuholen
					continue;
				}
				if(this.extractColor(i, j) != this.extractMove(mask[i], j)) {
					return false;
				}
			}
			
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
	public int extractColor(int face, int index) {
		return (this.seiten[face] >>> (index * 4)) & (0xF);
	}

	/**
	 * Gibt den "index"-ten Zug in seq zurück.
	 * 
	 * @param seq   Sequenz der Züge.
	 * @param index Index in der Sequenz 0-7.
	 * @return Zug in "seq" bei "index".
	 */
	public int extractMove(int seq, int index) {
		return (seq >>> (index << 2)) & (0xF);
	}

	/**
	 * Gibt zu gegebenem Binärkode die Farbe zuräck. Bei einem ungültigen Kode, wird
	 * 'X' zurückgegeben.
	 * 
	 * @param Farbe in Binär kodiert.
	 * @return Farbe als Buchstabe.
	 */
	public char lookupColor(int code) {
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
	 * 'X' zurückgegeben
	 * @param code in Binär kodiert.
	 * @return Move als Buchstabe.
	 */
	public String lookupMove(int code) {
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
}