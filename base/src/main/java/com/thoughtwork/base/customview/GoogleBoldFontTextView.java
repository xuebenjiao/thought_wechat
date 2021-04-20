package com.thoughtwork.base.customview;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import androidx.annotation.Nullable;

/**
 * Time :2020/3/19
 * Author:xbj
 * Description :
 */
public class GoogleBoldFontTextView extends TextView {
    public GoogleBoldFontTextView(Context context) {
        super(context);
    }

    public GoogleBoldFontTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public GoogleBoldFontTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public GoogleBoldFontTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private Typeface createTypeface(Context context, String fontPath) {
        return Typeface.createFromAsset(context.getAssets(), fontPath);
    }

    @Override
    public void setTypeface(@Nullable Typeface tf, int style) {
        super.setTypeface(createTypeface(getContext(), "GoogleSans-Bold.ttf"), style);
    }
}
