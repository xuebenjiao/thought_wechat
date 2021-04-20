package com.thoughtwork.comment.application;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.startup.Initializer;

import com.thoughtwork.base.preference.PreferencesUtil;

import java.util.Collections;
import java.util.List;

/**
 * Time :2020/6/24
 * Author:xbj
 * Description :
 */
public class PreferencesUtilInitializer implements Initializer<PreferencesUtil> {
    @NonNull
    @Override
    public PreferencesUtil create(@NonNull Context context) {
        PreferencesUtil.init((Application) context);
        return PreferencesUtil.getInstance();
    }

    @NonNull
    @Override
    public List<Class<? extends Initializer<?>>> dependencies() {
        return Collections.emptyList();
    }
}
