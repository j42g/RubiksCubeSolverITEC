package representation;

public class Util {
	
	public static void printArr(int[] arr) {
		for(int i = 0; i < arr.length; i++) {
			if(i == arr.length - 1) {
				System.out.print(Integer.toBinaryString(arr[i])+ "\n");
			} else {
				System.out.print(Integer.toBinaryString(arr[i]) + ", ");
			}
		}
	}
	
	public static String kodeZuegeZuNotation(int[] zuege) {
		String output = "";
		int currInteger = 0;
		int currIndex = 0;
		int currMove;
		while (true) {
			currMove = (zuege[currInteger] >>> (currIndex << 2)) & (0xF);
			if (currMove == 0xF) {
				break;
			}
			if (currIndex == 7) {
				currIndex = -1;
				currInteger++;
			}
			currIndex++;
			output = output + lookupMove(currMove) + " ";

		}
		return output;
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
	 * Übersetzt und gibt es zurück.
	 * 
	 * @param zuege Züge in Notation
	 */
	public static int[] gebeZugsequenz(String zuege) {
		char[] czuege = zuege.toCharArray();
		int movesIndex = 0;
		int intIndex = 0;
		int moveKode;
		int[] moves = new int[czuege.length / 8 + 1]; // rate Länge
		for (int i = 0; i < czuege.length; i++) {
			if (czuege[i] == ' ') {
				continue;
			}
			moveKode = "UBLFRD".indexOf(czuege[i]);
			if (moveKode != -1) { // Valider Zug
				if (i != czuege.length - 1) { // Überprüfen ob es ein Zug wie L' ist und sicherstellen, das man nicht
												// out-of-bounds kommt
					if (czuege[i + 1] == '\'') {
						moveKode |= 0b1000; // ' bit setzen
						i++; // da wir ja 2 Zeichen haben
					} else if (czuege[i + 1] == '2') {
						moves[movesIndex] |= moveKode << (intIndex << 2); // in das Array schieben
						if (intIndex == 7) {
							movesIndex++;
							intIndex = -1;
						}
						intIndex++;
						i++; // da wir ja 2 Zeichen haben
					}
				}
				moves[movesIndex] |= moveKode << (intIndex << 2); // in das Array schieben
				if (intIndex == 7) {
					movesIndex++;
					intIndex = -1;
				}
				intIndex++;

			} else {
				System.out.println("Fehler in dreheZugsequenz.");
			}
		}
		moves[movesIndex] |= 0xF << (intIndex << 2);
		return moves;
	}
	
}
