package representation;

public class Cube {
	int[] faces = new int[6];
	
	/**
	 * Im Array sind die 6 Seiten gespeicht. Die i-te Seite hat die i-te Farbe:
	 * 
	 * 0: Weiß
	 * 1: Blau
	 * 2: Orange
	 * 3: Grün
	 * 4: Rot
	 * 5: Gelb
	 * 
	 * Die Binärdarstellungen dieser Zahlen bilden die 32-bit Zahlen, welche die Seiten wiefolgt darstellen:
	 * 
	 * 0 1 2
	 * 3 M 4
	 * 5 6 7
	 * 
	 * Die 1 "zeigt" dabei immer auf die nächste Fläche, basierend auf der Reihenfolge der Farben.
	 */
	

	/**
	 * Dreht die Fläche am Index "face".
	 * @param face Index der zu drehenden Fläche
	 */
	void spinR(int face) {
		faces[face] = Integer.rotateRight(faces[face], 8);
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
		cache = faces[face0] >> offset0 & 0xF;

	}

	/**
	 * DAS MCAHST DU NICK
	 * @param face
	 * @param index
	 * @return
	 */
	public int extractStrip(int face, int index) {
		return (face >>> (index * 8)) & (0xFFF);
	}

	/**
	 * DAS MCAHST DU NICK
	 * @param face
	 */
	void spinL(int face) {
		faces[face] = Integer.rotateLeft(faces[face], 8);
	}
}
