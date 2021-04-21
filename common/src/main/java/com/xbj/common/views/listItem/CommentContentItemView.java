package com.xbj.common.views.listItem;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.thoughtwork.base.customview.BaseCustomView;
import com.xbj.common.R;
import com.xbj.common.databinding.CommentContentItemViewBinding;

/**
 * Time :2021/4/21
 * Author:xbj
 * Description :评论内容展示视图
 */
public class CommentContentItemView extends BaseCustomView<CommentContentItemViewBinding, CommentContentItemViewModel> {
    public CommentContentItemView(Context context) {
        super(context);
    }

    public CommentContentItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CommentContentItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public CommentContentItemView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected int setViewLayoutId() {
        return R.layout.comment_content_item_view;
    }

    @Override
    protected void setDataToView(CommentContentItemViewModel data) {
        getDataBinding().setPageModel(data);
    }

    @Override
    protected void onRootClick(View view) {

    }
}
