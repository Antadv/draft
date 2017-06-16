package threadpool;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * http://www.infoq.com/cn/articles/executor-framework-thread-pool-task-execution-part-01
 * Created by liubingguang on 2017/5/2.
 */
public class TestClass {

    class CustomerTask implements Callable<String> {

        public String call() throws Exception {
            // 查询
            Thread.sleep(500);
            return "thread asyn task!!";
        }
    }

    @Test
    public void testExecutor() throws ExecutionException, InterruptedException {
        long startTime = System.currentTimeMillis();
        ExecutorService executor = Executors.newFixedThreadPool(5);
        List<Future<String>> resultList = new ArrayList<Future<String>>();
        for (int i = 0; i < 10; i++) {
            resultList.add(executor.submit(new CustomerTask()));
        }

        for (Future<String> result : resultList) {
            System.out.println(result.get());
        }
        System.out.println("total time " + (System.currentTimeMillis() - startTime));
    }
}
