package com.thoughtwork.comment.entity;

/**
 * Time :2019/11/12
 * Author:xbj
 * Description :
 */
public class RequestLoginEntity {
    //邀请码
    public String inviteCode;
    //电话
    public String       phone;
    //验证码
    public String captchaCode;
    //验证码token
    public String captchaToken;

    //登录类型 0 是密码登录，1是验证码登录
    public int         loginType;
    //密码
    public String       passWord;

    public RequestLoginEntity(){

    }

    public RequestLoginEntity(String passWord, String phone) {
        this.passWord = passWord;
        this.phone = phone;
    }

    public RequestLoginEntity(String phone, String captchaCode, String captchaToken) {
        this.phone = phone;
        this.captchaCode = captchaCode;
        this.captchaToken = captchaToken;
    }
    public RequestLoginEntity( String phone, String captchaCode, String captchaToken, String passWord) {
        this.phone = phone;
        this.captchaCode = captchaCode;
        this.captchaToken = captchaToken;
        this.loginType = loginType;
        this.passWord = passWord;
    }
    public RequestLoginEntity(String inviteCode, String phone, String captchaCode, String captchaToken, String passWord) {
        this.inviteCode = inviteCode;
        this.phone = phone;
        this.captchaCode = captchaCode;
        this.captchaToken = captchaToken;
        this.loginType = loginType;
        this.passWord = passWord;
    }
}
