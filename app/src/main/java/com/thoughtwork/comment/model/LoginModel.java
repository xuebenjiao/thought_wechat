package com.thoughtwork.comment.model;
import com.thoughtwork.base.model.BaseModel;
import com.thoughtwork.base.constants.Constants;
import com.xbj.network.common.api.CommonApi;
import com.xbj.network.common.api.bean.BaseResponse;
import com.xbj.network.common.api.bean.LoginBean;
import com.xbj.network.errorhandler.ExceptionHandle;
import com.xbj.network.observer.BaseObserver;

/**
 * Time :2019/11/4
 * Author:xbj
 * Description :
 */
public class LoginModel extends BaseModel<BaseResponse<LoginBean>> {
    @Override
    public void refresh() {
    }
    @Override
    public void load() {
    }

    /**
     * 登录请求
     * @param json
     */
    public void requestLogin(String json){
        CommonApi.getInstance().postLogin(new BaseObserver<BaseResponse<LoginBean>>(null) {
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
            public void onNext(BaseResponse<LoginBean> loginBean) {
                loadSuccess(loginBean);
            }
        },json);
    }
}
