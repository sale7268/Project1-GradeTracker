package com.example.project1_gradetracker;

import android.content.Context;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.project1_gradetracker.DB.Course;
import com.example.project1_gradetracker.DB.CourseDAO;
import com.example.project1_gradetracker.DB.RoomDB;
import com.example.project1_gradetracker.DB.User;
import com.example.project1_gradetracker.DB.UserDAO;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;

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

        // insert user if user does not already exist in the table
        User user1 = new User("apka0987", "bapk", "apka0000");
        if(userDB.getUserByID("apka0987") == null) {
            userDB.insert(user1);
        }
        User user2 = userDB.getUserByID("apka0987");
        //Log.i("User: ", user1.toString());
        assertEquals(user1.toString(), user1, user2);
    }

    @Test
    public void deleteUserTable(){
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        UserDAO userDB = RoomDB.getInstance(appContext).userDAO();

        // insert user if user does not already exist in the table
        User user1 = new User("apka0987", "bapk", "apka0000");
        if(userDB.getUserByID("apka0987") == null) {
            userDB.insert(user1);
        }
        User user2 = new User("bapk1234", "bapk", "apka0000");
        if(userDB.getUserByID("bapk1234") == null) {
            userDB.insert(user2);
        }

        userDB.delete(user1);
        // checking is user1 is null wont work because when we call DAO.delete()
        // it removes he user from the table, but doesn't delete the user object
        // so instead I am checking if the object returned by getID is null
        assertNull(user1.toString(), userDB.getUserByID("apka0987"));
    }

    @Test
    public void updateUserTable(){
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        UserDAO userDB = RoomDB.getInstance(appContext).userDAO();

        User user = new User("J410", "J.A.R.V.I.S.", "Stone");
        if(userDB.getUserByID("J410") == null) {
            userDB.insert(user);
        }

        // update doesn't update the user object, just the user in the table
        // so we have to pull the data from the table after we update it
        userDB.updatePassword("MindStone", user.getUSER_ID());
        user = userDB.getUserByID(user.getUSER_ID());
        assertNotEquals(user.toString(), user.getPassword(), "Stone");
    }

    @Test
    public void insertCourseTable(){
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        CourseDAO courseDB = RoomDB.getInstance(appContext).courseDAO();

        // insert user if user does not already exist in the table
        Course course1 = new Course(438, "438", "Dr. C", "Software Engineering");
        if(courseDB.getCourseByID(438) == null) {
            courseDB.insert(course1);
        }
        Course course2 = courseDB.getCourseByID(438);
        //Log.i("User: ", user1.toString());
        assertEquals(course1.toString(), course1, course2);
    }

    @Test
    public void deleteCourseTable(){
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        CourseDAO courseDB = RoomDB.getInstance(appContext).courseDAO();

        // insert user if user does not already exist in the table
        Course course1 = new Course(438, "438", "Dr. C", "Software Engineering");
        if(courseDB.getCourseByID(438) == null) {
            courseDB.insert(course1);
        }
        Course course2 = new Course(462, "462", "Brian", "Service Learning");
        if(courseDB.getCourseByID(462) == null) {
            courseDB.insert(course2);
        }

        courseDB.delete(course1);
        // checking if course1 is null wont work because when we call DAO.delete()
        // it removes he user from the table, but doesn't delete the user object
        // so instead I am checking if the object returned by getID is null
        assertNull(course1.toString(), courseDB.getCourseByID(438));
    }

    @Test
    public void updateCourseTable(){
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        CourseDAO courseDB = RoomDB.getInstance(appContext).courseDAO();

        // insert user if course does not already exist in the table
        Course course = new Course(438, "438", "Dr. C", "Software Engineering");
        if(courseDB.getCourseByID(438) == null) {
            courseDB.insert(course);
        }

        // update doesn't update the course object, just the course in the table
        // so we have to pull the data from the table after we update it
        courseDB.updateTitle("438 Software Engineering", course.getCourseID());
        course = courseDB.getCourseByID(course.getCourseID());
        assertEquals(course.toString(), course.getTitle(), "438 Software Engineering");
    }

//    @Test
//    public void insertAssignmentTable(){
//        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
//        AssignmentDAO assignmentDB = RoomDB.getInstance(appContext).assignmentDAO();
//
//    }
//
//    @Test
//    public void deleteAssignmentTable(){
//        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
//        AssignmentDAO assignmentDB = RoomDB.getInstance(appContext).assignmentDAO();
//
//    }
//
//    @Test
//    public void updateAssignmentTable(){
//        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
//        AssignmentDAO assignmentDB = RoomDB.getInstance(appContext).assignmentDAO();
//
//    }
}