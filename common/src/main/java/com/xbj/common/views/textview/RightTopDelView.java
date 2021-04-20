package com.xbj.common.views.textview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.thoughtwork.base.customview.BaseCustomView;
import com.thoughtwork.base.utils.LiveDataBus;
import com.xbj.common.R;
import com.xbj.common.databinding.RightTopDelViewBinding;

/**
 * Time :2021/3/15
 * Author:xbj
 * Description : 右上角带有删除的按钮显示
 */
public  class RightTopDelView extends BaseCustomView<RightTopDelViewBinding, RightTopDelViewModel> {
    public RightTopDelView(Context context) {
        super(context);
    }

    public RightTopDelView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RightTopDelView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    public RightTopDelView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected int setViewLayoutId() {
        return R.layout.right_top_del_view;
    }

    @Override
    protected void setDataToView(RightTopDelViewModel data) {
        getDataBinding().setViewModel(data);
        getDataBinding().setClickEvent(this);
    }

    @Override
    protected void onRootClick(View view) {
    }

    /**
     * 去完成任务
     * @param view
     */
    public void clickDelImg(View view){
        LiveDataBus.getInstance().with("clickDelImg", RightTopDelViewModel.class).postValue(getViewModel());
    }
}


