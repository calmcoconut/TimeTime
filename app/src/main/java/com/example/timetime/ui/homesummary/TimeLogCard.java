package com.example.timetime.ui.homesummary;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.View;
import androidx.core.graphics.drawable.DrawableCompat;
import com.example.timetime.database.TimeLogic;
import com.example.timetime.database.entity.TimeLog;
import com.example.timetime.utils.DevProperties;
import org.jetbrains.annotations.NotNull;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.ChronoUnit;

public class TimeLogCard {

    public void setUpTimeCard(TimeLogListAdapter.TimeLogViewHolder holder
            , TimeLogic timeLogic
            , TimeLog timeLog
            , Context context, int position) {

        final String activityString = timeLog.getActivity();
        final Long timeStampCreated = timeLog.getTimestamp_created();
        final Long timestampModified = timeLog.getTimestamp_modified();
        final String timeSpan = getTimeSpan(timeLogic, timeStampCreated, timestampModified);
        String timeSpentValue = timeLogic.getHumanFormattedTimeBetweenTwoTimeSpans(timeStampCreated, timestampModified);
        final int icon = timeLog.getActivityIcon();
        final String color = timeLog.getActivityColor();

        holder.getTimeLogCardTitle().setText(activityString);
        holder.getTimeLogCardTimeSpan().setText(timeSpan);
        holder.getTimeLogCardTimeSpent().setText(timeSpentValue);
        holder.getTimeLogCardThumbnail().setBackgroundColor(Color.parseColor(("#" + color)));
        Drawable iconDrawable = context.getDrawable(icon);
        Drawable wrappedDrawable = DrawableCompat.wrap(iconDrawable);
        DrawableCompat.setTint(wrappedDrawable, Color.WHITE);
        holder.getTimeLogCardThumbnail().setImageDrawable(wrappedDrawable);

        setupCorrectTitle(holder, timeLogic, timeLog, position);
    }

    @NotNull
    private String getTimeSpan(TimeLogic timeLogic, Long timeStampCreated, Long timestampModified) {
        if (DevProperties.IS_24_HOUR_FORMAT) {
            return timeLogic.getLocalTimeFromDatabase(timeStampCreated) + " - " +
                    timeLogic.getLocalTimeFromDatabase(timestampModified);
        }
        else {
            return timeLogic.getLocalTimeFromDatabase12HourFormat(timeStampCreated) + " - " +
                    timeLogic.getLocalTimeFromDatabase12HourFormat(timestampModified);
        }
    }

    private void setupCorrectTitle(TimeLogListAdapter.TimeLogViewHolder holder, TimeLogic timeLogic, TimeLog timeLog,
                                   int position) {
        ZonedDateTime today = holder.getToday();
        ZonedDateTime yesterday = today.minusDays(1);
        ZonedDateTime currentEntryDate = timeLogic
                .getZonedDateTimeFromDatabaseLong(timeLog.getTimestamp_created())
                .truncatedTo(ChronoUnit.DAYS);

        ZonedDateTime previousEntryDate = holder.getPreviousCardDate();
        if (position == 0) {
            if (currentEntryDate.equals(today)) {
                holder.getTimeLogCardDateHeading().setText("Today");
            }
            else if (currentEntryDate.equals(yesterday)) {
                holder.getTimeLogCardDateHeading().setText("Yesterday");
            }
            else {
                String humanDate = DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL).format(currentEntryDate);
                holder.getTimeLogCardDateHeading().setText(humanDate);
            }
        }
        else {
            holder.getTimeLogDivider().setVisibility(View.VISIBLE);
            if (currentEntryDate.equals(previousEntryDate)) {
                holder.getTimeLogCardDateHeading().setVisibility(View.GONE);
            }
            else if (currentEntryDate.equals(yesterday)) {
                holder.getTimeLogCardDateHeading().setText("Yesterday");
            }
            else {
                String humanDate = DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL).format(currentEntryDate);
                holder.getTimeLogCardDateHeading().setText(humanDate);
            }
        }
        holder.setPreviousCardDate(currentEntryDate);
    }

    public TimeLogCard() {
    }
}
