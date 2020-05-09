package com.example.timetime.ui.homesummary;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import com.example.timetime.database.TimeLogic;
import com.example.timetime.database.entity.TimeLog;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.ChronoUnit;

public class TimeLogCard {


    public TimeLogCard() {
    }

    public void setUpTimeCard(TimeLogListAdapter.TimeLogViewHolder holder, TimeLogic timeLogic, TimeLog timeLog,
                              Context context, int position) {
        final String activityString = timeLog.getActivity();
        final Long timeStampCreated = timeLog.getTimestamp_created();
        final Long timestampModified = timeLog.getTimestamp_modified();
        final String timeSpan = timeLogic.getLocalTimeFromDatabase(timeStampCreated) + " - " +
                timeLogic.getLocalTimeFromDatabase(timestampModified);
        String timeSpentValue = timeLogic.getHumanformattedTimeBetweenTwoTimeSpans(timeStampCreated, timestampModified);
        final int icon = timeLog.getActivityIcon();
        final String color = timeLog.getActivityColor();

        holder.getmTimeLogCardTitle().setText(activityString);
        holder.getmTimeLogCardTimeSpan().setText(timeSpan);
        holder.getmTimeLogCardTimeSpent().setText(timeSpentValue);
        holder.getmTimeLogCardThumbnail().setBackgroundColor(Color.parseColor(("#" + color)));
        holder.getmTimeLogCardThumbnail().setImageDrawable(context.getDrawable(icon));

        setupCorrectTitle(holder, timeLogic, timeLog, position);
    }

    private void setupCorrectTitle(TimeLogListAdapter.TimeLogViewHolder holder, TimeLogic timeLogic, TimeLog timeLog,
                                   int position) {
        ZonedDateTime today = holder.getToday();
        ZonedDateTime yesterday = today.minusDays(1);
        ZonedDateTime currentEntryDate =
                timeLogic.getZonedDateTimeFromDatabaseLong(timeLog.getTimestamp_created()).truncatedTo(ChronoUnit.DAYS);
        ZonedDateTime previousEntryDate = holder.getPreviousCardDate();
        if (position == 0) {
            if (currentEntryDate.equals(today)) {
                holder.getmTimeLogCardDateHeading().setText("Today");
            }
            else if (currentEntryDate.equals(yesterday)) {
                holder.getmTimeLogCardDateHeading().setText("Yesterday");
            }
            else {
                String humanDate = DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL).format(currentEntryDate);
                holder.getmTimeLogCardDateHeading().setText(humanDate);
            }
        } else {
            holder.getmTimeLogDivider().setVisibility(View.VISIBLE);
            if (currentEntryDate.equals(previousEntryDate)) {
                holder.getmTimeLogCardDateHeading().setVisibility(View.GONE);
            } else if (currentEntryDate.equals(yesterday)) {
                holder.getmTimeLogCardDateHeading().setText("Yesterday");
            } else {
                String humanDate = DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL).format(currentEntryDate);
                holder.getmTimeLogCardDateHeading().setText(humanDate);
            }
        }
        holder.setPreviousCardDate(currentEntryDate);
    }
}
