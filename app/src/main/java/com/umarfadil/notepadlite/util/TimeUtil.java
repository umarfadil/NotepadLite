package com.umarfadil.notepadlite.util;

import android.text.format.DateUtils;

import java.util.Date;

/**
 * Created by alex on 10/02/17.
 */

public class TimeUtil {
    public static long getUnix() {
        return new Date().getTime();
    }

    public static String unixToTimeAgo(String unix) {
        CharSequence timeAgo = DateUtils.getRelativeTimeSpanString(
                Long.parseLong(unix),
                System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS);
        return timeAgo.toString();
    }
}
