package com.thoughtwork.comment.model;

import com.thoughtwork.base.model.BaseModel;
import com.xbj.network.common.api.CommonApi;
import com.xbj.network.common.api.bean.BaseResponse;
import com.xbj.network.errorhandler.ExceptionHandle;
import com.xbj.network.observer.BaseObserver;

/**
 * Time :2021/1/27
 * Author:xbj
 * Description :校验设备注册时短信验证码
 */
public class ValidateRegisterSmsCodeModel extends BaseModel<BaseResponse<String>> {
    private String json;

    @Override
    public void refresh() {

    }

    @Override
    public void load() {
        CommonApi.getInstance().validateSMSCode(new BaseObserver<BaseResponse>(null) {
            @Override
            public void onError(ExceptionHandle.ResponeThrowable e) {
              loadFail(e.getMessage());
            }
            @Override
            public void onNext(BaseResponse loginBean) {
             loadSuccess(loginBean);
            }
        },getJson());
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }
}
