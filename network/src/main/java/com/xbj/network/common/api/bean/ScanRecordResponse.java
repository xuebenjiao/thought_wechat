package com.xbj.network.common.api.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ScanRecordResponse extends BaseResponse {
    @SerializedName("data")
    @Expose
    public ArrayList<ScanRecordBean> data;
}
