package com.thoughtwork.comment.model;

import com.thoughtwork.base.BaseApplication;
import com.thoughtwork.base.model.BaseModel;
import com.thoughtwork.base.model.UpdateBean;
import com.thoughtwork.base.utils.ToastUtil;
import com.xbj.network.common.api.CommonApi;
import com.xbj.network.common.api.bean.BaseResponse;
import com.xbj.network.errorhandler.ExceptionHandle;
import com.xbj.network.observer.BaseObserver;

/**
 * Time :2021/1/26
 * Author:xbj
 * Description :版本检测model
 * */
public class AppUpdateModel extends BaseModel<BaseResponse<UpdateBean>> {
    @Override
    public void refresh() {

    }

    @Override
    public void load() {
            CommonApi.getInstance().getLatestVersion(new BaseObserver<BaseResponse<UpdateBean>>(null) {
                @Override
                public void onError(ExceptionHandle.ResponeThrowable e) {
                    loadFail(e.getMessage());
                    ToastUtil.show(BaseApplication.getmContext(), e.message);
                }
                @Override
                public void onNext(BaseResponse<UpdateBean> updateBean) {
                    loadSuccess(updateBean);
                }
            });
    }
}
