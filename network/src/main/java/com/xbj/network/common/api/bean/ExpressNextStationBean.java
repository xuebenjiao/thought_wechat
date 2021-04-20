package com.xbj.network.common.api.bean;

import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Time :2020/11/10
 * Author:xbj
 * Description : 下一站接口实体
 */
public class ExpressNextStationBean {

    /*     "sendCode": "00Y00730002",
        "orgCode": "00Y0073",
        "sendName": "发往配置测试",*/

    /**
     * 用户自定义的下一站code
     */
    @SerializedName("sendCode")
    @Expose
    public String sendCode;
    /**
     * 用户自定义的下一站名称
     */
    @SerializedName("sendName")
    @Expose
    public String sendName;

    @NonNull
    @Override
    public String toString() {
        return (TextUtils.isEmpty(sendCode)?"": sendCode+"+")+sendName;
    }
}
