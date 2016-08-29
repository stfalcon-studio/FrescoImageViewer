package com.stfalcon.frescoimageviewersample;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

/*
 * Created by troy379 on 29.08.16.
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Fresco.initialize(this);
    }
}
