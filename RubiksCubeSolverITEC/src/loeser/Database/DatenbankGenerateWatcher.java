package loeser.Database;

import representation.Zuege;

public class DatenbankGenerateWatcher implements Runnable {

    @Override
    public void run() {
        int totalSec = 0;
        while(true) {
            try {
                Thread.sleep(1000);
                totalSec++;
                System.out.print("\rMessage after " + totalSec + "s:\tProgess:\t" + (88179840 - zeroes()) + "/" + 88179840 + "\tLetzte Node Züge:\t" + Zuege.lookupZugseq(GenerateCornerDatabase.getStackTop()));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private int zeroes(){
        byte[] file = GenerateCornerDatabase.getBytes();
        int count = 0;
        for(int i = 0; i < file.length; i++){
            if(((file[i / 2] >>> 4) & 0xF) == 0xF){
                count++;
            }
            if((file[i / 2] & 0xF) == 0xF){
                count++;
            }
        }
        return count;
    }


}
