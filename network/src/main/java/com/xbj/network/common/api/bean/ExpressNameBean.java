package com.xbj.network.common.api.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import androidx.annotation.NonNull;

public class ExpressNameBean {
    @SerializedName("expressName")
    @Expose
    public String expressName;
    @SerializedName("expressCode")
    @Expose
    public String expressCode;

    public boolean isSelect;
    @NonNull
    @Override
    public String toString() {
        return this.expressName;
    }

    public ExpressNameBean(String expressName, String expressCode) {
        this.expressName = expressName;
        this.expressCode = expressCode;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }
}
