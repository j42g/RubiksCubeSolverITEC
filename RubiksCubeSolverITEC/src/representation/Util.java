package representation;

import loeser.ZweiMalZwei;

public class Util {

	public static void printArr(int[] arr) {
		for (int i = 0; i < arr.length; i++) {
			if (i == arr.length - 1) {
				System.out.print(arr[i] + "\n");
			} else {
				System.out.print(arr[i] + ", ");
			}
		}
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
	 * @param zuege Zugsequenz
	 * @return Zugsequenz gekürzt
	 */
	public static String kuerzen(String zuege) {
		boolean changed = true;
		if(!zuege.contains(" ")){
			return zuege;
		}
		while (changed) {
			changed = false;
			String[] s = zuege.split(" ");
			StringBuilder bessereZuege = new StringBuilder();
			for (int i = 0; i < s.length - 1; i++) {
				if (s[i].equals(s[i + 1])) { // D + D => D2
					bessereZuege.append(s[i].charAt(0)).append("2");
					bessereZuege.append(" ");
					i++;
					changed = true;
				} else if (s[i].equals(s[i + 1] + "'")) { // D + D'
					i++;
					changed = true;
				} else if ((s[i] + "'").equals(s[i + 1])) { // D' + D
					i++;
					changed = true;
				} else if (s[i].charAt(0) == s[i + 1].charAt(0)) { // D* + D*
					if (!(s[i].charAt(0) + "2").equals(s[i]) || !(s[i + 1].charAt(0) + "2").equals(s[i + 1])) {
						if ((s[i].charAt(0)+"2").equals(s[i])) {
							if ((s[i + 1].charAt(0)+"'").equals(s[i+1])) {
								bessereZuege.append(s[i].charAt(0));
							} else {
								bessereZuege.append(s[i].charAt(0)).append("'");
							}
						} else {
							if ((s[i].charAt(0)+"'").equals(s[i])) {
								bessereZuege.append(s[i].charAt(0));
							} else {
								bessereZuege.append(s[i].charAt(0)).append("'");
							}
						}
						bessereZuege.append(" ");
					}
					changed = true;
					i++;
				} else {
					bessereZuege.append(s[i]);
					bessereZuege.append(" ");
				}
				if(i==s.length-2) {
					bessereZuege.append(s[s.length - 1]);
				}
			}
			zuege = bessereZuege.toString();
		}
		return zuege;
	}

	public static void testLauf(int durchgaenge) {
		Wuerfel w = new Wuerfel();
		long summe = 0;
		long time;
		for (int i = 0; i < durchgaenge; i++) {
			w.verdrehe(26, false);
			ZweiMalZwei a = new ZweiMalZwei(w, 1);
			time = System.currentTimeMillis();
			a.loese();
			summe += System.currentTimeMillis() - time;
			System.out.println((i + 1) + "/" + durchgaenge + " fertig.");
		}
		// Bilde Durchschnitt
		System.out.println("Durchschnitt: " + summe / durchgaenge);
	}

	public static int factorial(int n){
		int f = 1;
		for(int i = 2; i < n + 1; i++){
			f *= i;
		}
		return f;
	}

}
