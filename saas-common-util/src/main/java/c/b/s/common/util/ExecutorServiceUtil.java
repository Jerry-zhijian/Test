package c.b.s.common.util;

import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * Created by guiqingqing on 2018/8/29.
 */
public class ExecutorServiceUtil {
    public static ListeningExecutorService fixedExecutorService;
    public static ListeningExecutorService cachedExecutorService;

    static {
        ThreadFactory fixedThreadFactory = new ThreadFactoryBuilder().setNameFormat("ZhiNiuFixedThread-%d").setDaemon(true).build();
        ThreadFactory cachedThreadFactory = new ThreadFactoryBuilder().setNameFormat("ZhiNiuCachedThread-%d").setDaemon(true).build();
        fixedExecutorService = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() *4, fixedThreadFactory));
        cachedExecutorService = MoreExecutors.listeningDecorator(Executors.newCachedThreadPool(cachedThreadFactory));
    }

    /**
     * 根据传进来的线程数, 任务数, 计算每个线程应该处理多少个任务（平均分）
     * @param threadCount
     * @param taskCount
     * @return
     */
    public static List<Integer> calcTaskCountPerThread(int threadCount, int taskCount) {
        List<Integer> result = new ArrayList();
        int averageCount = taskCount / threadCount; // 每个线程至少处理的任务数
        int mCount = taskCount % threadCount; // 除去每个线程至少处理的任务数, 剩下多余的任务数
        for (int i = 0; i < threadCount; i++) {
            // 如果剩下多余的任务数没有分配完成, 则线程最终处理的任务数+1
            int finalCount = mCount-- > 0 ? averageCount + 1 : averageCount;
            result.add(finalCount);
        }
        return result;
    }
}