package com.stfalcon.frescoimageviewersample.ui.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.view.SimpleDraweeView;
import com.stfalcon.frescoimageviewer.ImageViewer;
import com.stfalcon.frescoimageviewersample.R;
import com.stfalcon.frescoimageviewersample.ui.CustomImagesDemo;
import com.stfalcon.frescoimageviewersample.ui.Demo;
import com.stfalcon.frescoimageviewersample.ui.views.ImageOverlayView;

/*
 * Created by Alexander Krol (troy379) on 29.08.16.
 */
public class MainActivity extends AppCompatActivity {

    private String[] posters, descriptions;

    private static final int[] ids = new int[]{
            R.id.firstImage, R.id.secondImage,
            R.id.thirdImage, R.id.fourthImage,
            R.id.fifthImage, R.id.sixthImage,
            R.id.seventhImage, R.id.eighthImage,
            R.id.ninethImage
    };

    private ImageOverlayView overlayView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));

        posters = Demo.getPosters();
        descriptions = Demo.getDescriptions();
        initViews();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_custom:
                new CustomImagesDemo(Demo.getCustomImages())
                        .show(this);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void initViews() {
        for (int i = 0; i < ids.length; i++) {
            SimpleDraweeView drawee = (SimpleDraweeView) findViewById(ids[i]);
            initDrawee(drawee, i);

        }
    }

    private void initDrawee(SimpleDraweeView drawee, final int startPosition) {
        drawee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPicker(startPosition);
            }
        });
        drawee.setImageURI(posters[startPosition]);
    }

    private void showPicker(int startPosition) {
        overlayView = new ImageOverlayView(this);
        new ImageViewer.Builder<>(MainActivity.this, posters)
                .setStartPosition(startPosition)
                .hideStatusBar(false)
                .setImageMargin(this, R.dimen.image_margin)
                .setImageChangeListener(getImageChangeListener())
                .setOnDismissListener(getDismissListener())
                .setCustomDraweeHierarchyBuilder(getHierarchyBuilder())
                .setOverlayView(overlayView)
                .show();
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

            }
        };
    }

    private GenericDraweeHierarchyBuilder getHierarchyBuilder() {
        RoundingParams roundingParams = new RoundingParams();
        roundingParams.setRoundAsCircle(true);

        return GenericDraweeHierarchyBuilder.newInstance(getResources());
//                .setRoundingParams(roundingParams);
    }
}
