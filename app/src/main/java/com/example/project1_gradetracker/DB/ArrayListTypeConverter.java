//package com.example.project1_gradetracker.DB;
//
//import android.renderscript.Type;
//
//import androidx.room.TypeConverter;
//
//import java.util.Collections;
//import java.util.List;
//
//public class ArrayListTypeConverter {
//  Gson gson = new Gson();
//    @TypeConverter
//    public List<Course> stringToCourseList(String data) {
//        if (data == null) {
//            return Collections.emptyList();
//        }
//        Type listType = new TypeToken<List<Course>>() {}.getType();
//        return gson.fromJson(data, listType);
//    }
//    @TypeConverter
//    public String CourseListToString(List<Course> someObjects) {
//        return gson.toJson(someObjects);
//    }
//}
