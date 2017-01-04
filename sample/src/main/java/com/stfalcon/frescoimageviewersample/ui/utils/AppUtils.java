package com.stfalcon.frescoimageviewersample.ui.utils;

import android.content.Context;
import android.support.annotation.StringRes;
import android.widget.Toast;

/*
 * Created by troy379 on 27.12.16.
 */
public final class AppUtils {
    private AppUtils() {
        throw new AssertionError();
    }

    public static void showToast(Context context, @StringRes int text, boolean isLong) {
        showToast(context, context.getString(text), isLong);
    }

    public static void showToast(Context context, String text, boolean isLong) {
        Toast.makeText(context, text, isLong ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT).show();
    }
}
