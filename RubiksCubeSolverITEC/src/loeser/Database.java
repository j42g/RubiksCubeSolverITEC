package loeser;

import java.io.FileInputStream;
import java.io.FileOutputStream;

public class Database {


    public static byte[] data;
    public static boolean loaded = false;

    public static void writeDatabase(byte[] data){
		/*byte[] data = new byte[]{15, 6, 2, 1}; Dieser Kode funktioniert schonmal.
		byte[] actualOuput = new byte[(data.length + 1) / 2];
		int arrIndex = 0;
		for(int i = 0; i < actualOuput.length - 1; i++){
			arrIndex = i << 1;
			actualOuput[i] = (byte) ( (data[arrIndex] << 4) + data[arrIndex + 1] );
		}

		try {
			FileOutputStream out = new FileOutputStream("cornerData.bin");
			out.write(actualOuput, 0, actualOuput.length);
			out.flush();
			out.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}*/

        try { // Dieser Kode ist wahrscheinlich langsam, weil immer einzeln alles angehängt wird
            // Fix: Ein fertiges Byte-Array machen und das dann schreiben (total einfach)
            // In der Zeit in der ich diesen Kommentar geschrieben hab, hätte man das machen können TODO
            FileOutputStream out = new FileOutputStream("cornerData.bin", true);
            int arrIndex = 0;
            for(int i = 0; i < data.length / 2; i++){
                arrIndex = i << 1;
                out.write( (data[arrIndex] << 4) + data[arrIndex + 1] );

            }
            if(data.length % 2 == 1){
                out.write(data[data.length - 1] << 4);
            }
            out.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public static int readfromDatabase(int index){
        if(!loaded) {
            try {
                FileInputStream in = new FileInputStream("cornerData.bin");
                loaded = true;
                data = in.readAllBytes();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        if(index % 2 == 0){ // vorderer Teil des bytes
            return data[index / 2] >>> 4;
        } else { // hintere Teil des bytes
            return data[index / 2] & 0xF;
        }


    }
}
