package com.stfalcon.frescoimageviewersample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.facebook.drawee.view.SimpleDraweeView;
import com.stfalcon.frescoimageviewer.ImageViewer;

/*
 * Created by Alexander Krol (troy379) on 29.08.16.
 */
public class MainActivity extends AppCompatActivity {

    private static final String[] posters = {
            "https://pp.vk.me/c630619/v630619423/4637a/vAOodrqPzQM.jpg",
            "https://pp.vk.me/c630619/v630619423/46395/71QKIPW6BWM.jpg",
            "https://pp.vk.me/c630619/v630619423/46383/GOTf1IvHKoc.jpg",
            "https://pp.vk.me/c630619/v630619423/4638c/i1URx2fWj20.jpg",
            "https://pp.vk.me/c630619/v630619423/4639e/BPoHv4xEikA.jpg",
            "https://pp.vk.me/c630619/v630619423/463a7/9EjA0oqA_yQ.jpg",
            "https://pp.vk.me/c630619/v630619423/463b0/VLPAZQJ0kuI.jpg",
            "https://pp.vk.me/c630619/v630619423/463b9/O3-hk8kIvdY.jpg",
            "https://pp.vk.me/c630619/v630619423/463c2/WgtvE0FQwVY.jpg"
    };

    private static final int[] ids = new int[] {
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

        initViews();
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
        new ImageViewer.Builder(MainActivity.this, posters)
                .setStartPosition(startPosition)
                .setImageChangeListener(getImageChangeListener())
                .setOverlayView(overlayView)
                .show();
    }

    private ImageViewer.OnImageChangeListener getImageChangeListener() {
        return new ImageViewer.OnImageChangeListener() {
            @Override
            public void onImageChange(int position) {
                String url = posters[position];
                overlayView.setShareText(url);
                overlayView.setDescription(url);
            }
        };
    }
}
