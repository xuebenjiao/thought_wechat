package com.thoughtwork.comment.databing;

import com.thoughtwork.base.customview.BaseCustomViewModel;

/**
 * Time :2020/3/4
 * Author:xbj
 * Description :登录信息实体model
 */
public class LoginInforPageModel extends BaseCustomViewModel {
    public String expressCode;
    public String expressName;
    public String expressAppName;
    public boolean isSuccess;
    //是否需要双重验证
    public boolean isValidateFlag;
    //是否是双重检验
    public boolean doubleValiteFlag;
    //电话号码
    public String doubleValitePhone;
    public String errorTitle;
    public String errorMsg;

    public SpearListItemBean empGun;

    public boolean isFirstItem = false;



    public String getExpressName() {
        return expressName;
    }

    public void setExpressName(String expressName) {
        this.expressName = expressName;
    }

    public String getExpressAppName() {
        return expressAppName;
    }

    public void setExpressAppName(String expressAppName) {
        this.expressAppName = expressAppName;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public boolean isValidateFlag() {
        return isValidateFlag;
    }

    public void setValidateFlag(boolean validateFlag) {
        isValidateFlag = validateFlag;
    }

    public boolean isDoubleValiteFlag() {
        return doubleValiteFlag;
    }

    public void setDoubleValiteFlag(boolean doubleValiteFlag) {
        this.doubleValiteFlag = doubleValiteFlag;
    }
}
