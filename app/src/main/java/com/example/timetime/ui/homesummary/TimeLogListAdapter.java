package com.example.timetime.ui.homesummary;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.timetime.R;
import com.example.timetime.database.TimeLogic;
import com.example.timetime.database.entity.TimeLog;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class TimeLogListAdapter extends RecyclerView.Adapter<TimeLogListAdapter.TimeLogViewHolder> {
    private final LayoutInflater inflator;
    private final Context context;
    private static final TimeLogic timeLogic = TimeLogic.newInstance();
    ;
    private static ZonedDateTime zonedDateTime;
    private static ZonedDateTime mPreviousCardDate;
    private TimeLogCardListener timeLogCardListener;
    private List<TimeLog> timeLogList; // cached copy of categories

    TimeLogListAdapter(Context context, TimeLogCardListener timeLogCardListener) {
        this.context = context;
        zonedDateTime = timeLogic.getCurrentZonedDateTime().truncatedTo(ChronoUnit.DAYS);
        setHasStableIds(true);
        this.inflator = LayoutInflater.from(context);
        this.timeLogCardListener = timeLogCardListener;
    }

    @NonNull
    @Override
    public TimeLogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflator.inflate(R.layout.time_log_item, parent, false);
        return new TimeLogViewHolder(itemView, timeLogCardListener);
    }

    @Override
    public void onBindViewHolder(@NonNull TimeLogViewHolder holder, int position) {
        if (timeLogList != null) {
            TimeLog current = timeLogList.get(position);
            setTimeLogCardToCurrent(holder, current, position);
        }
        else {
            holder.mTimeLogCardTitle.setText("There is an error or no items");
        }
    }

    private void setTimeLogCardToCurrent(TimeLogViewHolder holder, TimeLog timeLog, int position) {
        TimeLogCard timeLogCard = new TimeLogCard();
        timeLogCard.setUpTimeCard(holder, timeLogic, timeLog, context, position);
    }

    void setTimeLogs(List<TimeLog> timeLogs) {
        timeLogList = timeLogs;
        notifyDataSetChanged();
    }

    public TimeLog getTimeCard(int position) {
        return timeLogList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        if (timeLogList != null) {
            return timeLogList.size();
        }
        else return 0;


    }

    public static class TimeLogViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final TextView mTimeLogCardTitle;
        private final TextView mTimeLogCardTimeSpent;
        private final TextView mTimeLogCardTimeSpan;
        private final TextView mTimeLogCardDateHeading;
        private final ImageView mTimeLogCardThumbnail;
        private final View mTimeLogDivider;
        private final TimeLogCardListener timeLogCardListener;

        private TimeLogViewHolder(@NonNull View itemView, @NonNull TimeLogCardListener timeLogCardListener) {

            super(itemView);
            mTimeLogCardTitle = itemView.findViewById(R.id.time_card_log_title);
            mTimeLogCardTimeSpent = itemView.findViewById(R.id.time_card_log_time_spent);
            mTimeLogCardTimeSpan = itemView.findViewById(R.id.time_card_log_time_span);
            mTimeLogCardDateHeading = itemView.findViewById(R.id.time_log_day_heading);
            mTimeLogDivider = itemView.findViewById(R.id.time_log_divider);
            mTimeLogCardThumbnail = itemView.findViewById(R.id.time_log_card_thumbnail);
            this.timeLogCardListener = timeLogCardListener;

            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            timeLogCardListener.onTimeLogClick(getAdapterPosition());
        }
        // getters

        public TextView getTimeLogCardTitle() {
            return mTimeLogCardTitle;
        }

        public TextView getTimeLogCardTimeSpent() {
            return mTimeLogCardTimeSpent;
        }

        public TextView getTimeLogCardTimeSpan() {
            return mTimeLogCardTimeSpan;
        }

        public TextView getTimeLogCardDateHeading() {
            return mTimeLogCardDateHeading;
        }

        public View getTimeLogDivider() {
            return mTimeLogDivider;
        }

        public ImageView getTimeLogCardThumbnail() {
            return mTimeLogCardThumbnail;
        }

        public ZonedDateTime getPreviousCardDate() {
            return mPreviousCardDate;
        }

        public void setPreviousCardDate(ZonedDateTime day) {
            mPreviousCardDate = day;
        }

        public ZonedDateTime getToday() {
            return zonedDateTime;
        }
    }

    public interface TimeLogCardListener {
        void onTimeLogClick(int position);
    }
}
