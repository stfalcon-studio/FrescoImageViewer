package com.stfalcon.frescoimageviewersample.features.demo.simple;

import android.os.Bundle;

import com.stfalcon.frescoimageviewer.ImageViewer;
import com.stfalcon.frescoimageviewersample.R;
import com.stfalcon.frescoimageviewersample.features.demo.DemoActivity;
import com.stfalcon.frescoimageviewersample.utils.AppUtils;

public class SimpleUsageActivity extends DemoActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AppUtils.showInfoSnackbar(findViewById(R.id.coordinator),
                R.string.message_open_viewer, true);
    }

    @Override
    protected void showPicker(int startPosition) {
        new ImageViewer.Builder<>(this, posters)
                .setStartPosition(startPosition)
                .show();
    }
}
