package com.thoughtwork.comment.application;

import android.app.Service;
import android.content.Context;
import android.os.Vibrator;

import androidx.annotation.NonNull;
import androidx.startup.Initializer;

import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;

import java.util.Collections;
import java.util.List;

/**
 * Time :2021/2/5
 * Author:xbj
 * Description :
 */
public class BaiduSDKInitializer implements Initializer<SDKInitializer> {
    @NonNull
    @Override
    public SDKInitializer create(@NonNull Context context) {
        SDKInitializer.initialize(context);
        //自4.3.0起，百度地图SDK所有接口均支持百度坐标和国测局坐标，用此方法设置您使用的坐标类型.
        //包括BD09LL和GCJ02两种坐标，默认是BD09LL坐标。
        SDKInitializer.setCoordType(CoordType.BD09LL);
        return null;
    }

    @NonNull
    @Override
    public List<Class<? extends Initializer<?>>> dependencies() {
        return Collections.emptyList();
    }
}
