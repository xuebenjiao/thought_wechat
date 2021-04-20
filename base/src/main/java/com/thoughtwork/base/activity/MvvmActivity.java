package com.thoughtwork.base.activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.ContentObserver;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.FileProvider;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.Observer;

import com.gyf.immersionbar.ImmersionBar;
import com.huantansheng.easyphotos.EasyPhotos;
import com.huantansheng.easyphotos.engine.GlideEngine;
import com.huantansheng.easyphotos.models.album.entity.Photo;
import com.kingja.loadsir.callback.Callback;
import com.kingja.loadsir.core.LoadService;
import com.kingja.loadsir.core.LoadSir;
//import com.open.hule.library.entity.AppUpdate;
//import com.open.hule.library.listener.MainPageExtraListener;
//import com.open.hule.library.utils.UpdateManager;
import com.thoughtwork.base.BaseApplication;
import com.thoughtwork.base.andpermission.RuntimeRationale;
import com.thoughtwork.base.constants.Constants;
import com.thoughtwork.base.customview.DialogLoading;
import com.thoughtwork.base.dialog.SDEnsureDialog;
import com.thoughtwork.base.loadsir.EmptyCallback;
import com.thoughtwork.base.loadsir.ErrorCallback;
import com.thoughtwork.base.loadsir.LoadingCallback;
import com.thoughtwork.base.utils.ActivityManager;
import com.thoughtwork.base.utils.LiveDataBus;
import com.thoughtwork.base.utils.RegexUtils;
import com.thoughtwork.base.utils.SPUtils;
import com.thoughtwork.base.utils.ToastUtil;
import com.thoughtwork.base.viewmodel.MvvmBaseViewModel;
import com.thoughtwork.base.viewmodel.ViewStatus;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.runtime.Permission;
import com.yanzhenjie.permission.runtime.PermissionDef;
import com.xbj.base.R;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import cn.net.yto.drive.BaseDeviceScannerActivity;
import top.zibin.luban.CompressionPredicate;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

/**
 * author : xbj
 * e-mail : xbjzhu@163.com
 * date   : 2019/10/22 17:14
 * desc   :
 */
public abstract class MvvmActivity<V extends ViewDataBinding,VM extends MvvmBaseViewModel> extends BaseDeviceScannerActivity implements  IBaseView, Observer {
    //是否是管理者
    public boolean mManagerFlag = SPUtils.getIntValue(Constants.ROLE_TYPE) == 1;
    public  final int takeOrSelectPicNum  = 6;
    private int mSelectPicCount;
    //图片文件
    private File file;
    private static final int CAMERA_RESULT_CODE = 0;
    private static final int ALBUM_RESULT_CODE = 3;
    private LocationManager mLocationManager;
    public boolean isVisibleToUser = false;
    //是否是首次获取获取展示数据
    public boolean isFristGetDataFlag = true;

    public boolean isLoadFlag = false;
    /*更新下载管理类*/
//    private UpdateManager updateManager;

    //如下两个变量是防止多次点击
    public int lastId;
    public long lastClickTimeStamp;
    public long lastPlayVerifyStamp;
    public long lastScanedStamp;
    public long lastPlayExpressStamp;
    public long lastWebClickTimeStamp;
    //应用包名与应用名称对应关系
    private HashMap<String, String> expressNameHashMap;
    //时间戳
    public long lastTimeStamp;

    public long mFirstOpenCameraTime;
    //是否是设置状态栏fitSystemWindow为true
    public boolean isFitsSystemWindows = true;
    private boolean isInitLoadDialgFlag = true;
    public ImmersionBar mImmersionBar;
    private static final String TAG = "MvvmActivity";
    private DialogLoading dialogLoading; //载入loading条
    public String Tag;
    private static final int REQUEST_CODE_SETTING = 1;
    private LoadService mLoadService;
    protected VM viewModel;
    //界面句柄
    protected V viewDatabinding;

    private final ContentObserver mGpsMonitor = new ContentObserver(null) {
        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            checkGpsIsOpen();
        }
    };

    public abstract
    @LayoutRes
