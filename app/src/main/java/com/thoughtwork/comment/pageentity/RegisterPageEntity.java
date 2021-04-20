package com.thoughtwork.comment.pageentity;

import android.text.TextUtils;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.xbj.comment.BR;
import com.xbj.comment.BuildConfig;

/**
 * Time :2021/2/22
 * Author:xbj
 * Description :注册界面实体
 */
public class RegisterPageEntity  extends BaseObservable {
    //验证码按钮显示内容
    public String validateBtnName ="获取验证码";
    private boolean isInvitationCode,isPhone,isValidateCode,isPwd,isConfirmPwd;
    public boolean selectRemPwd = true;
    public int etHasValueNum = 0;
    public int etNum = 5;
    public boolean isClickable= false;
    //验证码token
    public String captchaToken;
    public  String invitationCode;
    public  String registerPhone;
    public  String validateCode;
    public  String registerPwd;
    public  String registerConfirmPwd;



    /**
     * 退出登录时 如果勾选记住密码，则全部显示，如果没有勾选记住密码，则只有密码输入框为空
     * @param orgCode
     * @param phoneNum
     */
    public RegisterPageEntity(String orgCode, String phoneNum){
    /*    if(!TextUtils.isEmpty(orgCode) && !TextUtils.isEmpty(phoneNum)) {
            this.orgCode = orgCode.toUpperCase();
            this.userPhone = phoneNum;
            isPhone = true;
            isName = true;
            etHasValueNum = 2;
        }*/
    }
    /**
     * 退出登录时 如果勾选记住密码，则全部显示，如果没有勾选记住密码，则只有密码输入框为空
     * @param orgCode
     * @param phoneNum
     */
    public RegisterPageEntity(String orgCode, String phoneNum,String pwd){
     /*   this.orgCode = orgCode.toUpperCase();
        this.userPhone = phoneNum;
        this.userPass = pwd;
        isPhone = true;
        isPwd = true;
        isName = true;
        etHasValueNum = 3;
        isClickable  = true;
        selectRemPwd = true;*/
    }

    public  RegisterPageEntity(){
        if(BuildConfig.DEBUG){
           /* this.orgCode = "00Y0073";
            this.userPhone = "18092425120";
            this.userPass = "yto123";
            isPhone = true;
            isPwd = true;
            isName = true;
            etHasValueNum = 3;
            isClickable  = true;
            selectRemPwd = true;*/
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
            if (etHasValueNum == etNum) {
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
            if (num == 1 && !isInvitationCode) {
                etHasValueNum++;
                isInvitationCode = true;
            }
            if (num == 2 && !isPhone) {
                etHasValueNum++;
                isPhone = true;
            }
            if (num == 3 && !isValidateCode) {
                etHasValueNum++;
                isValidateCode = true;
            }
            if (num == 4 && !isPwd) {
                etHasValueNum++;
                isPwd = true;
            }
            if (num == 5 && !isConfirmPwd) {
                etHasValueNum++;
                isConfirmPwd = true;
            }
        }
        else{
            if(num == 1 && isInvitationCode){
                isInvitationCode = false;
                etHasValueNum --;
            }
            if (num == 2 && isPhone) {
                etHasValueNum--;
                isPhone = false;
            }
            if (num == 3 && isValidateCode) {
                etHasValueNum--;
                isValidateCode = false;
            }
            if (num == 4 && isPwd) {
                etHasValueNum--;
                isPwd = false;
            }
            if (num == 5 && isConfirmPwd) {
                etHasValueNum--;
                isConfirmPwd = false;
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
            notifyPropertyChanged(BR.clickable);
        }
    }
    @Bindable
    public boolean isSelectRemPwd() {
        return selectRemPwd;
    }

    public void setSelectRemPwd(boolean selectRemPwd) {
        if(this.selectRemPwd != selectRemPwd){
            this.selectRemPwd = selectRemPwd;
            notifyPropertyChanged(BR.selectRemPwd);
        }

    }
    @Bindable
    public String getInvitationCode() {
        return invitationCode;
    }

    public void setInvitationCode(String invitationCode) {
        if (!invitationCode.equalsIgnoreCase(this.registerPhone)) {
            setClickableValue(1,invitationCode);
            this.invitationCode = invitationCode;
            notifyPropertyChanged(BR.invitationCode);
        }
    }
    @Bindable
    public String getRegisterPhone() {
        return registerPhone;
    }

    public void setRegisterPhone(String registerPhone) {

        if (!registerPhone.equalsIgnoreCase(this.registerPhone)) {
            setClickableValue(2,registerPhone);
            this.registerPhone = registerPhone;
            notifyPropertyChanged(BR.registerPhone);
        }
    }
    @Bindable
    public String getValidateCode() {
        return validateCode;
    }

    public void setValidateCode(String validateCode) {
        if (!validateCode.equalsIgnoreCase(this.validateCode)) {
            setClickableValue(3,validateCode);
            this.validateCode = validateCode;
            notifyPropertyChanged(BR.validateCode);
        }
    }
    @Bindable
    public String getRegisterPwd() {
        return registerPwd;
    }

    public void setRegisterPwd(String registerPwd) {

        if (!registerPwd.equalsIgnoreCase(this.registerPwd)) {
            setClickableValue(4,registerPwd);
            this.registerPwd = registerPwd;
            notifyPropertyChanged(BR.registerPwd);
        }
    }
    @Bindable
    public String getRegisterConfirmPwd() {
        return registerConfirmPwd;
    }

    public void setRegisterConfirmPwd(String registerConfirmPwd) {
        if (!registerConfirmPwd.equalsIgnoreCase(this.registerConfirmPwd)) {
            setClickableValue(5,registerConfirmPwd);
            this.registerConfirmPwd = registerConfirmPwd;
            notifyPropertyChanged(BR.registerConfirmPwd);
        }
    }

    @Bindable
    public String getValidateBtnName() {
        return validateBtnName;
    }

    public void setValidateBtnName(String validateBtnName) {

        if(!validateBtnName.equals(this.validateBtnName)){
            this.validateBtnName = validateBtnName;
            notifyPropertyChanged(BR.validateBtnName);
        }
    }
}
