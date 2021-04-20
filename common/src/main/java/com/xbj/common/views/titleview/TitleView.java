package com.xbj.common.views.titleview;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import com.thoughtwork.base.customview.BaseCustomView;
import com.xbj.common.R;
import com.xbj.common.databinding.TitleViewBinding;

import org.simple.eventbus.EventBus;

/**
 *  date :2019/10/29.
 * author:xbj
 * description:
 */
public class TitleView extends BaseCustomView<TitleViewBinding, TitleViewViewModel> {
    private boolean isSendEventFlag = false;
    public TitleView(Context context) {
        super(context);
    }
    public TitleView(Context context,boolean isSendEventFlag) {
        super(context);
        this.isSendEventFlag = isSendEventFlag;
    }
    public TitleView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public int setViewLayoutId() {
        return R.layout.title_view;
    }

    @Override
    public void setDataToView(TitleViewViewModel data) {
        //界面绑定数据对象
        getDataBinding().setViewModel(data);
    }

    @Override
    public void onRootClick(View view) {
        if(isSendEventFlag) {
            SubItemCheckEntity entity = null;
            String code = getViewModel().code;
            if(!TextUtils.isEmpty(code)){
                 entity =  new SubItemCheckEntity(false,getViewModel().content,code,getViewModel().title);
            }
            else{
                entity  =  new SubItemCheckEntity(false,getViewModel().content);
            }
            EventBus.getDefault().post(entity, "TitleViewClick");
        }
//        WebActivity.startCommonWeb(view.getContext(), "", getViewModel().link);
    }
}
