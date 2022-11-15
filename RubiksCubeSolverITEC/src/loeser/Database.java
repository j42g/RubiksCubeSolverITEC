package loeser;

import java.io.FileInputStream;
import java.io.FileOutputStream;

public class Database {

    private final String filename;
    private byte[] data;
    private boolean loaded;

    public Database(String filename, boolean load) {
        this.filename = filename;
        loaded = load;
        if (load) {
            this.load();
        }
    }

    public void load() {
        if (this.loaded) {
            return;
        } else {
            try {
                FileInputStream in = new FileInputStream(this.filename);
                this.loaded = true;
                this.data = in.readAllBytes();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

    }

    public void writeDatabase(byte[] data) {
        try {
            FileOutputStream out = new FileOutputStream(this.filename);
            int arrIndex = 0;
            byte[] output = new byte[data.length / 2];
            for (int i = 0; i < data.length / 2; i++) {
                arrIndex = i << 1;
                output[i] = (byte) ((data[arrIndex] << 4) + data[arrIndex + 1]);

            }
            out.write(output);
            out.flush();
            if (data.length % 2 == 1) {
                out.write((data[data.length - 1] << 4));
                out.flush();
            }
            out.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public int readfromDatabase(int index) {
        if (!loaded) {
            this.load();
        }
        if (index % 2 == 0) { // vorderer Teil des bytes
            return (data[index / 2] >>> 4) & 0xF;
        } else { // hintere Teil des bytes
            return data[index / 2] & 0xF;
        }


    }
}
