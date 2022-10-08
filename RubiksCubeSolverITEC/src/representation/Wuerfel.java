package representation;

public class Wuerfel {

	/**
	 * Im Array sind die 6 Seiten gespeicht. Die i-te Seite hat die i-te Farbe:
	 * 
	 * 0: Wei�; 1: Blau; 2: Orange; 3: Gr�n; 4: Rot; 5: Gelb
	 * 
	 * Die Bin��rdarstellungen dieser Zahlen bilden die 32-bit Zahlen, welche die
	 * Seiten wiefolgt darstellen:
	 * 
	 * 0 1 2 7 M 3 6 5 4
	 * 
	 * Die 1 "zeigt" dabei immer auf die n�chste Fl�che, basierend auf der
	 * Reihenfolge der Farben. Gelb zeigt auf Gr�n.
	 * 
	 * 
	 * 
	 * Vielleicht �ndern idk:
	 * 
	 * Der W�rfel wird "gehalten", sodass Wei� oben und Gr�n vorne ist. Die Z�ge
	 * sind dann immer im Uhrzeigersinn, wenn man auf die Seite direkt draufschaut
	 * Die Z�ge sind dann wiefolgt definiert:
	 * 
	 * 0: U = Wei�; 1: B = Blau; 2: L = Orange; 3: F = Gr�n; 4: R = Rot; 5: D = Gelb;
	 * 
	 * Ein Zug sind 4 Bits. Das erste Bit steht daf�r, ob es gegen den
	 * Uhrzeigersinn ist.
	 * 
	 * 0100 w�re R.
	 * 1011 w�re F'. 
	 * 
	 * 1111 ist ein ung�ltiger Zug und steht f�r Ende der Zugsquenz.
	 * 
	 * Jeder Zugkode entspricht dem Index der Seite, die er dreht.
	 *
	 */
	private int[] seiten = new int[6];
	
	/**
	 * Erster Index definiert einen Zug 0-5
	 * Zweiter Index definiert einen Tauschvorgang im Zug (1,6) -> (4,4) -> (3,6) -> (2,6) und (2,6) -> (1,6)
	 * Dritte Index definiert die Vertauschungsreihe
	 * Vierter Index 0 -> Index der Seite und 1 -> Index auf der Seite
	 */
	private int[][][][] randZuege = {
			// U
			{
			// Blau -> Rot
			{{1,6},{4,4},{3,6},{2,6}},
			{{1,7},{4,5},{3,7},{2,7}},
			{{1,0},{4,6},{3,0},{2,0}}
			}
	};

	/**
	 * TODO Implementieren
	 */
	public Wuerfel() {

	}

	/**
	 * Dreht Zugsequenz
	 * @param zug Z�ge als Kode
	 */
	public void dreheZugsequenz(int[] zug) {
		int curInteger = 0;
		int curIndex = 0;
		int curMove;
		while(true) {
			curMove = this.extractMove(zug[curInteger], curIndex);
			if(curMove == 0xF) {
				break;
			}
			if(curIndex == 7) {
				curIndex = 0;
				curInteger++;
			}
			this.dreheZug(curMove);
		}
	}
	
	/**
	 * �bersetzt und ruft dreheZugSquenz auf.
	 * @param zuege Z�ge in Notation
	 */
	public void dreheZugsequenz(String zuege) {
		
	}
	
	/**
	 * Dreht einen Zug
	 * @param zug Zug als Kode.
	 */
	public void dreheZug(int zug) {
		// Gegen oder mit Uhrzeigersinn
		int richtungsBit = ((zug >> 3) & 1 );
		int seiteDesZuges = zug & ~0b1000; // zug & ~0b1000 stellt den Zugkode da, welcher der Index der Seite ist
		int prev;
		int curr;
		int index;
		if( richtungsBit == 1) {
			for(int i = 2; i > -1; i--) {
				prev = this.extractColor(this.randZuege[seiteDesZuges][i][0][0], this.randZuege[seiteDesZuges][i][0][1]);
				for(int j = 3; j > -1; j--) {
					index = ((j + 1) % 4 + 4) % 4; // (a % b + b) % b gibt keine negativen Reste 
					curr = this.extractColor(this.randZuege[seiteDesZuges][i][j][0], this.randZuege[seiteDesZuges][i][j][1]);
					this.veraendereEinzeln(this.randZuege[seiteDesZuges][i][j][0], this.randZuege[seiteDesZuges][i][j][1], prev);
					prev = curr;
					
				}
			}
			this.seiten[seiteDesZuges] = Integer.rotateRight(this.seiten[seiteDesZuges], 8);
		} else {
			for(int i = 0; i < 3; i++) {
				prev = this.extractColor(this.randZuege[seiteDesZuges][i][3][0], this.randZuege[seiteDesZuges][i][3][1]);
				for(int j = 0; j < 4; j++) {
					index = ((j + 1) % 4 + 4) % 4; // (a % b + b) % b gibt keine negativen Reste 
					curr = this.extractColor(this.randZuege[seiteDesZuges][i][j][0], this.randZuege[seiteDesZuges][i][j][1]);
					this.veraendereEinzeln(this.randZuege[seiteDesZuges][i][j][0], this.randZuege[seiteDesZuges][i][j][1], prev);
					prev = curr;
					
				}
			}
			this.seiten[seiteDesZuges] = Integer.rotateLeft(this.seiten[seiteDesZuges], 8);
		}
	}



