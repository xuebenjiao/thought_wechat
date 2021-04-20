package com.thoughtwork.base.utils;

import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.widget.Toast;

import com.thoughtwork.base.BaseApplication;

/**
 * author : xbj
 * e-mail : xbjzhu@163.com
 * date   : 2019/10/22 17:32
 * desc   :
 */
public class ToastUtil {
    private static Toast mToast;

    /**
     * 弹出Toast 默认底部
     * @param context
     * @param msg
     */
    public static void show(Context context, String msg) {
        try {
            if (context != null && !TextUtils.isEmpty(msg)) {
                if(mToast != null){
                    mToast.cancel();
                }
                mToast = Toast.makeText(context, "", Toast.LENGTH_LONG);
                mToast.setGravity(Gravity.CENTER,0,0);
                mToast.setText(msg);

//				if(BuildConfig.DEBUG) {
                mToast.show();
//				}
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置Toast弹出的位置
     * @param context
     * @param msg
     * @param gravity
     */
    public static void showGravity(Context context, String msg,int gravity) {
        try {
            if (context != null && !TextUtils.isEmpty(msg)) {
                if(mToast != null){
                    mToast.cancel();
                }
                mToast = Toast.makeText(context, "", Toast.LENGTH_LONG);
                mToast.setGravity(gravity,0,0);
                mToast.setText(msg);
//				if(BuildConfig.DEBUG) {
                mToast.show();
//				}
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 弹出Toast 默认底部
     * @param msg
     */
    public static void show(String msg) {
        try {
            if (!TextUtils.isEmpty(msg)) {
                if(mToast != null){
                    mToast.cancel();
                }
                mToast = Toast.makeText(BaseApplication.getmContext(), "", Toast.LENGTH_LONG);
                mToast.setGravity(Gravity.CENTER,0,0);
                mToast.setText(msg);

//				if(BuildConfig.DEBUG) {
                mToast.show();
//				}
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
