package com.abed.leitnerflashcards.data;

import android.arch.persistence.room.TypeConverter;

import java.sql.Date;
import java.util.Calendar;

public class CalendarConverter {

    @TypeConverter
    public Calendar fromTimeStamp(long date) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(date);
        return cal;
    }

    @TypeConverter
    public long toTimeStamp(Calendar cal) {
        return cal == null ? 0 : cal.getTimeInMillis();
    }
}
