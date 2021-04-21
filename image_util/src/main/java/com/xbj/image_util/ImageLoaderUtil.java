package com.xbj.image_util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Time :2021/4/21
 * Author:xbj
 * Description :图片下载工具类
 */
public  class ImageLoaderUtil {
    private static ImageLoaderUtil instance;
    private ImageLoaderUtil (){}
    public static synchronized ImageLoaderUtil getInstance() {
        if (instance == null) {
            instance = new ImageLoaderUtil();
        }
        return instance;
    }
    //缓存获取工具
    private IImageCache mImageCache = new GetCacheDataUtils();

    //线程池，线程的数量为CPU数，可以同时处理多个缓存线程
    //Runtime.getRuntime().availableProcessors() 得到的就是CPU数
    private  ExecutorService mExecutorService = Executors.newFixedThreadPool( Runtime.getRuntime().availableProcessors());

    /**
     * 根据图片连接显示图片
     * @param url 图片连接
     * @param imageView 显示图片的控件
     */
    public void displayImage(final String url, final ImageView imageView){
        Bitmap bitmap = mImageCache.getCache(url) ;
        if (bitmap != null){
            imageView.setImageBitmap(bitmap); return;
        }
        //图片还未缓存，开启线程从网上下载
        submitLoadRequest(url, imageView);
    }

    /**
     *
     * @param imageUrl 图片连接
     * @param imageView 显示图片控件
     */
    private void submitLoadRequest(final String imageUrl, final ImageView imageView){
        //需要开启新线程，用url唯一标识
        imageView.setTag(imageUrl);
        mExecutorService.submit(new Runnable() {
            @Override
            public void run() {
                //根据图片连接下载图片
                Bitmap bitmap = downloadImage(imageUrl);
                if (bitmap == null){
                    return;
                }
                //判断是否是要显示图片的控件
                if (imageView.getTag().equals(imageUrl)){
                    imageView.setImageBitmap(bitmap);
                }

                mImageCache.putCache(imageUrl, bitmap);
            }});
    }

    /**
     * 请求下载图片
     * @param imageUrl
     * @return
     */
    private Bitmap downloadImage(String imageUrl) {
        Bitmap bitmap = null;
        try {
            URL url = new URL(imageUrl);
            final HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            bitmap = BitmapFactory.decodeStream(conn.getInputStream());
            conn.disconnect();
        } catch (Exception e){
            e.printStackTrace();
        }

        return bitmap;
    }
}
