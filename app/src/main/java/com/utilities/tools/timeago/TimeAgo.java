package com.utilities.tools.timeago;

import android.content.Context;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class TimeAgo {
    private static final String KEY_YEAR = "YEAR";
    private static final String KEY_MONTH = "MONTH";
    private static final String KEY_WEEK = "WEEK";
    private static final String KEY_DAY = "DAY";
    private static final String KEY_HOUR = "HOUR";
    private static final String KEY_MINUTE = "MINUTE";
    private static final String KEY_JUST_NOW = "JUST_NOW";

    public static String toRelative(Context context, long duration, int maxLevel) {
        Map<String, Long> times = new HashMap<>();
        times.put(KEY_YEAR, TimeUnit.DAYS.toMillis(365));
        times.put(KEY_MONTH, TimeUnit.DAYS.toMillis(30));
        times.put(KEY_WEEK, TimeUnit.DAYS.toMillis(7));
        times.put(KEY_DAY, TimeUnit.DAYS.toMillis(1));
        times.put(KEY_HOUR, TimeUnit.HOURS.toMillis(1));
        times.put(KEY_MINUTE, TimeUnit.MINUTES.toMillis(1));
        times.put(KEY_JUST_NOW, TimeUnit.SECONDS.toMillis(60));

        StringBuilder res = new StringBuilder();
        int level = 0;

        for (Map.Entry<String, Long> time : times.entrySet()) {
            double timeDelta1 = (double) duration / time.getValue();
            long timeDelta2 = duration / time.getValue();
            long timeDelta3 = (long) Math.ceil(timeDelta1);
            if (timeDelta2 > 0) {
                if (time.getValue() == TimeUnit.DAYS.toMillis(730) ||
                        time.getValue() == TimeUnit.DAYS.toMillis(60) ||
                        time.getValue() == TimeUnit.DAYS.toMillis(14) ||
                        time.getValue() == TimeUnit.DAYS.toMillis(2) ||
                        time.getValue() == TimeUnit.HOURS.toMillis(2) ||
                        time.getValue() == TimeUnit.MINUTES.toMillis(2)) {
                    long timeTemp = timeDelta3 * 2;
                    res.append(timeTemp)
                            .append(" ")
                            .append(time.getKey())
                            .append(timeTemp > 1 ? "" : "")
                            .append(", ");
                    duration -= time.getValue() * timeTemp;
                } else {
                    res.append(timeDelta3)
                            .append(" ")
                            .append(time.getKey())
                            .append(timeDelta3 > 1 ? "" : "")
                            .append(", ");
                    duration -= time.getValue() * timeDelta3;
                }
                level++;
            }
            if (level == maxLevel) {
                break;
            }
        }
        if ("".equals(res.toString())) {
            return context.getString(R.string.just_now);
        } else {
            res.setLength(res.length() - 2);
            res.append(context.getString(R.string.ago));
            return res.toString();
        }
    }

//    public static String toRelative(long duration) {
//        return toRelative(duration, times.size());
//    }
//
//    public static String toRelative(Date start, Date end) {
//        assert start.after(end);
//        return toRelative(end.getTime() - start.getTime());
//    }
//
//    public static String toRelative(Date start, Date end, int level) {
//        assert start.after(end);
//        return toRelative(end.getTime() - start.getTime(), level);
//    }
//
//    public static String toRelative(Long startMilitimes, Long endMilitimes, int level) {
//
//        return toRelative(endMilitimes - startMilitimes, level);
//    }
}
