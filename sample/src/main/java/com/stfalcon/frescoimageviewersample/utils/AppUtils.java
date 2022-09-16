package com.stfalcon.frescoimageviewersample.utils;

import android.content.Context;
import android.os.Handler;
import androidx.annotation.StringRes;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.stfalcon.frescoimageviewersample.R;

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

    public static void showInfoSnackbar(final View view, final @StringRes int text, final boolean isLong) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                multilineSnackbar(
                        Snackbar.make(
                                view, text,
                                isLong ? BaseTransientBottomBar.LENGTH_LONG : BaseTransientBottomBar.LENGTH_SHORT)
                ).show();
            }
        }, 100);
    }

    public static void showGotItSnackbar(final View view, final @StringRes int text) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                multilineSnackbar(
                        Snackbar.make(
                                view, text, BaseTransientBottomBar.LENGTH_INDEFINITE)
                                .setAction(R.string.got_it, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                    }
                                })
                ).show();
            }
        }, 200);
    }

    private static Snackbar multilineSnackbar(Snackbar snackbar) {
        TextView textView = snackbar.getView().findViewById(com.google.android.material.R.id.snackbar_text);
        textView.setMaxLines(5);
        return snackbar;
    }
}
