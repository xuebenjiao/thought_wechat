package com.xbj.network.common.api.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Time :2020/2/21
 * Author:xbj
 * Description :
 */
public class LineRelatedResponse extends BaseResponse {
    @SerializedName("data")
    @Expose
    public LineRelatedDataEntity data;
}
