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
	
}
