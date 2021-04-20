package com.thoughtwork.base.constants;

import android.text.TextUtils;

/**
 * Time :2019/11/8
 * Author:xbj
 * Description :
 */
public enum  ExpressTypeEnum{

    /*YTO("YTO",101),
    YUNDA("YUNDA",102),
    ZTO("ZTO",103),
    BAISHITONG("BAISHITONG",104),
    SF("SF",105),
    DANNIAO("DANNIAO",106),
    TIANTIAN("TIANTIAN",107),
    EMS("EMS",108),
    STO("STO",109),
    OTHER("OTHER",110);*/
    YTO("圆通速递","yuantong","YTO"),
    YUNDA("韵达快递","yunda","YUNDA"),
    ZTO("中通快递","zhongtong","ZTO"),
    BAISHITONG("百世快递","huitongkuaidi","HTKY"),
    STO("申通快递","shentong","STO"),
    SF("顺丰快递","shunfeng","SF"),
    DANNIAO("丹鸟快递","danniao","DANNIAO"),
    TIANTIAN("天天快递","tiantian","TTKDEX"),
    EMS("中国邮政","youzheng","EMS"),
    EMSX("邮政小包","youzheng","EMS"),
    ZHAIJISONG("宅急送","zhaijisong","ZHAIJISONG"),
    DEBANG("德邦快递","debangwuliu","DEBANG"),
    YOUSU("优速快递","yousu","YOUSU"),
    JD("京东快递","jd","JD"),
    UNKNOWN("未知","unknown",""),
    OTHER("其他","other","OTHER");
    private String name;
    private String expressLabel;
    private String expressCode;
    // 构造方法
    private ExpressTypeEnum(String name, String expressCode,String expressLabel) {
        this.name = name;
        this.expressCode = expressCode;
        this.expressLabel  = expressLabel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExpressLabel() {
        return expressLabel;
    }

    public void setExpressLabel(String expressLabel) {
        this.expressLabel = expressLabel;
    }

    public String getExpressCode() {
        return expressCode;
    }

    public void setExpressCode(String expressCode) {
        this.expressCode = expressCode;
    }

    @Override
    public String toString() {
        return TextUtils.isEmpty(expressLabel)? name:name+"—"+expressLabel;
    }
}
