package loeser.Database;

import representation.Zuege;

public class DatenbankGenerateWatcher implements Runnable {

    @Override
    public void run() {
        double totalSec = 0;
        while(true) {
            try {
                Thread.sleep(100);
                totalSec += 0.1;
                System.out.print("\rMessage after " + Math.round(totalSec * 10) / 10d + "s:\tProgess:\t" + (88179840 - zeroes()) + "/" + 88179840 + "\tLetzte Node Züge:\t" + Zuege.lookupZugseq(GenerateCornerDatabase.getStackTop().getMoves()));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private int zeroes(){
        byte[] file = GenerateCornerDatabase.getBytes();
        int count = 0;
        for(int i = 0; i < file.length; i++){
            if(((file[i / 2] >>> 4) & 0xF) == 0){
                count++;
            }
            if((file[i / 2] & 0xF) == 0){
                count++;
            }
        }
        return count;
    }


}
