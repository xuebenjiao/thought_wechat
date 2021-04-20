package com.thoughtwork.comment.application;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.startup.Initializer;

import com.gu.toolargetool.TooLargeTool;

import java.util.Collections;
import java.util.List;

/**
 * Time :2020/6/24
 * Author:xbj
 * Description :
 */
public class TooLargeToolInitializer implements Initializer<TooLargeTool> {
    @NonNull
    @Override
    public TooLargeTool create(@NonNull Context context) {
        TooLargeTool.startLogging((Application) context);
        return TooLargeTool.INSTANCE;
    }

    @NonNull
    @Override
    public List<Class<? extends Initializer<?>>> dependencies() {
        return Collections.emptyList();
    }
}
