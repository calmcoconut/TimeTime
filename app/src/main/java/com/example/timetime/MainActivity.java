package com.example.timetime;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.timetime.database.database.FirstTimeDatabase;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirstTimeDatabase test = new FirstTimeDatabase();
    }
}
