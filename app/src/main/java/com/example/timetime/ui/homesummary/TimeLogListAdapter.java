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

        private final TextView timeLogCardTitle;
        private final TextView timeLogCardTimeSpent;
        private final TextView timeLogCardTimeSpan;

        private TimeLogViewHolder(View itemView) {
            super(itemView);
            timeLogCardTitle = itemView.findViewById(R.id.time_card_log_title);
            timeLogCardTimeSpent = itemView.findViewById(R.id.time_card_log_time_spent);
            timeLogCardTimeSpan = itemView.findViewById(R.id.time_card_log_time_span);
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
        View itemView = mInflator.inflate(R.layout.time_log_item,parent,false);
        return new TimeLogViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TimeLogViewHolder holder, int position) {
        if (mTimeLog != null) {
            TimeLog current = mTimeLog.get(position);
//            holder.timeLogCardTitle.setText(current.getActivity());
            setTimeLogCardToCurrent(holder,current);
        }
        else {
            holder.timeLogCardTitle.setText("There is an error or no items");
        }
    }

    private void setTimeLogCardToCurrent (TimeLogViewHolder holder, TimeLog timeLog) {
        final String activityString = timeLog.getActivity();
        final Long timeStampCreated = timeLog.getTimestamp_created();
        final Long timestampModified = timeLog.getTimestamp_modified();
        final TimeLogic timeLogic = TimeLogic.newInstance();
        final String timeSpan = timeLogic.getLocalTimeFromDatabase(timeStampCreated) + " - " +
            timeLogic.getLocalTimeFromDatabase(timestampModified);

        String timeSpentValue = timeLogic.formattedTimeBetweenTwoTimeSpans(timeStampCreated,timestampModified);

        holder.timeLogCardTitle.setText(activityString);
        holder.timeLogCardTimeSpan.setText(timeSpan);
        holder.timeLogCardTimeSpent.setText(timeSpentValue);
    }

    void setTimeLogs(List<TimeLog> timeLogs) {
        mTimeLog = timeLogs;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mTimeLog != null){
            return mTimeLog.size();
        }
        else return 0;
    }
}
