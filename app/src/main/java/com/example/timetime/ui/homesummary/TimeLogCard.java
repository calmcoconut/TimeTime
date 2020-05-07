package com.example.timetime.ui.homesummary;

import android.content.Context;
import android.graphics.Color;
import com.example.timetime.database.TimeLogic;
import com.example.timetime.database.entity.TimeLog;

public class TimeLogCard{



    public TimeLogCard (){
    }

    public void setUpTimeCard(TimeLogListAdapter.TimeLogViewHolder holder, TimeLogic timeLogic, TimeLog timeLog,
                              Context context) {
        final String activityString = timeLog.getActivity();
        final Long timeStampCreated = timeLog.getTimestamp_created();
        final Long timestampModified = timeLog.getTimestamp_modified();
        final String timeSpan = timeLogic.getLocalTimeFromDatabase(timeStampCreated) + " - " +
                timeLogic.getLocalTimeFromDatabase(timestampModified);
        String timeSpentValue = timeLogic.formattedTimeBetweenTwoTimeSpans(timeStampCreated, timestampModified);
        final int icon =timeLog.getActivityIcon();
        final String color = timeLog.getActivityColor();

        holder.getmTimeLogCardTitle().setText(activityString);
        holder.getmTimeLogCardTimeSpan().setText(timeSpan);
        holder.getmTimeLogCardTimeSpent().setText(timeSpentValue);
        holder.getmTimeLogCardThumbnail().setBackgroundColor(Color.parseColor(("#"+color)));
        holder.getmTimeLogCardThumbnail().setImageDrawable(context.getDrawable(icon));
        }

    }
