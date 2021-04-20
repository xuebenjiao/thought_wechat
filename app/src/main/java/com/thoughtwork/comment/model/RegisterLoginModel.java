package com.thoughtwork.comment.model;

import com.thoughtwork.base.constants.Constants;
import com.thoughtwork.base.model.BaseModel;
import com.xbj.network.common.api.CommonApi;
import com.xbj.network.common.api.bean.BaseResponse;
import com.xbj.network.errorhandler.ExceptionHandle;
import com.xbj.network.observer.BaseObserver;

/**
 * Time :2021/2/23
 * Author:xbj
 * Description :注册请求modle
 */
public class RegisterLoginModel extends BaseModel<BaseResponse<String>> {
    private String json;
    @Override
    public void refresh() {
    }
    @Override
    public void load() {
        CommonApi.getInstance().register(new BaseObserver<BaseResponse<String>>(null) {
            @Override
            public void onError(ExceptionHandle.ResponeThrowable e) {
                e.printStackTrace();
                if(e.code == Constants.DEVICE_FIRST_LOING){
                    loadFail(e.code +"#"+ e.message);
                }
                else{
                    loadFail(e.message);
                }
            }
            @Override
            public void onNext(BaseResponse<String> loginBean) {
                loadSuccess(loginBean);
            }
        },getJson());
    }

    /**
     * 登录请求
     * @param json
     */
    public void requesRegister(String json){
        CommonApi.getInstance().register(new BaseObserver<BaseResponse<String>>(null) {
            @Override
            public void onError(ExceptionHandle.ResponeThrowable e) {
                e.printStackTrace();
                if(e.code == Constants.DEVICE_FIRST_LOING){
                    loadFail(e.code +"#"+ e.message);
                }
                else{
                    loadFail(e.message);
                }
            }
            @Override
            public void onNext(BaseResponse<String> loginBean) {
                loadSuccess(loginBean);
            }
        },json);
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }
}