package com.xbj.common.views.listItem;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.thoughtwork.base.customview.BaseCustomView;
import com.xbj.common.R;
import com.xbj.common.databinding.CommentAvatarItemViewBinding;
import com.xbj.common.databinding.PublishPicItemViewBindingImpl;

/**
 * Time :2021/4/21
 * Author:xbj
 * Description :发布内容相关图片展示视图
 */
public class PublishPicItemView extends BaseCustomView<PublishPicItemViewBindingImpl, PublishPicItemViewModel> {
    public PublishPicItemView(Context context) {
        super(context);
    }

    public PublishPicItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PublishPicItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public PublishPicItemView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected int setViewLayoutId() {
        return R.layout.publish_pic_item_view;
    }

    @Override
    protected void setDataToView(PublishPicItemViewModel data) {
        getDataBinding().setPageModel(data);

    }

    @Override
    protected void onRootClick(View view) {

    }
}
