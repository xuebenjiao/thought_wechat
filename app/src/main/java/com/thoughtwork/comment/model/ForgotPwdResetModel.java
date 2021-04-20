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
 * Description :忘记密码重置
 */
public class ForgotPwdResetModel extends BaseModel<BaseResponse<String>> {
    @Override
    public void refresh() {
    }

    @Override
    public void load() {
    }

    /**
     * 登录请求
     *
     * @param json
     */
    public void forgetPassWord(String json) {
        CommonApi.getInstance().forgetPassWord(new BaseObserver<BaseResponse<String>>(null) {
            @Override
            public void onError(ExceptionHandle.ResponeThrowable e) {
                e.printStackTrace();
                if (e.code == Constants.DEVICE_FIRST_LOING) {
                    loadFail(e.code + "#" + e.message);
                } else {
                    loadFail(e.message);
                }
            }

            @Override
            public void onNext(BaseResponse<String> loginBean) {
                loadSuccess(loginBean);
            }
        }, json);
    }
}