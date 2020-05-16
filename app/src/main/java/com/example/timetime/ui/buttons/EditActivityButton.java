package com.example.timetime.ui.buttons;

import com.google.android.material.button.MaterialButton;

public class EditActivityButton extends BaseActivityButton {
    @Override
    public void setMaterialButtonOnClick(MaterialButton materialButton) {
        launchEditActivity();
    }

    private void launchEditActivity() {
    }

    @Override
    public void setMaterialButtonOnLongClick(MaterialButton materialButton) {

    }
}
