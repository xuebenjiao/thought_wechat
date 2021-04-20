package com.thoughtwork.base.customview;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import androidx.annotation.Nullable;

/**
 * Time :2021/3/25
 * Author:xbj
 * Description :
 */
public class ImpactFontTextView extends TextView {
    public ImpactFontTextView(Context context) {
        super(context);
    }

    public ImpactFontTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ImpactFontTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ImpactFontTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private Typeface createTypeface(Context context, String fontPath) {
        return Typeface.createFromAsset(context.getAssets(), fontPath);
    }

    @Override
    public void setTypeface(@Nullable Typeface tf, int style) {
        super.setTypeface(createTypeface(getContext(), "Impact.ttf"), style);
    }
}
