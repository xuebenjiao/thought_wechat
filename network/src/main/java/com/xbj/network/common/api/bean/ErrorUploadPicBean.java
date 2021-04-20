package com.xbj.network.common.api.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Time :2020/5/22
 * Author:xbj
 * Description :
 */
public class ErrorUploadPicBean {
    @SerializedName("yjtFilePath")
    @Expose
    public String yjtFilePath;

    @SerializedName("otherFilePath")
    @Expose
    public String otherFilePath;

    //签收上传图片成功返回的字段
    @SerializedName("wayBillNo")
    @Expose
    public String wayBillNo;

    @SerializedName("fileUrl")
    @Expose
    public String fileUrl;

}
