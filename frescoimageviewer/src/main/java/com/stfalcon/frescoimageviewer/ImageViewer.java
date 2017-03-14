/*
 * Copyright (C) 2016 stfalcon.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.stfalcon.frescoimageviewer;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.annotation.StyleRes;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;

import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
 * Created by Alexander Krol (troy379) on 29.08.16.
 */
public class ImageViewer implements OnDismissListener, DialogInterface.OnKeyListener {

    private static final String TAG = ImageViewer.class.getSimpleName();

    private Builder builder;
    private AlertDialog dialog;
    private ImageViewerView viewer;

    protected ImageViewer(Builder builder) {
        this.builder = builder;
        createDialog();
    }

    /**
     * Displays the built viewer if passed images list isn't empty
     */
    public void show() {
        if (!builder.dataSet.data.isEmpty()) {
            dialog.show();
        } else {
            Log.w(TAG, "Images list cannot be empty! Viewer ignored.");
        }
    }

    public String getUrl() {
        return viewer.getUrl();
    }

    private void createDialog() {
        viewer = new ImageViewerView(builder.context);
        viewer.setCustomImageRequestBuilder(builder.customImageRequestBuilder);
        viewer.setCustomDraweeHierarchyBuilder(builder.customHierarchyBuilder);
        viewer.allowZooming(builder.isZoomingAllowed);
        viewer.allowSwipeToDismiss(builder.isSwipeToDismissAllowed);
        viewer.setOnDismissListener(this);
        viewer.setBackgroundColor(builder.backgroundColor);
        viewer.setOverlayView(builder.overlayView);
        viewer.setImageMargin(builder.imageMarginPixels);
        viewer.setContainerPadding(builder.containerPaddingPixels);
        viewer.setUrls(builder.dataSet, builder.startPosition);
        viewer.setPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                if (builder.imageChangeListener != null) {
                    builder.imageChangeListener.onImageChange(position);
                }
            }
        });

        dialog = new AlertDialog.Builder(builder.context, getDialogStyle())
                .setView(viewer)
                .setOnKeyListener(this)
                .create();
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                if (builder.onDismissListener != null) {
                    builder.onDismissListener.onDismiss();
                }
            }
        });
    }

    /**
     * Fires when swipe to dismiss was initiated
     */
    @Override
    public void onDismiss() {
        dialog.dismiss();
    }

    /**
     * Resets image on {@literal KeyEvent.KEYCODE_BACK} to normal scale if needed, otherwise - hide the viewer.
     */
    @Override
    public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK &&
                event.getAction() == KeyEvent.ACTION_UP &&
                !event.isCanceled()) {
            if (viewer.isScaled()) {
                viewer.resetScale();
            } else {
                dialog.cancel();
            }
        }
        return true;
    }

    /**
     * Creates new {@code ImageRequestBuilder}.
     */
    public static ImageRequestBuilder createImageRequestBuilder() {
        return ImageRequestBuilder.newBuilderWithSource(Uri.parse(""));
    }

    /**
     * Interface definition for a callback to be invoked when image was changed
     */
    public interface OnImageChangeListener {
        void onImageChange(int position);
    }

    /**
     * Interface definition for a callback to be invoked when viewer was dismissed
     */
    public interface OnDismissListener {
        void onDismiss();
    }

    private @StyleRes int getDialogStyle() {
        return builder.shouldStatusBarHide
                ? android.R.style.Theme_Translucent_NoTitleBar_Fullscreen
                : android.R.style.Theme_Translucent_NoTitleBar;
    }

    /**
     * Interface used to format custom objects into an image url.
     */
    public interface Formatter<T> {

        /**
         * Formats an image url representation of the object.
         *
         * @param t The object that needs to be formatted into url.
         * @return An url of image.
         */
        String format(T t);
    }

    static class DataSet<T> {

        private List<T> data;
        private Formatter<T> formatter;

        DataSet(List<T> data) {
            this.data = data;
        }

        String format(int position) {
            return format(data.get(position));
        }

        String format(T t) {
            if (formatter == null) return t.toString();
            else return formatter.format(t);
        }

        public List<T> getData() {
            return data;
        }
    }

    /**
     * Builder class for {@link ImageViewer}
     */
    public static class Builder<T> {

        private Context context;
        private DataSet<T> dataSet;
        private @ColorInt int backgroundColor = Color.BLACK;
        private int startPosition;
        private OnImageChangeListener imageChangeListener;
        private OnDismissListener onDismissListener;
        private View overlayView;
        private int imageMarginPixels;
        private int[] containerPaddingPixels = new int[4];
        private ImageRequestBuilder customImageRequestBuilder;
        private GenericDraweeHierarchyBuilder customHierarchyBuilder;
        private boolean shouldStatusBarHide = true;
        private boolean isZoomingAllowed = true;
        private boolean isSwipeToDismissAllowed = true;

        /**
         * Constructor using a context and images urls array for this builder and the {@link ImageViewer} it creates.
         */
        public Builder(Context context, T[] images) {
            this(context, new ArrayList<>(Arrays.asList(images)));
        }

        /**
         * Constructor using a context and images urls list for this builder and the {@link ImageViewer} it creates.
         */
        public Builder(Context context, List<T> images) {
            this.context = context;
            this.dataSet = new DataSet<>(images);
        }

        /**
         * If you use an non-string collection, you can use custom {@link Formatter} to represent it as url.
         */
        public Builder setFormatter(Formatter<T> formatter) {
            this.dataSet.formatter = formatter;
            return this;
        }

        /**
         * Set background color resource for viewer
         *
         * @return This Builder object to allow for chaining of calls to set methods
         */
        @SuppressWarnings("deprecation")
        public Builder setBackgroundColorRes(@ColorRes int color) {
            return this.setBackgroundColor(context.getResources().getColor(color));
        }

        /**
         * Set background color int for viewer
         *
         * @return This Builder object to allow for chaining of calls to set methods
         */
        public Builder setBackgroundColor(@ColorInt int color) {
            this.backgroundColor = color;
            return this;
        }

        /**
         * Set background color int for viewer
         *
         * @return This Builder object to allow for chaining of calls to set methods
         */
        public Builder setStartPosition(int position) {
            this.startPosition = position;
            return this;
        }

        /**
         * Set {@link ImageViewer.OnImageChangeListener} for viewer.
         *
         * @return This Builder object to allow for chaining of calls to set methods
         */
        public Builder setImageChangeListener(OnImageChangeListener imageChangeListener) {
            this.imageChangeListener = imageChangeListener;
            return this;
        }

        /**
         * Set overlay view
         *
         * @return This Builder object to allow for chaining of calls to set methods
         */
        public Builder setOverlayView(View view) {
            this.overlayView = view;
            return this;
        }

        /**
         * Set space between the images in px.
         *
         * @return This Builder object to allow for chaining of calls to set methods
         */
        public Builder setImageMarginPx(int marginPixels) {
            this.imageMarginPixels = marginPixels;
            return this;
        }

        /**
         * Set space between the images using dimension.
         *
         * @return This Builder object to allow for chaining of calls to set methods
         */
        public Builder setImageMargin(Context context, @DimenRes int dimen) {
            this.imageMarginPixels = Math.round(context.getResources().getDimension(dimen));
            return this;
        }

        /**
         * Set {@code start}, {@code top}, {@code end} and {@code bottom} padding for zooming and scrolling area in px.
         *
         * @return This Builder object to allow for chaining of calls to set methods
         */
        public Builder setContainerPaddingPx(int start, int top, int end, int bottom) {
            this.containerPaddingPixels = new int[]{start, top, end, bottom};
            return this;
        }

        /**
         * Set {@code start}, {@code top}, {@code end} and {@code bottom} padding for zooming and scrolling area using dimension.
         *
         * @return This Builder object to allow for chaining of calls to set methods
         */
        public Builder setContainerPadding(Context context,
                                           @DimenRes int start, @DimenRes int top,
                                           @DimenRes int end, @DimenRes int bottom) {
            setContainerPaddingPx(
                    Math.round(context.getResources().getDimension(start)),
                    Math.round(context.getResources().getDimension(top)),
                    Math.round(context.getResources().getDimension(end)),
                    Math.round(context.getResources().getDimension(bottom))
            );
            return this;
        }

        /**
         * Set common padding for zooming and scrolling area in px.
         *
         * @return This Builder object to allow for chaining of calls to set methods
         */
        public Builder setContainerPaddingPx(int padding) {
            this.containerPaddingPixels = new int[]{padding, padding, padding, padding};
            return this;
        }

        /**
         * Set common padding for zooming and scrolling area using dimension.
         *
         * @return This Builder object to allow for chaining of calls to set methods
         */
        public Builder setContainerPadding(Context context, @DimenRes int padding) {
            int paddingPx = Math.round(context.getResources().getDimension(padding));
            setContainerPaddingPx(paddingPx, paddingPx, paddingPx, paddingPx);
            return this;
        }

        /**
         * Set status bar visibility. By default is true.
         *
         * @return This Builder object to allow for chaining of calls to set methods
         */
        public Builder hideStatusBar(boolean shouldHide) {
            this.shouldStatusBarHide = shouldHide;
            return this;
        }

        /**
         * Allow or disallow zooming. By default is true.
         *
         * @return This Builder object to allow for chaining of calls to set methods
         */
        public Builder allowZooming(boolean value) {
            this.isZoomingAllowed = value;
            return this;
        }

        /**
         * Allow or disallow swipe to dismiss gesture. By default is true.
         *
         * @return This Builder object to allow for chaining of calls to set methods
         */
        public Builder allowSwipeToDismiss(boolean value) {
            this.isSwipeToDismissAllowed = value;
            return this;
        }

        /**
         * Set {@link ImageViewer.OnDismissListener} for viewer.
         *
         * @return This Builder object to allow for chaining of calls to set methods
         */
        public Builder setOnDismissListener(OnDismissListener onDismissListener) {
            this.onDismissListener = onDismissListener;
            return this;
        }

        /**
         * Set @{@code ImageRequestBuilder} for drawees. Use it for post-processing, custom resize options etc.
         * Use {@link ImageViewer#createImageRequestBuilder()} to create its new instance.
         *
         * @return This Builder object to allow for chaining of calls to set methods
         */
        public Builder setCustomImageRequestBuilder(ImageRequestBuilder customImageRequestBuilder) {
            this.customImageRequestBuilder = customImageRequestBuilder;
            return this;
        }

        /**
         * Set {@link GenericDraweeHierarchyBuilder} for drawees inside viewer.
         * Use it for drawee customizing (e.g. failure image, placeholder, progressbar etc.)
         * N.B.! Due to zoom logic there is limitation of scale type which always equals FIT_CENTER. Other values will be ignored
         *
         * @return This Builder object to allow for chaining of calls to set methods
         */
        public Builder setCustomDraweeHierarchyBuilder(GenericDraweeHierarchyBuilder customHierarchyBuilder) {
            this.customHierarchyBuilder = customHierarchyBuilder;
            return this;
        }

        /**
         * Creates a {@link ImageViewer} with the arguments supplied to this builder. It does not
         * {@link ImageViewer#show()} the dialog. This allows the user to do any extra processing
         * before displaying the dialog. Use {@link #show()} if you don't have any other processing
         * to do and want this to be created and displayed.
         */
        public ImageViewer build() {
            return new ImageViewer(this);
        }

        /**
         * Creates a {@link ImageViewer} with the arguments supplied to this builder and
         * {@link ImageViewer#show()}'s the dialog.
         */
        public ImageViewer show() {
            ImageViewer dialog = build();
            dialog.show();
            return dialog;
        }
    }
}
