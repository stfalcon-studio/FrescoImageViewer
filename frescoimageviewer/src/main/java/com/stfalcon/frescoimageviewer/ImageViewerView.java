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
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.RelativeLayout;

import java.util.ArrayList;

/*
 * Created by Alexander Krol (troy379) on 29.08.16.
 */
class ImageViewerView extends RelativeLayout
        implements OnDismissListener, SwipeToDismissListener.OnViewMoveListener {

    private View backgroundView;
    private MultiTouchViewPager pager;
    private ImageViewerAdapter adapter;
    private SwipeDirectionDetector directionDetector;
    private ScaleGestureDetector scaleDetector;

    private View dismissContainer;
    private SwipeToDismissListener swipeDismissListener;

    private SwipeDirectionDetector.Direction direction;

    private boolean wasScaled;
    private OnDismissListener onDismissListener;

    public ImageViewerView(Context context) {
        super(context);
        init();
    }

    public ImageViewerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ImageViewerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void setUrls(ArrayList<String> urls, int startPosition) {
        adapter = new ImageViewerAdapter(getContext(), urls);
        pager.setAdapter(adapter);
        setStartPosition(startPosition);
    }

    @Override
    public void setBackgroundColor(int color) {
        findViewById(R.id.backgroundView)
                .setBackgroundColor(color);
    }

    private void init() {
        inflate(getContext(), R.layout.image_viewer, this);

        backgroundView = findViewById(R.id.backgroundView);
        pager = (MultiTouchViewPager) findViewById(R.id.pager);

        dismissContainer = findViewById(R.id.container);
        swipeDismissListener = new SwipeToDismissListener(findViewById(R.id.dismissView), this, this);
        dismissContainer.setOnTouchListener(swipeDismissListener);

        directionDetector = new SwipeDirectionDetector(getContext()) {
            @Override
            public void onDirectionDetected(Direction direction) {
                ImageViewerView.this.direction = direction;
            }
        };

        scaleDetector = new ScaleGestureDetector(getContext(),
                new ScaleGestureDetector.SimpleOnScaleGestureListener());
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        scaleDetector.onTouchEvent(event);

        if (event.getAction() == MotionEvent.ACTION_UP) {
            direction = null;
            wasScaled = false;
            pager.dispatchTouchEvent(event);
            swipeDismissListener.onTouch(dismissContainer, event);
        }

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            swipeDismissListener.onTouch(dismissContainer, event);
            pager.dispatchTouchEvent(event);
        }

        if (direction == null) {
            if (scaleDetector.isInProgress() || event.getPointerCount() > 1) {
                wasScaled = true;
                return pager.dispatchTouchEvent(event);
            }
        }

        if (!adapter.isScaled(pager.getCurrentItem())) {
            directionDetector.onTouchEvent(event);
            if (direction != null) {
                switch (direction) {
                    case UP:
                    case DOWN:
                        if (!wasScaled && pager.isScrolled()) {
                            return swipeDismissListener.onTouch(dismissContainer, event);
                        } else break;
                    case LEFT:
                    case RIGHT:
                        return pager.dispatchTouchEvent(event);
                }
            }
            return true;
        }
        return super.dispatchTouchEvent(event);
    }

    @Override
    public void onDismiss() {
        if (onDismissListener != null) {
            onDismissListener.onDismiss();
        }
    }

    @Override
    public void onViewMove(float translationY, int translationLimit) {
        float alpha = 1.0f - (1.0f / translationLimit / 4) * Math.abs(translationY);
        backgroundView.setAlpha(alpha);
    }

    public void setOnDismissListener(OnDismissListener onDismissListener) {
        this.onDismissListener = onDismissListener;
    }

    public void resetScale() {
        adapter.resetScale(pager.getCurrentItem());
    }

    public boolean isScaled() {
        return adapter.isScaled(pager.getCurrentItem());
    }

    private void setStartPosition(int position) {
        pager.setCurrentItem(position);
    }

}
