package com.thoughtwork.base.customview;

public interface ICustomView<S extends BaseCustomViewModel> {

    void setData(S data);

    void setStyle(int resId);

    void setActionListener(ICustomViewActionListener listener);

}
