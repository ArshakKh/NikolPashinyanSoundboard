package com.example.hello.appnav.adapterDb;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;


@Database(entities = GameInfo.class, version = 2, exportSchema = false)
public abstract class GameDb extends RoomDatabase {

    public abstract GameDao gameDao();

    private static GameDb instance;

    public static synchronized GameDb getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), GameDb.class, "gamer.db")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
