package com.example.timetime.ui.homesummary;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.timetime.R;
import com.example.timetime.database.TimeLogic;
import com.example.timetime.database.entity.TimeLog;

import java.util.List;

public class TimeLogListAdapter extends RecyclerView.Adapter<TimeLogListAdapter.TimeLogViewHolder> {

    class TimeLogViewHolder extends RecyclerView.ViewHolder {

        private final TextView mTimeLogCardTitle;
        private final TextView mTimeLogCardTimeSpent;
        private final TextView mTimeLogCardTimeSpan;
        private final TextView mTimeLogCardDateHeading;
        private final View mTimeLogDivider;

        private TimeLogViewHolder(View itemView) {
            super(itemView);

            mTimeLogCardTitle = itemView.findViewById(R.id.time_card_log_title);
            mTimeLogCardTimeSpent = itemView.findViewById(R.id.time_card_log_time_spent);
            mTimeLogCardTimeSpan = itemView.findViewById(R.id.time_card_log_time_span);
            mTimeLogCardDateHeading = itemView.findViewById(R.id.time_log_day_heading);
            mTimeLogDivider = itemView.findViewById(R.id.time_log_divider);
        }
    }

    private final LayoutInflater mInflator;
    private List<TimeLog> mTimeLog; // cached copy of categories

    TimeLogListAdapter(Context context) {
        mInflator = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public TimeLogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflator.inflate(R.layout.time_log_item, parent, false);
        return new TimeLogViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TimeLogViewHolder holder, int position) {
        if (mTimeLog != null) {
            TimeLog current = mTimeLog.get(position);
//            holder.timeLogCardTitle.setText(current.getActivity());
            setTimeLogCardToCurrent(holder, current);
        } else {
            holder.mTimeLogCardTitle.setText("There is an error or no items");
        }
    }

    private void setTimeLogCardToCurrent(TimeLogViewHolder holder, TimeLog timeLog) {
        final TimeLogic timeLogic = TimeLogic.newInstance();

        final String activityString = timeLog.getActivity();
        final Long timeStampCreated = timeLog.getTimestamp_created();
        final Long timestampModified = timeLog.getTimestamp_modified();

        final String timeSpan = timeLogic.getLocalTimeFromDatabase(timeStampCreated) + " - " +
                timeLogic.getLocalTimeFromDatabase(timestampModified);

        String timeSpentValue = timeLogic.formattedTimeBetweenTwoTimeSpans(timeStampCreated, timestampModified);

        holder.mTimeLogCardTitle.setText(activityString);
        holder.mTimeLogCardTimeSpan.setText(timeSpan);
        holder.mTimeLogCardTimeSpent.setText(timeSpentValue);
    }

    void setTimeLogs(List<TimeLog> timeLogs) {
        mTimeLog = timeLogs;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mTimeLog != null) {
            return mTimeLog.size();
        } else return 0;
    }
}
