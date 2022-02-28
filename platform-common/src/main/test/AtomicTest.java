import lombok.Data;
import org.junit.Test;

import java.util.concurrent.atomic.*;
import java.util.function.LongBinaryOperator;

/**
 * @author wejam
 * @description
 * @date 2021/10/26 下午5:15
 */
public class AtomicTest {

    @Test
    public void atomicBoolean(){
        AtomicBoolean b = new AtomicBoolean(Boolean.TRUE);
        System.out.println(b);
    }

    @Test
    public void atomicLongArray(){
        AtomicLongArray l = new AtomicLongArray(10);
        l.addAndGet(1,2);
        System.out.println(l.toString());
    }

    public void atomicFieldUpdater(){
        AtomicIntegerFieldUpdater<Integer> a = new AtomicIntegerFieldUpdater<Integer>() {
            @Override
            public boolean compareAndSet(Integer obj, int expect, int update) {
                return false;
            }

            @Override
            public boolean weakCompareAndSet(Integer obj, int expect, int update) {
                return false;
            }

            @Override
            public void set(Integer obj, int newValue) {

            }

            @Override
            public void lazySet(Integer obj, int newValue) {

            }

            @Override
            public int get(Integer obj) {
                return 0;
            }
        };
    }

    @Test
    public void longAdder(){
        LongAdder l = new LongAdder();
        l.add(1);
        System.out.println(l.sum());
        l.add(2);
        System.out.println(l.doubleValue());
        l.add(3);
        System.out.println(l.floatValue());
    }

    @Test
    public void longAccumulator(){
        LongAccumulator l = new LongAccumulator((left, right) -> left*right,1);
        l.accumulate(2);
        System.out.println(l.get());
        l.accumulate(2);
        System.out.println(l.get());
        l.accumulate(2);
        System.out.println(l.get());
    }

    @Test
    public void atomicReference(){

        A a = new A();
        a.setS(1);
        a.setT(2);
        AtomicReference<A> aa = new AtomicReference<>();
        aa.set(a);
        System.out.println(aa.toString());
    }

    @Data
    private static class A{
        private Integer s;
        private Integer t;
    }

}
