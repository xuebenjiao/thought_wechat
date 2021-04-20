package com.xbj.common;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.xbj.common.R;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

/**
 * Created by xbj on 2020/7/20.
 * 保留所有版权，未经允许请不要分享到互联网和其他人
 */
public class CommonBindingAdapters {
    @BindingAdapter("imageUrl")
    public static void loadImage(ImageView view, String url) {
        if(!TextUtils.isEmpty(url)) {
            Glide.with(view.getContext())
                    .load(url)
                    .transition(withCrossFade())
                    .into(view);
        }
    }
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
            Glide.with(imageView.getContext())
                    .load(pitureUrl)
//                                    .crossFade()
//                                    .transition(withCrossFade())
                    //加载成功前显示的照片
                    .placeholder(R.drawable.place_holder_pic)
                    //出错显示的照片
                    .error(R.drawable.error)
                    //url为空时显示的照片
                    .fallback(R.drawable.place_holder_pic)
                    //解决第一次加载不显示bug
                    .dontAnimate()
                    .into(imageView);
        }
    }
}
