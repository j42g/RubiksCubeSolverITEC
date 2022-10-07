package representation;

public class Cube {
	int[] faces = new int[6];
	// 0000 white
	// 0001 blue
	// 0010 orange
	// 0011 green
	// 0100 red
	// 0101 yellow

	// index one always in direction of next face -> white 1 points to blue etc.

	// 4 bits per color, 32 bits per face, 8 bits useless

	/*
	 * 0 1 2 7 3 6 5 4
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

	void sideSpin(int face0, int face1, int face2, int face3, int offset0, int offset1, int offset2, int offset3) {
		int cache = 0;
		cache = faces[face0] >> offset0 & 0xF;

	}

	public int extractStrip(int face, int index) {
		return (face >>> (index * 8)) & (0xFFF);
	}

	void spinL(int face) {
		faces[face] = Integer.rotateLeft(faces[face], 8);
	}
}
