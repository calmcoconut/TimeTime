package com.example.timetime.ui.categorysummary;

import android.view.View;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.timetime.R;
import com.example.timetime.database.entity.Activity;
import com.example.timetime.database.entity.Category;
import com.example.timetime.viewmodels.CategoryViewModel;

import java.util.List;

public abstract class BaseCategoryFragment extends Fragment {
    private CategoryViewModel mCategoryViewModel;
    private RecyclerView recyclerView;
    CategoryListAdapter adapter;

    private void startRecyclerForCategory() {
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        observeChangesInCategories();
        observeChangesInActivities();
    }

    private void observeChangesInCategories() {
        mCategoryViewModel.getAllCategories().observe(getViewLifecycleOwner(),
                new Observer<List<Category>>() {
                    @Override
                    public void onChanged(@Nullable final List<Category> categories) {
                        adapter.setCategories(categories);
                    }
                });
    }

    private void observeChangesInActivities() {
        mCategoryViewModel.getAllActivities().observe(getViewLifecycleOwner(),
                new Observer<List<Activity>>() {
                    @Override
                    public void onChanged(List<Activity> activities) {
                        adapter.setActivities(activities);
                    }
                });
    }

    public void setAttributes(View view) {
        this.recyclerView = view.findViewById(R.id.recycler_view_root);
        this.mCategoryViewModel = new ViewModelProvider(this).get(CategoryViewModel.class);
    }

    public void setAdapterNoClick() {
        this.adapter = new CategoryListAdapter(getContext());
        startRecyclerForCategory();
    }

    public void setAdapterWithClick(View.OnClickListener onClickListener) {
        this.adapter = new CategoryListAdapter(getContext(), onClickListener);
        startRecyclerForCategory();
    }
}
