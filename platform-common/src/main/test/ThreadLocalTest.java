import ch.qos.logback.core.util.TimeUtil;
import com.alibaba.ttl.TransmittableThreadLocal;
import com.alibaba.ttl.threadpool.TtlExecutors;
import com.mservice.common.util.concurrent.ThreadPoolUtil;
import lombok.SneakyThrows;
import org.apache.commons.lang3.ThreadUtils;
import org.junit.Test;

import java.util.concurrent.*;

/**
 * @author wejam
 * @description
 * @date 2021/10/27 下午3:25
 */
public class ThreadLocalTest {

    private ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);

    @Test
    public void inheritableThreadLocal(){

        InheritableThreadLocal<Integer> l = new InheritableThreadLocal<>();
        l.set(1);
        System.out.println("main :"+l.get());

        executor.execute(() -> System.out.println("son :"+l.get()));

        l.set(2);
        executor.execute(() -> System.out.println("son :"+l.get()));
    }

    @Test
    public void transmittableThreadLocal(){

        Executor ttlExecutor = TtlExecutors.getTtlExecutor(executor);
        TransmittableThreadLocal<Integer> ttl = new TransmittableThreadLocal<>();
        ttl.set(1);
        ttlExecutor.execute(() -> {
            System.out.println(ttl.get());
        });
        ttl.set(2);
        ttlExecutor.execute(() -> {
            System.out.println(ttl.get());
        });
        ttl.set(3);
        ttlExecutor.execute(() -> {
            System.out.println(ttl.get());
        });
    }


    private final ExecutorCompletionService<String> ecs = new ExecutorCompletionService<>(executor);
    @SneakyThrows
    @Test
    public void executorCompletionService(){

        for(int i=0;i<50;i++){
            int finalI = i;
//            ecs.submit(() -> System.out.println("i:"+finalI),"ecs:" + finalI);
            ecs.submit(() -> {
                if(finalI%2==0){
                    TimeUnit.SECONDS.sleep(2);
                }
                return "ecs:" + finalI;
            });
        }
        TimeUnit.SECONDS.sleep(1);
        for(int i=0;i<50;i++){
            try {
                System.out.println(ecs.take().get());
            } catch (InterruptedException | ExecutionException e) {
                System.out.println("error"+e.getMessage());
            }
        }


    }




}
