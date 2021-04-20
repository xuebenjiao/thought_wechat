package com.thoughtwork.comment.presenter;

import android.view.View;

import com.thoughtwork.comment.model.MainPageModel;

import org.simple.eventbus.EventBus;

/**
 * Time :2019/11/7
 * Author:xbj
 * Description :
 */
public class MainPageClickPresenter {
    public MainPageModel uMainPageModel;
    private ScanCallInterface uScanCallInterface;
    public MainPageClickPresenter(MainPageModel model,ScanCallInterface scanCallInterface){
        this.uMainPageModel = model;
        uScanCallInterface = scanCallInterface;
    }
    public void uploadBtnClick(View view){
        EventBus.getDefault().post(uMainPageModel.currentTabName, "uploadBtnClick");
    }
    public void scanBtnClick(View view){
        if(uScanCallInterface != null){
            uScanCallInterface.startScanAcitivity();
        }
    }
    public void clickDelBtn(View view){
        EventBus.getDefault().post(uMainPageModel.currentTabName, "delBtnClick");
    }
    public interface ScanCallInterface{
        void  startScanAcitivity();
        void showDelNote();
    }
}
