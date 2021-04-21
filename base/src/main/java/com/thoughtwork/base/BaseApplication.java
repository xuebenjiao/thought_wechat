package com.thoughtwork.base;

import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.Handler;


/**
 * author : xbj
 * e-mail : xbjzhu@163.com
 * date   : 2019/10/22 15:37
 * desc   :
 */
public class BaseApplication extends Application {
    //    public HPRTHelper mHelper;
    public static final Handler sHandler = new Handler();
    //OOM won't happen
    private static Context mContext;
    public static boolean sDebug;
    public static String serverUrl;
    public static String webviewUrl;
    public static String msgWebviewUrl;
    public static String controWebviewUrl;
    public static String mVersionName;
    public static String mApplicationId;
    public static String mAppVersion;


    //1 为开发，2 测试，3 uat,4生产
    public static int envType;
    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }

    public static Context getmContext() {
        return mContext;
    }

    public static void runUi(Runnable runnable) {
        sHandler.post(runnable);
    }

    public  void setVersionName(String versionName) {
        mVersionName = versionName;
    }

    public  void setApplicationId(String applicationId) {
        mApplicationId = applicationId;
    }

    public  void setAppVersion(String appVersion) {
        mAppVersion = appVersion;
    }

    //    BuildConfig 由于每个模块都有这个类，所以使用BuildConfig.DEBUG容易混乱
    public void setsDebug(boolean isDebug){
        sDebug = isDebug;
    }
    //设置服务端地址
    public  void setServerUrl(String url) {
        serverUrl = url;
    }

    public  void setWebviewUrl(String url) {
        webviewUrl = url;
    }

    public  void setControWebviewUrl(String url) {
        controWebviewUrl = url;
    }

    /**
     * 消息入口地址设置
     * @param msgWebviewUrl
     */
    public static void setMsgWebviewUrl(String msgWebviewUrl) {
        BaseApplication.msgWebviewUrl = msgWebviewUrl;
    }

    public static void setEnvType(int envType) {
        BaseApplication.envType = envType;
    }
}
