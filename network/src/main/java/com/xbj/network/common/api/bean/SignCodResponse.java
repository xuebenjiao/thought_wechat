package com.xbj.network.common.api.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Time :2020/4/9
 * Author:xbj
 * Description :
 */
public class SignCodResponse  extends BaseResponse {
    @SerializedName("data")
    @Expose
    public ArrayList<SignCodBean> data;
}
