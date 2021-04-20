package com.xbj.network.common.api;


import com.thoughtwork.base.BaseApplication;
import com.thoughtwork.base.constants.Constants;
import com.thoughtwork.base.model.UpdateBean;
import com.thoughtwork.base.utils.FileUtil;
import com.thoughtwork.base.utils.SPUtils;
import com.xbj.network.ApiBase;
import com.xbj.network.common.api.bean.BaseBean;
import com.xbj.network.common.api.bean.OltsStatVlmResponseBean;
import com.xbj.network.common.api.bean.QueryOutletStation;
import com.xbj.network.common.api.bean.QueryOutletsStationBean;
import com.xbj.network.common.api.bean.BaseResponse;
import com.xbj.network.common.api.bean.DaliyOrgemBean;
import com.xbj.network.common.api.bean.DeliveryAreaItemBean;
import com.xbj.network.common.api.bean.DeliveryPersonBean;
import com.xbj.network.common.api.bean.DeviceModelResponse;
import com.xbj.network.common.api.bean.ErrorListDataBean;
import com.xbj.network.common.api.bean.ErrorTypeBean;
import com.xbj.network.common.api.bean.ErrorUploadPicBean;
import com.xbj.network.common.api.bean.ExpressNameBean;
import com.xbj.network.common.api.bean.ExpressNextStationEntity;
import com.xbj.network.common.api.bean.ExpressStationBean;
import com.xbj.network.common.api.bean.FailListBean;
import com.xbj.network.common.api.bean.LineRelatedDataEntity;
import com.xbj.network.common.api.bean.LoginBean;
import com.xbj.network.common.api.bean.MsgTokenBean;
import com.xbj.network.common.api.bean.PersonGuidBean;
import com.xbj.network.common.api.bean.PicCodeBean;
import com.xbj.network.common.api.bean.PushFailResponseEntity;
import com.xbj.network.common.api.bean.QueryPublishedTaskResponsBean;
import com.xbj.network.common.api.bean.ScanRecordBean;
import com.xbj.network.common.api.bean.SignCodBean;
import com.xbj.network.common.api.bean.SignDeliveriedTypeBean;
import com.xbj.network.common.api.bean.SignTemplateBean;
import com.xbj.network.common.api.bean.SmsSendStatusBean;
import com.xbj.network.common.api.bean.StandardTemplateBean;
import com.xbj.network.common.api.bean.StationNameBean;
import com.xbj.network.common.api.bean.TaskCountBean;
import com.xbj.network.common.api.bean.TaskSummaryBean;
import com.xbj.network.common.api.bean.UserInfoEntity;
import com.xbj.network.common.api.bean.ValidateAccountInforBean;
import com.xbj.network.common.api.bean.ValidateExpressBean;
import com.xbj.network.common.api.bean.CaptchaCodeBean;
import com.xbj.network.common.api.bean.WaitDeliveryListBean;
import com.xbj.network.common.api.bean.WaybillInforResponse;
import com.xbj.common.entity.response.PublishResponBean;
import com.xbj.network.common.api.bean.response.QueryDeviceListResponseBean;
import com.xbj.network.common.api.bean.taskList.QueryTaskResponsBean;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * date :2019/10/29.
 * author:xbj
 * description:
 */

public final class CommonApi extends ApiBase {
    private static volatile CommonApi instance = null;
    private CommonApiInterface commonApiInterface;
    public static final String PAGE = "page";
    private CommonApi() {
        super(BaseApplication.serverUrl);
        commonApiInterface = retrofit.create(CommonApiInterface.class);
    }

    public static CommonApi getInstance() {
        if (instance == null) {
            synchronized (CommonApi.class) {
                if (instance == null) {
                    instance = new CommonApi();
                }
            }
        }
        return instance;
    }
    /**
     *
     * @param observer
     * @param json 用户、用户密码、网点编号json字符串
     */
    public void postLogin(Observer<BaseResponse<LoginBean>> observer, String json) {
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), json);
        ApiSubscribe(commonApiInterface.postLogin(body),observer);
    }
    /**
     * 获取邀请码
     * @param observer
     */
    public void getInviteCode(Observer<BaseResponse<String>> observer) {
        ApiSubscribe(commonApiInterface.getInviteCode(SPUtils.getStringValue(Constants.USER_TOKEN)), observer);
    }

}
