package com.thoughtwork.comment.model;
import android.text.TextUtils;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

/**
 * Time :2020/7/27
 * Author:xbj
 * Description :
 */
public class GetBackPwdPageEntity extends BaseObservable {
    private String orgCode;
    private  String loginBtnName = "确定";
    //密码校验token
    public String fpToken;
    //图形验证码token
    public  String captchaToken ;
    //短信验证码token
    public  String smsToken;
    private String picCode;
    private String smsCode;
    //短信验证码是否可点击
    public boolean smsTvEnabled = true;
    private boolean  showPhoneLayout = true;
    private boolean  showSmsLayout = false;
//    private boolean  showSmsCodeLayout = false;
    private boolean  showOrgCodeLayout = true;
    private boolean  showPicCodeLayout = false;
    private boolean  showPwdLayout = false;
    //确认密码
    public String confirmUserPass;
    //新密码
    public String newUserPass;

    //确认 按钮 true 可点击  false 不可点击
    public boolean btnClick = false;
    //电话号码
    public  String userPhone;
    @Bindable
    public boolean isShowPicCodeLayout() {
        return showPicCodeLayout;
    }

    public void setShowPicCodeLayout(boolean showPicCodeLayout) {

        if(showPicCodeLayout != this.showPicCodeLayout){
            this.showPicCodeLayout = showPicCodeLayout;
//            notifyPropertyChanged(com.yto.comment.BR.showPicCodeLayout);
        }
    }

    @Bindable
    public boolean isShowPhoneLayout() {
        return showPhoneLayout;
    }

    public void setShowPhoneLayout(boolean showPhoneLayout) {
        if(showPhoneLayout != this.showPhoneLayout){
            this.showPhoneLayout = showPhoneLayout;
//            notifyPropertyChanged(com.yto.comment.BR.showPhoneLayout);
        }
    }

    @Bindable
    public boolean isShowOrgCodeLayout() {
        return showOrgCodeLayout;
    }

    public void setShowOrgCodeLayout(boolean showOrgCodeLayout) {
        if(showOrgCodeLayout != this.showOrgCodeLayout){
            this.showOrgCodeLayout = showOrgCodeLayout;
//            notifyPropertyChanged(com.yto.comment.BR.showOrgCodeLayout);
        }
    }

    @Bindable
    public String getConfirmUserPass() {
        return confirmUserPass;
    }

    public void setConfirmUserPass(String confirmUserPass) {
        if(!confirmUserPass.equals(this.confirmUserPass)){
            this.confirmUserPass = confirmUserPass;
//            notifyPropertyChanged(com.yto.comment.BR.confirmUserPass);
            if(TextUtils.isEmpty(confirmUserPass)){
                setBtnClick(false);
            }
            else{
                judgeIsAllEtHasConentForPwd();
            }
        }
    }
    @Bindable
    public String getNewUserPass() {
        return newUserPass;
    }

    public void setNewUserPass(String newUserPass) {
        if(!newUserPass.equals(this.newUserPass)){
            this.newUserPass = newUserPass;
//            notifyPropertyChanged(com.yto.comment.BR.newUserPass);
            if(TextUtils.isEmpty(newUserPass)){
                setBtnClick(false);
            }
            else{
                judgeIsAllEtHasConentForPwd();
            }
        }
    }

    /**
     * 判断界面是否所有的输入框都有内容 如果都有则按钮可点击，反正不可点击
     */
    private void judgeIsAllEtHasConentForPwd(){
        if(!TextUtils.isEmpty(confirmUserPass)&&!TextUtils.isEmpty(newUserPass)){
            setBtnClick(true);
        }
        else{
            setBtnClick(false);
        }
    }
    /**
     * 判断界面是否所有的输入框都有内容 如果都有则按钮可点击，反正不可点击
     */
    private void validatePhoneAndOrgCodeInputText() {
        if (!TextUtils.isEmpty(orgCode)&&!TextUtils.isEmpty(userPhone)) {
            setBtnClick(true);
        }
        else{
            setBtnClick(false);
        }
    }
    @Bindable
    public boolean isBtnClick() {
        return btnClick;
    }

    public void setBtnClick(boolean btnClick) {
        if(this.btnClick != btnClick){
            this.btnClick = btnClick;
//            notifyPropertyChanged(com.yto.common.BR.btnClick);
        }
    }

    @Bindable
    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        if(!userPhone.equals(this.userPhone)){
            this.userPhone = userPhone;
//            notifyPropertyChanged(com.yto.comment.BR.userPhone);
            if(TextUtils.isEmpty(userPhone)){
                setBtnClick(false);
            }
            else{
                validatePhoneAndOrgCodeInputText();
            }
        }
    }

    @Bindable
    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        if(!orgCode.equals(this.orgCode)){
            this.orgCode = orgCode;
//            notifyPropertyChanged(com.yto.comment.BR.orgCode);
            if(TextUtils.isEmpty(orgCode)){
                setBtnClick(false);
            }
            else{
                validatePhoneAndOrgCodeInputText();
            }
        }
    }

    @Bindable
    public String getPicCode() {
        return picCode;
    }

    public void setPicCode(String picCode) {
        if(!picCode.equals(this.picCode)){
            this.picCode = picCode;
            setBtnClick(!TextUtils.isEmpty(picCode));
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
            setBtnClick(!TextUtils.isEmpty(smsCode));
        }
    }
    @Bindable
    public String getLoginBtnName() {
        return loginBtnName;
    }

    public void setLoginBtnName(String loginBtnName) {
        if(!this.loginBtnName.equals(loginBtnName)){
            this.loginBtnName = loginBtnName;
//            notifyPropertyChanged(com.yto.comment.BR.loginBtnName);
        }
    }
    @Bindable
    public boolean isShowSmsLayout() {
        return showSmsLayout;
    }

    public void setShowSmsLayout(boolean showSmsLayout) {
        if(showSmsLayout != this.showSmsLayout){
            this.showSmsLayout = showSmsLayout;
//            notifyPropertyChanged(com.yto.comment.BR.showSmsLayout);
        }
    }
    @Bindable
    public boolean isShowPwdLayout() {
        return showPwdLayout;
    }

    public void setShowPwdLayout(boolean showPwdLayout) {
        if(showPwdLayout != this.showPwdLayout){
            this.showPwdLayout = showPwdLayout;
//            notifyPropertyChanged(com.yto.comment.BR.showPwdLayout);
        }
    }
}
