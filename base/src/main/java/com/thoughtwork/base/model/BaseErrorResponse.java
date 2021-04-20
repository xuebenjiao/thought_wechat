package com.thoughtwork.base.model;

/**
 * Time :2021/2/1
 * Author:xbj
 * Description : 请求失败实体
 */
public  class BaseErrorResponse {
    //失败提示内容
    public String message;

    //运单号
    public String waybillNo;

    public BaseErrorResponse() {
    }

    public BaseErrorResponse(String message) {
        this(message,"");
    }

    public BaseErrorResponse(String message, String waybillNo) {
        this.message = message;
        this.waybillNo = waybillNo;
    }
}
