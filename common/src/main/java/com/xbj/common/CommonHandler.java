package com.xbj.common;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.view.View;

/**
 * Time :2019/11/13
 * Author:xbj
 * Description : 公用顶部界面事件处理
 */
public class CommonHandler {
    RightBtnCallBack callBack;
    IBackCallBack mBackCallBack;


    public CommonHandler(){


    }
    public CommonHandler(RightBtnCallBack callBack){
        this.callBack  = callBack;
    }

    public CommonHandler(IBackCallBack callBack) {
        this.mBackCallBack = callBack;
    }
    public CommonHandler(RightBtnCallBack callBack,IBackCallBack backCallBack){
        this.callBack  = callBack;
        this.mBackCallBack = backCallBack;
    }




    public void onBackBtnClick(View view){
        if(mBackCallBack != null){
            mBackCallBack.backBtnCallBack(view);
        }
        else {
            getActivityFromView(view).finish();
        }
    }
    public void onRightBtnClick(View view){
        if(callBack != null){
            callBack.rightBtnCallBack(view);
        }
    }
    public interface  RightBtnCallBack{
        void rightBtnCallBack(View view);
    }


    /**
     * try get host activity from view.
     * views hosted on floating window like dialog and toast will sure return null.
     * @return host activity; or null if not available
     * 解决java.lang.ClassCastException: android.support.v7.widget.TintContextWrapper cannot be cast to ...Activity**
     */
    public  Activity getActivityFromView(View view) {
        Context context = view.getContext();
        while (context instanceof ContextWrapper) {
            if (context instanceof Activity) {
                return (Activity) context;
            }
            context = ((ContextWrapper) context).getBaseContext();
        }
        return null;
    }

}
