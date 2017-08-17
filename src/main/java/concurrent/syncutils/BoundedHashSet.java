package concurrent.syncutils;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Semaphore;

/**
 * 信号量实现有界阻塞容器
 * Created by liubingguang on 2017/8/17.
 */
public class BoundedHashSet<T> {

    private Set<T> set;
    private Semaphore semaphore;

    public BoundedHashSet(int bound) {
        set = Collections.synchronizedSet(new HashSet<T>());
        semaphore = new Semaphore(bound);
    }

    public boolean add(T o) throws InterruptedException {
        semaphore.acquire();
        boolean wasAdded = false;
        try {
            wasAdded = set.add(o);
            return wasAdded;
        } finally {
            if (!wasAdded) {
                semaphore.release();
            }
        }
    }

    public boolean remove(T o) throws InterruptedException {
        boolean wasRemoved = set.remove(o);
        if (wasRemoved) {
            semaphore.release();
        }
        return wasRemoved;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (T t : set) {
            builder.append(String.valueOf(t));
        }
        return builder.toString();
    }

    public static void main(String[] args) throws InterruptedException {
        final BoundedHashSet<String> set = new BoundedHashSet<>(5);
        for (int i = 0; i < 5; i++) {
            set.add("hello-" + i);
        }

        Thread addThread = new Thread() {
            @Override
            public void run() {
                try {
                    System.out.println("开始添加...");
                    set.add("hello-5");
                    System.out.println("添加完成...");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        Thread removeThread = new Thread() {
            @Override
            public void run() {
                try {
                    System.out.println("开始删除...");
                    set.remove("hello-0");
                    System.out.println("删除完成...");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        addThread.start();
        Thread.sleep(500);
        removeThread.start();
    }
}
