package com.example.project1_gradetracker.DB;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

/** RoomDB
 * This database holds three tables (User, Course, and Assignment)
 * Update the version (+1) any time you change anything on this page
 * every time the version updates, the database gets wiped clean
 * there is a way around this, I just don't know it ATM
 */


@Database(entities = {User.class, Course.class, Assignment.class}, version = 9, exportSchema = false)
@TypeConverters({ArrayListTypeConverter.class, ArrayListTypeConverterAssignment.class})
public abstract class RoomDB extends RoomDatabase {

    private static RoomDB database;
    public static String DB_Name = "User-Course-Assignment";

    public synchronized static RoomDB getInstance(Context context){
        // initialize database
        if (database == null) {
            database = Room.databaseBuilder(context.getApplicationContext(),
                    RoomDB.class,
                    DB_Name).allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return database;
    }

    // Create userDAO
    public abstract UserDAO userDAO();

    // Create courseDAO
    public abstract CourseDAO courseDAO();

    // Create AssignmentDAO
    public abstract AssignmentDAO assignmentDAO();
}
