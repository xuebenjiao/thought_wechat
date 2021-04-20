package com.xbj.network.interceptor;

import androidx.annotation.NonNull;

import com.thoughtwork.base.utils.LiveDataBus;
import com.thoughtwork.base.utils.LogUtils;
import com.thoughtwork.base.viewmodel.ViewStatus;

import org.simple.eventbus.EventBus;

import java.io.IOException;
import java.net.SocketTimeoutException;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * author : xbj
 * e-mail : xbjzhu@163.com
 * date   : 2019/10/22 17:32
 * desc   : 请求响应头处理
 */
public class ResponseInterceptor implements Interceptor {
    private static final String TAG = "ResponseInterceptor";
    public ResponseInterceptor() {
    }
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        LogUtils.i(TAG,"ResponseInterceptor-->intercept()-->chain"+chain);
        Request request = chain.request();
        Response response = null;
        try {
            response = chain.proceed(request);
            String rawJson = response.body() == null ? "" : response.body().string();
            LogUtils.i(TAG,"请求响应数据："+rawJson);
            return response.newBuilder().body(ResponseBody.create(response.body().contentType(), rawJson)).build();
        } catch (SocketTimeoutException e) {
            throw e;
        } catch (Exception e) {
            throw e;
        }
        finally {
            LiveDataBus.getInstance().with(LiveDataBus.VIEW_STATUS, ViewStatus.class).postValue(ViewStatus.HIDE_LOADING);
        }
    }
}
