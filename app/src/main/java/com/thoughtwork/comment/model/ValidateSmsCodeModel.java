package com.thoughtwork.comment.model;
import com.xbj.network.common.api.CommonApi;
import com.xbj.network.common.api.bean.BaseResponse;
import com.xbj.network.errorhandler.ExceptionHandle;
import com.xbj.network.observer.BaseObserver;

/**
 * Time :2021/1/26
 * Author:xbj
 * Description :校验短信验证码
 */
public class ValidateSmsCodeModel extends ExpressSmsCodeModel {
    @Override
    public void load() {
        CommonApi.getInstance().validateZtoSMSCode(new BaseObserver<BaseResponse<ExpressLoginBean>>(null) {
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
}
