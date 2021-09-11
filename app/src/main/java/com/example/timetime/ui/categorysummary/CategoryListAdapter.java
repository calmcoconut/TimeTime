package com.example.timetime.ui.categorysummary;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.timetime.R;
import com.example.timetime.database.entity.Activity;
import com.example.timetime.database.entity.Category;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class CategoryListAdapter extends RecyclerView.Adapter<CategoryListAdapter.CategoryViewHolder> {

    private final LayoutInflater mInflator;
    private List<Category> mCategories; // cached copy of categories
    private List<Activity> mActivities; // cached copy of Activities
    private HashMap<String, List<String>> mCategoryActivityMap;
    private View.OnClickListener onClickListener;

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflator.inflate(R.layout.category_item, parent, false);
        return new CategoryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        if (mCategories != null && mActivities != null) {
            if (mCategoryActivityMap == null) {
                mCategoryActivityMap = createCategoryActivityMap();
            }
            Category current = mCategories.get(position);
            setCategoryRelatedAttributes(holder, current);
            setActivityList(holder, current);
            if (this.onClickListener != null) {
                holder.itemView.setTag(current.getCategory());
                holder.itemView.setOnClickListener(this.onClickListener);
            }
        }
        else {
            holder.mCategoryItemTitle.setText("There is an error or no items");
        }
    }

    private HashMap<String, List<String>> createCategoryActivityMap() {
        HashMap<String, List<String>> result = new HashMap<String, List<String>>();
        for (Category category : mCategories) {
            result.put(category.getCategory(), new ArrayList<String>());
        }
        for (Activity activity : mActivities) {
            if (result.containsKey(activity.getCategory())) {
                Objects.requireNonNull(result.get(activity.getCategory())).add(activity.getActivity());
            }
        }
        return result;
    }

    CategoryListAdapter(Context context) {
        mInflator = LayoutInflater.from(context);
    }

    CategoryListAdapter(Context context, View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
        mInflator = LayoutInflater.from(context);
    }

    private void setActivityList(CategoryViewHolder holder, Category category) {
        List<String> activities = mCategoryActivityMap.get(category.getCategory());
        if (Objects.requireNonNull(activities).size() > 0) {
            String activitiesString = activities.toString()
                    .replace("[", "")
                    .replace("]", "");
            holder.mCategoryItemActivityNames.setText(activitiesString);
        }
    }

    private void setCategoryRelatedAttributes(CategoryViewHolder holder, Category category) {
        holder.mCategoryItemTitle.setText(category.getCategory());
        holder.colorFab.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#" + category.getColor())));
    }

    public void setCategories(List<Category> categories) {
        mCategories = categories;
        notifyDataSetChanged();
    }

    public void setActivities(List<Activity> activities) {
        mActivities = activities;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mCategories != null) {
            return mCategories.size();
        }
        else return 0;
    }

    ///

    static class CategoryViewHolder extends RecyclerView.ViewHolder {
        private final TextView mCategoryItemTitle;
        private final TextView mCategoryItemActivityNames;
        private final FloatingActionButton colorFab;

        private CategoryViewHolder(View itemView) {
            super(itemView);
            mCategoryItemTitle = itemView.findViewById(R.id.category_item_heading);
            mCategoryItemActivityNames = itemView.findViewById(R.id.category_item_activity);
            colorFab = itemView.findViewById(R.id.category_color_fab);
        }
    }
}
