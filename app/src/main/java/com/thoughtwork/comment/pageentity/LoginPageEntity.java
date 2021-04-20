package com.thoughtwork.comment.pageentity;

import androidx.databinding.Bindable;

import com.xbj.comment.BR;

/**
 * Time :2021/2/22
 * Author:xbj
 * Description :账号/验证码登录界面实体
 */
public class LoginPageEntity extends RegisterPageEntity {

    public LoginPageEntity(int etNum) {
        this.etNum = etNum;
    }
    public String btnName ="登录";
    public String switchBtnName = "切换验证码登录";
    //    public boolean isForgetPwdPage;
    //是否显示验证码布局
    public  boolean isShowValidate;
    @Bindable
    public boolean isShowValidate() {
        return isShowValidate;
    }

    public void setShowValidate(boolean showValidate) {
        if(showValidate != this.isShowValidate){
            this.isShowValidate = showValidate;
            notifyPropertyChanged(BR.showValidate);
        }
    }

    @Bindable
    public String getSwitchBtnName() {
        return switchBtnName;
    }

    public void setSwitchBtnName(String switchBtnName) {
        if(!switchBtnName.equals(this.switchBtnName)){
            this.switchBtnName = switchBtnName;
            notifyPropertyChanged(BR.switchBtnName);
        }
    }

}
