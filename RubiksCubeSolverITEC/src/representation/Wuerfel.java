package representation;

public class Wuerfel {

	/**
	 * Im Array sind die 6 Seiten gespeicht. Die i-te Seite hat die i-te Farbe:
	 * 
	 * 0: Weiß 1: Blau 2: Orange 3: Grün 4: Rot 5: Gelb
	 * 
	 * Die Binïärdarstellungen dieser Zahlen bilden die 32-bit Zahlen, welche die
	 * Seiten wiefolgt darstellen:
	 * 
	 * 0 1 2
	 * 7 M 3
	 * 6 5 4
	 * 
	 * Die 1 "zeigt" dabei immer auf die nächste Fläche, basierend auf der
	 * Reihenfolge der Farben. Gelb zeigt auf Grün.
	 */
	private int[] seiten = new int[6];

	
	/**
	 *  TODO Implementieren
	 */
	public Wuerfel() {

	}

	/**
	 * Dreht die Fläche am Index "face".
	 * 
	 * @param face Index der zu drehenden Fläche
	 */
	void spinR(int face) {
		seiten[face] = Integer.rotateRight(seiten[face], 8);
		switch (face) {
		case 0:
			break;
		case 1:
			break;
		case 2:
			break;
		case 3:
			break;
		case 4:
			break;
		case 5:
			break;
		}
	}

	/**
	 * DAS MCAHST DU NICK
	 * 
	 * @param face0
	 * @param face1
	 * @param face2
	 * @param face3
	 * @param offset0
	 * @param offset1
	 * @param offset2
	 * @param offset3
	 */
	void sideSpin(int face0, int face1, int face2, int face3, int offset0, int offset1, int offset2, int offset3) {
		int cache = 0;
		cache = seiten[face0] >> offset0 & 0xF;

	}

	/**
	 * Ändert an der Fläche "seitenIndex" an den "feldIndex"-ten Position die Farbe zu "farbe".
	 * @param seitenIndex Index der Seite.
	 * @param feldIndex Index der Seite.
	 * @param farbe Farbe als Binärkode.
	 */
	public void veraendereEinzeln(int seitenIndex, int feldIndex, int farbe) {
		// feldIndex * 4 in schnell
		feldIndex = feldIndex << 2;
		// Bit magic
		for(int i = 0; i < 4; i++) {
			if(((farbe >> i) & 1) == 1) {
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
	 * DAS MCAHST DU NICK
	 * 
	 * @param face
	 * @param index
	 * @return
	 */
	public int extractStrip(int face, int index) {
		return (face >>> (index * 8)) & (0xFFF);
	}

	/**
	 * DAS MCAHST DU NICK
	 * 
	 * @param face
	 */
	void spinL(int face) {
		seiten[face] = Integer.rotateLeft(seiten[face], 8);
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
	 * Gibt zu einer Fläche an einem Stelle die Farbe in Binär zuräck.
	 * 
	 * @param face  Index der Seite.
	 * @param index Index in der Seite.
	 * @return Die Farbe in Binär kodiert.
	 */
	public int extractColor(int face, int index) {
		return (face >>> (index * 4)) & (0xF);
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
	 * Gibt die Seite "index" aus, sodass 1 nach oben zeigt.
	 * 
	 * @param index Index der Seite, welche ausgegeben werden soll.
	 */
	public void printFace(int index) {
		for (int i = 0; i < 9; i++) {
			if (i < 3) {
				System.out.print(lookupColor(extractColor(seiten[index], i)) + " ");
			} else if (i == 3) {
				System.out.print("\n" + lookupColor(extractColor(seiten[index], 7)) + " ");
			} else if (i == 4) {
				System.out.print(lookupColor(index) + " ");
			} else if (i == 5) {
				System.out.print(lookupColor(extractColor(seiten[index], i)) + "\n");
			} else {
				System.out.print(lookupColor(extractColor(seiten[index], 12 - i)) + " ");
			}
		}
	}
	
	/**
	 * Gibt den Würfel aus
	 */
	public void wuerfelAusgeben() {
		// syntax verringern
		char[][] s = new char[6][8];
		for(int i = 0; i < this.seiten.length; i++) {
			for(int j = 0; j < 8; j++) {
				s[i][j] = lookupColor(extractColor(this.seiten[i], j));
			}
		}
		// Abstände definieren
		String la = "        ";
		String md = "   ";
		String sm = " ";
		// Weiße Fläche
		System.out.println(la + s[0][0] + sm + s[0][1] + sm + s[0][2]);
		System.out.println(la + s[0][7] + sm +    "W"  + sm + s[0][3]);
		System.out.println(la + s[0][6] + sm + s[0][5] + sm + s[0][4]);
		// Orange														Grün											Rot												Blau
		System.out.println(s[2][0] + sm + s[2][1] + sm + s[2][2] + md     + s[3][0] + sm + s[3][1] + sm + s[3][2] + md    + s[4][0] + sm + s[4][1] + sm + s[4][2] + md    + s[1][0] + sm + s[1][1] + sm + s[1][2]);
		System.out.println(s[2][7] + sm +    "O"  + sm + s[2][3] + md     + s[3][7] + sm +    "G"  + sm + s[3][3] + md    + s[4][7] + sm +    "R"  + sm + s[4][3] + md    + s[1][7] + sm +    "B"  + sm + s[1][3]);
		System.out.println(s[2][6] + sm + s[2][5] + sm + s[2][4] + md     + s[3][6] + sm + s[3][5] + sm + s[3][4] + md    + s[4][6] + sm + s[4][5] + sm + s[4][4] + md    + s[1][6] + sm + s[1][5] + sm + s[1][4]);
		// Gelb
		System.out.println(la + s[5][0] + sm + s[5][1] + sm + s[5][2]);
		System.out.println(la + s[5][7] + sm +    "Y"  + sm + s[5][3]);
		System.out.println(la + s[5][6] + sm + s[5][5] + sm + s[5][4]);
		
	}
}