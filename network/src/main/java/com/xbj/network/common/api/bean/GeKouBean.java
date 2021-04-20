package com.xbj.network.common.api.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Time :2019/12/2
 * Author:xbj
 * Description :
 */
public class GeKouBean extends BaseResponse {
    @SerializedName("data")
    @Expose
    public ArrayList<String> data;
}
