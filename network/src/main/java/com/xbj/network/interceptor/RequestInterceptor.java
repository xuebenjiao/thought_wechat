package com.xbj.network.interceptor;

import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.xbj.network.INetworkRequestInfo;
import com.thoughtwork.base.utils.LogUtils;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;


/**
 * author : xbj
 * e-mail : xbjzhu@163.com
 * date   : 2019/10/22 17:32
 * desc   : 请求头拦截器
 */
public class RequestInterceptor implements Interceptor {
    private static final String TAG = "RequestInterceptor";
    private INetworkRequestInfo mNetworkRequestInfo;
    public RequestInterceptor(INetworkRequestInfo networkRequestInfo) {
        this.mNetworkRequestInfo = networkRequestInfo;
    }

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request.Builder builder = chain.request()
                .newBuilder();
        LogUtils.i(TAG,"RequestInterceptor-->intercept()--->builder-start:"+builder);
        if(mNetworkRequestInfo != null) {
            for(String key:mNetworkRequestInfo.getRequestHeaderMap().keySet()){
                if(!TextUtils.isEmpty(mNetworkRequestInfo.getRequestHeaderMap().get(key))&& builder!=null) {
                    LogUtils.i(TAG,key+"-->value:"+ mNetworkRequestInfo.getRequestHeaderMap().get(key));
                    builder.addHeader(key, mNetworkRequestInfo.getRequestHeaderMap().get(key));
                }
            }
        }
        LogUtils.i(TAG,"RequestInterceptor-->intercept()--->builder-end:"+builder);
        return chain.proceed(builder.build());
    }
}