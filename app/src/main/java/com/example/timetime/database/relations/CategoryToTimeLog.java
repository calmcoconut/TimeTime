package com.example.timetime.database.relations;

import androidx.room.Embedded;
import androidx.room.Relation;
import com.example.timetime.database.entity.Category;
import com.example.timetime.database.entity.TimeLog;

import java.util.List;

public class CategoryToTimeLog {
    @Embedded
    public Category category;
    @Relation(
            parentColumn = "category",
            entityColumn = "id"
    )
    public List<TimeLog> timeLogs;
}
