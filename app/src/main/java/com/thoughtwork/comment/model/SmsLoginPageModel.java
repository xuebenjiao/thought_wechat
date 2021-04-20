package com.thoughtwork.comment.model;

import android.text.TextUtils;

import androidx.databinding.Bindable;

/**
 * Time :2020/2/8
 * Author:xbj
 * Description :
 */
public class SmsLoginPageModel extends User {
    private boolean isPic,isSms;
    public boolean isShowSmsLayout ;
    public String picCode;
    public String smsCode;
    public boolean isBtnClickable= false;
    public  String captchaToken ;
    public String loginShowPhone;
    public String smsToken;
    public String loginBtnName = "下一步";
    //个人指引信息
    public String privacyPolicy;
    //个人指引中的协议
    public String serviceAgreement;

    @Bindable
    public String getPicCode() {
        return picCode;
    }

    public void setPicCode(String picCode) {
        if(!picCode.equals(this.picCode)){
            this.picCode = picCode;
            setClickable(!TextUtils.isEmpty(picCode));
//            notifyPropertyChanged(com.yto.comment.BR.picCode);
        }
    }
    @Bindable
    public String getSmsCode() {
        return smsCode;
    }

    public void setSmsCode(String smsCode) {
        if(!smsCode.equals(this.smsCode)){
            this.smsCode = smsCode;
//            notifyPropertyChanged(com.yto.comment.BR.smsCode);
            setClickable(!TextUtils.isEmpty(smsCode));
        }
    }
    /**
     * 判断是否将isClickable设置为true or false
     * @param value
     */
    @Override
    public void setClickableValue(int num,String value) {
        if (TextUtils.isEmpty(value)) {
            if(this.isBtnClickable) {
                setClickable(false);
            }
            setValue(false,num);

        } else {
            setValue(true, num);
            if (etHasValueNum == 2) {//判断是否三个输入框都有值
                if (!this.isBtnClickable) {
                    setClickable(true);
                }
            }
        }
    }
    /**
     *
     * @param flag 输入框是否在输入内容的标识
     * @param num 三个输入框的标识 1 图片验证码 2 短信验证码
     *  辨识每个输入框 累计标明当前有内容的输入框个数及那些输入框已经有内容的标识
     */
    @Override
    public void setValue(boolean flag,int num) {
        if(flag) {
            if (num == 1 && !isPic) {
                etHasValueNum++;
                isPic = true;
            }
            if (num == 2 && !isSms) {
                etHasValueNum++;
                isSms = true;
            }

        }
        else{
            if(num == 1 && isPic){
                isPic = false;
                etHasValueNum --;
            }
            if(num == 2 && isSms){
                isSms = false;
                etHasValueNum --;
            }
        }
    }


    public String getCaptchaToken() {
        return captchaToken;
    }

    public void setCaptchaToken(String captchaToken) {
        this.captchaToken = captchaToken;
    }
    @Bindable
    public boolean isShowSmsLayout() {
        return isShowSmsLayout;
    }

    public void setShowSmsLayout(boolean showSmsLayout) {
        if(this.isShowSmsLayout != showSmsLayout){
            this.isShowSmsLayout = showSmsLayout;
//            notifyPropertyChanged(com.yto.comment.BR.showSmsLayout);
        }
    }
    @Bindable
    public String getLoginBtnName() {
        return loginBtnName;
    }

    public void setLoginBtnName(String loginBtnName) {
        if(!this.loginBtnName.equals(loginBtnName)){
            this.loginBtnName = loginBtnName;
            notifyPropertyChanged(com.xbj.comment.BR.loginBtnName);
        }
    }

    @Bindable
    public boolean isBtnClickable() {
        return isBtnClickable;
    }
    @Override
    public void setClickable(boolean clickable) {
        if(this.isBtnClickable != clickable){
            this.isBtnClickable = clickable;
//            notifyPropertyChanged(com.yto.comment.BR.btnClickable);
        }
    }

}
