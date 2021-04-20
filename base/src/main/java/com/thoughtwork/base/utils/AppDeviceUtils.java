package com.thoughtwork.base.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.view.WindowManager;

import java.lang.reflect.Method;

/**
 * Time :2019/11/20
 * Author:xbj
 * Description : 应用相关信息与设备相关信息获取工具类
 */
public class AppDeviceUtils {
    /**
     * 获取应用的版本
     */
    public static int getVersionCode(Context context) {
        try {
            String pkName = context.getPackageName();
            int versionCode = context.getPackageManager().getPackageInfo(pkName, 0).versionCode;
            return versionCode;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }
    /**
     * 获取应用的版本
     */
    public static String getVersionName(Context context) {
        try {
            String pkName = context.getPackageName();
            String versionCode = context.getPackageManager().getPackageInfo(pkName, 0).versionName;
            return versionCode;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "V1.0.0.0";
    }

    /**
     * 设置导航栏透明
     * @param context
     */
    public static void transportStatus(Activity context){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            context.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            if (!isHaveNavigationBar(context))
                context.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

    /**
     * 判断导航栏是否存在
     * @param context
     * @return
     */
    public static boolean isHaveNavigationBar(Context context) {
        boolean hasNavigationBar = false;
        Resources rs = context.getResources();
        int id = rs.getIdentifier("config_showNavigationBar", "bool", "android");
        if (id > 0) {
            hasNavigationBar = rs.getBoolean(id);
        }
        try {
            Class systemPropertiesClass = Class.forName("android.os.SystemProperties");
            Method m = systemPropertiesClass.getMethod("get", String.class);
            String navBarOverride = (String) m.invoke(systemPropertiesClass, "qemu.hw.mainkeys");
            if ("1".equals(navBarOverride)) {
                hasNavigationBar = false;
            } else if ("0".equals(navBarOverride)) {
                hasNavigationBar = true;
            }
        } catch (Exception e) {
            //do something
        }
        return hasNavigationBar;

    }


    public  static int getNavigationBarHeight(Context context) {
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height","dimen", "android");
        int height = resources.getDimensionPixelSize(resourceId);
        return height;

    }


}
