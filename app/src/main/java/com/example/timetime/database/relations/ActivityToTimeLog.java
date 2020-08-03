package com.example.timetime.database.relations;

import androidx.room.Embedded;
import androidx.room.Relation;
import com.example.timetime.database.entity.Activity;
import com.example.timetime.database.entity.TimeLog;

import java.util.List;

public class ActivityToTimeLog {
    @Embedded
    public Activity activity;
    @Relation(
            parentColumn = "activity",
            entityColumn = "id"
    )
    public List<TimeLog> timeLogs;
}
