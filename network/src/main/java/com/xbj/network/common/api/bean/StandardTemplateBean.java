package com.xbj.network.common.api.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.thoughtwork.base.customview.BaseCustomViewModel;

public class StandardTemplateBean extends BaseCustomViewModel {

    /**
     * code : 200
     * msg :
     * data : {"id":1,"name":"标准模板","code":"001","tag":"【易拣通】","content":"您的<快递品牌>快递<快递单号>正在派送，请您保持电话畅通，谢谢","createName":"tony","createDate":1597209465000,"updateName":"tony","updateDate":1597814304000}
     */
    /**
     * id : 1
     * name : 标准模板
     * code : 001
     * tag : 【易拣通】
     * content : 您的<快递品牌>快递<快递单号>正在派送，请您保持电话畅通，谢谢
     * createName : tony
     * createDate : 1597209465000
     * updateName : tony
     * updateDate : 1597814304000
     */

    @SerializedName("id")
    @Expose
    public int id;

    @SerializedName("name")
    @Expose
    public String name;

    @SerializedName("code")
    @Expose
    public String code;

    @SerializedName("tag")
    @Expose
    public String tag;

    @SerializedName("content")
    @Expose
    public String content;

    @SerializedName("createName")
    @Expose
    public String createName;

    @SerializedName("createDate")
    @Expose
    public long createDate;

    @SerializedName("updateName")
    @Expose
    public String updateName;

    @SerializedName("updateDate")
    @Expose
    public long updateDate;


    /**
     * 替换掉地址的具体信息
     */
    @SerializedName("replaceContent")
    @Expose
    public String replaceContent;

}
