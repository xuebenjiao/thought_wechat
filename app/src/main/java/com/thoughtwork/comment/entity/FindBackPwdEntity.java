package com.thoughtwork.comment.entity;

import com.thoughtwork.base.utils.MD5Util;

/**
 * Time :2020/8/3
 * Author:xbj
 * Description :
 */
public class FindBackPwdEntity {
    public String confirmPass;
    public String newPass;
    //密码校验token
    public String fpToken;
    public FindBackPwdEntity(){

    }
    public FindBackPwdEntity(String confirmPass, String newPass,String fpToken) {
        this.confirmPass = MD5Util.MD5(confirmPass);
        this.newPass = MD5Util.MD5(newPass);
        this.fpToken = fpToken;
    }
}
