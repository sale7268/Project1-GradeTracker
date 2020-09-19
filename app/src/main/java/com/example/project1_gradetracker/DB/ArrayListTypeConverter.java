package com.example.project1_gradetracker.DB;

/**
*  Author: Jacob Fahy
 *  editors: Blessing & Hamza
 *  date: 9/17/2020
* */

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

public class ArrayListTypeConverter {
  Gson gson = new GsonBuilder().serializeSpecialFloatingPointValues().create();

    @TypeConverter
    public List<Course> stringToCourseList(String data) {
        if (data == null) {
            return Collections.emptyList();
        }
        Type listType = new TypeToken<List<Course>>() {}.getType();
        return gson.fromJson(data, listType);
    }
    @TypeConverter
    public String CourseListToString(List<Course> someObjects) {
        return gson.toJson(someObjects);
    }
}
