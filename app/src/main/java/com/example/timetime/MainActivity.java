package com.example.timetime;

import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.timetime.database.DatabaseInitializer;
import com.example.timetime.database.database.AppDatabase;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void populateDB(View view) {
        DatabaseInitializer.populateAsync(AppDatabase.getAppDatabase(this));
    }
    public void destroyDB(View view) {

    }

    @Override
    protected void onDestroy() {
        AppDatabase.destroyInstance();
        super.onDestroy();
    }
}
