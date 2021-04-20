package com.thoughtwork.comment.acitivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.billy.cc.core.component.CC;
import com.billy.cc.core.component.CCResult;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.open.hule.library.listener.MainPageExtraListener;
import com.open.hule.library.utils.UpdateManager;
import com.yanzhenjie.permission.runtime.Permission;
import com.thoughtwork.base.BaseApplication;
import com.thoughtwork.base.activity.MvvmActivity;
import com.thoughtwork.base.constants.Constants;
import com.thoughtwork.base.dialog.SDEnsureDialog;
import com.thoughtwork.base.utils.ActivityManager;
import com.thoughtwork.base.utils.DeviceTool;
import com.thoughtwork.base.utils.SPUtils;
import com.thoughtwork.base.utils.ScreenUtil;
import com.thoughtwork.base.utils.ToastUtil;
import com.thoughtwork.base.utils.Utils;
import com.xbj.comment.R;
import com.xbj.comment.databinding.ActivityMainBinding;
import com.thoughtwork.comment.model.MainPageModel;
import com.thoughtwork.comment.viewmodel.AppUpdateViewModel;
import com.xbj.common.notice.api.requestparameter.RequestParameter;
import com.xbj.webview.CommonWebFragment;
import com.zltd.utils.DeviceUtils;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import java.lang.reflect.Field;
import java.util.List;

import dialog.DialogManager;

public class MainActivity extends MvvmActivity<ActivityMainBinding, AppUpdateViewModel> {//, BtDevAdapter.Listener implements MainPageExtraListener
    private Dialog gpsNoteDilag;
    private int webScreenVisibleHeight = 0;
    private String roleType = SPUtils.getStringValue(Constants.ROLE_TYPE);
    private String orgCode = SPUtils.getStringValue(Constants.ORG_CODE);
    private String orgLevel = SPUtils.getStringValue(Constants.ORG_LEVEL);
    private String jobNumer = SPUtils.getStringValue(Constants.JOB_NUMBER);
    private boolean isManagerFlag = roleType.contains("_MANAGER");
    private boolean isShowExpressCabinet = SPUtils.getBooleanValue(Constants.EXPRESS_CABINET);
    private int GPS_REQUEST_CODE = 1;
    private LocationManager locationManager;
    private String locationProvider = null;
    private static final String TAG = "MainActivity";
    /*是否显示最新版本的提示信息*/
    private boolean isShowNewUpToDateToast = false;
    //声明一个long类型变量：用于存放上一点击“返回键”的时刻
    private long mExitTime;
    /*更新下载管理类*/
    private UpdateManager updateManager;

    public View dialogView = null;
    private boolean hasWindowsFocus = false;
    //界面绑定数据的model
    private MainPageModel uEntity;
    private Fragment mHomeFragment, mExpressLockerFragment, mimeFragment;
    private Fragment mCategoryFrament = CommonWebFragment.newInstance(BaseApplication.webviewUrl + Utils.packageParamsForWebView("", 0),
            "易拣通-" + SPUtils.getStringValue(Constants.ORG_NAME));
    private Fragment mCurrentFragment;

    @Override
    public int getLayoutId() {
        return R.layout.activity_select_city;
    }

    @Override
    protected AppUpdateViewModel getViewModel() {
        return new AppUpdateViewModel();
    }



    @Override
    protected void onRetryBtnClick() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        isFitsSystemWindows = false;
        initControlCabinFragment();
        super.onCreate(savedInstanceState);
        //如果是工业机且未设置扫描，则默认是工业机扫描
        if (!DeviceUtils.isMobilePhone && !SPUtils.getBooleanValue(Constants.DEVICE_SCAN) && !SPUtils.getBooleanValue(Constants.CAMERA_SCAN)) {
            SPUtils.saveBooleanValue(Constants.DEVICE_SCAN, true);
        }
        //如下位车签好设置默认值
        String carSign = SPUtils.getStringValue(Constants.CAR_SIGN + SPUtils.getStringValue(Constants.JOB_NUMBER));
        if (TextUtils.isEmpty(carSign)) {
            SPUtils.saveStringValue(Constants.CAR_SIGN + SPUtils.getStringValue(Constants.JOB_NUMBER), "CQ" + SPUtils.getStringValue(Constants.ORG_CODE));
        }
        //标识成功进入主界面
        SPUtils.saveBooleanValue(Constants.JUMP_TO_HOME_SUCCESS, true);
        /*如下代码是使用CC组件化通信的方式*/
        //扫描
        CCResult scan = CC.obtainBuilder(getString(R.string.Scan)).setActionName(getString(R.string.Scan_ActionName_getHomeFragment)).build().call();
        mHomeFragment = (Fragment) scan.getDataMap().get(getString(R.string.Component_parameter_fragment));
        mCurrentFragment = mHomeFragment;

