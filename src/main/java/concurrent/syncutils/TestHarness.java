package concurrent.syncutils;

import lombok.Data;

import java.util.UUID;
import java.util.concurrent.CountDownLatch;

/**
 * 使用 CountDownLatch 来启动和停止线程
 * (并发编程实战书中例子)
 * Created by liubingguang on 2017/8/16.
 */
public class TestHarness {

    public static void main(String[] args) throws InterruptedException {
        letsPrepare(10);
    }

    private static void letsPrepare(int gamerCount)
            throws InterruptedException {

        final CountDownLatch startGate = new CountDownLatch(1);
        final CountDownLatch endGate = new CountDownLatch(gamerCount);

        for (int i =0; i < gamerCount; i++) {
            Thread t = new Thread() {
                @Override
                public void run() {
                    try {
                        Gamer gamer = new Gamer();
                        System.out.println("玩家 " + gamer.getUserCode() + " 进入房间");
                        startGate.await();
                        gamer.prepare();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        endGate.countDown();
                    }
                }
            };
            t.start();
            Thread.sleep(500);
        }

        startGate.countDown();
        endGate.await();
        System.out.println("所有玩家已准备好，准备发车了哦~");
    }

    @Data
    static class Gamer {
        private String userCode;

        public Gamer() {
            this.userCode = UUID.randomUUID().toString();
        }

        public void prepare() {
            System.out.println("玩家 " + userCode + " 已准备");
        }
    }
}
