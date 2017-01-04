package com.stfalcon.frescoimageviewersample.ui;

import android.content.Context;

import com.stfalcon.frescoimageviewer.ImageViewer;
import com.stfalcon.frescoimageviewersample.R;
import com.stfalcon.frescoimageviewersample.ui.utils.AppUtils;
import com.stfalcon.frescoimageviewersample.ui.views.ImageOverlayView;

import java.util.List;

/*
 * Created by troy379 on 27.12.16.
 */
public class CustomImagesDemo {

    private List<Demo.CustomImage> images;
    private ImageOverlayView overlayView;

    public CustomImagesDemo(List<Demo.CustomImage> images) {
        this.images = images;
    }

    public void show(Context context) {
        AppUtils.showToast(context, R.string.custom_viewer_message, true);
        overlayView = new ImageOverlayView(context);
        new ImageViewer.Builder<>(context, images)
                .setFormatter(getCustomFormatter())
                .setImageMargin(context, R.dimen.image_margin)
                .setImageChangeListener(getImageChangeListener())
                .setOverlayView(overlayView)
                .show();
    }

    private ImageViewer.Formatter<Demo.CustomImage> getCustomFormatter() {
        return new ImageViewer.Formatter<Demo.CustomImage>() {
            @Override
            public String format(Demo.CustomImage customImage) {
                return customImage.getUrl();
            }
        };
    }

    private ImageViewer.OnImageChangeListener getImageChangeListener() {
        return new ImageViewer.OnImageChangeListener() {
            @Override
            public void onImageChange(int position) {
                Demo.CustomImage image = images.get(position);
                overlayView.setShareText(image.getUrl());
                overlayView.setDescription(image.getDescription());
            }
        };
    }
}
