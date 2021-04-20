package com.thoughtwork.comment.wxapi;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.xbj.webview.R;

import org.simple.eventbus.EventBus;

import static com.thoughtwork.base.constants.Constants.WEIXIN_PAY_APP_ID;

public class WXPayEntryActivity extends AppCompatActivity implements IWXAPIEventHandler {

    private IWXAPI msgApi;
    private int payState = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay_result);
        msgApi = WXAPIFactory.createWXAPI(this, WEIXIN_PAY_APP_ID, false);
        msgApi.registerApp(WEIXIN_PAY_APP_ID);// 将该app注册到微信
        msgApi.handleIntent(getIntent(), this);

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        msgApi.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {
    }

    @Override
    public void onResp(BaseResp resp) {
//        MessageEvent messageEvent = new MessageEvent();
//        messageEvent.setMessage("weixinPayResult");
//        messageEvent.setObject(resp.errCode);
//        EventBus.getDefault().postSticky(messageEvent);
        EventBus.getDefault().post(resp.errCode,"weixinPayResult");
       /* if (resp.errCode == 0) {// 支付成功
            payState = 1;
        } else if (resp.errCode == -1) {//
            payState = 2;
            //ToastUtil.showShortToastCenter("订单支付失败1");
//            shareResult("微信支付结果", resp.errStr, "确定");
        } else if (resp.errCode == -2) {// 取消支付
            payState = 3;
            //ToastUtil.showShortToastCenter("订单取消支付1");
//            shareResult("微信支付结果", "订单取消支付", "关闭");
        }*/
        finish();
    }

}