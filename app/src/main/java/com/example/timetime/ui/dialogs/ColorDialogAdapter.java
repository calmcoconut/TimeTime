package com.example.timetime.ui.dialogs;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.example.timetime.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class ColorDialogAdapter extends ArrayAdapter<String> {

    FloatingActionButton itemFab;
    FloatingActionButton colorFab;
    FloatingActionButton iconFab;
    Context context;
    List<String> colors;

    public ColorDialogAdapter(@NonNull Context context, int resource, List<String> colors,
                              FloatingActionButton colorFab, FloatingActionButton iconFab) {
        super(context, resource, colors);
        this.context = context;
        this.colors = colors;
        this.colorFab = colorFab;
        this.iconFab = iconFab;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_item,parent,false);
        }
        itemFab = convertView.findViewById(R.id.dialog_item_fab);
        if (colors != null) {
            int color =  Color.parseColor("#"+getItem(position));
            itemFab.setBackgroundTintList(ColorStateList.valueOf(color));
            setCurrentColorOnClick(itemFab, color);
        }
        return convertView;
    }

    private void setCurrentColorOnClick(FloatingActionButton currentItem, int color) {
        currentItem.setOnClickListener(v -> {
            colorFab.setBackgroundTintList(ColorStateList.valueOf(color));
            iconFab.setBackgroundTintList(ColorStateList.valueOf(color));
        });
    }
}
