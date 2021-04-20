package com.thoughtwork.comment.application;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.startup.Initializer;

import com.billy.cc.core.component.CC;
import com.xbj.comment.BuildConfig;

import java.util.Collections;
import java.util.List;

/**
 * Time :2020/6/24
 * Author:xbj
 * Description :
 */
public class CCInitializer implements Initializer<CC> {
    @NonNull
    @Override
    public CC create(@NonNull Context context) {
        CC.init((Application) context);
        CC.enableDebug(BuildConfig.DEBUG);
        // 默认是false: 关闭状态 开启日志跟踪后，会在Logcat中输出CC调用的详细流程，将打印出每一个执行时的CC对象或CCResult对象的详细信息
        CC.enableVerboseLog(BuildConfig.DEBUG);
        // 默认是false: 关闭状态 开启跨app组件调用
        CC.enableRemoteCC(BuildConfig.DEBUG);
        return null;
    }

    @NonNull
    @Override
    public List<Class<? extends Initializer<?>>> dependencies() {
        return Collections.emptyList();
    }
}
