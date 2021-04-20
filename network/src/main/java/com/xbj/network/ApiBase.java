package com.xbj.network;

//import com.ding.library.CaptureInfoInterceptor;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.thoughtwork.base.BaseApplication;
import com.thoughtwork.base.utils.NetWorkUtil;
import com.thoughtwork.base.utils.ToastUtil;
import com.thoughtwork.base.viewmodel.ViewStatus;
import com.thoughtwork.base.utils.LiveDataBus;
import com.xbj.network.converter.NullOnEmptyConverterFactory;
import com.xbj.network.errorhandler.AppDataErrorHandler;
import com.xbj.network.errorhandler.HttpErrorHandler;
import com.xbj.network.interceptor.NetworkRequestInfo;
import com.xbj.network.interceptor.RequestInterceptor;
import com.xbj.network.interceptor.ResponseInterceptor;
import com.thoughtwork.base.utils.LogUtils;

import org.simple.eventbus.EventBus;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * author : xbj
 * e-mail : xbjzhu@163.com
 * date   : 2019/10/22 17:32
 * desc   :
 */
public abstract class ApiBase {
    private static final String TAG = "ApiBase";
    protected Retrofit retrofit;
    private static INetworkRequestInfo networkRequestInfo;
    private static ErrorTransformer sErrorTransformer = new ErrorTransformer();
    //请求的拦截器
//    private static RequestInterceptor sHttpsRequestInterceptor;
    //响应的拦截器
    private static ResponseInterceptor sHttpsResponseInterceptor;

    protected ApiBase(String baseUrl) {
        initRetrefitWithUrl(baseUrl);
    }

    private void initRetrefitWithUrl(String baseUrl) {
        retrofit = new Retrofit
                .Builder()
                .client(getOkHttpClient())
                .baseUrl(baseUrl)//baseUrl必须以斜杠结束 否则会抛出一个异常  IllegalArgumentException
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                //解决空数据转换器
                .addConverterFactory(new NullOnEmptyConverterFactory())
                //数据转换器
                .addConverterFactory(GsonConverterFactory.create()).build();
    }

    public static void setNetworkRequestInfo(INetworkRequestInfo requestInfo) {
        networkRequestInfo = requestInfo;
//        sHttpsRequestInterceptor = new RequestInterceptor(requestInfo);
//        sHttpsResponseInterceptor = new ResponseInterceptor();
    }

    public OkHttpClient getOkHttpClient() {
        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
//                .sslSocketFactory()//添加证书
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS);



