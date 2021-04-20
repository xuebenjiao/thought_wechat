package com.xbj.network.common.api.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Time :2020/11/12
 * Author:xbj
 * Description :账号校验实体类
 */
public class ValidateAccountInforBean {

    /*
    "checkNumber": 6,//可校验的总次数
    "surplusNumber": 5,//剩余可校验的次数
    "checkStatus": 0, 200为校验成功
    "checkMessage": "校验功能暂不支持，请自行确保账号正确"*/

    /*"empSex":"男","headPic":"headPic","userName":"薛本皎"}}*/
    @SerializedName("checkNumber")
    @Expose
    public int checkNumber;

    @SerializedName("surplusNumber")
    @Expose
    public int surplusNumber;
/*1成功，2失败*/
    @SerializedName("checkStatus")
    @Expose
    public int checkStatus;

    @SerializedName("checkMessage")
    @Expose
    public String checkMessage;
}
