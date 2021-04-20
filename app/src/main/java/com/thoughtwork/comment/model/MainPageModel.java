package com.thoughtwork.comment.model;

import java.util.HashMap;

import androidx.databinding.BaseObservable;

/**
 * Time :2019/11/7
 * Author:xbj
 * Description :
 */
public class MainPageModel extends BaseObservable {
    /*车签号*/
    public String carSignCode;
    public HashMap<String,String> geKouMap = new HashMap<>();
    //判断是否是扫描模块
    public boolean scanFragmentFlag = true;
    public String currentTabName;
   /*
      public boolean showOrHideUploadBtn;
    public boolean showOrHideScanBtn;

     public MainPageModel(boolean showDefaultSanBtn){
        this.showOrHideScanBtn = showDefaultSanBtn;
    }

   @Bindable
    public boolean isScanFragmentFlag() {
        return scanFragmentFlag;
    }

    public void setScanFragmentFlag(boolean scanFragmentFlag) {
       if(this.scanFragmentFlag != scanFragmentFlag){
           this.scanFragmentFlag = scanFragmentFlag;
           notifyPropertyChanged(com.yto.comment.BR.scanFragmentFlag);
       }
    }



    @Bindable
    public boolean isShowOrHideUploadBtn() {
        return showOrHideUploadBtn;
    }

    public void setShowOrHideUploadBtn(boolean showOrHideUploadBtn) {
        if(this.showOrHideUploadBtn != showOrHideUploadBtn){
            this.showOrHideUploadBtn = showOrHideUploadBtn;
            notifyPropertyChanged(com.yto.comment.BR.showOrHideUploadBtn);
        }
    }
    @Bindable
    public boolean isShowOrHideScanBtn() {
        return showOrHideScanBtn;
    }
    public void setShowOrHideScanBtn(boolean showOrHideScanBtn) {
        if(this.showOrHideScanBtn != showOrHideScanBtn){//避免死循环
            this.showOrHideScanBtn = showOrHideScanBtn;
            notifyPropertyChanged(com.yto.comment.BR.showOrHideScanBtn);
        }
    }*/
}
