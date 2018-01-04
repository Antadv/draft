package com.somelogs.concurrent.cache;

import java.util.concurrent.*;

/**
 * 使用 FutureTask 构建高效可伸缩的结果缓存
 * Created by lbg on 2017/8/18
 */
public class Memoizer2<A, V> implements Computable<A, V> {

    private final ConcurrentHashMap<A, Future<V>> cache = new ConcurrentHashMap<>();
    private final Computable<A, V> c;

    public Memoizer2(Computable<A, V> c) {
        this.c = c;
    }

    @Override
    public V compute(final A arg) {
        Future<V> future = cache.get(arg);
        if (future == null) {
            Callable<V> eval = new Callable<V>() {
                @Override
                public V call() throws Exception {
                    return c.compute(arg);
                }
            };
            FutureTask<V> task = new FutureTask<>(eval);

            // putIfAbsent return the previous url associated with the specified key
            // so future is always null here
            future = cache.putIfAbsent(arg, task);
            if (future == null) {
                future = task;
                task.run();
            }
        }
        try {
            return future.get();
        } catch (InterruptedException e) {
            cache.remove(arg);
            throw new RuntimeException(e.getCause());
        } catch (ExecutionException e) {
            throw new RuntimeException(e.getCause());
        }
    }

    public static void main(String[] args) {
        Computable<String, Integer> computable = new ExpensiveFunction();
        Memoizer2<String, Integer> memoizer = new Memoizer2<>(computable);
        Integer val = memoizer.compute("5");
        System.out.println(val);

        System.out.println(Runtime.getRuntime().availableProcessors());
    }
}
