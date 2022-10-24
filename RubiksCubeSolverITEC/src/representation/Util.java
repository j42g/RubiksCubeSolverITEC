package representation;

import loeser.ZweiMalZwei;

public class Util {

	public static void printBinArr(int[] arr) {
		for (int i = 0; i < arr.length; i++) {
			if (i == arr.length - 1) {
				System.out.print(Integer.toBinaryString(arr[i]) + "\n");
			} else {
				System.out.print(Integer.toBinaryString(arr[i]) + ", ");
			}
		}
	}
	
	public static void printArr(int[] arr) {
		System.out.print(arr.length + ":");
		for (int i = 0; i < arr.length; i++) {
			if (i == arr.length - 1) {
				System.out.print(arr[i] + "\n");
			} else {
				System.out.print(arr[i] + ", ");
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
	 * @param zuege
	 * @return
	 */
	public static String kuerzen(String zuege) {
		char[] s = zuege.toCharArray();
		String bessereZuege = "";
		boolean changed = false;
		for (int i = 0; i < s.length - 3; i++) {
			if (s[i] == ' ') {
				continue;
			} else if (s[i] == s[i + 2] && s[i + 3] == '\'') { // R R'
				i += 3;
				changed = true;
				continue;
			} else if (s[i] == s[i + 3] && s[i + 1] == '\'' && s[i + 4] != '\'') { // R' R
				i += 3;
				changed = true;
				continue;
			} else {
				if (s[i + 1] == '\'') {
					bessereZuege += s[i] + "' ";
					i++;
				} else {
					bessereZuege += s[i] + " ";
				}
			}
		}

		if (changed) {
			return kuerzen(bessereZuege);
		}
		return bessereZuege;
	}
	
	public static void testLauf(int durchgaenge) {
		Wuerfel w = new Wuerfel();
		long summe = 0;
		long time;
		for(int i = 0; i < durchgaenge; i++) {
			w.verdrehe(26, false);
			ZweiMalZwei a = new ZweiMalZwei(w);
			time = System.currentTimeMillis();
			a.loese();
			summe += System.currentTimeMillis() - time;
			System.out.println((i + 1) + "/" + durchgaenge + " fertig.");
		}
		// Bilde Durchschnitt
		System.out.println("Durchschnitt: " + summe/durchgaenge);
	}

}
