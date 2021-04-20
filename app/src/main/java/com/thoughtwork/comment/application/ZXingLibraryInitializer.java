package com.thoughtwork.comment.application;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.startup.Initializer;

import com.uuzuche.lib_zxing.activity.ZXingLibrary;

import java.util.Collections;
import java.util.List;

/**
 * Time :2020/6/24
 * Author:xbj
 * Description :
 */
public class ZXingLibraryInitializer implements Initializer<ZXingLibrary> {
    @NonNull
    @Override
    public ZXingLibrary create(@NonNull Context context) {
        ZXingLibrary.initDisplayOpinion(context);
        return null;
    }

    @NonNull
    @Override
    public List<Class<? extends Initializer<?>>> dependencies() {
        return Collections.emptyList();
    }
}
