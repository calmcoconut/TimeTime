package com.example.timetime.ui.activitysummary;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.timetime.R;
import com.example.timetime.database.entity.Category;

import java.util.List;

public class ActivityListAdapter extends RecyclerView.Adapter<ActivityListAdapter.ActivityViewHolder> {

    class ActivityViewHolder extends RecyclerView.ViewHolder {
        private final TextView categoryItemView;

        private ActivityViewHolder(View itemView) {
            super(itemView);
            categoryItemView = itemView.findViewById(R.id.time_card_recycler_title);
        }
    }
    private final LayoutInflater mInflator;
    private List<Category> mCategories ; // cached copy of categories

    ActivityListAdapter(Context context) {
        mInflator = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ActivityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflator.inflate(R.layout.recyclerview_item,parent,false);
        return new ActivityViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ActivityViewHolder holder, int position) {
        if (mCategories != null) {
            Category current = mCategories.get(position);
            holder.categoryItemView.setText(current.getCategory());
        }
        else {
            holder.categoryItemView.setText("There is an error or no items");
        }
    }

    void setCategories(List<Category> categories) {
        mCategories = categories;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mCategories != null){
            return mCategories.size();
        }
        else return 0;
    }
}
