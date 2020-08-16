package com.example.timetime.utils;

import android.os.Parcel;
import android.os.Parcelable;
import com.example.timetime.database.entity.Activity;

public class ParcelableActivity implements Parcelable {
    private String activityName;
    private String categoryName;
    private String activityColor;
    private Integer activityIcon;

    public ParcelableActivity(Activity activity) {
        activityName = activity.getActivity();
        categoryName = activity.getCategory();
        activityColor = activity.getColor();
        activityIcon = activity.getIcon();
    }

    protected ParcelableActivity(Parcel in) {
        activityName = in.readString();
        categoryName = in.readString();
        activityColor = in.readString();
        if (in.readByte() == 0) {
            activityIcon = null;
        }
        else {
            activityIcon = in.readInt();
        }
    }

    public static final Creator<ParcelableActivity> CREATOR = new Creator<ParcelableActivity>() {
        @Override
        public ParcelableActivity createFromParcel(Parcel in) {
            return new ParcelableActivity(in);
        }

        @Override
        public ParcelableActivity[] newArray(int size) {
            return new ParcelableActivity[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(activityName);
        dest.writeString(categoryName);
        dest.writeString(activityColor);
        if (activityIcon == null) {
            dest.writeByte((byte) 0);
        }
        else {
            dest.writeByte((byte) 1);
            dest.writeInt(activityIcon);
        }
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String name) {
        this.activityName = name;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getActivityColor() {
        return activityColor;
    }

    public void setActivityColor(String activityColor) {
        this.activityColor = activityColor;
    }

    public int getActivityIcon() {
        return activityIcon;
    }

    public void setActivityIcon(int activityIcon) {
        this.activityIcon = activityIcon;
    }
}
