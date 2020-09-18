package com.example.project1_gradetracker.DB;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

public class ArrayListTypeConverterAssignment{

    Gson gson = new Gson();
    @TypeConverter
    public List<Assignment> stringToCourseList(String data) {
        if (data == null) {
            return Collections.emptyList();
        }
        Type listType = new TypeToken<List<Assignment>>() {}.getType();
        return gson.fromJson(data, listType);
    }
    @TypeConverter
    public String CourseListToString(List<Assignment> someObjects) {
        return gson.toJson(someObjects);
    }
}
