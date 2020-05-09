package com.example.timetime.ui.homesummary;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import com.example.timetime.database.TimeLogic;
import com.example.timetime.database.entity.TimeLog;

public class TimeLogCard {


    public TimeLogCard() {
    }

    public void setUpTimeCard(TimeLogListAdapter.TimeLogViewHolder holder, TimeLogic timeLogic, TimeLog timeLog,
                              Context context, int position, TimeLog previousTimeLog) {
        final String activityString = timeLog.getActivity();
        final Long timeStampCreated = timeLog.getTimestamp_created();
        final Long timestampModified = timeLog.getTimestamp_modified();
        final String timeSpan = timeLogic.getLocalTimeFromDatabase(timeStampCreated) + " - " +
                timeLogic.getLocalTimeFromDatabase(timestampModified);
        String timeSpentValue = timeLogic.formattedTimeBetweenTwoTimeSpans(timeStampCreated, timestampModified);
        final int icon = timeLog.getActivityIcon();
        final String color = timeLog.getActivityColor();

        holder.getmTimeLogCardTitle().setText(activityString);
        holder.getmTimeLogCardTimeSpan().setText(timeSpan);
        holder.getmTimeLogCardTimeSpent().setText(timeSpentValue);
        holder.getmTimeLogCardThumbnail().setBackgroundColor(Color.parseColor(("#" + color)));
        holder.getmTimeLogCardThumbnail().setImageDrawable(context.getDrawable(icon));

        setupCorrectTitle(holder,timeLogic,timeLog, position, previousTimeLog,mPreviousTimeLogView);
    }
    private void setupCorrectTitle(TimeLogListAdapter.TimeLogViewHolder holder, TimeLogic timeLogic, TimeLog timeLog,
                                   int position, TimeLog previousTimeLog) {
        Log.d("timeLogCard",
                "current timeLog name: " + " last timelog name " + previousTimeLog.getActivity());
        if (position == 0) {
        }
        // check if the same date as last entry. if it is not, modify last entry.
        else {
//            String currentEntryDate = timeLogic.getLocalDateFromDatabase(timeLog.getTimestamp_modified());
        }
    }
}
