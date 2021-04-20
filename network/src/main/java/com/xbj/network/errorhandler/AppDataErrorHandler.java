package com.xbj.network.errorhandler;

import com.thoughtwork.base.constants.Constants;
import com.xbj.network.common.api.bean.BaseResponse;
import com.thoughtwork.base.utils.LogUtils;

import io.reactivex.functions.Function;

/**
 * author : xbj
 * e-mail : xbjzhu@163.com
 * date   : 2019/10/22 17:32
 * desc   :
 */

/**
 * HandleFuc处理以下网络错误：
 * 1、应用数据的错误会抛RuntimeException；
 */
public class AppDataErrorHandler implements Function<BaseResponse, BaseResponse> {
    private static final String TAG = "AppDataErrorHandler";
    @Override
    public BaseResponse apply(BaseResponse response) throws Exception {
        LogUtils.i(TAG,"AppDataErrorHandler--->apply()--start---》response："+response);
        //由于服务端返回的code 没有具体的成功标识code，所以此处不做过滤
      /* if (response instanceof BaseResponse && (response.code !=200)) {
        if (response instanceof BaseResponse && (response.code == 0)) {
            if(response.code == Constants.TOKEN_OVERTIME|| response.code == Constants.DEVICE_FIRST_LOING ){
                throw new RuntimeException(response.message != null ? (response.code + "#"+response.message ): response.code+"");
            }
            else{
            throw new RuntimeException(response.message != null ? response.message : "");
            }
        }*/
        LogUtils.i(TAG,"AppDataErrorHandler--->apply()--start---》response："+response);
        //不能处理
       if(response.code != Constants.SUCCESS_CODE){
            //避免服务端将错误信息放在data中返回导致解析错误
            response.data = null;
        }
        return response;
    }
}