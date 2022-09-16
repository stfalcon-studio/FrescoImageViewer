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

package com.stfalcon.frescoimageviewer.drawee;

import android.content.Context;
import android.graphics.Canvas;
import androidx.annotation.NonNull;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;

import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.view.SimpleDraweeView;

import me.relex.photodraweeview.IAttacher;
import me.relex.photodraweeview.OnPhotoTapListener;
import me.relex.photodraweeview.OnScaleChangeListener;
import me.relex.photodraweeview.OnViewTapListener;

/*
 * Created by Alexander Krol (troy379) on 29.08.16.
 */
public class ZoomableDraweeView extends SimpleDraweeView implements IAttacher {

    private NonInterceptableAttacher attacher;

    public ZoomableDraweeView(Context context, GenericDraweeHierarchy hierarchy) {
        super(context, hierarchy);
        init();
    }

    public ZoomableDraweeView(Context context) {
        super(context);
        init();
    }

    public ZoomableDraweeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ZoomableDraweeView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    protected void init() {
        if (attacher == null || attacher.getDraweeView() == null) {
            attacher = new NonInterceptableAttacher(this);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        return super.onTouchEvent(event);
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        int saveCount = canvas.save();
        canvas.concat(attacher.getDrawMatrix());
        super.onDraw(canvas);
        canvas.restoreToCount(saveCount);
    }

    @Override
    protected void onAttachedToWindow() {
        init();
        super.onAttachedToWindow();
    }

    @Override
    protected void onDetachedFromWindow() {
        attacher.onDetachedFromWindow();
        super.onDetachedFromWindow();
    }

    @Override
    public float getMinimumScale() {
        return attacher.getMinimumScale();
    }

    @Override
    public float getMediumScale() {
        return attacher.getMediumScale();
    }

    @Override
    public float getMaximumScale() {
        return attacher.getMaximumScale();
    }

    @Override
    public void setMinimumScale(float minimumScale) {
        attacher.setMinimumScale(minimumScale);
    }

    @Override
    public void setMediumScale(float mediumScale) {
        attacher.setMediumScale(mediumScale);
    }

    @Override
    public void setMaximumScale(float maximumScale) {
        attacher.setMaximumScale(maximumScale);
    }

    @Override
    public float getScale() {
        return attacher.getScale();
    }

    @Override
    public void setScale(float scale) {
        attacher.setScale(scale);
    }

    @Override
    public void setScale(float scale, boolean animate) {
        setScale(scale, getRight() / 2, getBottom() / 2, animate);
    }

    @Override
    public void setScale(float scale, float focalX, float focalY, boolean animate) {
        attacher.setScale(scale, focalX, focalY, animate);
    }

    @Override
    public void setZoomTransitionDuration(long duration) {
        attacher.setZoomTransitionDuration(duration);
    }

    @Override
    public void setAllowParentInterceptOnEdge(boolean allow) {
        attacher.setAllowParentInterceptOnEdge(allow);
    }

    @Override
    public void setOnDoubleTapListener(GestureDetector.OnDoubleTapListener listener) {
        attacher.setOnDoubleTapListener(listener);
    }

    @Override
    public void setOnScaleChangeListener(OnScaleChangeListener listener) {
        attacher.setOnScaleChangeListener(listener);
    }

    @Override
    public void setOnLongClickListener(OnLongClickListener listener) {
        attacher.setOnLongClickListener(listener);
    }

    @Override
    public void setOnPhotoTapListener(OnPhotoTapListener listener) {
        attacher.setOnPhotoTapListener(listener);
    }

    @Override
    public void setOnViewTapListener(OnViewTapListener listener) {
        attacher.setOnViewTapListener(listener);
    }

    @Override
    public OnPhotoTapListener getOnPhotoTapListener() {
        return attacher.getOnPhotoTapListener();
    }

    @Override
    public OnViewTapListener getOnViewTapListener() {
        return attacher.getOnViewTapListener();
    }

    @Override
    public void update(int imageInfoWidth, int imageInfoHeight) {
        attacher.update(imageInfoWidth, imageInfoHeight);
    }
}
