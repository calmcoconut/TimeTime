package com.example.timetime.ui.homesummary;

import com.example.timetime.database.TimeLogic;
import com.example.timetime.database.entity.TimeLog;

public class TimeLogCard{



    public TimeLogCard (){
    }

    public void setUpTimeCard(TimeLogListAdapter.TimeLogViewHolder holder, TimeLogic timeLogic, TimeLog timeLog) {
        final String activityString = timeLog.getActivity();
        final Long timeStampCreated = timeLog.getTimestamp_created();
        final Long timestampModified = timeLog.getTimestamp_modified();
        final String timeSpan = timeLogic.getLocalTimeFromDatabase(timeStampCreated) + " - " +
                timeLogic.getLocalTimeFromDatabase(timestampModified);
        String timeSpentValue = timeLogic.formattedTimeBetweenTwoTimeSpans(timeStampCreated, timestampModified);

//        Activity activity = getActivity(activityString);
//        int icon = activity.getIcon();
//        int color = Integer.parseInt(activity.getColor());

        holder.getmTimeLogCardTitle().setText(activityString);
        holder.getmTimeLogCardTimeSpan().setText(timeSpan);
        holder.getmTimeLogCardTimeSpent().setText(timeSpentValue);
//        holder.getmTimeLogCardThumbnail().setBackgroundColor(color);
//        holder.getmTimeLogCardThumbnail().setImageResource(icon);
    }

//    private Activity getActivity(String activityName) {
//
//        return activityViewModel.getActivityByName(activityName);
//    }
}
