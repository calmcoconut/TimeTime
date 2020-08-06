package com.example.timetime.db;

import com.example.timetime.database.entity.Activity;
import com.example.timetime.database.entity.Category;
import com.example.timetime.database.entity.TimeLog;

import java.util.Arrays;
import java.util.List;

public class TestData {
    static final Activity ACTIVITY_ENTITY1 = new Activity("testActivity1", "testCategory1", 1, "red");
    static final Activity ACTIVITY_ENTITY2 = new Activity("testActivity2", "testCategory1", 1, "red");
    static final Activity ACTIVITY_ENTITY3 = new Activity("testActivity3", "testCategory2", 1, "red");
    static final List<Activity> ACTIVITIES_LIST = Arrays.asList(ACTIVITY_ENTITY1, ACTIVITY_ENTITY2, ACTIVITY_ENTITY3);

    static final Category CATEGORY_ENTITY1 = new Category("testCategory1", "red");
    static final Category CATEGORY_ENTITY2 = new Category("testCategory2", "red");
    static final List<Category> CATEGORIES_LIST = Arrays.asList(CATEGORY_ENTITY1, CATEGORY_ENTITY2);

    static final TimeLog TIME_LOG_ENTITY1 = new TimeLog(1L, 2L, "testActivity1",
            "red", 1, "testCategory1");
    static final TimeLog TIME_LOG_ENTITY2 = new TimeLog(2L, 3L, "testActivity2",
            "red", 1, "testCategory1");
    static final TimeLog TIME_LOG_ENTITY3 = new TimeLog(3L, 4L, "testActivity3",
            "red", 1, "testCategory2");
    static final List<TimeLog> TIME_LOG_LIST = Arrays.asList(TIME_LOG_ENTITY1, TIME_LOG_ENTITY2, TIME_LOG_ENTITY3);
}
