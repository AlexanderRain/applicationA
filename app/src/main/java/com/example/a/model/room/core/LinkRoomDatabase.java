package com.example.a.room.core;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.a.model.Link;

@Database(entities = {Link.class}, version = 1, exportSchema = false)
public abstract class LinkRoomDatabase extends RoomDatabase {
    public abstract LinkDao linkDao();
    private static LinkRoomDatabase INSTANCE;
    public static LinkRoomDatabase getDatabase(final Context context) {
        if(INSTANCE == null) {
            synchronized(LinkRoomDatabase.class) {}
            if(INSTANCE == null) {
                INSTANCE = Room
                        .databaseBuilder(context.getApplicationContext(), LinkRoomDatabase.class, "link_database")
                        .build();
            }
        }
        return INSTANCE;
    }
}
