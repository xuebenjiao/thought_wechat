package com.thoughtwork.base;

import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.Handler;

import com.hprt.m300lib.HidConncetUtil;
import com.thoughtwork.base.baidu.LocationService;
import com.zltd.scan.ScanUtils;
import com.zltd.utils.DeviceUtils;

/**
 * author : xbj
 * e-mail : xbjzhu@163.com
 * date   : 2019/10/22 15:37
 * desc   :
 */
public class BaseApplication extends Application {
    public LocationService locationService;
    public HidConncetUtil mHidConncetUtil;
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
    private static ScanUtils mScanManager;

    //1 为开发，2 测试，3 uat,4生产
    public static int envType;
    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        //判断是否是手机
        DeviceUtils.hasAppAuth();
        //设置相关参数
        locationService = new LocationService(getApplicationContext());
        //4.0以上才支持HID模式
        if (Build.VERSION.SDK_INT >= 14) {
            mHidConncetUtil = new HidConncetUtil(this);
//            HPRTHelper.getHPRTHelper(this);
        }
        if(!DeviceUtils.isMobilePhone) {
            mScanManager = ScanUtils.getInstance(this);
            mScanManager.init();//开启扫描头
        }
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
    public static ScanUtils getmScanManager() {
        return mScanManager;
    }

    public static void setmScanManager(ScanUtils mScanManager) {
        BaseApplication.mScanManager = mScanManager;
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
