package com.thoughtwork.comment.application;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.startup.Initializer;

import com.thoughtwork.base.utils.SPUtils;

import java.util.Collections;
import java.util.List;

/**
 * Time :2020/6/24
 * Author:xbj
 * Description :
 */
public class SPUtilsInitializer implements Initializer<SPUtils> {
    @NonNull
    @Override
    public SPUtils create(@NonNull Context context) {
        SPUtils.initCon(context);
        return null;
    }

    @NonNull
    @Override
    public List<Class<? extends Initializer<?>>> dependencies() {
        return Collections.emptyList();
    }
}
