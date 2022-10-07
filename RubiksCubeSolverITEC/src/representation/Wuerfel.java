package representation;

public class Wuerfel {

	/**
	 * Im Array sind die 6 Seiten gespeicht. Die i-te Seite hat die i-te Farbe:
	 * 
	 * 0: Weiß 1: Blau 2: Orange 3: Grün 4: Rot 5: Gelb
	 * 
	 * Die Binärdarstellungen dieser Zahlen bilden die 32-bit Zahlen, welche die
	 * Seiten wiefolgt darstellen:
	 * 
	 * 0 1 2 3 M 4 5 6 7
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
		this.seiten[seitenIndex] = 0;
	}
	
	/**
	 * Ersetzt den Würfel, durch einen gelösten Würfel.
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
	 * Überprüft ob der Würfel gelöst ist.
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
		return (face >>> (index * 4)) & (0xF);
	}

	/**
	 * Gibt zu gegebenem Binärkode die Farbe zurück. Bei einem ungültigen Kode, wird
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
}
gfkij