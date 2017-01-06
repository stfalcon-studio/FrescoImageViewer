/*
 * Copyright 2015 "Henry Tao <hi@henrytao.me>"
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.stfalcon.frescoimageviewer.adapter;

import android.os.Bundle;
import android.os.Parcelable;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

/*
 * Created by henrytao on 11/13/15.
 */
public abstract class ViewHolder {

    private static final String STATE = ViewHolder.class.getSimpleName();

    public final View itemView;

    boolean mIsAttached;

    int mPosition;

    public ViewHolder(View itemView) {
        if (itemView == null) {
            throw new IllegalArgumentException("itemView should not be null");
        }
        this.itemView = itemView;
    }

    void attach(ViewGroup parent, int position) {
        mIsAttached = true;
        mPosition = position;
        parent.addView(itemView);
    }

    void detach(ViewGroup parent) {
        parent.removeView(itemView);
        mIsAttached = false;
    }

    void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle) {
            Bundle bundle = (Bundle) state;
            SparseArray<Parcelable> ss = bundle.containsKey(STATE) ? bundle.getSparseParcelableArray(STATE) : null;
            if (ss != null) {
                itemView.restoreHierarchyState(ss);
            }
        }
    }

    Parcelable onSaveInstanceState() {
        SparseArray<Parcelable> state = new SparseArray<>();
        itemView.saveHierarchyState(state);
        Bundle bundle = new Bundle();
        bundle.putSparseParcelableArray(STATE, state);
        return bundle;
    }
}