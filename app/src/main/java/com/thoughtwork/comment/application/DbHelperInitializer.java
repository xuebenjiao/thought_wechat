package com.thoughtwork.comment.application;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.startup.Initializer;

import com.xbj.database.DbHelper;

import java.util.Collections;
import java.util.List;

/**
 * Time :2020/6/24
 * Author:xbj
 * Description :
 */
public class DbHelperInitializer implements Initializer<DbHelper> {
    @NonNull
    @Override
    public DbHelper create(@NonNull Context context) {
        DbHelper.getInstance().init(context);
        return DbHelper.getInstance();
    }

    @NonNull
    @Override
    public List<Class<? extends Initializer<?>>> dependencies() {
        return Collections.emptyList();
    }
}
