package com.somelogs.distribution.uid;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * SnowFlake 分布式 Id 生成算法实现，参考：
 * https://www.cnblogs.com/relucent/p/4955340.html
 * https://juejin.im/post/5a7f9176f265da4e721c73a8
 * https://segmentfault.com/a/1190000011282426
 *
 * @author LBG - 2019/5/5
 */
public class SnowflakeIdWorker {

    // ==============================Fields===========================================
    /** 开始时间截 (2015-01-01) */
    private final long twepoch = 1420041600000L;

    /** 机器id所占的位数 */
    private final long workerIdBits = 5L;

    /** 数据标识id所占的位数 */
    private final long dataCenterIdBits = 5L;

    /** 支持的最大机器id，结果是31 (这个移位算法可以很快的计算出几位二进制数所能表示的最大十进制数) */
    //private final long maxWorkerId = -1L ^ (-1L << workerIdBits);
    private final long maxWorkerId = ~(-1L << workerIdBits);

    /** 支持的最大数据标识id，结果是31 */
    //private final long maxDataCenterId = -1L ^ (-1L << dataCenterIdBits);
    private final long maxDataCenterId = ~(-1L << dataCenterIdBits);

    /** 序列在id中占的位数 */
    private final long sequenceBits = 12L;

    /** 机器ID向左移12位 */
    private final long workerIdShift = sequenceBits;

    /** 数据标识id向左移17位(12+5) */
    private final long dataCenterIdShift = sequenceBits + workerIdBits;

    /** 时间截向左移22位(5+5+12) */
    private final long timestampLeftShift = sequenceBits + workerIdBits + dataCenterIdBits;

    /** 生成序列的掩码，这里为4095 (0b111111111111=0xfff=4095) */
    //private final long sequenceMask = -1L ^ (-1L << sequenceBits);
    private final long sequenceMask = ~(-1L << sequenceBits);

    /** 工作机器ID(0~31) */
    private long workerId;

    /** 数据中心ID(0~31) */
    private long dataCenterId;

    /** 毫秒内序列(0~4095, 2^12) */
    private long sequence = 0L;

    /** 上次生成ID的时间截 */
    private long lastTimestamp = -1L;

    private byte sequenceOffset;

    //==============================Constructors=====================================
    /**
     * 构造函数
     * @param workerId 工作ID (0~31)
     * @param dataCenterId 数据中心ID (0~31)
     */
    public SnowflakeIdWorker(long workerId, long dataCenterId) {
        if (workerId > maxWorkerId || workerId < 0) {
            throw new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0", maxWorkerId));
        }
        if (dataCenterId > maxDataCenterId || dataCenterId < 0) {
            throw new IllegalArgumentException(String.format("data center Id can't be greater than %d or less than 0", maxDataCenterId));
        }
        this.workerId = workerId;
        this.dataCenterId = dataCenterId;
    }

    // ==============================Methods==========================================
    /**
     * 获得下一个ID (该方法是线程安全的)
     * @return SnowflakeId
     */
    public synchronized long nextId() {
        long timestamp = timeGen();

        //如果当前时间小于上一次ID生成的时间戳，说明系统时钟回退过这个时候应当抛出异常
        if (timestamp < lastTimestamp) {
            throw new RuntimeException(
                    String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds", lastTimestamp - timestamp));
        }

        //如果是同一时间生成的，则进行毫秒内序列
        if (lastTimestamp == timestamp) {
            sequence = (sequence + 1) & sequenceMask;
            //毫秒内序列溢出
            if (sequence == 0) {
                //阻塞到下一个毫秒,获得新的时间戳
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            // 时间戳改变，毫秒内序列重置
            //sequence = 0L;
            vibrateSequenceOffset();
            sequence = sequenceOffset;
        }

        //上次生成ID的时间截
        lastTimestamp = timestamp;

        //移位并通过或运算拼到一起组成64位的ID
        return ((timestamp - twepoch) << timestampLeftShift) //
                | (dataCenterId << dataCenterIdShift) //
                | (workerId << workerIdShift) //
                | sequence;
    }

    /**
     * 阻塞到下一个毫秒，直到获得新的时间戳
     * @param lastTimestamp 上次生成ID的时间截
     * @return 当前时间戳
     */
    private long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    /**
     * 这里是解决并发量不高的时候，id 都是偶数问题
     * From Sharding Sphere
     */
    private void vibrateSequenceOffset() {
        sequenceOffset = (byte) (~sequenceOffset & 1);
    }

    /**
     * 返回以毫秒为单位的当前时间
     * @return 当前时间(毫秒)
     */
    private long timeGen() {
        return System.currentTimeMillis();
    }

    //==============================Test=============================================
    /** 测试 */
    public static void main(String[] args) throws Exception {
        // 1位 - 34位时间 - 16位机器id - 13位序列
        int unsigned = 1;
        int seqBits = 8;
        int workerBits = 17;
        int timeBits = 38;
        int workerIdShift = seqBits;
        int timestampShift = workerBits + seqBits;

        System.out.println(~(-1L << workerBits));

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date epochDate = dateFormat.parse("2019-03-20");
        Date endDate = dateFormat.parse("2020-02-28");
        Date nextDate = dateFormat.parse("2028-08-28");

        System.out.println(epochDate.getTime());

        long epochSeconds = TimeUnit.MILLISECONDS.toSeconds(epochDate.getTime());
        long endSeconds = TimeUnit.MILLISECONDS.toSeconds(endDate.getTime());
        long nextSeconds = TimeUnit.MILLISECONDS.toSeconds(nextDate.getTime());
        long deltaSeconds = endSeconds - epochSeconds;
        long nextDeltaSeconds = nextSeconds - epochSeconds;
        long result = (deltaSeconds << timestampShift) | (556 << workerIdShift) | 1;
        long result2 = (nextDeltaSeconds << timestampShift) | (556 << workerIdShift) | 1;
        System.out.println(result + " " + String.valueOf(result).length());
        System.out.println(result2 + " " + String.valueOf(result2).length());
    }
}
