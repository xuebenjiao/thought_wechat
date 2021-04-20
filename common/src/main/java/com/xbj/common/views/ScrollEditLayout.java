package com.xbj.common.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

public class ScrollEditLayout extends ScrollView {

    public ScrollEditLayout(Context context) {
        super(context);
    }

    public ScrollEditLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ScrollEditLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return false;
    }
}
