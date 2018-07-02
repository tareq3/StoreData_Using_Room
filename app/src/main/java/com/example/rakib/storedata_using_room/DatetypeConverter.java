package com.example.rakib.storedata_using_room;

import android.arch.persistence.room.TypeConverter;
import android.util.Log;

import java.util.Date;

public class DatetypeConverter {
    @TypeConverter
    public static Date toDate(Long value){
        return value==null? null: new Date(value);
    }

    @TypeConverter
    public static Long toLong(Date value){
        return value==null? null : value.getTime();
    }
}
