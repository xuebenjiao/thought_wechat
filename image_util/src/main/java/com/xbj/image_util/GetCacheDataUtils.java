package com.xbj.image_util;

import android.graphics.Bitmap;

/**
 * Time :2021/4/21
 * Author:xbj
 * Description :缓存数据获取工具类（根据此工具判断是否有对应的缓存，如果没有则开启线程下载图片）
 */
public class GetCacheDataUtils implements IImageCache{

    private MemoryCacheUtil mMemoryCache = new MemoryCacheUtil();
    private DiskCacheUtil mDiskCache = new DiskCacheUtil();
    @Override
    public Bitmap getCache(String url) {
        Bitmap bitmap = mMemoryCache.getCache(url);
        if (bitmap == null) {
            bitmap = mDiskCache.getCache(url);
        }
        return bitmap;
    }

    @Override
    public void putCache(String url, Bitmap bmp){
        mMemoryCache.putCache(url, bmp);
        mDiskCache.putCache(url, bmp);
    }
}
