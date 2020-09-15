package com.example.project1_gradetracker;

import android.content.Context;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.project1_gradetracker.DB.RoomDB;
import com.example.project1_gradetracker.DB.User;
import com.example.project1_gradetracker.DB.UserDAO;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.example.project1_gradetracker", appContext.getPackageName());
    }

    @Test
    public void insertUserTable(){
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

        UserDAO userDB = RoomDB.getInstance(appContext).userDAO();

        User user1 = new User("apka0987", "bapk", "apka0000");
        userDB.insert(user1);

        User user2 = userDB.getUserByID("apka0987");

        assertEquals(user1, user2);
    }

    @Test
    public void deleteUserTable(){

    }

    @Test
    public void updateUserTable(){

    }
}