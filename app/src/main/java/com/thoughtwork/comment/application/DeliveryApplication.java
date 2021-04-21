package com.thoughtwork.comment.application;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import androidx.multidex.MultiDex;

import com.thoughtwork.base.BaseApplication;
import com.thoughtwork.base.utils.ActivityManager;
import com.thoughtwork.base.utils.FUtils;
import com.thoughtwork.base.utils.LogUtils;
import com.thoughtwork.base.utils.exception.C;
import com.xbj.comment.BuildConfig;

//import com.github.anrwatchdog.ANRWatchDog;

/**
 * author : xbj
 * e-mail : xbjzhu@163.com
 * date   : 2019/10/22 15:32
 * desc   :
 */
public class DeliveryApplication extends BaseApplication {

    private static final String TAG = "DeliveryApplication";
    @Override
    public void onCreate() {
        super.onCreate();
        long startTime = System.currentTimeMillis();
        LogUtils.i(TAG,"start:"+startTime);
        //只有在主进程的时候，初始化一次W
        if (FUtils.isSpecifyProcess(this, getPackageName())) {
            C.init(this);
            setsDebug(BuildConfig.DEBUG);
            setServerUrl(BuildConfig.SERVER_URL);
            setWebviewUrl(BuildConfig.WEBVIEW_URL);
            setMsgWebviewUrl(BuildConfig.MESSAGE_WEBVIEW_URL);
            setControWebviewUrl(BuildConfig.CONTRO_WEBVIEW_URL);
            setApplicationId(BuildConfig.APPLICATION_ID);
            setAppVersion( String.valueOf(BuildConfig.VERSION_CODE));
            setVersionName(BuildConfig.VERSION_NAME);
            setEnvType(BuildConfig.ENV_TYPE);
            registerActivityListener();
            LogUtils.i(TAG,"end:"+ (System.currentTimeMillis() - startTime));

        }
    }

    /**
     * 全局监听Activity 生命周期
     */
    private void registerActivityListener() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
                @Override
                public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                    /**
                     *  监听到 Activity创建事件 将该 Activity 加入list
                     */
                    ActivityManager.getActivityManager().pushActivity(activity);

                }

                @Override
                public void onActivityStarted(Activity activity) {

                }

                @Override
                public void onActivityResumed(Activity activity) {

                }

                @Override
                public void onActivityPaused(Activity activity) {

                }

                @Override
                public void onActivityStopped(Activity activity) {

                }

                @Override
                public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

                }

                @Override
                public void onActivityDestroyed(Activity activity) {
                    /**
                     *  监听到 Activity销毁事件 将该Activity 从list中移除
                     */
                    ActivityManager.getActivityManager().destroyActivity(activity);
                }
            });
        }
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
