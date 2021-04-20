package com.xbj.network.common.api.bean;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Time :2020/5/19
 * Author:xbj
 * Description :
 */
public class SignTemplateBean {
    @SerializedName("templateId")
    @Expose
    public String templateId;
    @SerializedName("templateName")
    @Expose
    public String templateName;

    @NonNull
    @Override
    public String toString() {
        return templateName;
    }
}
