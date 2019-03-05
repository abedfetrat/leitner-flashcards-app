package com.abed.leitnerflashcards.data;

import android.arch.persistence.room.TypeConverter;

import java.time.LocalDate;

public class LocalDateConverter {

    @TypeConverter
    public LocalDate fromString(String date) {
        return LocalDate.parse(date);
    }

    @TypeConverter
    public String toString(LocalDate date) {
        return date.toString();
    }
}
