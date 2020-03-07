package chapter_13;

import java.util.concurrent.atomic.AtomicInteger;
//代码清单13-4 Atomic的原子自增运算
public class AtomicTest {
    static AtomicInteger race = new AtomicInteger(0);

    static void increase() {
        race.incrementAndGet();
    }

    static int THREADS_COUNT = 20;

    public static void main(String[] args) {
        Thread[] threads = new Thread[THREADS_COUNT];
        for(int i = 0; i < THREADS_COUNT; i++) {
            threads[i] = new Thread(new Runnable() {
                public void run() {
                    for (int j = 0; j < 10000; j++) {
                        race.incrementAndGet();
                    }
                }
            });
            threads[i].start();
        }

        // 等待所有累加线程都结束
        // 在IntelliJ IDEA执行简单的main方法，调用Thread.activeCount()方法输出的数量，非预期的1（main方法本身），用命令行或者 Debug 模式就没有问题
        // 相关解释：https://www.jianshu.com/p/5b843d82a43f
        System.out.println(Thread.activeCount());
        Thread.currentThread().getThreadGroup().list();
        while(Thread.activeCount() > 1) {
            // main方法线程，使用 Thread.yield(); 让出cpu时间
            // yield 方法可以很好的控制多线程，如执行某项复杂的任务时，如果担心占用资源过多，可以在完成某个重要的工作后使用 yield 方法让掉当前 CPU
            // 的调度权，等下次获取到再继续执行，这样不但能完成自己的重要工作，也能给其他线程一些运行的机会，避免一个线程长时间占有 CPU 资源
            Thread.yield();
        }
        System.out.println(race);
    }
}