//表示必须是一个Layout的资源id
    int getLayoutId();

    //数据结构需要子类去实现
    protected abstract VM getViewModel();

    //获取界面绑定的参数
//    public abstract int getBindingVariable();

    //点击提示信息的回调
    protected abstract void onRetryBtnClick();

    private void performDataBinding() {
        viewDatabinding = DataBindingUtil.setContentView(this, getLayoutId());
        if (viewModel == null) {
            this.viewModel = getViewModel();
            if (viewModel != null) {
                //绑定界面
                viewModel.attachUI(this);
                getLifecycle().addObserver(viewModel);

            }
        }
       /* if (getBindingVariable() > 0) {
            //实现View和Data的绑定
            viewDatabinding.setVariable(getBindingVariable(), viewModel);
        }*/
        LiveDataBus.getInstance().with(LiveDataBus.VIEW_STATUS, ViewStatus.class).observe(this, this);
        viewDatabinding.executePendingBindings();
    }

    /**
     * 在显示界面提示相关信息
     *
     * @param view 需要提示信息的界面
     */
    public void setLoadSir(View view) {
        //you can change  the callback on sub thread directly
        mLoadService = LoadSir.getDefault().register(view, new Callback.OnReloadListener() {
            @Override
            public void onReload(View v) {
                onRetryBtnClick();
            }
        });
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if (mScanManager == null) {
            mScanManager = BaseApplication.getmScanManager();
        }
        mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        super.onCreate(savedInstanceState);
        getIntentData();
        setStatusOrNavigationBar();
        performDataBinding();
        initLoadingDialog();
        initParameters();
    }

    public void initLoadingDialog() {
        if (isNeedInitLoadDialg() && dialogLoading == null) {
            dialogLoading = DialogLoading.getInstance(this, false);
            getLifecycle().addObserver(dialogLoading);
        }
    }

    public void getIntentData() {

    }

    /**
     * @return
     */
    public boolean isNeedInitLoadDialg() {
        return true;
    }

    /**
     * 展示请求进度条
     */
    public void showLoadingDialog() {
        if (dialogLoading != null && !dialogLoading.isShowing()) {
            dialogLoading.show();
        }
    }

    /**
     * 隐藏请求进度条
     */
    public void hideLoadingDialog() {
        if (dialogLoading != null && dialogLoading.isShowing()) {
            dialogLoading.dismiss();
        }
    }

    /**
     * 设置状态栏
     */
    public void setStatusOrNavigationBar() {
        mImmersionBar = ImmersionBar.with(this);
        if (isFitsSystemWindows) {
            //使用该属性,必须指定状态栏颜色
            mImmersionBar.fitsSystemWindows(true);
            //状态栏字体是深色，不写默认为亮色
//            mImmersionBar.statusBarDarkFont(true);
        }
        mImmersionBar.statusBarColor(R.color.theme_color);
        mImmersionBar.init();
    }

    /**
     * 初始化参数 类似于Bundle等
     */
    protected void initParameters() {
        Tag = getClass().getName();
        expressNameHashMap = new HashMap<String, String>();
        expressNameHashMap.put("com.geenk.zto.sys", "掌中通");
        expressNameHashMap.put("com.yunda.bmapp", "韵镖侠");
    }


    @Override
    public void showContent() {
        if (mLoadService != null) {
            mLoadService.showSuccess();
        }
    }

    @Override
    public void showLoading() {
        if (mLoadService != null) {
            mLoadService.showCallback(LoadingCallback.class);
        }
    }

    @Override
    public void onRefreshEmpty() {
        if (mLoadService != null) {
            Log.i(TAG, "onRefreshEmpty: ");
            mLoadService.showCallback(EmptyCallback.class);
        }
    }

    @Override
    public void onRefreshFailure(String message) {
        if (mLoadService != null) {
            mLoadService.showCallback(ErrorCallback.class);
        }
    }

    /**
     * Request permissions.
     */
    public void requestPermission(final PermissionQuestListener listener, @PermissionDef String... permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (AndPermission.hasPermissions(this, permissions)) {
                if (listener != null)
                    listener.onGranted();
                return;
            }
            AndPermission.with(this)
                    .runtime()
                    .permission(permissions)
                    .rationale(new RuntimeRationale())
                    .onGranted(new Action<List<String>>() {
                        @Override
                        public void onAction(List<String> permissions) {
                            listener.onGranted();
                        }
                    })
                    .onDenied(new Action<List<String>>() {
                        @Override
                        public void onAction(@NonNull List<String> permissions) {
                            if (AndPermission.hasAlwaysDeniedPermission(MvvmActivity.this, permissions)) {
                                showSettingDialog(MvvmActivity.this, permissions);
                            } else {
                                listener.onDenied();
                            }
                        }
                    })
                    .start();
        } else if (listener != null) {
            listener.onGranted();
        }
    }

    /**
     * Display setting dialog.
     */
    public void showSettingDialog(Context context, final List<String> permissions) {
        if (!isFinishing()) {
            List<String> permissionNames = Permission.transformText(context, permissions);
            String message = context.getString(R.string.message_permission_always_failed,
                    TextUtils.join("\n", permissionNames));

            new AlertDialog.Builder(context).setCancelable(false)
                    .setTitle(R.string.title_dialog)
                    .setMessage(message)
                    .setPositiveButton(R.string.setting, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            setPermission();
                        }
                    })
                    .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //取消则退出App
                            ActivityManager.getActivityManager().popAllActivity();
                        }
                    })
                    .show();
        }
    }

    /**
     * Set permissions.
     */
    private void setPermission() {
        AndPermission.with(this).runtime().setting().start(REQUEST_CODE_SETTING);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CODE_SETTING:
                break;
            case Constants.GPS_REQUEST_CODE:
                judgeIsHasLocationService();
                //拍照结果
            case CAMERA_RESULT_CODE:
                //标识拍照成功
                if(resultCode == RESULT_OK) {
                    compressPicture(file);
                }
                break;
            case ALBUM_RESULT_CODE:
                //相机或相册回调
                //返回对象集合：如果你需要了解图片的宽、高、大小、用户是否选中原图选项等信息，可以用这个
                if(data != null) {
                    ArrayList<Photo> resultPhotos = data.getParcelableArrayListExtra(EasyPhotos.RESULT_PHOTOS);
                    for (Photo photo : resultPhotos) {
                        compressPicture(new File(photo.path));
                    }
                }
                break;
        }
    }

    /**
     * 权限申请监听
     */
    public interface PermissionQuestListener {
        void onGranted(); //允许获得权限后操作

        void onDenied();//拒绝
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onStart() {
        super.onStart();
        getContentResolver()
                .registerContentObserver(
                        Settings.Secure
                                .getUriFor(Settings.System.LOCATION_PROVIDERS_ALLOWED),
                        false, mGpsMonitor);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initLoadingDialog();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        hideLoadingDialog();
        getContentResolver().unregisterContentObserver(mGpsMonitor);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        EventBus.getDefault().unregister(this);
        recycleParentPageParameters();
    }

    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        if (res.getConfiguration().fontScale != 1) {//非默认值
            Configuration newConfig = new Configuration();
            newConfig.setToDefaults();//设置默认
            res.updateConfiguration(newConfig, res.getDisplayMetrics());
        }
        return res;
    }

    private void setKeyguardEnable(boolean enable) {
        //disable
        if (!enable) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |
                    WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
            return;
        }
        //enable
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
    }


    /**
     * token
     *
     * @param message
     */
    public void showTokenOverTimeDialog(String message) {
        if (!this.isFinishing()) {
            //token 失效
            new SDEnsureDialog(this).builder().setTitle(getResources().getString(R.string.over_time_dialog_title)).setSubTitle("您的登录用户身份已过期！", getResources().getColor(R.color.middle_gray))
                    .setPositiveButton("确定", getResources().getColor(R.color.theme_color), new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            reLoginForOverTimeToken();
                        }
                    }).setNegativeButton("取消", R.color.float_cancle_color, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                }
            }).show();//.setCancelable(false)
        }
    }

    /**
     * token 失效需要重新登录
     */
    private void reLoginForOverTimeToken() {
        jumpToLoginActivity();
    }

    /**
     * 跳转至登录界面
     */
    public void jumpToLoginActivity() {
        SPUtils.saveStringValue(Constants.USER_TOKEN, "");
        SPUtils.saveStringValue(Constants.USER_NAME, "");
        SPUtils.saveStringValue(Constants.LOGIN_PHONE, "");
        SPUtils.saveStringValue(Constants.HEAD_URL, "");
        SPUtils.saveIntValue(Constants.ROLE_TYPE, 0);

        ActivityManager.getActivityManager().popAllActivity();
        SPUtils.saveBooleanValue(Constants.JUMP_TO_HOME_SUCCESS, false);
        SPUtils.saveStringValue(Constants.MESSAGE_TOKEN, "");
        Intent intent = new Intent();
        ComponentName cn = new ComponentName(BaseApplication.mApplicationId, "com.yto.comment.acitivity.AccountLoginActivity");
        intent.setComponent(cn);
        startActivity(intent);
    }

    /**
     * 每次登陆都清除上次定位信息
     */
    public void clearLastLocation() {
        SPUtils.saveLongValue(Constants.LAST_LOACTION_TIME, 0L);
        SPUtils.saveStringValue(Constants.LATIUDE, "");
        SPUtils.saveStringValue(Constants.LONGITUDE, "");
    }


    /**
     * 是否初始化进度条
     *
     * @param initLoadDialgFlag
     */
    public void setInitLoadDialgFlag(boolean initLoadDialgFlag) {
        isInitLoadDialgFlag = initLoadDialgFlag;
    }

    @Override
    public void finish() {
        super.finish();
        recycleParentPageParameters();
    }

    /**
     * 释放父类中相关变量
     */
    private void recycleParentPageParameters() {
        if (mImmersionBar != null) {
            //必须调用该方法，防止内存泄漏，不调用该方法，如果界面bar发生改变，在不关闭app的情况下，退出此界面再进入将记忆最后一次bar改变的状态
            mImmersionBar = null;
        }
        if (dialogLoading != null) {
            dialogLoading.dismiss();
            dialogLoading = null;
        }
        if (viewModel != null) {
            viewModel.detachUI();
            viewModel = null;
        }
        if (mLoadService != null) {
            mLoadService = null;
        }
    }

    /**
     * 启动其他app
     */
    public void startAnotherAppFromPackageName(String packageName) {
        if (!TextUtils.isEmpty(packageName)) {
            String appName = "";
            if (expressNameHashMap != null) {
                appName = expressNameHashMap.get(packageName);
            }
            // 通过包名获取要跳转的app，创建intent对象
            Intent intent = getPackageManager().getLaunchIntentForPackage(packageName);
// 这里如果intent为空，就说名没有安装要跳转的应用嘛
            if (intent != null) {
                // 这里跟Activity传递参数一样的嘛，不要担心怎么传递参数，还有接收参数也是跟Activity和Activity传参数一样
                startActivity(intent);
            } else {
                // 没有安装要跳转的app应用，提醒一下
                ToastUtil.show(BaseApplication.getmContext(), "您还没有安装" + appName + "，赶紧下载安装这个APP吧！");
            }
        } else {
            // 没有安装要跳转的app应用，提醒一下
            ToastUtil.show(BaseApplication.getmContext(), "应用名称为空!");
        }
    }


    /**
     * 是否多次点击
     *
     * @param view
     * @return
     */
    public boolean isValidClick(View view) {
        long time = System.currentTimeMillis();
        boolean valid = view.getId() != lastId || time - lastClickTimeStamp > 500;
        lastId = view.getId();
        lastClickTimeStamp = time;
        return valid;
    }

