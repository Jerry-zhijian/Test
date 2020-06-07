package c.b.s.common.util.lineup;

import java.util.concurrent.Semaphore;

/**
 * Created by guiqingqing on 2018/9/21.
 */
final class LineUpUtil {
    private static final Semaphore oneTaskPerMachine = new Semaphore(4, true);

    static void otpmAcquire() throws InterruptedException {
        oneTaskPerMachine.acquire();
    }

    static void otpmRelease() {
        oneTaskPerMachine.release();
    }
}