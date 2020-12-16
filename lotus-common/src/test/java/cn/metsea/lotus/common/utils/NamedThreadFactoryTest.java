package cn.metsea.lotus.common.utils;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import org.junit.Test;

public class NamedThreadFactoryTest {

    @Test
    public void testThreadName() {
        NamedThreadFactory factory = new NamedThreadFactory("Heartbeat", 3);
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(3, factory);
        Runnable hello = new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + " : " + "hello");
            }
        };
        for (int i = 0; i < 10; i++) {
            executor.submit(hello);
        }
    }

}
