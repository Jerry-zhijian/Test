package c.b.s.common.util.lineup;

import java.io.Closeable;
import java.io.IOException;

/**
 * Created by guiqingqing on 2018/9/21.
 */
public class OneTaskPerMachine implements Closeable {
    public void acquire() throws InterruptedException {
        LineUpUtil.otpmAcquire();
    }

    @Override
    public void close() throws IOException {
        LineUpUtil.otpmRelease();
    }
}