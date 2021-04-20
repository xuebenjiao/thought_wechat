package com.xbj.network;


import java.util.HashMap;

/**
 * author : xbj
 * e-mail : xbjzhu@163.com
 * date   : 2019/10/22 17:32
 * desc   :
 */
public interface INetworkRequestInfo {
    HashMap<String, String> getRequestHeaderMap();
    boolean isDebug();
}