        //快递柜
        CCResult result = CC.obtainBuilder(getString(R.string.Locker)).setActionName(getString(R.string.Locker_ActionName_getMainHomeFragment)).build().call();
        mExpressLockerFragment = (Fragment) result.getDataMap().get(getString(R.string.Component_parameter_fragment));

        //我的
        CCResult center = CC.obtainBuilder(getString(R.string.UserCenter)).setActionName(getString(R.string.UserCenter_ActionName_getMyCenterFragment)).build().call();
        mimeFragment = (Fragment) center.getDataMap().get(getString(R.string.Component_parameter_fragment));

        //为了解决BottomView(最多五个)超过三个标签后不显示Text的问题
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            disableShiftMode(viewDatabinding.bottomView);
        }
        //处理底部Tab切换
        handlerBottomClickEnvent();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(viewDatabinding.container.getId(), isManagerFlag ? mCategoryFrament : mHomeFragment);
        fragmentTransaction.commit();
        openGPSSEtting();
    }

    /**
     * 初始化驾驶舱模块
     *     /*SYS_MANAGER("SYS_MANAGER","系统管理员"),
     *       ORG_MANAGER("ORG_MANAGER","网点管理员"),
     *       SENCOND_ORG_MANAGER("SENCOND_ORG_MANAGER","二级网点管理员"),
     *       THIRD_ORG_MANAGER("THIRD_ORG_MANAGER","三级网点管理员"),
     */
    private void initControlCabinFragment() {
        if (isManagerFlag) {
            //h5最大可见区域
            webScreenVisibleHeight =
                    //屏幕总高度
                    ScreenUtil.px2dip(ScreenUtil.getDisplayHeight()) -
                            //状态栏高度
                            ScreenUtil.px2dip(ScreenUtil.getStatusBarHeight(BaseApplication.getmContext()))
                            //标题高度
//                            - ScreenUtil.dip2px(24f)
                            - 24
                            //底部Tab高度
//                            - ScreenUtil.dip2px(50f)
                            - 50
                            //导航栏高度
                            - ScreenUtil.px2dip(ScreenUtil.getNavBarHeight(BaseApplication.getmContext()));

            mCategoryFrament = CommonWebFragment.newInstance(
                    BaseApplication.controWebviewUrl + Utils.packageParamsForWebView("", webScreenVisibleHeight),
                    true);
        }
    }

    private void setToolBar() {
    }

    /**
     * 界面底部Tab页切换逻辑
     */
    public void handlerBottomClickEnvent() {
        if (isManagerFlag) {
            viewDatabinding.bottomView.inflateMenu(R.menu.tab_cabin_menu);
        } else if(isShowExpressCabinet) {
            viewDatabinding.bottomView.inflateMenu(R.menu.tab_menu);
            //如果判断是否显示快递柜
        }
        else{
            viewDatabinding.bottomView.inflateMenu(R.menu.tab_menu_without_cabinet);
        }
        viewDatabinding.bottomView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
              /*  Fragment seleteFragment = null;
                switch (menuItem.getItemId()) {
                    case R.id.menu_home:
                        seleteFragment = mHomeFragment;
                        break;
                    case R.id.menu_locker:
                        seleteFragment = mExpressLockerFragment;
                        break;
                    case R.id.menu_categories:
                    case R.id.menu_cabin:
                        seleteFragment = mCategoryFrament;
                        break;
                    case R.id.menu_account:
                        seleteFragment = mimeFragment;
                        break;
                    default:
                        seleteFragment = mHomeFragment;
                        break;
                }*/
          /*      switchFragment(mCurrentFragment, seleteFragment);
                mCurrentFragment = seleteFragment;*/
                return true;//如果返回false 界面底部Bottom时颜色不会变化
            }
        });
     /*   if (isManagerFlag) {
            viewDatabinding.bottomView.setSelectedItemId(R.id.menu_cabin);
        } else {
            //默认选中第一个
            viewDatabinding.bottomView.setSelectedItemId(R.id.menu_home);
        }*/
    }

    /**
     * 解决BottomNavigationView 选项超过三个不显示text的问题
     *  注意：BottomNavigationView中的选项最多五个
     * @param view
     */
    @SuppressLint("RestrictedApi")
    private void disableShiftMode(BottomNavigationView view) {
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) view.getChildAt(0);
        try {
            Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
            shiftingMode.setAccessible(true);
            shiftingMode.setBoolean(menuView, false);
            shiftingMode.setAccessible(false);
            for (int i = 0; i < menuView.getChildCount(); i++) {
                BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
                // item.setShiftingMode(false);
                // set once again checked value, so view will be updated
                item.setChecked(item.getItemData().isChecked());
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
        }
    }

    /**
     * 界面底部Tab页切换
     * @param fromFragment
     * @param toFragment
     */
    private void switchFragment(Fragment fromFragment, Fragment toFragment) {
        if (fromFragment != toFragment) {
            FragmentManager manger = getSupportFragmentManager();
            FragmentTransaction transaction = manger.beginTransaction();
            if (!toFragment.isAdded()) {
                if (fromFragment != null) {
                    transaction.hide(fromFragment);
                }
                if (toFragment != null) {
                    transaction.add(R.id.container, toFragment).commit();
                }

            } else {
                if (fromFragment != null) {
                    transaction.hide(fromFragment);
                }
                if (toFragment != null) {
                    transaction.show(toFragment).commit();
                }
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                finish();
                break;
            }
        }
        return true;
    }

    @Override
    protected void initParameters() {
        EventBus.getDefault().register(this);
        uEntity = new MainPageModel();
        // 除去自带效果
        viewDatabinding.bottomView.setItemIconTintList(null);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkIsRemeberPwdOverTime();
       /* if (ApiBase.getsHttpsRequestInterceptor() == null) {
            ApiBase.setNetworkRequestInfo(new NetworkRequestInfo());
        }*/
        //清除 保存的签收人与指派人 之所以放在这里是确保 操作某一模块途中不会（相机扫描--今日操作数据--相机扫描）清除用户选择的，避免用户再次选择
        //指派
/*        SPUtils.saveStringValue(Constants.CHOOSE_PERSON_NAME,"");
        SPUtils.saveStringValue(Constants.CHOOSE_PERSON_CODE,"");
        SPUtils.saveStringValue(Constants.CHOOSE_PERSON_SHOW_NAME,"");*/
        //签收人
        SPUtils.saveStringValue(Constants.LAST_SELECT_SIGNER, "");
        //首次不调用
        if (hasWindowsFocus) {
            getPublicNotice();
        }
        openGPSSEtting();

    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        recyclePageParameters();
    }

    /**
     * 释放界面参数
     */
    private void recyclePageParameters() {
        if (mImmersionBar != null) {
            mImmersionBar = null;
        }
        if (locationManager != null) {
            locationManager = null;
        }
        if (uEntity != null) {
            uEntity = null;
        }
        if (updateManager != null) {
            updateManager = null;
        }
        if (mHomeFragment != null) {
            mHomeFragment = null;
        }
        if (mCategoryFrament != null) {
            mCategoryFrament = null;
        }
        if (mimeFragment != null) {
            mimeFragment = null;
        }
        if (mCurrentFragment != null) {
            mCurrentFragment = null;
        }
        if (locationListener != null) {
            locationListener = null;
        }
        if(gpsNoteDilag != null){
            gpsNoteDilag = null;
        }
    }

    /*版本更新*/
    @Subscriber(tag = "AppUpdate")
    private void appUpdate(String str) {
        isShowNewUpToDateToast = true;
        checkUpdate();
    }

    @Override
    public void finish() {
        super.finish();
        recyclePageParameters();
    }
    /**
     * 真正的窗体完成初始化可见获取焦点可交互是在onWindowFocusChanged()方法被执行时，而这之前，对用户的操作需要做一点限制。
     * @param hasFocus
     */
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus && !hasWindowsFocus) {
            hasWindowsFocus = true;
            checkUpdate();
        }
    }

    /**
     * 处理返回键
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //判断用户是否点击了“返回键”
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            boolean flag = false;
            if (mCurrentFragment != null && mCurrentFragment instanceof CommonWebFragment) {
                flag = ((CommonWebFragment) mCategoryFrament).onKeyDown(keyCode, event);
            }
            if (flag) {
                return flag;
            } else {
                //与上次点击返回键时刻作差
                if ((System.currentTimeMillis() - mExitTime) > 2000) {
                    //大于2000ms则认为是误操作，使用Toast进行提示
                    ToastUtil.show(BaseApplication.getmContext(), "再按一次退出程序");
                    //并记录下本次点击“返回键”的时刻，以便下次进行判断
                    mExitTime = System.currentTimeMillis();
                } else {
                    //小于2000ms则认为是用户确实希望退出程序-调用System.exit()方法进行退出
                    ActivityManager.getActivityManager().popAllActivity();
                }
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 强制退出应用
     */
