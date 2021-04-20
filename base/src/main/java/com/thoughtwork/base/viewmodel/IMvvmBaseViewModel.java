package com.thoughtwork.base.viewmodel;

/**
 * author : xbj
 * e-mail : xbjzhu@163.com
 * date   : 2019/10/23 16:12
 * desc   :
 */
public interface IMvvmBaseViewModel<V> {
    void attachUI(V view);

    V getPageView();

    boolean isUIAttached();

    void detachUI();
}
