package com.xbj.network.common.api.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Time :2020/2/8
 * Author:xbj
 * Description :
 */
public class PicCodeBean {
        @SerializedName("img")
        @Expose
        public String img;

        @SerializedName("captchaToken")
        @Expose
        public String captchaToken;
}