//    @Override
    public void forceExit() {
        //小于2000ms则认为是用户确实希望退出程序-调用System.exit()方法进行退出
        ActivityManager.getActivityManager().popAllActivity();
    }

    /**
     * 请求版本更新接口
     */
    private void checkUpdate() {
        if (viewModel != null) {
            viewModel.reqestUpdateVersion();
        }
    }



    /**
     * 相机扫描回调处理
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GPS_REQUEST_CODE) {
            openGPSSEtting();
        }
    }

    /**
     * 供H5调用（token）
     * @param message
     */
    @Subscriber(tag = "showTokenOverTimeDiloag")
    public void showTokenOverTimeDiloag(String message) {
        showTokenOverTimeDialog(message);
    }

    /**
     * token 失效需要重新登录
     */
    private void reLoginForOverTimeToken() {
        ActivityManager.getActivityManager().popAllActivity();
        startActivity(new Intent(this, LoginActivity.class));
    }

    /**
     * 检查密码记住密码是否超时（默认记住7天）
     */
    public void checkIsRemeberPwdOverTime() {
        boolean remeberPwd = SPUtils.getBooleanValue(Constants.REMEBER_PWD);
        long remeberTimeLong = SPUtils.getLongValue(Constants.REMEBER_PWD_TIME);
        boolean pwdOverTimeFlag = (System.currentTimeMillis() - remeberTimeLong) > (7 * 24 * 3600 * 1000) ? true : false;
        if (remeberPwd && pwdOverTimeFlag) {
            SPUtils.saveBooleanValue(Constants.REMEBER_PWD, false);
            SPUtils.saveLongValue(Constants.REMEBER_PWD_TIME, 0L);
            ActivityManager.getActivityManager().popAllActivity();
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        }
    }

    /**
     * 删除弹框提示
     */
    public void showNoteBeforeDel() {
        new SDEnsureDialog(MainActivity.this).builder().setTitle("是否删除所选数据?").setPositiveButton("确定", getResources().getColor(R.color.blue), new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventBus.getDefault().post(uEntity.currentTabName, "delBtnClick");
            }
        }).setNegativeButton("取消", getResources().getColor(R.color.note_font_color), new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        }).show();
    }

    /**
     * 获取定位信息
     */
    private void getLocation() {
//        if (TextUtils.isEmpty(SPUtils.getStringValue(Constants.LATIUDE))) {
        requestPermission(new PermissionQuestListener() {
                              @SuppressLint("MissingPermission")
                              @Override
                              public void onGranted() {
                                  setLocationManager();
                              }

                              @Override
                              public void onDenied() {
                              }
                          }
                , Permission.ACCESS_COARSE_LOCATION
                , Permission.ACCESS_FINE_LOCATION);
//        }
    }

    /**
     * 设置定位相关信息
     */
    private void setLocationManager() {
        if(locationManager == null) {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        }
        //获取所有可用的位置提供器
        List<String> providers = locationManager.getProviders(true);
        if (providers.contains(LocationManager.NETWORK_PROVIDER)) {
            //如果是Network
            locationProvider = LocationManager.NETWORK_PROVIDER;
        } else if (providers.contains(LocationManager.GPS_PROVIDER)) {
            //如果是GPS
            locationProvider = LocationManager.GPS_PROVIDER;
        }
        //防止locationProvider 为null
        if (locationProvider == null) {
            locationProvider = LocationManager.GPS_PROVIDER;
        }
        locationManager.requestLocationUpdates(locationProvider, 3000, 1, locationListener);
        Location location = locationManager.getLastKnownLocation(locationProvider);
        if (location != null) {
            SPUtils.saveLongValue(Constants.LAST_LOACTION_TIME,System.currentTimeMillis());
            SPUtils.saveStringValue(Constants.LATIUDE,location.getLatitude()+"");
            SPUtils.saveStringValue(Constants.LONGITUDE,location.getLongitude()+"");
        }
    }

    /**
     * 位置监听
     */
    public LocationListener locationListener = new LocationListener() {
        // Provider的状态在可用、暂时不可用和无服务三个状态直接切换时触发此函数
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }
        // Provider被enable时触发此函数，比如GPS被打开
        @Override
        public void onProviderEnabled(String provider) {
        }
        // Provider被disable时触发此函数，比如GPS被关闭
        @Override
        public void onProviderDisabled(String provider) {
        }
        //当坐标改变时触发此函数，如果Provider传进相同的坐标，它就不会被触发
        @Override
        public void onLocationChanged(Location location) {
            if (location != null) {
                SPUtils.saveLongValue(Constants.LAST_LOACTION_TIME,System.currentTimeMillis());
                SPUtils.saveStringValue(Constants.LATIUDE,location.getLatitude()+"");
                SPUtils.saveStringValue(Constants.LONGITUDE,location.getLongitude()+"");
                //不为空,显示地理位置经纬度
//                ToastUtil.show( MainActivity.this,location.getLongitude() + " " + location.getLatitude() + "");
            }
        }
    };


    private void openGPSSEtting() {
        if (checkGpsIsOpen()){
            getLocation();
        }else {
            if(gpsNoteDilag == null) {
                gpsNoteDilag = new AlertDialog.Builder(this)
                        .setTitle("GPS")
                        .setMessage("是否去设置GPS?")
                        //  取消选项
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // 关闭dialog
                                dialogInterface.dismiss();
                            }
                        })
                        //  确认选项
                        .setPositiveButton("设置", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                                //跳转到手机原生设置页面
                                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                startActivityForResult(intent, GPS_REQUEST_CODE);
                            }
                        })
                        .setCancelable(false)
                        .create();
                gpsNoteDilag.show();
            }
            else if(!gpsNoteDilag.isShowing()){
                gpsNoteDilag.show();
            }
        }
    }

    /**
     * 富文本适配
     */
    private String getHtmlData(String bodyHTML) {
        String head = "<head>"
                + "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, user-scalable=no\"> "
                + "</head>";
        return "<html>" + head + "<body>" + bodyHTML + "</body></html>";
    }

    /**
     * 获取公告
     */
    private void getPublicNotice() {
        RequestParameter parameter = new RequestParameter();
        //生产环境
        if (BaseApplication.envType == 4){
            parameter.setAppCode("26e80fd95baa4256aa2360514f606bb4");
            parameter.setAppSecret("446c771e62");
        }
        else{
            parameter.setAppCode("688d1c03959c4553b48e2a524273d52f");
            parameter.setAppSecret("d729fdcdc8");
        }
        parameter.setUserCode(TextUtils.isEmpty(DeviceTool.getDeviceId(BaseApplication.getmContext()))? DeviceTool.getMacDeviceMacAddress(BaseApplication.getmContext())+ jobNumer : DeviceTool.getDeviceId(BaseApplication.getmContext())+jobNumer);
        parameter.setUserName("zc");
        new DialogManager.Builder(getSupportFragmentManager(), this)
                .setParameter(parameter)
                .setTitleColor("#FFFFFF")
                .setTitleBarBgColor("#5242FF")
                .setBackImageColor("#FFFFFF")
                .create();
    }

}

