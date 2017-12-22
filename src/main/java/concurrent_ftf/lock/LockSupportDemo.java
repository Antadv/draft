package concurrent_ftf.lock;

import org.junit.Test;

import java.util.Calendar;
import java.util.concurrent.locks.LockSupport;

/**
 * LockSupport demo
 *
 * @author LBG - 2017/12/8 0008
 */
public class LockSupportDemo {

    @Test
    public void park() {
        System.out.println("before park...");
        LockSupport.park();
        System.out.println("after park...");
    }

    @Test
    public void unparkBeforePark() throws Exception {
        System.out.println("before park...");
        LockSupport.unpark(Thread.currentThread());
        LockSupport.park();
        System.out.println("after park...");
    }

    @Test
    public void multiUnparkAndPark() throws Exception {
        System.out.println("unpark one...");
        LockSupport.unpark(Thread.currentThread());
        System.out.println("unpark two...");
        LockSupport.unpark(Thread.currentThread());
        LockSupport.park();
        System.out.println("park one...");
        LockSupport.park();
        System.out.println("park two...");
    }

    /**
     * 有三种方式可以让 park() 返回
     *
     * 1. 别的线程调用 unpark(),参数为当前线程
     * 2. 当前线程被中断
     * 3. 虚假唤醒
     */
    @Test
    public void wakeUpAfterUnpark() throws Exception {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("before park...");
                LockSupport.park();
                System.out.println("after park...");
            }
        });
        thread.start();
        Thread.sleep(1000L);

        //System.out.println("main thread unpark");
        //LockSupport.unpark(thread);

        System.out.println("main thread interrupter child thread");
        thread.interrupt();
    }

    @Test
    public void wakeUpAfterInterrupter() throws Exception {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("interrupter...");
                while (!Thread.currentThread().isInterrupted()) {
                    LockSupport.park();
                }
                System.out.println("after park...");
            }
        });
        thread.start();
        Thread.sleep(1000L);

        System.out.println("main thread interrupter child thread");
        //thread.interrupt();
        LockSupport.unpark(thread);
    }

    @Test
    public void wakeUpNano() throws Exception {
        System.out.println("before park...");
        LockSupport.parkNanos(3000000000L);
        System.out.println("time is up");
    }

    @Test
    public void wakeUpUntilTime() throws Exception {
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.SECOND, 3);
        System.out.println("before park...");
        LockSupport.parkUntil(instance.getTimeInMillis());
        System.out.println("time is up");
    }

    /**
     * dump 线程
     *
     * 1. jps 找到程序pid
     * 2. jstack pid 显示线程dump
     */
    @Test
    public void parkWithBlocker() throws Exception {
        System.out.println("before park...");
        LockSupport.park(this);
        System.out.println("after park...");
    }
}
