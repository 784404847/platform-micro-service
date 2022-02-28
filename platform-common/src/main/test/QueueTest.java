import org.junit.Test;

import java.util.Spliterator;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @author wejam
 * @description
 * @date 2021/10/28 上午10:58
 */
public class QueueTest {

    @Test
    public void concurrentLinkedQueue(){

        ConcurrentLinkedQueue<Integer> queue = new ConcurrentLinkedQueue<>();
        queue.add(1);
        Spliterator<Integer> spliterator = queue.spliterator();
        queue.offer(1);
        System.out.println(queue.peek());
        System.out.println(queue.poll());
    }

}
