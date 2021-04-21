package com.xbj.image_util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.thoughtwork.base.BaseApplication;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Time :2021/4/21
 * Author:xbj
 * Description :本地缓存工具类
 * */
public class DiskCacheUtil implements IImageCache{
    //缓存路径
    static String cacheDir = "sdcard/cache/";

    @Override
    public Bitmap get(String url){
        return BitmapFactory.decodeFile(cacheDir + url);
    }

    @Override
    void put(String url, Bitmap bmp){
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(cacheDir + url);
            bmp.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            if (fileOutputStream != null){
                try {
                    fileOutputStream.close();
                } catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
    }
}
