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
	
}
