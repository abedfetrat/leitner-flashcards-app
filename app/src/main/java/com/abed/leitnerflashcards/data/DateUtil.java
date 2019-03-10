package com.abed.leitnerflashcards.data;

import java.util.Calendar;

public abstract class DateUtil {
    public static Calendar getCalendarWithoutTime() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal;
    }

    public static Calendar getCalendarPlusDays(int days) {
        Calendar cal = getCalendarWithoutTime();
        cal.add(Calendar.DATE, days);
        return cal;
    }
}
