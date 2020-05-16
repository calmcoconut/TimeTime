package com.example.timetime.ui.buttons;

import android.content.Intent;
import android.view.View;
import com.example.timetime.ui.activitySummary.EditActivityActivity;
import com.google.android.material.button.MaterialButton;

public class EditActivityButton extends BaseActivityButton {
    public static final String EXTRA_ACTIVITY_NAME = "activityName";
    public static final String EXTRA_ACTIVITY_COLOR= "activityColor";
    public static final String EXTRA_ACTIVITY_ICON = "activityIcon";
    public static final String EXTRA_ACTIVITY_CATEGORY = "activityCategory";
    @Override
    public void setMaterialButtonOnClick(MaterialButton materialButton) {
        materialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchEditActivity(materialButton);
            }
        });
    }

    private void launchEditActivity(MaterialButton materialButton) {
        Intent intent = new Intent(getContext(), EditActivityActivity.class);
        String name = materialButton.getText().toString();
        String category = materialButton.getTag(BaseActivityButton.CAT_KEY).toString();
        String color = materialButton.getTag(BaseActivityButton.COLOR_KEY).toString();
        int icon = Integer.parseInt(materialButton.getTag(BaseActivityButton.ICON_KEY).toString());

        intent.putExtra(EXTRA_ACTIVITY_NAME,name);
        intent.putExtra(EXTRA_ACTIVITY_COLOR,color);
        intent.putExtra(EXTRA_ACTIVITY_ICON, icon);
        intent.putExtra(EXTRA_ACTIVITY_CATEGORY, category);
        getContext().startActivity(intent);
    }

    @Override
    public void setMaterialButtonOnLongClick(MaterialButton materialButton) {

    }
}
