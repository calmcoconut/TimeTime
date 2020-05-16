package com.example.timetime.ui.buttons;

import android.content.Intent;
import android.view.View;
import com.example.timetime.ui.activitySummary.EditActivityActivity;
import com.google.android.material.button.MaterialButton;

public class EditActivityButton extends BaseActivityButton {
    @Override
    public void setMaterialButtonOnClick(MaterialButton materialButton) {
        materialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchEditActivity();
            }
        });
    }

    private void launchEditActivity() {
        Intent intent = new Intent(getContext(), EditActivityActivity.class);
        getContext().startActivity(intent);
    }

    @Override
    public void setMaterialButtonOnLongClick(MaterialButton materialButton) {

    }
}
