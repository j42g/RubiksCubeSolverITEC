package representation;

public class Util {
	
	public static int cnk(int n, int k) {
		if(n < k) {
			return 0;
		}
		if(k > (n / 2)) {
			k = n - k;
		}
		int s = 1;
		int i = n;
		int j = 1;
		while(i != n - k) {
			s *= i;
			s /= j;
			i--;
			j++;
		}
		return s;
	}

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

}
