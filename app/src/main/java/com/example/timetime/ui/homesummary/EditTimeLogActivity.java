package com.example.timetime.ui.homesummary;

import android.content.Intent;
import android.graphics.Color;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import com.example.timetime.MainActivity;
import com.example.timetime.R;
import org.jetbrains.annotations.NotNull;

public class EditTimeLogActivity extends LogTimeToActivity {
    private Long timeLogId;

    @Override
    public void getTimeToDisplayOnToolBar() {
        Intent intent = getIntent();
        this.timeLogId = intent.getLongExtra("old_time_log_id", 0);
        Long oldCreatedTime = intent.getLongExtra("old_time_log_created_time", 0L);
        Long oldModifiedTime = intent.getLongExtra("old_time_log_modified_time", 0L);
        setToolBarTime(getTimeLogic().getHumanFormattedTimeBetweenTwoTimeSpans(oldCreatedTime, oldModifiedTime));
    }

    private void editTimeLog() {

    }

    private void deleteTimeLog() {
        getTimeLogViewModel().deleteTimeLog(this.timeLogId);
        closeToHome();
    }

    @Override
    public void setUpToolBar(boolean initialSetUp) {
        super.setUpToolBar(initialSetUp);
        setTitle("Edit Time Log");
        getToolbar().setSubtitle(getToolBarTime() + " is what you've logged to " + getIntent().getStringExtra("old_time_log_activity"));
        getToolbar().setSubtitleTextColor(Color.WHITE);
    }

    private void closeToHome() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("tab", 0);
        startActivity(intent);
        finish();
        overridePendingTransition(0, 0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.delete_action, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull @NotNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_delete:
                deleteTimeLog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public EditTimeLogActivity() {
        super();
    }
}
