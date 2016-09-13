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
import android.view.MotionEvent;
import android.view.ViewConfiguration;

/*
 * Created by Alexander Krol (troy379) on 29.08.16.
 */
abstract class SwipeDirectionDetector {

    public abstract void onDirectionDetected(Direction direction);

    private int touchSlop;
    private float startX, startY;
    private boolean isDetected;

    public SwipeDirectionDetector(Context context) {
        this.touchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startX = event.getX();
                startY = event.getY();
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                if (!isDetected) {
                    onDirectionDetected(Direction.NOT_DETECTED);
                }
                startX = startY = 0.0f;
                isDetected = false;
                break;
            case MotionEvent.ACTION_MOVE:
                if (!isDetected && getDistance(event) > touchSlop) {
                    isDetected = true;
                    float x = event.getX();
                    float y = event.getY();

                    Direction direction = getDirection(startX, startY, x, y);
                    onDirectionDetected(direction);
                }
                break;
        }
        return false;
    }

    /**
     * Given two points in the plane p1=(x1, x2) and p2=(y1, y1), this method
     * returns the direction that an arrow pointing from p1 to p2 would have.
     *
     * @param x1 the x position of the first point
     * @param y1 the y position of the first point
     * @param x2 the x position of the second point
     * @param y2 the y position of the second point
     * @return the direction
     */
    public Direction getDirection(float x1, float y1, float x2, float y2) {
        double angle = getAngle(x1, y1, x2, y2);
        return Direction.get(angle);
    }

    /**
     * Finds the angle between two points in the plane (x1,y1) and (x2, y2)
     * The angle is measured with 0/360 being the X-axis to the right, angles
     * increase counter clockwise.
     *
     * @param x1 the x position of the first point
     * @param y1 the y position of the first point
     * @param x2 the x position of the second point
     * @param y2 the y position of the second point
     * @return the angle between two points
     */
    public double getAngle(float x1, float y1, float x2, float y2) {
        double rad = Math.atan2(y1 - y2, x2 - x1) + Math.PI;
        return (rad * 180 / Math.PI + 180) % 360;
    }

    private float getDistance(MotionEvent ev) {
        float distanceSum = 0;

        float dx = (ev.getX(0) - startX);
        float dy = (ev.getY(0) - startY);
        distanceSum += Math.sqrt(dx * dx + dy * dy);

        return distanceSum;
    }

    public enum Direction {
        NOT_DETECTED,
        UP,
        DOWN,
        LEFT,
        RIGHT;

        public static Direction get(double angle) {
            if (inRange(angle, 45, 135)) {
                return Direction.UP;
            } else if (inRange(angle, 0, 45) || inRange(angle, 315, 360)) {
                return Direction.RIGHT;
            } else if (inRange(angle, 225, 315)) {
                return Direction.DOWN;
            } else {
                return Direction.LEFT;
            }
        }

        private static boolean inRange(double angle, float init, float end) {
            return (angle >= init) && (angle < end);
        }
    }
}
