package com.thoughtwork.base.utils.exception;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.xbj.base.R;

public class C {

    public static Context context = null;

    public static AssetManager asset;
    public static SharedPreferences userSpf = null;

    public static void init(Application app) {
        // TODO Auto-generated method stub
        context = app;
        // 得到AssetManager
        asset = app.getAssets();
    }

    private static Animation Anim_Alpha = null;

    public static Animation getAnimationAlpha() {
        // TODO Auto-generated method stub
        if (Anim_Alpha == null)
            Anim_Alpha = AnimationUtils.loadAnimation(context,
                    R.anim.alpha_action);
        return Anim_Alpha;
    }

    public static void show(Object text) {
        show(context, text);
    }

    public static void show(Context con, Object text) {
        Toast.makeText(con, text.toString(), Toast.LENGTH_SHORT).show();
    }

    public static void show(Context con, int text) {
        Toast.makeText(con, text, Toast.LENGTH_SHORT).show();
    }

    /**
     * sleep thread
     *
     * @param time
     *            time
     */
    public static void sleep(long time) {
        if (time <= 0)
            return;
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            // e.printStackTrace();
        }
    }

}
