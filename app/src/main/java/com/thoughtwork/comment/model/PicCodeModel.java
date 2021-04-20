package com.thoughtwork.comment.model;

import com.thoughtwork.base.model.BaseModel;
import com.xbj.network.common.api.CommonApi;
import com.xbj.network.common.api.bean.BaseResponse;
import com.xbj.network.common.api.bean.PicCodeBean;
import com.xbj.network.errorhandler.ExceptionHandle;
import com.xbj.network.observer.BaseObserver;

/**
 * Time :2021/1/26
 * Author:xbj
 * Description :获取图形验证码
 */
public class PicCodeModel extends BaseModel<BaseResponse<PicCodeBean>> {
    @Override
    public void refresh() {
    }

    @Override
    public void load() {
        CommonApi.getInstance().getCaptchCode(new BaseObserver<BaseResponse<PicCodeBean>>(null) {
            @Override
            public void onNext(BaseResponse<PicCodeBean> picCodeBean) {
                loadSuccess(picCodeBean);
            }
            @Override
            public void onError(ExceptionHandle.ResponeThrowable e) {
                loadFail(e.getMessage());
            }
        });
    }


}
