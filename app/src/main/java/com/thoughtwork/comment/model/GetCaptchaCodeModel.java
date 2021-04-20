package com.thoughtwork.comment.model;

import com.thoughtwork.base.BaseApplication;
import com.thoughtwork.base.model.BaseModel;
import com.thoughtwork.base.utils.ToastUtil;
import com.xbj.network.common.api.CommonApi;
import com.xbj.network.common.api.bean.BaseResponse;
import com.xbj.network.common.api.bean.CaptchaCodeBean;
import com.xbj.network.errorhandler.ExceptionHandle;
import com.xbj.network.observer.BaseObserver;

/**
 * Time :2021/2/23
 * Author:xbj
 * Description :获取校验请求model
 */
public class GetCaptchaCodeModel extends BaseModel<BaseResponse<CaptchaCodeBean>> {
    private String phone;
    @Override
    public void refresh() {

    }

    @Override
    public void load() {
            CommonApi.getInstance().getCaptchaCode(new BaseObserver<BaseResponse<CaptchaCodeBean>>(null) {
                @Override
                public void onError(ExceptionHandle.ResponeThrowable e) {
                    loadFail(e.getMessage());
                    ToastUtil.show(BaseApplication.getmContext(), e.message);
                }
                @Override
                public void onNext(BaseResponse<CaptchaCodeBean> updateBean) {
                    loadSuccess(updateBean);
                }
            },getPhone());
        }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}