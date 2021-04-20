package com.thoughtwork.comment.viewmodel;

import com.thoughtwork.base.BaseApplication;
import com.thoughtwork.base.activity.IBaseView;
import com.thoughtwork.base.constants.Constants;
import com.thoughtwork.base.model.BaseModel;
import com.thoughtwork.base.model.UpdateBean;
import com.thoughtwork.base.utils.AppDeviceUtils;
import com.thoughtwork.base.utils.LiveDataBus;
import com.thoughtwork.base.utils.SPUtils;
import com.thoughtwork.base.utils.ToastUtil;
import com.thoughtwork.base.viewmodel.MvvmBaseViewModel;
import com.thoughtwork.base.viewmodel.ViewStatus;
import com.thoughtwork.comment.model.AppUpdateModel;
import com.xbj.network.common.api.bean.BaseResponse;

/**
 * Time :2019/11/26
 * Author:xbj
 * Description : 主界面的ViewModel  业务逻辑处理类
 */
public class AppUpdateViewModel extends MvvmBaseViewModel<IBaseView, AppUpdateModel>  implements  BaseModel.IModelListener<BaseResponse<UpdateBean>,String> {
    public AppUpdateViewModel(){
        model = new AppUpdateModel();
        model.register(this);
    }

    @Override
    public void onLoadFinish(BaseModel model, BaseResponse<UpdateBean> data) {
        if (getPageView() != null) {
            if(data == null) return;
            if (data.code == Constants.SUCCESS_CODE) {
                int currentCode = AppDeviceUtils.getVersionCode(BaseApplication.getmContext());
                if (data.data.versionNum != 0) {
                    SPUtils.saveStringValue(Constants.APP_DOWNLOAD_URL, data.data.upgradeUrl);
                    //判断服务端版本是否比当前版本高
                    if (currentCode < data.data.versionNum) {
                        LiveDataBus.getInstance().with("AppUpdate",UpdateBean.class).postValue(data.data);
                    }
                }
                //避免进入主界面自动检测版本时提醒
                else {
                    ToastUtil.show(BaseApplication.getmContext(), "已是最新版本！");
                }
            }
            //token失效
            else if (data.code == Constants.TOKEN_OVERTIME) {

                LiveDataBus.getInstance().with(LiveDataBus.VIEW_STATUS, ViewStatus.class).postValue(ViewStatus.INVALID_TOKEN);
            } else {
                ToastUtil.show(BaseApplication.getmContext(), data.message);
            }
        }
    }

    @Override
    public void onLoadFail(BaseModel model, String prompt) {
        if(model !=null && model instanceof AppUpdateModel && getPageView() != null){
            ToastUtil.show(BaseApplication.getmContext(), prompt);
        }
    }

    public void reqestUpdateVersion(){
        if(model != null) {
//            model.load();
        }
    }
/*    public static class UpdateViewModelFactory implements ViewModelProvider.Factory {
        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            return (T) new AppUpdateViewModel();
        }
    }*/
}
