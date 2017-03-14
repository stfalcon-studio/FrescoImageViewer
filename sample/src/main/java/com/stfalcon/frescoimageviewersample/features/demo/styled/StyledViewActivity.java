package com.stfalcon.frescoimageviewersample.features.demo.styled;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.generic.RoundingParams;
import com.stfalcon.frescoimageviewer.ImageViewer;
import com.stfalcon.frescoimageviewersample.R;
import com.stfalcon.frescoimageviewersample.common.views.ImageOverlayView;
import com.stfalcon.frescoimageviewersample.features.demo.DemoActivity;
import com.stfalcon.frescoimageviewersample.utils.AppUtils;
import com.stfalcon.frescoimageviewersample.utils.StylingOptions;

import java.util.Random;

import jp.wasabeef.fresco.processors.GrayscalePostprocessor;

/*
 * Created by troy379 on 06.03.17.
 */
public class StyledViewActivity extends DemoActivity {

    private ImageOverlayView overlayView;
    private StylingOptions options;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        options = new StylingOptions();

        AppUtils.showGotItSnackbar(findViewById(R.id.coordinator),
                R.string.message_styling);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.styling_options_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        options.showDialog(this);
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void showPicker(int startPosition) {
        ImageViewer.Builder builder = new ImageViewer.Builder<>(this, posters)
                .setStartPosition(startPosition)
                .setOnDismissListener(getDismissListener());

        builder.hideStatusBar(options.get(StylingOptions.Property.HIDE_STATUS_BAR));

        if (options.get(StylingOptions.Property.IMAGE_MARGIN)) {
            builder.setImageMargin(this, R.dimen.image_margin);
        }

        if (options.get(StylingOptions.Property.CONTAINER_PADDING)) {
            builder.setContainerPadding(this, R.dimen.image_margin);
        }

        if (options.get(StylingOptions.Property.IMAGES_ROUNDING)) {
            builder.setCustomDraweeHierarchyBuilder(getRoundedHierarchyBuilder());
        }

        builder.allowSwipeToDismiss(options.get(StylingOptions.Property.SWIPE_TO_DISMISS));

        builder.allowZooming(options.get(StylingOptions.Property.ZOOMING));

        if (options.get(StylingOptions.Property.SHOW_OVERLAY)) {
            overlayView = new ImageOverlayView(this);
            builder.setOverlayView(overlayView);
            builder.setImageChangeListener(getImageChangeListener());
        }

        if (options.get(StylingOptions.Property.RANDOM_BACKGROUND)) {
            builder.setBackgroundColor(getRandomColor());
        }

        if (options.get(StylingOptions.Property.POST_PROCESSING)) {
            builder.setCustomImageRequestBuilder(
                    ImageViewer.createImageRequestBuilder()
                            .setPostprocessor(new GrayscalePostprocessor()));
        }

        builder.show();
    }

    private ImageViewer.OnImageChangeListener getImageChangeListener() {
        return new ImageViewer.OnImageChangeListener() {
            @Override
            public void onImageChange(int position) {
                String url = posters[position];
                overlayView.setShareText(url);
                overlayView.setDescription(descriptions[position]);
            }
        };
    }

    private ImageViewer.OnDismissListener getDismissListener() {
        return new ImageViewer.OnDismissListener() {
            @Override
            public void onDismiss() {
                AppUtils.showInfoSnackbar(findViewById(R.id.coordinator),
                        R.string.message_on_dismiss, false);
            }
        };
    }

    private GenericDraweeHierarchyBuilder getRoundedHierarchyBuilder() {
        RoundingParams roundingParams = new RoundingParams();
        roundingParams.setRoundAsCircle(true);

        return GenericDraweeHierarchyBuilder.newInstance(getResources())
                .setRoundingParams(roundingParams);
    }

    private int getRandomColor() {
        Random random = new Random();
        return Color.argb(255, random.nextInt(156), random.nextInt(156), random.nextInt(156));
    }
}
