package cn.chronos.image;

import android.support.annotation.IntDef;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.PixelUtil;
import com.facebook.react.uimanager.events.Event;
import com.facebook.react.uimanager.events.RCTEventEmitter;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


/**
 * 用于处理ImageAndroid的事件回调
 * <p/>
 * Created by chronos on 16/3/12.
 */


public class ImageAndroidLoadEvent extends Event<ImageAndroidLoadEvent> {
    @IntDef({ON_ERROR, ON_LOAD, ON_LOAD_END, ON_LOAD_START, ON_PROGRESS})
    @Retention(RetentionPolicy.SOURCE)
    @interface ImageEventType {
    }

    // Currently ON_ERROR and ON_PROGRESS are not implemented, these can be added
    // easily once support exists in fresco.
    public static final int ON_ERROR = 1;
    public static final int ON_LOAD = 2;
    public static final int ON_LOAD_END = 3;
    public static final int ON_LOAD_START = 4;
    public static final int ON_PROGRESS = 5;

    private final int mEventType;
    private final int mWidth;
    private final int mHeight;

    public ImageAndroidLoadEvent(int viewId, long timestampMs, @ImageEventType int eventType) {
        super(viewId, timestampMs);
        mEventType = eventType;
        mWidth = 0;
        mHeight = 0;
    }

    public ImageAndroidLoadEvent(int viewId, long timestampMs, @ImageEventType int eventType, int width, int height) {
        super(viewId, timestampMs);
        mEventType = eventType;
        // 输出宽高
        mWidth = width;
        mHeight = height;
    }

    public static String eventNameForType(@ImageEventType int eventType) {
        switch (eventType) {
            case ON_ERROR:
                return "topError";
            case ON_LOAD:
                return "topLoad";
            case ON_LOAD_END:
                return "topLoadEnd";
            case ON_LOAD_START:
                return "topLoadStart";
            case ON_PROGRESS:
                return "topProgress";
            default:
                throw new IllegalStateException("Invalid image event: " + Integer.toString(eventType));
        }
    }

    @Override
    public String getEventName() {
        return ImageAndroidLoadEvent.eventNameForType(mEventType);
    }

    @Override
    public short getCoalescingKey() {
        // Intentionally casting mEventType because it is guaranteed to be small
        // enough to fit into short.
        return (short) mEventType;
    }

    @Override
    public void dispatch(RCTEventEmitter rctEventEmitter) {
        rctEventEmitter.receiveEvent(getViewTag(), getEventName(), serializeEventData());
    }

    // 输出宽高值
    private WritableMap serializeEventData() {
        WritableMap event = Arguments.createMap();
        event.putDouble("width", mWidth);
        event.putDouble("height", mHeight);
        return event;
    }
}
