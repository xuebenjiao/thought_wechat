package com.thoughtwork.comment.application;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.startup.Initializer;

import com.thoughtwork.base.utils.exception.CrashHandler;

import java.util.Collections;
import java.util.List;

/**
 * Time :2020/6/24
 * Author:xbj
 * Description :
 */
public class CrashHandlerInitializer implements Initializer<CrashHandler> {
    @NonNull
    @Override
    public CrashHandler create(@NonNull Context context) {
        CrashHandler.getInstance().init(context);

        return CrashHandler.getInstance();
    }

    @NonNull
    @Override
    public List<Class<? extends Initializer<?>>> dependencies() {
        //该处不能返回null，否则会报错
        return Collections.emptyList();
    }
}
