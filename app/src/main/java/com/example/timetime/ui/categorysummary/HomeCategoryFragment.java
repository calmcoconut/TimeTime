package com.example.timetime.ui.categorysummary;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.example.timetime.R;

public class HomeCategoryFragment extends BaseCategoryFragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.recycler_view, container, false);
        setAttributes(rootView);
        setAdapterWithClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), EditCategoryActivity.class);
                startActivity(intent);
            }
        });
        return rootView;
    }

    // factory method for returning an instance of the class
    public static HomeCategoryFragment newInstance() {
        return new HomeCategoryFragment();
    }

    // empty constructor
    public HomeCategoryFragment() {
    }

}
