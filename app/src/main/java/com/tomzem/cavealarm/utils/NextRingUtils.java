package com.tomzem.cavealarm.utils;

/**
 * @author Tomze
 * @time 2019年03月12日 21:08
 * @desc 获取下次响铃时间
 */
public class NextRingUtils {

    private static int[] currentHourMin;
    private static int[] selectHourMin;

    private NextRingUtils(int[] currentHourMin, int[] selectHourMin) {
        NextRingUtils.currentHourMin = currentHourMin;
        NextRingUtils.selectHourMin = selectHourMin;
    }

    public static NextRingUtils getNextRingUtils(int[] currentHourMin, int[] selectHourMin) {
        return new NextRingUtils(currentHourMin, selectHourMin);
    }

    /**
     * 按照周期 当天或第二天必响铃 计算方式
     */
    public long ringInTodayOrNextDay() {
        long currentTime = TimeUtils.getCurrentTime();
        long ringTime;
        int poor = isRingInToday();
        if (poor <= 0) {
            //选择的时间已经过了 获取第二天的这个时候的时间戳
            ringTime = getAssignDayRingTime(TimeUtils.getNextDay());
        } else {
            // 今天会响铃， 返回今天的响铃时间 时间戳
            ringTime = currentTime + poor * 1000 * 60;
        }
        return ringTime;
    }

    /**
     * 判断当天是否需要响铃
     *
     * @return 大于0 响  小于等于 不会响
     */
    private int isRingInToday() {
        return selectHourMin[0] * 60 - currentHourMin[0] * 60
                + selectHourMin[1] - currentHourMin[1];
    }

    public boolean isRingToday() {
        return isRingInToday() > 0;
    }

    /**
     * 获取在指定日期响铃时间的时间戳
     *
     * @return
     */
    public long getAssignDayRingTime(String assignDay) {
        StringBuffer time = new StringBuffer();
        // time 明日响铃时间点
        time.append(assignDay).append((selectHourMin[0]<10?" 0":" ") +selectHourMin[0] + ":" + selectHourMin[1]);
        // 将明日响铃时间转换为时间戳
        return TimeUtils.dateToStamp(time.toString());
    }
}
