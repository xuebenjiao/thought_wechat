package com.thoughtwork.comment.model;

import android.text.TextUtils;

import com.xbj.comment.BuildConfig;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

/**
 * Time :2019/11/6
 * Author:xbj
 * Description :
 */
public class User extends BaseObservable {
    //是否有登录失败的快递公司，如果有则不跳转至主界面
    public boolean isHasFailureExpress = false;
    public boolean initialPwdFlag = false;
    //快递公司登陆信息标识
    public String  titleName = "快递公司登录";
    //显示登陆信息标识
    public boolean isShowLoginNoteLayout;
    //是否显示中通验证的标识
    public boolean isShowZtoNote = false;
    private boolean isName,isPwd,isPhone;
    public boolean selectRemPwd = true;
    public int etHasValueNum = 0;
    public boolean isClickable= false;
    public  String userPass;
    public  String userPhone;
    public  String orgCode;


    /**
     * 退出登录时 如果勾选记住密码，则全部显示，如果没有勾选记住密码，则只有密码输入框为空
     * @param orgCode
     * @param phoneNum
     */
    public User(String orgCode, String phoneNum){
        if(!TextUtils.isEmpty(orgCode) && !TextUtils.isEmpty(phoneNum)) {
            this.orgCode = orgCode.toUpperCase();
            this.userPhone = phoneNum;
            isPhone = true;
            isName = true;
            etHasValueNum = 2;
        }
    }
    /**
     * 退出登录时 如果勾选记住密码，则全部显示，如果没有勾选记住密码，则只有密码输入框为空
     * @param orgCode
     * @param phoneNum
     */
    public User(String orgCode, String phoneNum,String pwd){
        this.orgCode = orgCode.toUpperCase();
        this.userPhone = phoneNum;
        this.userPass = pwd;
        isPhone = true;
        isPwd = true;
        isName = true;
        etHasValueNum = 3;
        isClickable  = true;
        selectRemPwd = true;
    }

    public  User(){
       if(BuildConfig.DEBUG){
            this.orgCode = "00Y0073";
            this.userPhone = "18092425120";
            this.userPass = "yto123";
            isPhone = true;
            isPwd = true;
            isName = true;
            etHasValueNum = 3;
            isClickable  = true;
            selectRemPwd = true;
        }
    }
    @Bindable
    public String getUserPass() {
        return userPass;
    }
    public void setUserPass(String userPass) {

        if(!userPass.equals(this.userPass)){
            setClickableValue(3,userPass);
            this.userPass = userPass;
//            notifyPropertyChanged(com.yto.comment.BR.userPass);
        }
    }
    @Bindable
    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {

        if(!userPhone .equals(this.userPhone)){
            setClickableValue(2,userPhone);
            this.userPhone = userPhone;
//            notifyPropertyChanged(com.yto.comment.BR.userPhone);
        }
    }
    @Bindable
    public String getOrgCode() {
        return orgCode;
    }
    public void setOrgCode(String orgCode) {
        if (!orgCode.equalsIgnoreCase(this.orgCode)) {
            setClickableValue(1,orgCode);
            this.orgCode = orgCode.toUpperCase();
//            notifyPropertyChanged(com.yto.comment.BR.orgCode);
        }
    }

    /**
     * 判断是否将isClickable设置为true or false
     * @param value
     */
    public void setClickableValue(int num,String value) {
        if (TextUtils.isEmpty(value)) {
            if(this.isClickable) {
                setClickable(false);
            }
            setValue(false,num);

        } else {
            setValue(true, num);
            //判断是否第三个输入框都有值
            if (etHasValueNum == 3) {
                if (!this.isClickable) {
                    setClickable(true);
                }
            }
        }
    }

    /**
     *
     * @param flag 输入框是否在输入内容的标识
     * @param num 三个输入框的标识 1 网点编号 2 电话号码  3 密码
     *  辨识每个输入框 累计标明当前有内容的输入框个数及那些输入框已经有内容的标识
     */
    public void setValue(boolean flag,int num) {
        if(flag) {
            if (num == 1 && !isName) {
                etHasValueNum++;
                isName = true;
            }
            if (num == 2 && !isPhone) {
                etHasValueNum++;
                isPhone = true;
            }
            if (num == 3 && !isPwd) {
                etHasValueNum++;
                isPwd = true;
            }
        }
        else{
            if(num == 1 && isName){
                isName = false;
                etHasValueNum --;
            }
            if(num == 2 && isPhone){
                isPhone = false;
                etHasValueNum --;
            }
            if(num == 3 && isPwd){
                isPwd = false;
                etHasValueNum --;
            }
        }
    }

    @Bindable
    public boolean isClickable() {
        return isClickable;
    }
    public void setClickable(boolean clickable) {
        if(this.isClickable != clickable){
            this.isClickable = clickable;
//            notifyPropertyChanged(com.yto.comment.BR.clickable);
        }
    }
    @Bindable
    public boolean isSelectRemPwd() {
        return selectRemPwd;
    }

    public void setSelectRemPwd(boolean selectRemPwd) {
        if(this.selectRemPwd != selectRemPwd){
            this.selectRemPwd = selectRemPwd;
//            notifyPropertyChanged(com.yto.comment.BR.selectRemPwd);
        }

    }

    @Bindable
    public boolean isShowLoginNoteLayout() {
        return isShowLoginNoteLayout;
    }
    @Bindable
    public boolean isShowZtoNote() {
        return isShowZtoNote;
    }

    public void setShowZtoNote(boolean showZtoNote) {
        if(this.isShowZtoNote != showZtoNote){
            isShowZtoNote = showZtoNote;
//            notifyPropertyChanged(com.yto.comment.BR.showZtoNote);
        }
    }

    public void setShowLoginNoteLayout(boolean showLoginNoteLayout) {
        if(this.isShowLoginNoteLayout != showLoginNoteLayout){
            this.isShowLoginNoteLayout = showLoginNoteLayout;
//            notifyPropertyChanged(com.yto.comment.BR.showLoginNoteLayout);
        }
    }
    @Bindable
    public boolean isHasFailureExpress() {
        return isHasFailureExpress;
    }

    public void setHasFailureExpress(boolean hasFailureExpress) {
        if(hasFailureExpress != this.isHasFailureExpress){
            this.isHasFailureExpress = hasFailureExpress;
//            notifyPropertyChanged(com.yto.comment.BR.hasFailureExpress);
        }
    }
}
