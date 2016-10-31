package com.stfalcon.frescoimageviewersample;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.facebook.imagepipeline.decoder.SimpleProgressiveJpegConfig;

/*
 * Created by Alexander Krol (troy379) on 29.08.16.
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

//        enable this configuration, if you expect to open really large images
//        ImagePipelineConfig config = ImagePipelineConfig.newBuilder(this)
//                .setProgressiveJpegConfig(new SimpleProgressiveJpegConfig())
//                .build();
//        Fresco.initialize(this, config);

        Fresco.initialize(this);
    }
}