	/**
	 * �ndert an der Fl�che "seitenIndex" an den "feldIndex"-ten Position die Farbe
	 * zu "farbe".
	 * 
	 * @param seitenIndex Index der Seite.
	 * @param feldIndex   Index der Seite.
	 * @param farbe       Farbe als Bin�rkode.
	 */
	public void veraendereEinzeln(int seitenIndex, int feldIndex, int farbe) {
		// feldIndex * 4 in schnell
		feldIndex = feldIndex << 2;
		// Bit magic
		for (int i = 0; i < 4; i++) {
			if (((farbe >> i) & 1) == 1) {
				this.seiten[seitenIndex] |= (0b1 << (feldIndex + i));
			} else {
				this.seiten[seitenIndex] &= ~(0b1 << (feldIndex + i));
			}
		}
	}

	/**
	 * Ersetzt den W�rfel, durch einen gel�sten W�rfel.
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
	 * Redundant?
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
	 * Redundant?
	 * DAS MCAHST DU NICK
	 * 
	 * @param face
	 */
	void spinL(int face) {
		seiten[face] = Integer.rotateLeft(seiten[face], 8);
	}

	/**
	 * �berpr�ft ob der W�rfel gel�st ist.
	 * 
	 * @return true wenn gel�st, sonst false.
	 */
	public boolean isSolved() {
		if (seiten[0] == 0x00000000 && seiten[1] == 0x11111111 && seiten[2] == 0x22222222 && seiten[3] == 0x33333333
				&& seiten[4] == 0x44444444 && seiten[5] == 0x55555555) {
			return true;
		}
		return false;
	}

	/**
	 * Gibt zu einer Fl�che an einem Stelle die Farbe in Bin�r zur�ck.
	 * 
	 * @param face  Index der Seite.
	 * @param index Index in der Seite.
	 * @return Die Farbe in Bin�r kodiert.
	 */
	public int extractColor(int face, int index) {
		return (this.seiten[face] >>> (index * 4)) & (0xF);
	}
	
	/**
	 * Gibt den "index"-ten Zug in seq zur�ck.
	 * @param seq Sequenz der Z�ge.
	 * @param index Index in der Sequenz 0-7.
	 * @return Zug in "seq" bei "index".
	 */
	public int extractMove(int seq, int index) {
		return (seq >>> (index * 4)) & (0xF);
	} 

	/**
	 * Gibt zu gegebenem Bin�rkode die Farbe zur�ck. Bei einem ung�ltigen Kode, wird
	 * 'X' zur�ckgegeben.
	 * 
	 * @param Farbe in Bin�r kodiert.
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
	 * Gibt den W�rfel aus
	 */
	public void wuerfelAusgeben() {
		// syntax verringern
		char[][] s = new char[6][8];
		for (int i = 0; i < this.seiten.length; i++) {
			for (int j = 0; j < 8; j++) {
				s[i][j] = lookupColor(extractColor(i, j));
			}
		}
		// Abst�nde definieren
		String la = "        ";
		String md = "   ";
		String sm = " ";
		// Wei�e Fl�che
		System.out.println(la + s[0][0] + sm + s[0][1] + sm + s[0][2]);
		System.out.println(la + s[0][7] + sm + "W" + sm + s[0][3]);
		System.out.println(la + s[0][6] + sm + s[0][5] + sm + s[0][4]);
		// Orange Gr�n Rot Blau
		System.out.println(s[2][6] + sm + s[2][7] + sm + s[2][0] + md + s[3][6] + sm + s[3][7] + sm + s[3][0] + md + s[4][4] + sm + s[4][5] + sm + s[4][6] + md + s[1][6] + sm + s[1][7] + sm + s[1][0]);
		System.out.println(s[2][5] + sm + "O"     + sm + s[2][1] + md + s[3][5] + sm + "G"     + sm + s[3][1] + md + s[4][3]+ sm +     "R"  + sm + s[4][7] + md + s[1][5] + sm + "B"     + sm + s[1][1]);
		System.out.println(s[2][4] + sm + s[2][3] + sm + s[2][2] + md + s[3][4] + sm + s[3][3] + sm + s[3][2] + md + s[4][2] + sm + s[4][1] + sm + s[4][0] + md + s[1][4] + sm + s[1][3] + sm + s[1][2]);
		// Gelb
		System.out.println(la + s[5][0] + sm + s[5][1] + sm + s[5][2]);
		System.out.println(la + s[5][7] + sm + "Y" + sm + s[5][3]);
		System.out.println(la + s[5][6] + sm + s[5][5] + sm + s[5][4]);
		System.out.println("\n\n");

	}
}