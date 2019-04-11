package com.example.rakib.storedata_using_room;

//Todo: Create a database Class for database implementation

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Movies.class}, version = 1, exportSchema = false)
public abstract class MovieDatabase extends RoomDatabase {
    public abstract DaoAccess daoAccess();
}
