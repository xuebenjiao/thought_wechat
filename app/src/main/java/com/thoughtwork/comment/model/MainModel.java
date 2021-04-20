package com.thoughtwork.comment.model;

import com.thoughtwork.base.model.BaseModel;
import com.xbj.network.common.api.CommonApi;
import com.xbj.network.common.api.bean.BaseResponse;
import com.thoughtwork.base.model.UpdateBean;
import com.xbj.network.errorhandler.ExceptionHandle;
import com.xbj.network.observer.BaseObserver;

/**
 * Time :2019/11/26
 * Author:xbj
 * Description :
 */
public class MainModel extends BaseModel<BaseResponse<UpdateBean>> {
    @Override
    public void refresh() {

    }

    @Override
    public void load() {

        CommonApi.getInstance().getLatestVersion(new BaseObserver<BaseResponse<UpdateBean>>(null) {
            @Override
            public void onError(ExceptionHandle.ResponeThrowable e) {
                loadFail(e.code + ":"+e.message);
            }
            @Override
            public void onNext(BaseResponse<UpdateBean> updateBean) {
                loadSuccess(updateBean);
            }
        });

    }
}
