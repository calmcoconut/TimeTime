package com.example.timetime.ui.categorysummary;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.timetime.R;

public class CategoryFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.recyclerview_item, container, false);
        TextView textView = rootView.findViewById(R.id.time_card_recycler_title);
        textView.setText("THIS IS CATEGORY FRAGMENT");
        return rootView;
    }




    // factory method for returning an instance of the class
    public static CategoryFragment newInstance() {
        return new CategoryFragment();
    }
    // empty constructor
    public CategoryFragment() {
    }
}
