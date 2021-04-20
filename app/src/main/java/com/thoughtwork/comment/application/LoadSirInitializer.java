package com.thoughtwork.comment.application;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.startup.Initializer;

import com.kingja.loadsir.core.LoadSir;
import com.thoughtwork.base.loadsir.CustomCallback;
import com.thoughtwork.base.loadsir.EmptyCallback;
import com.thoughtwork.base.loadsir.ErrorCallback;
import com.thoughtwork.base.loadsir.LoadingCallback;
import com.thoughtwork.base.loadsir.TimeoutCallback;

import java.util.Collections;
import java.util.List;

/**
 * Time :2020/6/24
 * Author:xbj
 * Description :
 */
public class LoadSirInitializer implements Initializer<LoadSir> {
    @NonNull
    @Override
    public LoadSir create(@NonNull Context context) {
        LoadSir.beginBuilder()
                //添加各种状态页
                .addCallback(new ErrorCallback())
                .addCallback(new EmptyCallback())
                .addCallback(new LoadingCallback())
                .addCallback(new TimeoutCallback())
                .addCallback(new CustomCallback())
//                    .setDefaultCallback(EmptyCallback.class)//设置默认状态页
                .commit();
        return null;
    }

    @NonNull
    @Override
    public List<Class<? extends Initializer<?>>> dependencies() {
        return Collections.emptyList();
    }
}
