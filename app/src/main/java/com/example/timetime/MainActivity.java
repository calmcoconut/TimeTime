package com.example.timetime;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.timetime.viewmodels.CategoryViewModel;

public class MainActivity extends AppCompatActivity {
    private CategoryViewModel mCategoryViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        /*
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        final CategoryListAdapter adapter = new CategoryListAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mCategoryViewModel = new ViewModelProvider(this).get(CategoryViewModel.class);
        mCategoryViewModel.getAllCategories().observe(this, new Observer<List<Category>>() {
            @Override
            public void onChanged(@Nullable final List<Category> categories) {
                adapter.setCategories(categories);
            }
        });
         */
    }
}
