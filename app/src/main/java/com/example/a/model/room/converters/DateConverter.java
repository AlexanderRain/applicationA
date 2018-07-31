package com.example.a.model.room.core.converters;

import android.arch.persistence.room.TypeConverter;

import java.util.Date;

public class DateConverter {
    @TypeConverter
    public Date dateFromTimestamp(Long date) {
        return date == null ? null : new Date(date);
    }
    @TypeConverter
    public Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }
}
