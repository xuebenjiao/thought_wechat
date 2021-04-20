package com.thoughtwork.base.recyclerview;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.thoughtwork.base.customview.BaseCustomViewModel;
import com.thoughtwork.base.customview.ICustomView;

/**
 * Time :2019/10/29
 * Author:xbj
 * Description :
 */
public class BaseViewHolder extends RecyclerView.ViewHolder {
    ICustomView view;
    public BaseViewHolder(@NonNull ICustomView itemView) {
        super((View)itemView);
        this.view= itemView;
    }
    public void bind(@NonNull BaseCustomViewModel item) {
        view.setData(item);
    }
}
