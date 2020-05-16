package com.example.timetime.ui.dialogs;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import com.example.timetime.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;
import java.util.Objects;

public class DialogAdapter extends ArrayAdapter<String> {
    FloatingActionButton fab;
    Context context;
    List<String> colors;

    public DialogAdapter(@NonNull Context context, int resource, List<String> colors) {
        super(context, resource, colors);
        this.context = context;
        this.colors = colors;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_item,parent,false);
        }
        fab = convertView.findViewById(R.id.dialog_item_fab);
        if (colors != null) {
            Log.d("dialog", "getting item " + Objects.requireNonNull(getItem(position)));
            int color =  Color.parseColor("#"+getItem(position));
            fab.setBackgroundTintList(ColorStateList.valueOf(color));
        }

//        Drawable icon = context.getDrawable(R.drawable.icon_camera);
//        fab.setImageDrawable(icon);
        return convertView;
    }

    private class DialogViewHolder extends RecyclerView.ViewHolder {
        public DialogViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
