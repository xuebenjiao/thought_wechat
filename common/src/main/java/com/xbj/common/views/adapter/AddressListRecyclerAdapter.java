package com.xbj.common.views.adapter;

import android.text.TextUtils;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.thoughtwork.base.recyclerview.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Time :2020/4/22
 * Author:xbj
 * Description :
 */
public class AddressListRecyclerAdapter extends RecyclerView.Adapter<BaseViewHolder> {
    private String currentModuleName;
    private String mTabName;
    /*是否发送EventBus事件的标识 默认是发送，避免公用时到时主界面按钮显示混乱*/
    private boolean isSendEventBusFlag = true;
    private ArrayList<AddressItemViewViewModel> mItems = new ArrayList<AddressItemViewViewModel>();

    public ArrayList<AddressItemViewViewModel> getmItems() {
        return mItems;
    }

    private final int VIEW_TYPE_PICTURE_TITLE = 1;
    public AddressListRecyclerAdapter(){

    }
    public AddressListRecyclerAdapter(String name){
        this.mTabName = name;

    }
    public AddressListRecyclerAdapter(String name, String currentModuleName){
        this.mTabName = name;
        this.currentModuleName = currentModuleName;
    }
    public AddressListRecyclerAdapter(boolean isSendEventBusFlag){
        this.isSendEventBusFlag = isSendEventBusFlag;
    }

    /**
     * 设置RecyclerView的数据源
     * @param items
     */
    public void setData(ArrayList<AddressItemViewViewModel> items){
//        mItems = items;
//        notifyDataSetChanged();
        //使用该方法的更新内部数据集，没有默认的动画效果，同时更新数据的效率页不如上面的方法，官方不推荐使用这种方式更新数据集。官方建议  Don’t call notifyDataSetChanged if you don’t have to.*/
        notifyData(items);
    }

    /**
     * 解决该异常 Inconsistency detected. Invalid view holder adapter positionViewHolder
     * @param items
     */
    public void notifyData(List<AddressItemViewViewModel> items) {
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
        if(viewType == VIEW_TYPE_PICTURE_TITLE){//带图片显示的item
            AddressItemView pictureTitleView = new AddressItemView(parent.getContext(),isSendEventBusFlag,mTabName, TextUtils.isEmpty(currentModuleName)?"":currentModuleName);
            RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
            pictureTitleView.setLayoutParams(layoutParams);
            return  new BaseViewHolder(pictureTitleView);
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
        if(mItems.get(position) instanceof AddressItemViewViewModel){
            return VIEW_TYPE_PICTURE_TITLE;
        }
        return -1;
    }
}