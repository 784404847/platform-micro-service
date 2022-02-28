package com.mservice.common.util.concurrent;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author: wejam
 * @Description 线程池工具类
 * @Date: 2020/8/27 6:01 下午
 */
@Slf4j
public class ThreadPoolUtil {

    private ThreadPoolUtil() {
    }

    /**
     * CPU数
     */
    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();

    /**
     * 通用线程池工具类
     * 有界队列
     * 队列满则由回调线程去执行
     *
     * @return
     */
    public static ThreadPoolExecutor getThreadPool(int corePoolSize, int maxPoolSize, String poolName) {

        if (corePoolSize <= 0) {
            corePoolSize = CPU_COUNT + 1;
        }

        if (maxPoolSize <= 0) {
            maxPoolSize = CPU_COUNT * 2 + 1;
        }

        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                corePoolSize,
                maxPoolSize,
                /*空闲时间设置60s*/
                60L,
                TimeUnit.SECONDS,
                /*有界队列*/
                getQueue(),
                getThreadFactory(poolName),
                /*回调策略为让回调线程去执行*/
                new ThreadPoolExecutor.CallerRunsPolicy()
        );

        /*设置核心线程池超时的时候也关闭线程*/
        threadPoolExecutor.allowCoreThreadTimeOut(true);

        return threadPoolExecutor;
    }


    public static ThreadPoolExecutor getCommonThreadPool() {
        return getThreadPool(-1, -1, "common");
    }

    public static ExecutorService getSinglePool() {
        return getThreadPool(1, 1, "single");
    }

    /**
     * 线程命名
     *
     * @param poolName
     * @return
     */
    private static ThreadFactory getThreadFactory(String poolName) {
        return new TypicalThreadFactory(poolName + "-thread-%d");
    }


    /**
     * 有界队列为 Integer.MAX_VALUE
     *
     * @return
     */
    private static BlockingQueue<Runnable> getQueue() {
        return new LinkedBlockingQueue<>();
    }

    private static class TypicalThreadFactory implements ThreadFactory {

        private final ThreadGroup group;
        private final AtomicInteger threadNumber = new AtomicInteger(1);
        private final String namePrefix;

        public TypicalThreadFactory(String poolName) {
            SecurityManager s = System.getSecurityManager();
            this.group = (s != null) ? s.getThreadGroup() :
                    Thread.currentThread().getThreadGroup();
            this.namePrefix = "pl-" + poolName + "-t-";
        }

        @Override
        public Thread newThread(Runnable r) {
            Thread thread = new Thread(group, r, namePrefix + threadNumber.getAndIncrement(), 0);
            thread.setUncaughtExceptionHandler((t, e) -> {
                log.error(thread.getName(), e);
            });
            return thread;
        }
    }

}
