package com.xbj.network.common.api;

import com.xbj.network.common.api.bean.BaseResponse;
import com.xbj.network.common.api.bean.DaliyOrgemBean;
import com.xbj.network.common.api.bean.DeliveryAreaItemBean;
import com.xbj.network.common.api.bean.DeliveryPersonBean;
import com.xbj.network.common.api.bean.BaseBean;
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
import com.xbj.network.common.api.bean.OltsStatVlmResponseBean;
import com.xbj.network.common.api.bean.PersonGuidBean;
import com.xbj.network.common.api.bean.PicCodeBean;
import com.xbj.network.common.api.bean.PushFailResponseEntity;
import com.xbj.network.common.api.bean.QueryOutletStation;
import com.xbj.network.common.api.bean.QueryOutletsStationBean;
import com.xbj.network.common.api.bean.QueryPublishedTaskResponsBean;
import com.xbj.network.common.api.bean.ScanRecordBean;
import com.xbj.network.common.api.bean.SignCodBean;
import com.xbj.network.common.api.bean.SignDeliveriedTypeBean;
import com.xbj.network.common.api.bean.SignTemplateBean;
import com.xbj.network.common.api.bean.SmsSendStatusBean;
import com.xbj.network.common.api.bean.StandardTemplateBean;
import com.thoughtwork.base.model.UpdateBean;
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

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;


/**
 * date :2019/10/29.
 * author:xbj
 * description:
 */

public interface CommonApiInterface {
    @Headers({"Content-Type: application/json"})//需要添加头
    @POST("app/user/login")
    Observable<BaseResponse<LoginBean>> postLogin(@Body RequestBody requestBody);


    @GET("app/mine/getInviteCode")
    Observable<BaseResponse<String>> getInviteCode(@Header("token") String token);
}

