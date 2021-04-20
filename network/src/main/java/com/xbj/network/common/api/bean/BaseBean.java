package com.xbj.network.common.api.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Time :2021/3/17
 * Author:xbj
 * Description :
 */
public class BaseBean {
    //内容
    @SerializedName("name")
    @Expose
    public String name;

    @Override
    public String toString() {
        return  name ;
    }
}
