package com.thoughtwork.base.customview;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

import com.xbj.base.R;

/**
 * 自定义loading
 */
public class DialogLoading extends ProgressDialog  implements LifecycleObserver {
    private static final String TAG = "DialogLoading";
    private static AppCompatActivity ac1;
    private boolean cancel = false;
    private ImageView roateImage;
    private static DialogLoading dl;

    public static DialogLoading getInstance(AppCompatActivity ac) {
        if (dl == null) {
            dl = new DialogLoading(ac);
        } else if (!ac1.equals(ac)) {
            dl.dismiss();
            dl = new DialogLoading(ac);
        }
        return dl;
    }

    public static DialogLoading getInstance(AppCompatActivity ac, boolean back) {
        if (dl == null) {
            dl = new DialogLoading(ac, back);
        } else if (!ac1.equals(ac)) {
            dl.dismiss();
            dl = new DialogLoading(ac, back);
        }
        return dl;
    }

    public DialogLoading(AppCompatActivity ac) {
        //适配21主题
        super(ac, R.style.loading_dialog);
        DialogLoading.ac1 = ac;
    }

    public DialogLoading(AppCompatActivity ac, boolean cancel) {
        this(ac);
        this.cancel = cancel;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = this.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.alpha = 1.0f;// 透明度
        lp.dimAmount = 0.6f;// 黑暗度
        window.setAttributes(lp);
        setContentView(R.layout.dialogloading);
        this.setCancelable(cancel);
        roateImage = (ImageView) findViewById(R.id.img);


    }

    @Override
    public void show() {
        Log.i(TAG, "show: ac1-->"+ac1.toString());
        if (!this.isShowing() && ac1 != null && ac1.getLifecycle().getCurrentState() == Lifecycle.State.RESUMED) {
            try {
                super.show();
                loadAni();
            } catch (Exception e) {

            }

        }
    }

    //解决loading掉针
    private void loadAni() {
        // 加载动画
        Animation mAnimation = AnimationUtils.loadAnimation(
                ac1, R.anim.anim_roate);
        // 使用ImageView显示动画
        roateImage.startAnimation(mAnimation);
    }

    //纵坐标便宜
    public void offsetY(int offsetTop, int offsetBottom) {
        ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) roateImage.getLayoutParams();
        lp.setMargins(0, offsetTop, 0, offsetBottom);

        roateImage.setLayoutParams(lp);

    }

    public void show(AppCompatActivity ac) {
        if (ac1 == null || !ac1.equals(ac)) dl = new DialogLoading(ac, false);
        show();
    }

    public void show(AppCompatActivity ac, boolean back) {
        if (ac1 == null || !ac1.equals(ac)) dl = new DialogLoading(ac, back);
        show();
    }

    @Override
    public void dismiss() {
        if (this.isShowing() && ac1 != null && !ac1.isFinishing()) {
            try {
                super.dismiss();

            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && cancel) {
            ac1.finish();
        } else {
            return super.onKeyDown(keyCode, event);
        }
        return true;
    }
    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void onStop(){
        if(dl != null){
            dl.dismiss();
        }
    }
}
