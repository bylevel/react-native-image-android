package cn.chronos.image;


import javax.annotation.Nullable;

import java.util.Map;

import android.graphics.Color;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.AbstractDraweeControllerBuilder;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.ViewProps;
import com.facebook.react.views.image.ImageResizeMode;

public class ImageAndroidManager extends SimpleViewManager<ImageAndroidView> {

    public static final String REACT_CLASS = "RCTImageAndroidView";

    @Override
    public String getName() {
        return REACT_CLASS;
    }

    private
    @Nullable
    AbstractDraweeControllerBuilder mDraweeControllerBuilder;
    private final
    @Nullable
    Object mCallerContext;

    public ImageAndroidManager(
            AbstractDraweeControllerBuilder draweeControllerBuilder,
            Object callerContext) {
        mDraweeControllerBuilder = draweeControllerBuilder;
        mCallerContext = callerContext;
    }

    public ImageAndroidManager() {
        // Lazily initialize as FrescoModule have not been initialized yet
        mDraweeControllerBuilder = null;
        mCallerContext = null;
    }

    public AbstractDraweeControllerBuilder getDraweeControllerBuilder() {
        if (mDraweeControllerBuilder == null) {
            mDraweeControllerBuilder = Fresco.newDraweeControllerBuilder();
        }
        return mDraweeControllerBuilder;
    }

    public Object getCallerContext() {
        return mCallerContext;
    }

    @Override
    public ImageAndroidView createViewInstance(ThemedReactContext context) {
        return new ImageAndroidView(
                context,
                getDraweeControllerBuilder(),
                getCallerContext());
    }

    // In JS this is Image.props.source.uri
    @ReactProp(name = "src")
    public void setSource(ImageAndroidView view, @Nullable String source) {
        view.setSource(source);
    }

    // In JS this is Image.props.loadingIndicatorSource.uri
    @ReactProp(name = "loadingIndicatorSrc")
    public void setLoadingIndicatorSource(ImageAndroidView view, @Nullable String source) {
        view.setLoadingIndicatorSource(source);
    }

    @ReactProp(name = "borderColor", customType = "Color")
    public void setBorderColor(ImageAndroidView view, @Nullable Integer borderColor) {
        if (borderColor == null) {
            view.setBorderColor(Color.TRANSPARENT);
        } else {
            view.setBorderColor(borderColor);
        }
    }

    @ReactProp(name = "overlayColor")
    public void setOverlayColor(ImageAndroidView view, @Nullable Integer overlayColor) {
        if (overlayColor == null) {
            view.setOverlayColor(Color.TRANSPARENT);
        } else {
            view.setOverlayColor(overlayColor);
        }
    }

    @ReactProp(name = "borderWidth")
    public void setBorderWidth(ImageAndroidView view, float borderWidth) {
        view.setBorderWidth(borderWidth);
    }

    @ReactProp(name = "borderRadius")
    public void setBorderRadius(ImageAndroidView view, float borderRadius) {
        view.setBorderRadius(borderRadius);
    }

    @ReactProp(name = ViewProps.RESIZE_MODE)
    public void setResizeMode(ImageAndroidView view, @Nullable String resizeMode) {
        view.setScaleType(ImageResizeMode.toScaleType(resizeMode));
    }

    @ReactProp(name = "tintColor", customType = "Color")
    public void setTintColor(ImageAndroidView view, @Nullable Integer tintColor) {
        if (tintColor == null) {
            view.clearColorFilter();
        } else {
            view.setColorFilter(tintColor);
        }
    }

    @ReactProp(name = "progressiveRenderingEnabled")
    public void setProgressiveRenderingEnabled(ImageAndroidView view, boolean enabled) {
        view.setProgressiveRenderingEnabled(enabled);
    }

    @ReactProp(name = "fadeDuration")
    public void setFadeDuration(ImageAndroidView view, int durationMs) {
        view.setFadeDuration(durationMs);
    }

    @ReactProp(name = "shouldNotifyLoadEvents")
    public void setLoadHandlersRegistered(ImageAndroidView view, boolean shouldNotifyLoadEvents) {
        view.setShouldNotifyLoadEvents(shouldNotifyLoadEvents);
    }

    @Override
    public
    @Nullable
    Map getExportedCustomDirectEventTypeConstants() {
        return MapBuilder.of(
                ImageAndroidLoadEvent.eventNameForType(ImageAndroidLoadEvent.ON_LOAD_START),
                MapBuilder.of("registrationName", "onLoadStart"),
                ImageAndroidLoadEvent.eventNameForType(ImageAndroidLoadEvent.ON_LOAD),
                MapBuilder.of("registrationName", "onLoad"),
                ImageAndroidLoadEvent.eventNameForType(ImageAndroidLoadEvent.ON_LOAD_END),
                MapBuilder.of("registrationName", "onLoadEnd")
        );
    }

    @Override
    protected void onAfterUpdateTransaction(ImageAndroidView view) {
        super.onAfterUpdateTransaction(view);
        view.maybeUpdateView();
    }
}
