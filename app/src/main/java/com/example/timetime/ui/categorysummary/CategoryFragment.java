package com.example.timetime.ui.categorysummary;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.timetime.R;
import com.example.timetime.database.entity.Category;
import com.example.timetime.viewmodels.CategoryViewModel;

import java.util.List;

public class CategoryFragment extends Fragment {
    private CategoryViewModel mCategoryViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.recycler_view, container, false);
        startRecyclerForCategory(rootView);
        return rootView;
    }

    private void startRecyclerForCategory(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_root);

        final CategoryListAdapter adapter = new CategoryListAdapter(getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mCategoryViewModel = new ViewModelProvider(this).get(CategoryViewModel.class);
        mCategoryViewModel.getAllCategories().observe(getViewLifecycleOwner(),
                new Observer<List<Category>>() {
                    @Override
                    public void onChanged(@Nullable final List<Category> categories) {
                        adapter.setCategories(categories);
                    }
                });
    }

    // factory method for returning an instance of the class
    public static CategoryFragment newInstance() {
        return new CategoryFragment();
    }
    // empty constructor
    public CategoryFragment() {
    }
}
