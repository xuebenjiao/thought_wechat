package com.thoughtwork.comment.entity;

import com.thoughtwork.comment.model.GetBackPwdPageEntity;
import com.thoughtwork.comment.model.SmsLoginPageModel;

/**
 * Time :2020/2/8
 * Author:xbj
 * Description :
 */
public class RequestPicSmsEntity {
    public String imgCode;
    public String userPhone;
    private String captchaToken ;
    private String smsLoginCode;
    private String smsToken;
    private String orgCode;
    private String smsVerifier;
    public RequestPicSmsEntity(){

    }
    public RequestPicSmsEntity(SmsLoginPageModel model){
        this.imgCode = model.getPicCode();
        this.userPhone = model.userPhone;
        this.captchaToken = model.getCaptchaToken();
        this.smsToken = model.smsToken;
        this.smsLoginCode = model.getSmsCode();
        this.orgCode = model.orgCode;
    }
    public RequestPicSmsEntity(String ztoSmsCode){
        this.smsVerifier = ztoSmsCode;
    }

    public RequestPicSmsEntity(GetBackPwdPageEntity model){
        this.imgCode = model.getPicCode();
        this.userPhone = model.userPhone;
        this.captchaToken = model.captchaToken;
        this.smsToken = model.smsToken;
        this.smsLoginCode = model.getSmsCode();
        this.orgCode = model.getOrgCode();
    }


}
