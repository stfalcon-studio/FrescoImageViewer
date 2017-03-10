package com.stfalcon.frescoimageviewersample.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import com.stfalcon.frescoimageviewersample.R;

import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

/*
 * Created by troy379 on 10.03.17.
 */
public class StylingOptions {

    private Map<Property, Boolean> options = new TreeMap<Property, Boolean>() {{
        put(Property.HIDE_STATUS_BAR, true);
        put(Property.IMAGE_MARGIN, true);
        put(Property.CONTAINER_PADDING, false);
        put(Property.IMAGES_ROUNDING, false);
        put(Property.SWIPE_TO_DISMISS, true);
        put(Property.ZOOMING, true);
        put(Property.SHOW_OVERLAY, true);
        put(Property.RANDOM_BACKGROUND, false);
    }};

    public boolean get(Property property) {
        return options.get(property);
    }

    public void showDialog(Context context) {
        AlertDialog dialog = new AlertDialog.Builder(context)
                .setMultiChoiceItems(
                        context.getResources().getStringArray(R.array.options),
                        toPrimitiveBooleanArray(options.values()),
                        new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int indexSelected, boolean isChecked) {
                                options.put(Property.values()[indexSelected], isChecked);
                            }
                        }).create();
        dialog.show();
    }

    public enum Property {
        HIDE_STATUS_BAR,
        IMAGE_MARGIN,
        CONTAINER_PADDING,
        IMAGES_ROUNDING,
        SWIPE_TO_DISMISS,
        ZOOMING,
        SHOW_OVERLAY,
        RANDOM_BACKGROUND
    }

    private static boolean[] toPrimitiveBooleanArray(Collection<Boolean> collection) {
        boolean[] array = new boolean[collection.size()];
        int i = 0;
        for (Boolean value : collection) {
            array[i] = value;
            i++;
        }
        return array;
    }
}
