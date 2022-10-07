package representation;

public class Starter {

	public class Würfel {
	    
	    int[] faces;

	    public boolean isSolved() {
	        if (faces[0] == 0x00000000 &&
	            faces[1] == 0x11111111 &&
	            faces[2] == 0x22222222 &&
	            faces[3] == 0x33333333 &&
	            faces[4] == 0x44444444 &&
	            faces[5] == 0x55555555) {
	            return true;
	        }
	        return false;
	    }
	    
	    public void printCube() {
	        // convert to colors
	        char[][] coloredCube = new char[3][6];
	        // print white face
	        System.out.println("\t" + faces[0] + " ");
	        System.out.println("\t" + faces[0]);
	        System.out.println("\t" + faces[0]);
	    }
	    
	    public void printFace(int index) {
	        for(int i = 0; i < 9; i++) {
	            if(i < 3) {
	                System.out.print(lookupColor(extractColor(faces[index], i)) + " ");
	            } else if (i == 4) {
	                System.out.println(lookupColor(extractColor(faces[index], i)));
	            }
	            if(i == 5) {
	                
	            }
	        }
	    }
	    
	    /**
	     * Gibt zu gegebenem Binärkode die Farbe zurück. Bei einem ungültigen Kode, wird 'X' zurückgegeben. 
	     * @param Farbe in Binär kodiert.
	     * @return Farbe als Buchstabe.
	     */
	    public char lookupColor(int code){
	        switch(code) {
	        case 0: return 'W';
	        case 1: return 'B';
	        case 2: return 'O';
	        case 3: return 'G';
	        case 4: return 'R';
	        case 5: return 'Y';
	        }
	        return 'X';
	    }
	    
	    /**
	     * Gibt zu einer Fläche an einem Stelle die Farbe in Binär zurück.
	     * @param face Index der Seite.
	     * @param index Index in der Seite.
	     * @return Die Farbe in Binär kodiert.
	     */
	    public int extractColor(int face, int index) { 
	        return (face >>> (index*4)) & (0xF);
	    }
	}

}
