package com.example.timetime.ui.dialogs;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.example.timetime.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class IconDialogAdapter extends ArrayAdapter<String> {
    FloatingActionButton currentFab;
    FloatingActionButton iconFab;
    ColorStateList color;
    Context context;
    List<String> icons;

    public IconDialogAdapter(@NonNull Context context, int resource, List<String> icons, FloatingActionButton iconFab) {
        super(context, resource, icons);
        this.context = context;
        this.icons = icons;
        this.iconFab = iconFab;
        this.color = iconFab.getBackgroundTintList();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_item,parent,false);
        }
        currentFab = convertView.findViewById(R.id.dialog_item_fab);
        if (icons != null) {
            currentFab.setBackgroundTintList(this.color);
            int iconInt = Integer.parseInt(getItem(position));
            Drawable icon = context.getDrawable(iconInt);
            currentFab.setImageDrawable(icon);
            currentFab.setColorFilter(Color.WHITE);
            setCurrentIconOnClick(currentFab, iconInt,icon);
        }
        return convertView;
    }

    private void setCurrentIconOnClick(FloatingActionButton currentFab, int iconId, Drawable icon) {
        currentFab.setOnClickListener(v -> {
            iconFab.setImageDrawable(icon);
            iconFab.setTag(iconId);
        });
    }
}
