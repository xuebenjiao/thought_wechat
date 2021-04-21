package com.xbj.common.views.listItem;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.thoughtwork.base.customview.BaseCustomView;
import com.thoughtwork.base.utils.ScreenUtil;
import com.xbj.common.R;
import com.xbj.common.databinding.PublisherInforItemViewBinding;
import com.xbj.common.views.adapter.RecyclerViewAdapter;

import java.util.ArrayList;

/**
 * Time :2021/4/21
 * Author:xbj
 * Description :发布消息展示视图
 */
public class PublisherInforItemView extends BaseCustomView<PublisherInforItemViewBinding, PublisherInforItemViewModel> {
    private  Context mContext;

    public PublisherInforItemView(Context context) {

        super(context);
        this.mContext = context;
    }

    public PublisherInforItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
    }

    public PublisherInforItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
    }

    public PublisherInforItemView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.mContext = context;
    }

    @Override
    protected int setViewLayoutId() {
        return R.layout.publisher_infor_item_view;
    }

    @Override
    protected void setDataToView(PublisherInforItemViewModel data) {
       //展示发布的图片
        if(data.images != null && data.images.size() >0){
            int imageSize = data.images.size();
            RecyclerViewAdapter  adapter = new RecyclerViewAdapter();
//            getDataBinding().publishPicList.setHasFixedSize(true);
            //如果是只有一张,则展示一站大图
            if(imageSize == 1) {
                getDataBinding().publishPicList.setLayoutManager(new LinearLayoutManager(mContext));
            }
            //如果是2~3张则有几张分布几列
            else if(imageSize >1 && imageSize <= 3){
                getDataBinding().publishPicList.setLayoutManager(new GridLayoutManager(mContext,imageSize));
            }
            //如果是四张则分布两列
            else if( imageSize ==4){
                getDataBinding().publishPicList.setLayoutManager(new GridLayoutManager(mContext,2));
            }
            //如果是大于4张在分布三列显示
            else{
                getDataBinding().publishPicList.setLayoutManager(new GridLayoutManager(mContext,3));
            }
            adapter.setData(data.images);
            getDataBinding().publishPicList.setAdapter(adapter);
        }
        //展示参与评论/点赞用户的头像
        if(data.comments != null && data.comments.size() >0){

            int oneAvatarWidth = (int) mContext.getResources().getDimension(R.dimen.dp_46);
            int marginLeft = (int) mContext.getResources().getDimension(R.dimen.dp_50);
            int parentWidth =    ScreenUtil.getDisplayWidth();
            int oneLineShowCount = parentWidth - marginLeft  / oneAvatarWidth;
            getDataBinding().commentAvatarList.setLayoutManager(new GridLayoutManager(mContext,oneLineShowCount));
            RecyclerViewAdapter<CommentAvatarItemViewModel>  adapter = new RecyclerViewAdapter<CommentAvatarItemViewModel>();
            ArrayList<CommentAvatarItemViewModel> list = new ArrayList<CommentAvatarItemViewModel>();
            for(CommentContentItemViewModel item :data.comments){
                CommentAvatarItemViewModel model = new CommentAvatarItemViewModel();
                model.avatar = item.sender.avatar;
                list.add(model);
            }
            adapter.setData(list);
            getDataBinding().commentAvatarList.setAdapter(adapter);

            //展示评论
     /*       getDataBinding().commentContentList.setLayoutManager(new LinearLayoutManager(mContext));
            RecyclerViewAdapter<CommentContentItemViewModel>  commentContentAdapter = new RecyclerViewAdapter<CommentContentItemViewModel>();
            commentContentAdapter.setData(data.comments);
            getDataBinding().commentContentList.setAdapter(commentContentAdapter);*/
        }
        getDataBinding().setPageModel(data);
    }

    @Override
    protected void onRootClick(View view) {

    }
}
