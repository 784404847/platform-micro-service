package com.micro.common.util.concurrent;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

/**
 * @Author: wejam
 * @Description 线程池工具类
 * @Date: 2020/8/27 6:01 下午
 */
@Slf4j
public class ThreadPoolUtil {

    private ThreadPoolUtil() {
    }

    /**CPU数*/
    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();

    /**
     * 通用线程池工具类
     * @return
     */
    public static ThreadPoolExecutor getThreadPool(int corePoolSize, int maxPoolSize, String poolName){

        if(corePoolSize<=0) {
            corePoolSize = CPU_COUNT + 1;
        }

        if(maxPoolSize<=0) {
            maxPoolSize = CPU_COUNT * 2 + 1;
        }

        ThreadPoolExecutor threadPoolExecutor = new  ThreadPoolExecutor(
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


    public static ThreadPoolExecutor getCommonThreadPool(){
        return getThreadPool(-1,-1,"common");
    }

    /**
     * 线程命名
     * @param poolName
     * @return
     */
    private static ThreadFactory getThreadFactory(String poolName) {
        return new ThreadFactoryBuilder()
                .setNameFormat(poolName+"-thread-%d").build();
    }


    /**
     * 有界队列为 Integer.MAX_VALUE
     * @return
     */
    private static BlockingQueue<Runnable> getQueue() {
        return  new LinkedBlockingQueue<>();
    }

    public static void main(String[] args) {
        ThreadPoolExecutor abv = getCommonThreadPool();

        for(int i=0; i<1000;i++){
            int finalI = i;
            abv.execute(() -> System.out.println(finalI));
        }

    }

}
