package com.xbj.common.views.adapter;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.thoughtwork.base.customview.BaseCustomViewModel;
import com.thoughtwork.base.recyclerview.BaseViewHolder;
import com.xbj.common.views.listItem.CommentAvatarItemView;
import com.xbj.common.views.listItem.CommentAvatarItemViewModel;
import com.xbj.common.views.listItem.CommentContentItemView;
import com.xbj.common.views.listItem.CommentContentItemViewModel;
import com.xbj.common.views.listItem.PublisherInforItemView;
import com.xbj.common.views.listItem.PublisherInforItemViewModel;

import java.util.ArrayList;
import java.util.List;


/**
 * Time :2019/10/29
 * Author:xbj
 * Description : 扫描列表适配器
 */
public class RecyclerViewAdapter<T extends BaseCustomViewModel> extends RecyclerView.Adapter<BaseViewHolder> {
    private List<T> mItems = new ArrayList<>();

    public List<T> getmItems() {
        return mItems;
    }

    private final int VIEW_TYPE_PULISH_PIC = 1;
    private final int VIEW_TYPE_COMMENT_AVATAR = 2;
    private final int VIEW_TYPE_COMMENT_CONTENT = 3;

    public RecyclerViewAdapter(){
    }

    /**
     * 设置RecyclerView的数据源
     * @param items
     */
    public void setData(List<T> items){
            mItems = items;
            //使用该方法的更新内部数据集，没有默认的动画效果，同时更新数据的效率页不如上面的方法，官方不推荐使用这种方式更新数据集。官方建议  Don’t call notifyDataSetChanged if you don’t have to.
            notifyDataSetChanged();
    }

    /**
     * 解决该异常 Inconsistency detected. Invalid view holder adapter positionViewHolder
     * @param items
     */
    public void notifyData(List<T> items) {
        if (items != null ) {
            int previousSize = mItems.size();
            mItems.clear();
            //列表从positionStart位置到itemCount数量的列表项批量删除数据时调用，伴有动画效果
            notifyItemRangeRemoved(0, previousSize);
            mItems.addAll(items);
            // 列表从positionStart位置到itemCount数量的列表项批量添加数据时调用，伴有动画效果
            notifyItemRangeInserted(0, items.size());
        }
    }
    /**
     * 创建RecyclerView每条数据的视图
     * @param parent
     * @param viewType
     * @return
     */
    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if(viewType == VIEW_TYPE_PULISH_PIC){
            PublisherInforItemView publisherInforItemView = new PublisherInforItemView(parent.getContext());
            RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
            publisherInforItemView.setLayoutParams(layoutParams);
            return  new BaseViewHolder(publisherInforItemView);
        }
       else  if(viewType == VIEW_TYPE_COMMENT_AVATAR){
            CommentAvatarItemView commentAvatarItemView = new CommentAvatarItemView(parent.getContext());
            RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
            commentAvatarItemView.setLayoutParams(layoutParams);
            return  new BaseViewHolder(commentAvatarItemView);
        }
       else if(viewType == VIEW_TYPE_COMMENT_CONTENT){
            CommentContentItemView commentContentItemView = new CommentContentItemView(parent.getContext());
            RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
            commentContentItemView.setLayoutParams(layoutParams);
            return  new BaseViewHolder(commentContentItemView);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.bind(mItems.get(position));
    }


    @Override
    public int getItemCount() {
        if(mItems != null && mItems.size() >0){
            return mItems.size();
        }
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        if(mItems.get(position) instanceof PublisherInforItemViewModel){
            return VIEW_TYPE_PULISH_PIC;
        }
        else if(mItems.get(position) instanceof CommentContentItemViewModel){
            return VIEW_TYPE_COMMENT_CONTENT;
        }
        else if(mItems.get(position) instanceof CommentAvatarItemViewModel){
            return VIEW_TYPE_COMMENT_AVATAR;
        }

        return -1;
    }
    /**
     * 删除数据
     */
    public void deleteItem(T t){
        if(mItems != null) {
            int index = mItems.indexOf(t);
            if (index != -1) {
                mItems.remove(t);
                notifyItemRemoved(index);
//                notifyDataSetChanged();
            }
        }
    }

    /**
     * 更新数据
     */
    public void updateItem(T t) {
        if (mItems != null) {
            int index = mItems.indexOf(t);
            if (index != -1) {
                notifyItemChanged(index);
            }
        }
    }
    /**
     * 更新数据
     */
    public void updateItemForInsert(T t) {
        if (mItems != null) {
            int index = mItems.indexOf(t);
            if (index != -1) {
                notifyItemInserted(index);
            }
        }
    }

}
