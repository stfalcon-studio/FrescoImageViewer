package com.stfalcon.frescoimageviewer;

import com.facebook.drawee.backends.pipeline.Fresco;
import android.content.Context;

public class FrescoWrapper {
    public static void Initialize(Context context)
    {
        Fresco.initialize(context);
    }
}
