package com.thoughtwork.comment.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.thoughtwork.base.BaseApplication;
import com.thoughtwork.base.constants.Constants;
import com.thoughtwork.base.utils.ToastUtil;

/**
 * Time :2020/8/31
 * Author:xbj
 * Description : 监听用户设置的定时器
 */
public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action == Constants.INTENT_ALARM_LOG) {
            ToastUtil.show(BaseApplication.getmContext(),"时间到了！");
        }
    }
}
