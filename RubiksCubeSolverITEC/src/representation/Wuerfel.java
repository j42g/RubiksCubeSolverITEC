package representation;

public class Wuerfel {

	/**
	 * Im Array sind die 6 Seiten gespeicht. Die i-te Seite hat die i-te Farbe:
	 * 
	 * 0: Wei� 1: Blau 2: Orange 3: Gr�n 4: Rot 5: Gelb
	 * 
	 * Die Bin�rdarstellungen dieser Zahlen bilden die 32-bit Zahlen, welche die
	 * Seiten wiefolgt darstellen:
	 * 
	 * 0 1 2
	 * 7 M 3
	 * 6 5 4
	 * 
	 * Die 1 "zeigt" dabei immer auf die n�chste Fl�che, basierend auf der
	 * Reihenfolge der Farben. Gelb zeigt auf Gr�n.
	 */
	private int[] seiten = new int[6];

	
	/**
	 *  TODO Implementieren
	 */
	public Wuerfel() {

	}

	/**
	 * Dreht die Fl�che am Index "face".
	 * 
	 * @param face Index der zu drehenden Fl�che
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
	 * �ndert an der Fl�che "seitenIndex" an den "feldIndex"-ten Position die Farbe zu "farbe".
	 * @param seitenIndex Index der Seite.
	 * @param feldIndex Index der Seite.
	 * @param farbe Farbe als Bin�rkode.
	 */
	public void veraendereEinzeln(int seitenIndex, int feldIndex, int farbe) {
		this.seiten[seitenIndex] = 0;
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
		return (face >>> (index * 4)) & (0xF);
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
}