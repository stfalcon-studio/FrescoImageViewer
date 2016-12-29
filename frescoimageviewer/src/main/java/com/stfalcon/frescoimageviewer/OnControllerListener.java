package com.stfalcon.frescoimageviewer;

import com.facebook.drawee.backends.pipeline.PipelineDraweeControllerBuilder;
import com.stfalcon.frescoimageviewer.drawee.ZoomableDraweeView;

/**
 * Created by xufeng on 2016/12/29.
 */

public interface OnControllerListener {

    void setController(PipelineDraweeControllerBuilder controllerBuilder, String url, ZoomableDraweeView drawee);
}
