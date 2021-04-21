package com.xbj.common.views.listItem;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.thoughtwork.base.customview.BaseCustomView;
import com.xbj.common.R;
import com.xbj.common.databinding.CommentAvatarItemViewBinding;


/**
 * Time :2021/4/21
 * Author:xbj
 * Description :评价/点赞用户头像展示视图
 */
public class CommentAvatarItemView extends BaseCustomView<CommentAvatarItemViewBinding, CommentAvatarItemViewModel> {
    public CommentAvatarItemView(Context context) {
        super(context);
    }

    public CommentAvatarItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CommentAvatarItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public CommentAvatarItemView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected int setViewLayoutId() {
        return R.layout.comment_avatar_item_view;
    }

    @Override
    protected void setDataToView(CommentAvatarItemViewModel data) {
        getDataBinding().setPageModel(data);

    }

    @Override
    protected void onRootClick(View view) {

    }
}
