import com.github.benmanes.caffeine.cache.*;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.junit.Test;

import java.lang.reflect.Parameter;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;

/**
 * @author wejam
 * @description
 * @date 2021/11/5 上午11:15
 */
@Slf4j
public class CaffeineTest
{

    ScheduledExecutorService scheduled = Executors.newSingleThreadScheduledExecutor();
    Cache<String, String> ca = Caffeine.newBuilder()
            .expireAfterWrite(1, TimeUnit.SECONDS)
            .scheduler(Scheduler.forScheduledExecutorService(scheduled))
            .removalListener((RemovalListener<String, String>) (o1, o2, removalCause) -> {
                if(RemovalCause.EXPIRED == removalCause){
                    System.out.println("key {} value {} has bean removed {}");
                    log.error("key {} value {} has bean removed {}", o1, o2, removalCause);
                }
            }).build();

    @Test
    public void caffeineTest() throws InterruptedException {
        ca.put("xxx","yyy");
        ca.put("xxx","TTT");
        TimeUnit.SECONDS.sleep(5);
        System.out.println(ca.getIfPresent("xxx"));
    }

}
