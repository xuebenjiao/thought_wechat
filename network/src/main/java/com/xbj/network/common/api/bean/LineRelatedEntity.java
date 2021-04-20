package com.xbj.network.common.api.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Time :2020/2/24
 * Author:xbj
 * Description :
 */
public class LineRelatedEntity {

    /*
        "endOrgCode":"210205",
    "endOrgName":"上海市闵行区吴泾",
    "endOrgType":"转运中心",
    "id":null,
    "lineName":"上海中心-吴泾公司",
    "lineNo":"MOT210901TO21020501",
    "lineProperty":null,
    "lineStatus":null,
    "orgCode":null,
    "orgType":null,
    "startOrgCode":null,
    "startOrgName":null,
    "versionNo":1524881817184,
    "lineId":null,
    "startOrgType":null,
    "lineFrequencyName":"p1（0610）",
    "lineFrequencyNo":"MOT210901TO210205010610",
    "shift":null*/
    @SerializedName("endOrgCode")
    @Expose
    public String endOrgCode;

    @SerializedName("endOrgName")
    @Expose
    public String endOrgName;

    @SerializedName("endOrgType")
    @Expose
    public String endOrgType;

    @SerializedName("lineName")
    @Expose
    public String lineName;

    @SerializedName("lineNo")
    @Expose
    public String lineNo;

    @SerializedName("lineStatus")
    @Expose
    public String lineStatus;


    @SerializedName("lineFrequencyName")
    @Expose
    public String lineFrequencyName;

    @SerializedName("lineFrequencyNo")
    @Expose
    public String lineFrequencyNo;

    @SerializedName("versionNo")
    @Expose
    public long versionNo;




}
