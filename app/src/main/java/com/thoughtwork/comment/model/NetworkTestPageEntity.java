package com.thoughtwork.comment.model;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

/**
 * Time :2021/1/24
 * Author:xbj
 * Description :
 */
public class NetworkTestPageEntity extends BaseObservable {

    public String firstPingResult = "测试中…";
    public boolean  firstPingFlag ;
    public String secPingResult = "测试中…";
    public boolean  secPingFlag ;
    public String thirdPingResult = "测试中…";
    public boolean  thirdPingFlag ;
    public String fourPingResult = "测试中…";
    public boolean  fourPingFlag ;
    @Bindable
    public String getFirstPingResult() {
        return firstPingResult;
    }

    public void setFirstPingResult(String firstPingResult) {

        if(!firstPingResult.equals(this.firstPingResult)){
            this.firstPingResult = firstPingResult;
//            notifyPropertyChanged(com.yto.comment.BR.firstPingResult);
        }
    }
    @Bindable
    public String getSecPingResult() {
        return secPingResult;
    }

    public void setSecPingResult(String secPingResult) {
        if(!secPingResult.equals(this.secPingResult)){
            this.secPingResult = secPingResult;
//            notifyPropertyChanged(com.yto.comment.BR.secPingResult);
        }
    }
    @Bindable
    public String getThirdPingResult() {
        return thirdPingResult;
    }

    public void setThirdPingResult(String thirdPingResult) {
        if(!thirdPingResult.equals(this.thirdPingResult)){
            this.thirdPingResult = thirdPingResult;
//            notifyPropertyChanged(com.yto.comment.BR.thirdPingResult);
        }
    }
    @Bindable
    public boolean isFirstPingFlag() {
        return firstPingFlag;
    }

    public void setFirstPingFlag(boolean firstPingFlag) {

        if(firstPingFlag != this.firstPingFlag){
            this.firstPingFlag = firstPingFlag;
//            notifyPropertyChanged(com.yto.comment.BR.firstPingFlag);
        }
    }
    @Bindable
    public boolean isSecPingFlag() {
        return secPingFlag;
    }

    public void setSecPingFlag(boolean secPingFlag) {

        if(secPingFlag != this.secPingFlag){
            this.secPingFlag = secPingFlag;
//            notifyPropertyChanged(com.yto.comment.BR.secPingFlag);
        }
    }
    @Bindable
    public boolean isThirdPingFlag() {
        return thirdPingFlag;
    }

    public void setThirdPingFlag(boolean thirdPingFlag) {
        if(thirdPingFlag != this.thirdPingFlag){
            this.thirdPingFlag = thirdPingFlag;
//            notifyPropertyChanged(com.yto.comment.BR.thirdPingFlag);
        }
    }
    @Bindable
    public String getFourPingResult() {
        return fourPingResult;
    }

    public void setFourPingResult(String fourPingResult) {
        if(!fourPingResult.equals(this.fourPingResult)){
            this.fourPingResult = fourPingResult;
//            notifyPropertyChanged(com.yto.comment.BR.fourPingResult);
        }
    }
    @Bindable
    public boolean isFourPingFlag() {
        return fourPingFlag;
    }

    public void setFourPingFlag(boolean fourPingFlag) {

        if(fourPingFlag != this.fourPingFlag){
            this.fourPingFlag = fourPingFlag;
//            notifyPropertyChanged(com.yto.comment.BR.fourPingFlag);
        }
    }
}