/*
    *//**
     * 设置更新信息
     *
     * @param updateBean
     *//*
    public void setUpdateManagerParams(UpdateBean updateBean) {
        if (updateBean != null) {
            updateManager = new UpdateManager(this);
            // 更新的数据参数
            AppUpdate appUpdate = new AppUpdate.Builder()
                    //更新地址（必传）
                    .newVersionUrl(updateBean.upgradeUrl)//updateBean.updateUrl  "https://imtt.dd.qq.com/16891/apk/062C96A8B86D52D1EEECC0EFCC56DA14.apk"
                    // 版本号
                    .newVersionCode(updateBean.appVersion)
                    // 通过传入资源id来自定义更新对话框，注意取消更新的id要定义为btnUpdateLater，立即更新的id要定义为btnUpdateNow
                    .updateResourceId(R.layout.dialog_update)
                    // 文件大小
//                .fileSize("5.8M")
                    //是否采取静默下载模式（只显示更新提示，后台下载完自动弹出安装界面），否则，显示下载进度，显示下载失败弹框
                    .isSilentMode(false)
                    //默认不采取强制更新，否则，不更新无法使用
                    .forceUpdate(updateBean.lastForce)
                    //更新内容说明
                    .updateInfo(getUpdateInfo(updateBean))
                    .build();
            updateManager.startUpdate(appUpdate, this);
        }

    }


    *//**
     * 获取富文本信息
     *
     * @param updateBean
     * @return
     *//*
    private String getUpdateInfo(UpdateBean updateBean) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            return (Html.fromHtml(updateBean.upgradeInfo, Html.FROM_HTML_MODE_LEGACY)).toString().replace("\n\n", "\n");
        } else {
            return (Html.fromHtml(updateBean.upgradeInfo)).toString().replace("\n\n", "\n");
        }
    }

    @Override
    public void forceExit() {
        //小于2000ms则认为是用户确实希望退出程序-调用System.exit()方法进行退出
        ActivityManager.getActivityManager().popAllActivity();
    }

    *//**
     * android 8.0 申请androidO 安装未知应用的权限
     * 检测到无权限安装未知来源应用，回调接口中需要重新请求安装未知应用来源的权限
     *//*
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void applyAndroidOInstall() {
        AndPermission.with(this)
                .install()
//                .file(apkFile)
                .onGranted(file -> {
                    // 被允许安装app执行
                    installApkAgain();
                })
                .onDenied(file -> {
                    ToastUtil.show(BaseApplication.getmContext(), "您拒绝了安装未知来源应用，应用暂时无法更新！");
                    if (0 != updateManager.getAppUpdate().getForceUpdate()) {
                        forceExit();
                    }
                })
                .start();

    }

    *//**
     * 授权后，再次尝试安装
     *//*
    private void installApkAgain() {
        if (updateManager != null) {
            updateManager.installAppAgain();
        }
    }*/

    /**
     * 校验本地保存的定位信息是否有效
     */
    public void validateLocalLocation() {
        //超过3小时没有更新定位则清除本地缓存的定位信息
        boolean flag = (System.currentTimeMillis() - SPUtils.getLongValue(Constants.LAST_LOACTION_TIME)) > (3 * 3600 * 1000) ? true : false;
        if (flag) {
            SPUtils.saveLongValue(Constants.LAST_LOACTION_TIME, 0L);
            SPUtils.saveStringValue(Constants.LATIUDE, "");
            SPUtils.saveStringValue(Constants.LONGITUDE, "");
        }
    }

    /**
     * 检查定位是否可用,如果不可用清除上次定位信息
     *
     * @return
     */
    public boolean checkGpsIsOpen() {
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        boolean isOpen = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (!isOpen) {
            clearLastLocation();
        }
        return isOpen;
    }


    @Override
    public void onChanged(Object o) {
        if (o instanceof ViewStatus) {
            switch ((ViewStatus) o) {
                case LOADING:
                    showLoadingDialog();
                    break;
                case HIDE_LOADING:
                    hideLoadingDialog();
                    finishListReFreshLoadMore();
                    break;
                case INVALID_TOKEN:
                    showTokenOverTimeDialog("");
                    break;
                case EMPTY:
                    if (mLoadService != null) {
                        mLoadService.showCallback(EmptyCallback.class);
                    }
                    break;
                case SHOW_CONTENT:
                    if (mLoadService != null) {
                        mLoadService.showSuccess();
                    }
                    break;
                case NO_MORE_DATA:
//                    ToastUtil.show(BaseApplication.getmContext().getResources().getString(R.string.no_more_data));
                    break;
             /*   case REFRESH_ERROR:
                    if (viewModel != null && viewModel.dataList != null && viewModel.dataList.getValue() != null  && ((ObservableArrayList) viewModel.dataList.getValue()).size() == 0) {
                        mLoadService.showCallback(ErrorCallback.class);
                    } else {
                        ToastUtil.show(viewModel.errorMessage.getValue().toString());
                    }
                    break;
                case LOAD_MORE_FAILED:
                    ToastUtil.show(viewModel.errorMessage.getValue().toString());
                    break;*/
                case FINISH_REFRESH_OR_LOAD_MORE:
                    finishListReFreshLoadMore();
            }
//            onNetworkResponded(null, false);
        } else if (o instanceof List) {
//            onNetworkResponded((List<DATA>) o, true);
        }
    }

    public void finishListReFreshLoadMore() {

    }

    ;

    /**
     * 拨打电话（直接拨打电话）
     *
     * @param phoneNum 电话号码
     */
    public void callPhone(String phoneNum) {
        if (!TextUtils.isEmpty(phoneNum)) {
            if (!RegexUtils.checkMobile(phoneNum)) {
                ToastUtil.show(BaseApplication.getmContext(), "电话号码有误！");
                return;
            }
            Intent intent = new Intent(Intent.ACTION_CALL);
            Uri data = Uri.parse("tel:" + phoneNum);
            intent.setData(data);
            startActivity(intent);
        }
    }

    /**
     * 判断是否拥有定位权限
     */
    public  void judgeIsHasLocationService() {
        if (isLocationEnabled()) {
            getLocation();
        } else {
            new SDEnsureDialog(this)
                    .builder()
                    .setTitle("该软件需要定位功能，是否去开启?", getResources().getColor(R.color.color_303133))
                    .setPositiveButton("确定", getResources().getColor(R.color.theme_color)
                            , new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    //跳转到手机原生设置页面
                                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                    startActivityForResult(intent, Constants.GPS_REQUEST_CODE);
                                }
                            }).setNegativeButton("取消", getResources().getColor(R.color.note_font_color), new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                }
            }).show();

        }
    }
    /**
     * 判断定位服务是否开启
     *
     * @param
     * @return true 表示开启
     */
    public boolean isLocationEnabled() {
        int locationMode = 0;
        String locationProviders;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            try {
                locationMode = Settings.Secure.getInt(getContentResolver(), Settings.Secure.LOCATION_MODE);
            } catch (Settings.SettingNotFoundException e) {
                e.printStackTrace();
                return false;
            }
            return locationMode != Settings.Secure.LOCATION_MODE_OFF;
        } else {
            locationProviders = Settings.Secure.getString(getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
            return !TextUtils.isEmpty(locationProviders);
        }
    }
    /**
     * 获取定位信息
     */
    private void getLocation() {
        requestPermission(new PermissionQuestListener() {
                              @SuppressLint("MissingPermission")
                              @Override
                              public void onGranted() {
                                  setLocationParameters();
                              }

                              @Override
                              public void onDenied() {
                              }
                          }
                , Permission.ACCESS_COARSE_LOCATION
                , Permission.ACCESS_FINE_LOCATION);
//        }
    }
    //设置定位相关参数
    public void setLocationParameters(){}



    /**
     * 拍照
     */
    public void takeOrSelctPicture(){
        requestPermission(new MvvmActivity.PermissionQuestListener(){
                              @Override
                              public void onGranted()  {
//                                  openSysCamera();
                                  showAvatarSelectDialog();
                              }
                              @Override
                              public void onDenied() {
                                  ToastUtil.show(BaseApplication.getmContext(),"没有相机权限，暂时不能使用相机!");

                              }
                          }
                ,  Permission.Group.CAMERA//相机
        );
    }
    /**
     * 展示获取图像选项的弹出框
     */
    private  void showAvatarSelectDialog(){
        Dialog dialog = new Dialog(this, R.style.ActionSheetDialogStyle);
        //填充对话框的布局
        View inflate = LayoutInflater.from(this).inflate(R.layout.dialog_bottom_camera_layout, null);
        TextView cameraTv = (TextView) inflate.findViewById(R.id.open_camera_tv);
        TextView selectTv = (TextView) inflate.findViewById(R.id.select_from_album_tv);
        TextView cancel = (TextView) inflate.findViewById(R.id.tv_cancel);
        cameraTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                openSysCamera();
            }
        });
        selectTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                openSysAlbum();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        //将布局设置给Dialog
        dialog.setContentView(inflate);
        //获取当前Activity所在的窗体
        Window dialogWindow = dialog.getWindow();
        WindowManager windowManager = getWindowManager();
        Display display = windowManager.getDefaultDisplay();


        //设置Dialog从窗体底部弹出
        dialogWindow.setGravity( Gravity.BOTTOM);
        //获得窗体的属性
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = display.getWidth(); // 设置dialog宽度为屏幕的宽
//        lp.y = 20;//设置Dialog距离底部的距离
//       将属性设置给窗体
        dialogWindow.setAttributes(lp);
        dialog.show();//显示对话框
    }

    /**
     * 打开系统相机
     */
    private void openSysCamera(){
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        try {
            file = createOriImageFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (file != null) {
            Uri imgUriOri;
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
                imgUriOri = Uri.fromFile(file);
            } else {
                imgUriOri = FileProvider.getUriForFile(this, getPackageName() + ".fileprovider", file);
            }
            //将用于输出的文件Uri传递给相机
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imgUriOri);
            startActivityForResult(cameraIntent, CAMERA_RESULT_CODE);
        }
    }
    /**
     * 创建原图像保存的文件
     *
     * @return
     * @throws IOException
     */
    private File createOriImageFile() throws IOException {
        String imgNameOri = "HomePic_" + new SimpleDateFormat(
                "yyyyMMdd_HHmmss").format(new Date());
        File pictureDirOri = new File(getExternalFilesDir(
                Environment.DIRECTORY_PICTURES).getAbsolutePath() + "/OriPicture");
        if (!pictureDirOri.exists()) {
            pictureDirOri.mkdirs();
        }
        File image = File.createTempFile(
                imgNameOri,         /* prefix */
                ".jpg",             /* suffix */
                pictureDirOri       /* directory */
        );
        String imgPathOri = image.getAbsolutePath();

        Log.i(TAG, "createOriImageFile: imgPathOri-->"+imgPathOri);
        return image;
    }

    /**
     * 打开系统相册
     */
    private void openSysAlbum() {
        EasyPhotos.createAlbum(this, false, GlideEngine.getInstance())
                .setCount(getSelectPicNum())
                .start(ALBUM_RESULT_CODE);
    }

    public int getSelectPicNum(){
        return 0;
    }

    /**
     * 压缩图片
     */
    private void compressPicture(File file){
        Luban.with(this)
                .load(file)
                .ignoreBy(100)
                .filter(new CompressionPredicate() {
                    @Override
                    public boolean apply(String path) {
                        return !(TextUtils.isEmpty(path) || path.toLowerCase().endsWith(".gif"));
                    }
                })
                .setCompressListener(new OnCompressListener() {
                    @Override
                    public void onStart() {
                        //  压缩开始前调用，可以在方法内启动 loading UI
                    }
                    @Override
                    public void onSuccess(File file) {
                        Log.i(TAG, "compressPicture ->onSuccess: file的大小：" + (file.length()/1024) +"KB");
                        //  压缩成功后调用，返回压缩后的图片文件
//                        uploadErrorPicture(file);
                        displayPic(file);
                    }
                    @Override
                    public void onError(Throwable e) {
                        // 当压缩过程出现问题时调用
                    }
                }).launch();
    }

    /**
     *
     * @param file 图片文件
     */
    public  void displayPic(File file){
    }
}
