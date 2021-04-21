package com.xbj.image_util;

import android.graphics.Bitmap;

import androidx.collection.LruCache;

/**
 * Time :2021/4/21
 * Author:xbj
 * Description :内存缓存工具
 */
public class MemoryCacheUtil implements IImageCache{
    //用来缓存的工具类对象，通过类声明可以看出这个类也是通过key-value来存储对象的
    private LruCache<String, Bitmap> mImageCache;

    public MemoryCacheUtil(){
        initImageCache();
    }
    /**
     *
     * @param url 图片连接
     * @param bitmap 图片链接对应的bitmap
     */
    @Override
    public void put(String url, Bitmap bitmap) {
        mImageCache.put(url, bitmap);
    }
    /**
     * 根据图片连接地址获取图片缓存
     * @param url
     * @return
     */
    @Override
    public Bitmap get(String url) {
        return mImageCache.get(url);
    }

    /**
     * 设置图片内存缓存大小
     */
    private void initImageCache() {
        //获取当前应用可使用内存大小
        final int maxMemory = (int)(Runtime.getRuntime().maxMemory() / 1024);
        //分配缓存的内存大小，官方推荐为当前应用可使用内存的1/8
        final int cacheSize = maxMemory / 4;
        //初始化缓存类
        mImageCache = new LruCache<String, Bitmap>(cacheSize){
            /*sizeof()方法。这个方法默认返回的是你缓存的item数目，如果你想要自定义size的大小，直接重写这个方法，返回自定义的值即可*/
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                return bitmap.getRowBytes() * bitmap.getHeight() / 1024;
            }
        };
    }

}
