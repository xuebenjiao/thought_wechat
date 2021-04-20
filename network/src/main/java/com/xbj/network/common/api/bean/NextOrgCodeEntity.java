package com.xbj.network.common.api.bean;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Time :2020/2/21
 * Author:xbj
 * Description :
 */
public class NextOrgCodeEntity {
    @SerializedName("orgName")
    @Expose
    public String orgName;

    @SerializedName("orgCode")
    @Expose
    public String orgCode;

    @SerializedName("nextOrgCode")
    @Expose
    public String nextOrgCode;

    @SerializedName("nextOrgName")
    @Expose
    public String nextOrgName;

    @SerializedName("expressCompanyType")
    @Expose
    public String expressCompanyType;

    @NonNull
    @Override
    public String toString() {
        return nextOrgCode +"+"+nextOrgName;
    }
}
