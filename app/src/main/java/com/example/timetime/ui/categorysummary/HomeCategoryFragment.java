package com.example.timetime.ui.categorysummary;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.example.timetime.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Objects;

public class HomeCategoryFragment extends BaseCategoryFragment {
    public static final String EXTRA_CATEGORY_NAME = "categoryName";
    public static final String EXTRA_CATEGORY_COLOR= "categoryColor";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.recycler_view, container, false);

        setAttributes(rootView);
        setAdapterWithClick(v -> {
            TextView textView = v.findViewById(R.id.category_item_heading);
            FloatingActionButton colorFab = v.findViewById(R.id.category_color_fab);
            int color = Objects.requireNonNull(colorFab.getBackgroundTintList()).getDefaultColor();

            Intent intent = new Intent(getContext(), EditCategoryActivity.class);
            intent.putExtra(EXTRA_CATEGORY_NAME, textView.getText().toString());
            intent.putExtra(EXTRA_CATEGORY_COLOR,color);
            startActivity(intent);
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
