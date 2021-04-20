package com.xbj.common;

import android.view.View;

/**
 * Time :2021/2/23
 * Author:xbj
 * Description :界面事件处理基类
 */
public abstract   class BaseClickHandler {
    //如下两个变量是防止多次点击
    public int lastId;
    public long lastClickTimeStamp;
    /**
     * 是否多次点击
     * @param view
     * @return
     */
    public boolean isValidClick(View view) {
        long time = System.currentTimeMillis();
        boolean valid = view.getId() != lastId || time - lastClickTimeStamp > 500;
        lastId = view.getId();
        lastClickTimeStamp = time;
        return valid;
    }
    /**
     * 保存处理
     */
    public void btnClick(View view){
        if(isValidClick(view)){
            handlerBtnClick(view);
        }
    }
    /**
     * 界面按钮事件处理
     */
    public abstract void handlerBtnClick(View view);

}
