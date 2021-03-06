package com.thoughtwork.base.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.ContentObserver;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.Observer;

import com.gyf.immersionbar.ImmersionBar;
import com.kingja.loadsir.callback.Callback;
import com.kingja.loadsir.core.LoadService;
import com.kingja.loadsir.core.LoadSir;
import com.thoughtwork.base.andpermission.RuntimeRationale;
import com.thoughtwork.base.constants.Constants;
import com.thoughtwork.base.customview.DialogLoading;
import com.thoughtwork.base.loadsir.EmptyCallback;
import com.thoughtwork.base.loadsir.ErrorCallback;
import com.thoughtwork.base.loadsir.LoadingCallback;
import com.thoughtwork.base.utils.ActivityManager;
import com.thoughtwork.base.utils.LiveDataBus;
import com.thoughtwork.base.utils.SPUtils;
import com.thoughtwork.base.viewmodel.MvvmBaseViewModel;
import com.thoughtwork.base.viewmodel.ViewStatus;
import com.xbj.base.R;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.runtime.Permission;
import com.yanzhenjie.permission.runtime.PermissionDef;

import java.io.File;
import java.util.HashMap;
import java.util.List;
/**
 * author : xbj
 * e-mail : xbjzhu@163.com
 * date   : 2019/10/22 17:14
 * desc   :
 */
public abstract class MvvmActivity<V extends ViewDataBinding,VM extends MvvmBaseViewModel> extends AppCompatActivity implements  IBaseView, Observer {
    private static final int REQUEST_CODE_SETTING = 1;
    public long mFirstOpenCameraTime;
    //????????????????????????fitSystemWindow???true
    public boolean isFitsSystemWindows = true;
    public ImmersionBar mImmersionBar;
    private static final String TAG = "MvvmActivity";
    private DialogLoading dialogLoading; //??????loading???
    public String Tag;
    private LoadService mLoadService;
    protected VM viewModel;
    //????????????
    protected V viewDatabinding;


    public abstract
    @LayoutRes
//?????????????????????Layout?????????id
    int getLayoutId();

    //?????????????????????????????????
    protected abstract VM getViewModel();

    //???????????????????????????
    protected abstract void onRetryBtnClick();

    private void performDataBinding() {
        viewDatabinding = DataBindingUtil.setContentView(this, getLayoutId());
        if (viewModel == null) {
            this.viewModel = getViewModel();
            if (viewModel != null) {
                //????????????
                viewModel.attachUI(this);
                getLifecycle().addObserver(viewModel);

            }
        }
        LiveDataBus.getInstance().with(LiveDataBus.VIEW_STATUS, ViewStatus.class).observe(this, this);
        viewDatabinding.executePendingBindings();
    }

    /**
     * ?????????????????????????????????
     *
     * @param view ???????????????????????????
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
        super.onCreate(savedInstanceState);
        getIntentData();
        setStatusOrNavigationBar();
        performDataBinding();
        initLoadingDialog();
        initParameters();
    }


    /**
     * ??????????????? ?????????Bundle???
     */
    protected void initParameters() {
    }

    /**
     * ??????????????????
     */
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
     * ?????????????????????
     */
    public void showLoadingDialog() {
        if (dialogLoading != null && !dialogLoading.isShowing()) {
            dialogLoading.show();
        }
    }

    /**
     * ?????????????????????
     */
    public void hideLoadingDialog() {
        if (dialogLoading != null && dialogLoading.isShowing()) {
            dialogLoading.dismiss();
        }
    }

    /**
     * ???????????????
     */
    public void setStatusOrNavigationBar() {
        mImmersionBar = ImmersionBar.with(this);
        if (isFitsSystemWindows) {
            //???????????????,???????????????????????????
            mImmersionBar.fitsSystemWindows(true);
            //????????????????????????????????????????????????
//            mImmersionBar.statusBarDarkFont(true);
        }
        mImmersionBar.statusBarColor(R.color.white);
        mImmersionBar.init();
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
                            //???????????????App
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


    /**
     * ??????????????????
     */
    public interface PermissionQuestListener {
        void onGranted(); //???????????????????????????
        void onDenied();//??????
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
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        recycleParentPageParameters();
    }

    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        if (res.getConfiguration().fontScale != 1) {//????????????
            Configuration newConfig = new Configuration();
            newConfig.setToDefaults();//????????????
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

    @Override
    public void finish() {
        super.finish();
        recycleParentPageParameters();
    }

    /**
     * ???????????????????????????
     */
    private void recycleParentPageParameters() {
        if (mImmersionBar != null) {
            //??????????????????????????????????????????????????????????????????????????????bar???????????????????????????app????????????????????????????????????????????????????????????bar???????????????
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

}
