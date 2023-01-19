package loeser;

import representation.Wuerfel;

import java.util.Arrays;

public class TIDDFSSpawner {

    private final TIDDFS[] threadPool;
    private volatile int bestSolution[];

    private final int[] startPos;
    private final int[] zielPos;
    private final int[] zielMaske;
    private final int[] zuege;
    private final int debug;

    public TIDDFSSpawner(int[] startPos, int[] zielPos, int[] zielMaske, int[] zuege, int debug) {
        this.startPos = startPos;
        this.zielPos = zielPos;
        this.zielMaske = zielMaske;
        this.zuege = zuege;
        this.debug = debug;
        this.threadPool = new TIDDFS[zuege.length];
    }

    public int[] start() {
        // kekw
        if ((new Wuerfel(startPos)).isMaskSolved(zielPos, zielMaske)) {
            return new int[]{};
        }
        // spawn threads
        for (int i = 0; i < zuege.length; i++) {
            threadPool[i] = new TIDDFS(this, startPos, zuege[i], zielPos, zielMaske, zuege, debug);
            threadPool[i].start();
        }

        for (int i = 0; i < zuege.length; i++) {
            try {
                threadPool[i].join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        return bestSolution;
    }

    private void stopAllThread() {
        for (TIDDFS t : this.threadPool) {
            if (t != null) {
                t.stoppe();
            }
        }
    }

    public void updateSolution(int[] solution) {
        //if (solution.length < bestSolution.length) {
            bestSolution = Arrays.copyOf(solution, solution.length);
        //}
        stopAllThread();
    }

}
