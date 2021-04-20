package com.xbj.network.common.api.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Time :2021/2/23
 * Author:xbj
 * Description :验证码实体
 */
public class CaptchaCodeBean {
    @SerializedName("phone")
    @Expose
    public String phone;

    @SerializedName("captchaToken")
    @Expose
    public String captchaToken;

}
