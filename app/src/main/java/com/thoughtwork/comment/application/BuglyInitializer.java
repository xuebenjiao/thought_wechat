/*
package com.yto.comment.application;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.startup.Initializer;

import com.tencent.bugly.crashreport.CrashReport;
import com.yto.comment.BuildConfig;

import java.util.Collections;
import java.util.List;

*/
/**
 * Time :2021/1/29
 * Author:xbj
 * Description :
 *//*

public class BuglyInitializer implements Initializer<CrashReport> {
    @NonNull
    @Override
    public CrashReport create(@NonNull Context context) {
        */
/* Bugly SDK初始化
         * 参数1：上下文对象
         * 参数2：APPID，平台注册时得到,注意替换成你的appId
         * 参数3：是否开启调试模式，调试模式下会输出'CrashReport'tag的日志
         * 注意：如果您之前使用过Bugly SDK，请将以下这句注释掉。
         *//*

        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(context);
        strategy.setAppVersion(  BuildConfig.VERSION_NAME);
        strategy.setAppPackageName(context.getPackageName());
        //Bugly会在启动20s后联网同步数据
        strategy.setAppReportDelay(20000);

        */
/*  第三个参数为SDK调试模式开关，调试模式的行为特性如下：
            输出详细的Bugly SDK的Log；
            每一条Crash都会被立即上报；
            自定义日志将会在Logcat中输出。
            建议在测试阶段建议设置成true，发布时设置为false。*//*

        CrashReport.initCrashReport(context, "cfcd83e311", BuildConfig.DEBUG ,strategy);
        return null;
    }

    @NonNull
    @Override
    public List<Class<? extends Initializer<?>>> dependencies() {
        //该处不能返回null，否则会报错
        return Collections.emptyList();
    }
}
*/
