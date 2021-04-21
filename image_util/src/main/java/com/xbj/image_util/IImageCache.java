package com.xbj.image_util;

import android.graphics.Bitmap;

/**
 * Time :2021/4/21
 * Author:xbj
 * Description :图片缓存接口(主要是缓存bitmap)
 */
public interface IImageCache {
    /**
     *
     * @param url 图片连接
     * @param bitmap 图片链接对应的bitmap
     */
    public void putCache(String url, Bitmap bitmap);

    /**
     * 根据图片连接地址获取图片缓存
     * @param url
     * @return
     */
    public Bitmap getCache(String url);
}
