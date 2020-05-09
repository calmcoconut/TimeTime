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


    class TimeLogViewHolder extends RecyclerView.ViewHolder {

        private final TextView mTimeLogCardTitle;
        private final TextView mTimeLogCardTimeSpent;
        private final TextView mTimeLogCardTimeSpan;
        private final TextView mTimeLogCardDateHeading;
        private final ImageView mTimeLogCardThumbnail;
        private final View mTimeLogDivider;

        private TimeLogViewHolder(View itemView) {
            super(itemView);
            mTimeLogCardTitle = itemView.findViewById(R.id.time_card_log_title);
            mTimeLogCardTimeSpent = itemView.findViewById(R.id.time_card_log_time_spent);
            mTimeLogCardTimeSpan = itemView.findViewById(R.id.time_card_log_time_span);
            mTimeLogCardDateHeading = itemView.findViewById(R.id.time_log_day_heading);
            mTimeLogDivider = itemView.findViewById(R.id.time_log_divider);
            mTimeLogCardThumbnail = itemView.findViewById(R.id.time_log_card_thumbnail);
        }

        // getters
        public TextView getmTimeLogCardTitle() {
            return mTimeLogCardTitle;
        }

        public TextView getmTimeLogCardTimeSpent() {
            return mTimeLogCardTimeSpent;
        }

        public TextView getmTimeLogCardTimeSpan() {
            return mTimeLogCardTimeSpan;
        }

        public TextView getmTimeLogCardDateHeading() {
            return mTimeLogCardDateHeading;
        }

        public View getmTimeLogDivider() {
            return mTimeLogDivider;
        }

        public ImageView getmTimeLogCardThumbnail() {
            return mTimeLogCardThumbnail;
        }

        public ZonedDateTime getPreviousCardDate() {
            return mPreviousCardDate;
        }

        public void setPreviousCardDate(ZonedDateTime day) {
            mPreviousCardDate = day;
        }

        public ZonedDateTime getToday() {
            return mTodaysDay;
        }
    }


    private final LayoutInflater mInflator;
    private final Context mContext;
    private final TimeLogic timeLogic;
    private final ZonedDateTime mTodaysDay;
    private static ZonedDateTime mPreviousCardDate;
    private List<TimeLog> mTimeLog; // cached copy of categories

    TimeLogListAdapter(Context context) {
        mContext = context;
        this.timeLogic = TimeLogic.newInstance();
        this.mTodaysDay = timeLogic.getCurrentZonedDateTime().truncatedTo(ChronoUnit.DAYS);
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
            setTimeLogCardToCurrent(holder, current, position);
        } else {
            holder.mTimeLogCardTitle.setText("There is an error or no items");
        }
    }

    private void setTimeLogCardToCurrent(TimeLogViewHolder holder, TimeLog timeLog, int position) {
        TimeLogCard timeLogCard = new TimeLogCard();
        if (getItemCount() > 1) {
            timeLogCard.setUpTimeCard(holder, timeLogic, timeLog, mContext, position);
        } else {
            timeLogCard.setUpTimeCard(holder, timeLogic, timeLog, mContext, position);
        }
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
