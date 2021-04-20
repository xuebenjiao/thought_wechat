package com.xbj.network.interceptor;



import android.text.TextUtils;

import com.thoughtwork.base.BaseApplication;
import com.thoughtwork.base.constants.Constants;
import com.thoughtwork.base.utils.NetWorkUtil;
import com.xbj.network.INetworkRequestInfo;
import com.thoughtwork.base.utils.DeviceTool;
import com.thoughtwork.base.utils.FileUtil;
import com.thoughtwork.base.utils.SPUtils;

import java.util.HashMap;

/**
 * author : xbj
 * e-mail : xbjzhu@163.com
 * date   : 2019/10/22 17:32
 * desc   : 自定义请求头中的参数
 */
public class NetworkRequestInfo implements INetworkRequestInfo {
    HashMap<String, String> headerMap = new HashMap<>();

    public NetworkRequestInfo(){
        headerMap.put("os", "android");
        headerMap.put("versionName", BaseApplication.mVersionName);
//        headerMap.put("token", SPUtils.getStringValue(Constants.USER_TOKEN));
        headerMap.put("applicationId", BaseApplication.mApplicationId);
        //设备唯一序列号 （自己组装）
        String imsi = SPUtils.getStringValue(Constants.ANDROID_IMSI);
        headerMap.put("imsi", TextUtils.isEmpty(imsi) ? DeviceTool.getDeviceUnique():imsi);
        headerMap.put("appVersion", BaseApplication.mAppVersion);
        //devBrand、devType devMac,devSn
        headerMap.put("devSn", FileUtil.readFile(FileUtil.getSDPath()));
        //设备品牌
        headerMap.put("devBrand", DeviceTool.getBuildBrand());
        //设备型号
        headerMap.put("devType", DeviceTool.getBuildBrandModel());
        //内网IP
        headerMap.put("ipAddr", NetWorkUtil.getIPAddress(true));
        //AndroidId
        String androidID = DeviceTool.getAndroidId(BaseApplication.getmContext());
        headerMap.put("androidId", TextUtils.isEmpty(androidID)? null :androidID);
        //DeviceId
        String deviceId = DeviceTool.getDeviceId(BaseApplication.getmContext());
        headerMap.put("deviceId", TextUtils.isEmpty(deviceId)? null : deviceId);
        //设备Mac地址
        headerMap.put("devMac", DeviceTool.getMacDeviceMacAddress(BaseApplication.getmContext()));
        //设备android版本与android 版本号
        headerMap.put("androidVersion", DeviceTool.getBuildVersion());
        //设备Mac地址
        headerMap.put("exactLevel", "500");

//        headerMap.put("devBrand", DeviceTool.getBuildBrand());
    }

    @Override
    public HashMap<String, String> getRequestHeaderMap() {
        return headerMap;
    }

    @Override
    public boolean isDebug() {
        return BaseApplication.sDebug;
    }
}
