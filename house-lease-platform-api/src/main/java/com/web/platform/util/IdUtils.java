package com.web.platform.util;

/**
 * @author li
 * &#064;date  2023/6/2
 * &#064;description  时间戳41位，序号22位
 */
public class IdUtils {
    private static IdUtils idUtils;

    static {
        idUtils = new IdUtils();
    }

    // 时间戳, 代码编写时间
    private static final long INIT_EPOCH = 1685694868526L;

    //上一次id生成时间
    private long lastTimeMillis = -1L;

    //
    private static final long SEQUENCE_BITS = 9L;

    private static final long SEQUENCE_MASK = ~(-1L << SEQUENCE_BITS);

    private long sequence;

    private static final long TIMESTAMP_SHIFT = SEQUENCE_BITS;

    private IdUtils() {

    }

    public static Long createId() {
        return idUtils.nextId();
    }

    public synchronized Long nextId() {
        long currentTimeMillis = System.currentTimeMillis();
        if (currentTimeMillis < lastTimeMillis) {
            throw new RuntimeException("服务器时钟回拨");
        }
        if (currentTimeMillis == lastTimeMillis) {
            sequence = (sequence + 1) & SEQUENCE_MASK;
            if (sequence == 0) {
                currentTimeMillis = getNextMillis(lastTimeMillis);
            }
        } else {
            sequence = 0;
        }
        lastTimeMillis = currentTimeMillis;
        return ((currentTimeMillis - INIT_EPOCH) << TIMESTAMP_SHIFT) | sequence;
    }

    /**
     * @description 用于在当前毫秒序号用光的情况下，获取下一毫秒
     *
     */
    private long getNextMillis(long lastTimeMillis) {
        long currentTimeMillis = System.currentTimeMillis();
        while (currentTimeMillis <= lastTimeMillis) {
            currentTimeMillis = System.currentTimeMillis();
        }
        return currentTimeMillis;
    }


}
