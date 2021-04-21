package com.xbj.image_util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.thoughtwork.base.utils.FileUtil;
import com.thoughtwork.base.utils.exception.C;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Time :2021/4/21
 * Author:xbj
 * Description :硬盘缓存工具类
 * */
public class DiskCacheUtil implements IImageCache{
    //缓存路径
    private final  String cacheDir = FileUtil.getCanCacheDataPath(C.context )+"/picCache/";

    @Override
    public Bitmap getCache(String url){
        return BitmapFactory.decodeFile(cacheDir+ url);
    }

    @Override
    public void putCache(String url, Bitmap bmp){
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
