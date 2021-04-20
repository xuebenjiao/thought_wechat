package com.xbj.common.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

/**
 * Time :2019/12/10
 * Author:xbj
 * Description :自定义ViewPager去重左右滑动
 */
public class CustomScrollViewPager extends ViewPager {
    //是否可以左右滑动？true 可以，像Android原生ViewPager一样。
    // false 禁止ViewPager左右滑动。
    private boolean scrollable = true;

    public CustomScrollViewPager(@NonNull Context context) {
        super(context);
    }

    public CustomScrollViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public void setScrollable(boolean scrollable) {
        this.scrollable = scrollable;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if(scrollable) {
           return super.onInterceptTouchEvent(ev);
        }
        else {
            return scrollable;//false 不拦截让子View处理滑动事件  true 拦截
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (scrollable){
            return super.onTouchEvent(ev);
        }else{
            return scrollable;
        }
    }
}
