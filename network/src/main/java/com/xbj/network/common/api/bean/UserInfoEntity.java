package com.xbj.network.common.api.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Time :2020/2/25
 * Author:xbj
 * Description :
 */
public class UserInfoEntity {

    /*"empSex":"男","headPic":"headPic","userName":"薛本皎"}}*/
    @SerializedName("empSex")
    @Expose
    public String empSex;

    @SerializedName("headPic")
    @Expose
    public String headPic;

    @SerializedName("userName")
    @Expose
    public String userName;
}
