package com.xbj.network.common.api.bean;

import java.util.ArrayList;

/**
 * Time :2021/3/17
 * Author:xbj
 * Description :设备/车辆列表项解析对象
 */
public class DeviceBusItemBean {
    //id
    public int  id;
    //车牌号
    public String numberPlate;
    //车辆类型/设备型号
    public String model;
    //使用状态 1 使用  0 闲置
    public int status;
    //车辆照片
    public ArrayList<String> carImages;
    //设备照片
    public ArrayList<String> deviceImages;

    //设备数量
    public int amount;
    //设备类型
    public String type;



}