        /*可以统一添加网络参数到请求头*/
    /*    if(sHttpsRequestInterceptor != null) {
            okHttpClient.addInterceptor(sHttpsRequestInterceptor);
        }*/
//        if(sHttpsResponseInterceptor != null) {
//            okHttpClient.addInterceptor(sHttpsResponseInterceptor);
//        }
        if(networkRequestInfo != null){
            okHttpClient.addInterceptor( new RequestInterceptor(networkRequestInfo));
        }
        okHttpClient.addInterceptor(new ResponseInterceptor());
        setLoggingLevel(okHttpClient);
        OkHttpClient httpClient = okHttpClient.build();
        //同一主机最大可以请求数
        httpClient.dispatcher().setMaxRequestsPerHost(20);
        return httpClient;
    }

    private void setLoggingLevel(OkHttpClient.Builder builder) {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        //BODY打印信息,NONE不打印信息
        if(networkRequestInfo != null) {
            logging.setLevel(networkRequestInfo.isDebug() ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);
        }
        builder.addInterceptor(logging);
    }
    /**
     * 封装线程管理和订阅的过程
     */
    public  void ApiSubscribe(Observable observable, Observer observer,boolean...isNeedLoading) {
        try{
            if(NetWorkUtil.isAvailable(BaseApplication.getmContext())) {
                //验证请求拦截器是否未null
//                validataRequestIntercepter();

                if(isNeedLoading != null &&  isNeedLoading.length != 0 && !isNeedLoading[0]) {
                }
                else{
                    LiveDataBus.getInstance().with(LiveDataBus.VIEW_STATUS, ViewStatus.class).postValue(ViewStatus.LOADING);
                }
//RxJava操作，此操作在拦截器后执行
                observable.subscribeOn(io.reactivex.schedulers.Schedulers.io())
                        .unsubscribeOn(io.reactivex.schedulers.Schedulers.io())
                        //回调在主线程
                        .observeOn(AndroidSchedulers.mainThread())
                        //组成
                        .compose(sErrorTransformer)
                        .subscribe(observer);
            }
            else{
                LiveDataBus.getInstance().with(LiveDataBus.VIEW_STATUS, ViewStatus.class).postValue(ViewStatus.HIDE_LOADING);
                ToastUtil.show(BaseApplication.getmContext(),"网络不可用，请设置后重试！");
            }

        }catch (Exception e){
            LogUtils.i(TAG,"ApiSubscribe:"+e.toString());
            LiveDataBus.getInstance().with(LiveDataBus.VIEW_STATUS, ViewStatus.class).postValue(ViewStatus.HIDE_LOADING);
            ToastUtil.show(BaseApplication.getmContext(),"网络异常，请稍后重试！");
        }
    }

    /**
     * 验证okhttp的拦截器是否未空，避免请求时请求头没有参数（部分手机请求时请求头）
     */
    private void validataRequestIntercepter(){
//        initRetrefitWithUrl(BaseApplication.serverUrl);
        OkHttpClient okHttpClient = (OkHttpClient) retrofit.callFactory();
        LogUtils.i(TAG,"validataRequestIntercepter:okHttpClient"+okHttpClient);
        if(okHttpClient != null  && okHttpClient.interceptors() != null ){
            LogUtils.i(TAG,"validataRequestIntercepter:拦截器的个数是:"+okHttpClient.interceptors().size());
            //系统异常后第一次调用还未添加拦截器时或者有拦截器但是为null
            if( okHttpClient.interceptors().size()  == 0){
                okHttpClient.interceptors().add(0, new RequestInterceptor(new NetworkRequestInfo()));
                okHttpClient.interceptors().add(1, new ResponseInterceptor());
            }
            //只有打印日志拦截器
            else if(okHttpClient.interceptors().size() == 1){
                okHttpClient.interceptors().add(0, new RequestInterceptor(new NetworkRequestInfo()));
                okHttpClient.interceptors().set(0, new RequestInterceptor(new NetworkRequestInfo()));
                okHttpClient.interceptors().set(1, new ResponseInterceptor());
            }
            else if(okHttpClient.interceptors().size() == 3){
                if(okHttpClient.interceptors().get(0) == null){
                    okHttpClient.interceptors().set(0, new RequestInterceptor(new NetworkRequestInfo()));
                }
                if(okHttpClient.interceptors().get(1) == null){
                    okHttpClient.interceptors().set(1, new ResponseInterceptor());
                }
            }
        }
    }

    /**
     * 处理错误的变换
     * 网络请求的错误处理，其中网络错误分为两类：
     * 1、http请求相关的错误，例如：404，403，socket timeout等等；
     * 2、http请求正常，但是返回的应用数据里提示发生了异常，表明服务器已经接收到了来自客户端的请求，但是由于
     * 某些原因，服务器没有正常处理完请求，可能是缺少参数，或者其他原因；
     */
    private static class ErrorTransformer<T> implements ObservableTransformer {

        @Override
        public ObservableSource apply(Observable upstream) {
            //onErrorResumeNext当发生错误的时候，由另外一个Observable来代替当前的Observable并继续发射数据
            return (Observable<T>) upstream
                    /*   //上游处理线程
                       .subscribeOn(Schedulers.io())
                       //下游处理线程
                       .observeOn(AndroidSchedulers.mainThread())*/
                    .map(new AppDataErrorHandler())/*返回的数据统一错误处理*/
                    .onErrorResumeNext(new HttpErrorHandler<T>());/*Http 错误处理**/
        }
    }

   /* public static RequestInterceptor getsHttpsRequestInterceptor() {
        return sHttpsRequestInterceptor;
    }

    public static ResponseInterceptor getsHttpsResponseInterceptor() {
        return sHttpsResponseInterceptor;
    }

    public static void setsHttpsRequestInterceptor(RequestInterceptor sHttpsRequestInterceptor) {
        ApiBase.sHttpsRequestInterceptor = sHttpsRequestInterceptor;
    }*/


}
