package com.thoughtwork.comment.entity;

/**
 * Time :2020/8/11
 * Author:xbj
 * Description :
 */
public class DoubleValidateRequestEntity {
    public String expressCode;
    public String smsVerifier;
    public String phoneNum;

    public DoubleValidateRequestEntity() {
    }


    public DoubleValidateRequestEntity(String expressCode,String phoneNum) {
        this.expressCode = expressCode;
        this.phoneNum  = phoneNum;
    }


}
