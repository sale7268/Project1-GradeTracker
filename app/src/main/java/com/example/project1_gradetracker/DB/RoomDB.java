package com.example.project1_gradetracker.DB;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {User.class, Course.class}, version = 2, exportSchema = false)
public abstract class RoomDB extends RoomDatabase {

    private static RoomDB database;
    private static String DB_Name = "User";

    public synchronized static RoomDB getInstance(Context context){
        // initialize database
        if (database == null) {
            database = Room.databaseBuilder(context.getApplicationContext(),
                    RoomDB.class,
                    DB_Name).allowMainThreadQueries().fallbackToDestructiveMigration().build();
        }
        return database;
    }

    // Create DAO
    public abstract UserDAO userDAO();
}
