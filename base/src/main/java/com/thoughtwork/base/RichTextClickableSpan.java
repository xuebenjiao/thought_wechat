package com.thoughtwork.base;

import android.content.Context;
import android.graphics.Color;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;

import androidx.annotation.NonNull;

/**
 * Time :2020/5/18
 * Author:xbj
 * Description : TextView显示富文本时 需要富文本内容点击处理
 */
public class RichTextClickableSpan extends ClickableSpan {
    private RichTextClickCallBack mCallBack;
    private int mClickTextColor ;
    private String mClickTextUrl = "";
    private boolean isNeedLineText = false;
    private Context context;
    public RichTextClickableSpan(){

    }
    public RichTextClickableSpan(Context context, int color) {
        this.mClickTextColor = color;
        this.context = context;
    }
    public RichTextClickableSpan(Context context, int color, String url) {
        this.mClickTextColor = color;
        this.mClickTextUrl = url;
        this.context = context;
    }

    public RichTextClickableSpan(Context context, int color,boolean needLineText,RichTextClickCallBack mCallBack) {
        this.mClickTextColor = color;
        this.isNeedLineText = needLineText;
        this.context = context;
        this.mCallBack = mCallBack;
    }
    @Override
    public void updateDrawState(TextPaint ds) {
        //是否显示下划线
        ds.setUnderlineText(isNeedLineText);
        //文本的颜色
//        ds.setColor(Color.parseColor(mClickTextColor));
        ds.setColor(mClickTextColor);
    }

    @Override
    public void onClick(@NonNull View widget) {
        if(mCallBack != null){
            mCallBack.onClick(widget);
        }
    }
    public interface RichTextClickCallBack{
        void onClick(View view);
    }
}
