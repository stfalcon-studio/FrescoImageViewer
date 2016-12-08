package com.stfalcon.frescoimageviewer.adapter;

import android.content.Context;
import android.graphics.drawable.Animatable;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeControllerBuilder;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.imagepipeline.image.ImageInfo;
import com.stfalcon.frescoimageviewer.drawee.ZoomableDraweeView;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import me.relex.photodraweeview.OnScaleChangeListener;

/*
 * Created by troy379 on 07.12.16.
 */
public class ImageViewerAdapter
        extends RecyclingPagerAdapter<ImageViewerAdapter.ImageViewHolder> {

    private Context context;
    private List<String> urls;
    private HashSet<ImageViewHolder> holders;
    private GenericDraweeHierarchyBuilder hierarchyBuilder;

    public ImageViewerAdapter(Context context, List<String> urls,
                              GenericDraweeHierarchyBuilder hierarchyBuilder) {
        this.context = context;
        this.urls = urls;
        this.holders = new HashSet<>();
        this.hierarchyBuilder = hierarchyBuilder;
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ImageViewHolder holder = new ImageViewHolder(new ZoomableDraweeView(context));
        holders.add(holder);
        return holder;
    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return urls.size();
    }


    public boolean isScaled(int index) {
        for (ImageViewHolder holder : holders) {
            if (holder.position == index) {
                return holder.isScaled;
            }
        }
        return false;
    }

    public void resetScale(int index) {
        for (ImageViewHolder holder : holders) {
            if (holder.position == index) {
                holder.resetScale();
                break;
            }
        }
    }

    public String getUrl(int index) {
        return urls.get(index);
    }

    private BaseControllerListener<ImageInfo>
    getDraweeControllerListener(final ZoomableDraweeView drawee) {
        return new BaseControllerListener<ImageInfo>() {
            @Override
            public void onFinalImageSet(String id, ImageInfo imageInfo, Animatable animatable) {
                super.onFinalImageSet(id, imageInfo, animatable);
                if (imageInfo == null) {
                    return;
                }
                drawee.update(imageInfo.getWidth(), imageInfo.getHeight());
            }
        };
    }

    class ImageViewHolder extends ViewHolder implements OnScaleChangeListener {

        private int position = -1;
        private ZoomableDraweeView drawee;
        private boolean isScaled;

        ImageViewHolder(View itemView) {
            super(itemView);
            drawee = (ZoomableDraweeView) itemView;
        }

        void bind(int position) {
            this.position = position;

            tryToSetHierarchy();
            setController(urls.get(position));

            drawee.setOnScaleChangeListener(this);
        }

        @Override
        public void onScaleChange(float scaleFactor, float focusX, float focusY) {
            isScaled = drawee.getScale() > 1.0f;
        }

        public void resetScale() {
            drawee.setScale(1.0f, true);
        }

        private void tryToSetHierarchy() {
            if (hierarchyBuilder != null) {
                hierarchyBuilder.setActualImageScaleType(ScalingUtils.ScaleType.FIT_CENTER);
                drawee.setHierarchy(hierarchyBuilder.build());
            }
        }

        private void setController(String url) {
            PipelineDraweeControllerBuilder controllerBuilder = Fresco.newDraweeControllerBuilder();
            controllerBuilder.setUri(url);
            controllerBuilder.setOldController(drawee.getController());
            controllerBuilder.setControllerListener(getDraweeControllerListener(drawee));
            drawee.setController(controllerBuilder.build());
        }

    }
}
