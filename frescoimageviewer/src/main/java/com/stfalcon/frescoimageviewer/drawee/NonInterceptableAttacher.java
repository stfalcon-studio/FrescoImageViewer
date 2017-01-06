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

import android.view.ViewParent;

import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.view.DraweeView;

import me.relex.photodraweeview.Attacher;
import me.relex.photodraweeview.OnScaleChangeListener;

/*
 * Created by Alexander Krol (troy379) on 29.08.16.
 */
class NonInterceptableAttacher extends Attacher {

    private OnScaleChangeListener scaleChangeListener;

    public NonInterceptableAttacher(DraweeView<GenericDraweeHierarchy> draweeView) {
        super(draweeView);
    }

    @Override
    public void onDrag(float dx, float dy) {
        DraweeView<GenericDraweeHierarchy> draweeView = getDraweeView();
        if (draweeView != null) {
            getDrawMatrix().postTranslate(dx, dy);
            checkMatrixAndInvalidate();

            ViewParent parent = draweeView.getParent();
            if (parent == null) {
                return;
            }

            if (getScale() == 1.0f) {
                parent.requestDisallowInterceptTouchEvent(false);
            } else {
                parent.requestDisallowInterceptTouchEvent(true);
            }
        }
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    @Override
    public void setOnScaleChangeListener(OnScaleChangeListener listener) {
        this.scaleChangeListener = listener;
    }

    @Override
    public void onScale(float scaleFactor, float focusX, float focusY) {
        super.onScale(scaleFactor, focusX, focusY);
        if (scaleChangeListener != null) {
            scaleChangeListener.onScaleChange(scaleFactor, focusX, focusY);
        }
    }
}