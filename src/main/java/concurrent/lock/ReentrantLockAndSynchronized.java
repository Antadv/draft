package concurrent.lock;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

/**
 * ReentrantLock VS synchronized
 * @author LBG - 2017/9/8 0008
 */
public class ReentrantLockAndSynchronized {

    // default fairness policy
    private final ReentrantLock lock = new ReentrantLock();
    private List<String> list = new ArrayList<>();

    public void writeWithReentrantLock() {
        lock.lock();
        try {
            list.add("ReentrantLock test");
        } finally {
            lock.unlock();
        }
    }

    public void writeWithSynchronized() {
        synchronized (this) {
            list.add("synchronized test");
        }
    }

    public static void writeStaticWithSynchronized() {
        synchronized (ReentrantLockAndSynchronized.class) {
            // do sth...
        }
    }
}
