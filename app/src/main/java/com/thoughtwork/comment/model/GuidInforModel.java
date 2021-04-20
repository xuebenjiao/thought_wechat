package com.thoughtwork.comment.model;

import com.thoughtwork.base.model.BaseModel;
import com.xbj.network.common.api.CommonApi;
import com.xbj.network.common.api.bean.BaseResponse;
import com.xbj.network.common.api.bean.PersonGuidBean;
import com.xbj.network.errorhandler.ExceptionHandle;
import com.xbj.network.observer.BaseObserver;

/**
 * Time :2021/1/26
 * Author:xbj
 * Description :获取用户指引信息
 * */
public class GuidInforModel extends BaseModel<BaseResponse<PersonGuidBean>> {
    @Override
    public void refresh() {

    }

    @Override
    public void load() {
        CommonApi.getInstance().getPersonGuidInfor(new BaseObserver<BaseResponse<PersonGuidBean>>(null) {
            @Override
            public void onError(ExceptionHandle.ResponeThrowable e) {
                loadFail(e.getMessage());
            }
            @Override
            public void onNext(BaseResponse<PersonGuidBean> personGuidBean) {
                loadSuccess(personGuidBean);
            }
        });
    }
}
