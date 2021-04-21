package com.xbj.common;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.xbj.common.R;
import com.xbj.image_util.ImageLoaderUtil;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

/**
 * Created by xbj on 2020/7/20.
 * 保留所有版权，未经允许请不要分享到互联网和其他人
 */
public class CommonBindingAdapters {
    @BindingAdapter("android:visibility")
    public static void setVisibility(View view, Boolean value) {
        view.setVisibility(value ? View.VISIBLE : View.GONE);
    }
    @BindingAdapter({"android:src"})
    public static void setImageViewResource(ImageView imageView, int resource) {
        imageView.setImageResource(resource);
    }

    @BindingAdapter({"android:textColor"})
    public static void setTextViewContentColor(TextView tv, int color){
        tv.setTextColor(color);
    }

    @BindingAdapter("loadImageUrl")
    public static void loadImageUrl(ImageView imageView, String pitureUrl){
        if(!TextUtils.isEmpty(pitureUrl)) {
            ImageLoaderUtil.getInstance().displayImage(pitureUrl,imageView);
        }
    }
}
