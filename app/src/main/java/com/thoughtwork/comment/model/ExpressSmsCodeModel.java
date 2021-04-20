package com.thoughtwork.comment.model;

import com.thoughtwork.base.model.BaseModel;
import com.xbj.network.common.api.CommonApi;
import com.xbj.network.common.api.bean.BaseResponse;
import com.xbj.network.errorhandler.ExceptionHandle;
import com.xbj.network.observer.BaseObserver;

/**
 * Time :2021/1/26
 * Author:xbj
 * Description : 获取快递公司短信验证码
 */
public class ExpressSmsCodeModel extends BaseModel<BaseResponse<ExpressLoginBean>> {
    private String smsToken;
    private String json;
    @Override
    public void refresh() {

    }

    @Override
    public void load() {
        CommonApi.getInstance().getZtoSmsCode(new BaseObserver<BaseResponse<ExpressLoginBean>>(null) {
            @Override
            public void onNext(BaseResponse<ExpressLoginBean> smsCodeBean) {
                loadSuccess(smsCodeBean);
            }
            @Override
            public void onError(ExceptionHandle.ResponeThrowable e) {
                loadFail(e.getMessage());
            }
        },getSmsToken(),getJson());

    }


    public void request(){

    }

    public String getSmsToken() {
        return smsToken;
    }

    public void setSmsToken(String smsToken) {
        this.smsToken = smsToken;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }
}
