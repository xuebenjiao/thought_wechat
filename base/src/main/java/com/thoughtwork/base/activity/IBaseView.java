package com.thoughtwork.base.activity;

/**
 * author : xbj
 * e-mail : xbjzhu@163.com
 * date   : 2019/10/22 18:05
 * desc   :
 */
public interface IBaseView {
    void showContent();
    void showLoading();
    void onRefreshEmpty();
    void onRefreshFailure(String message);
}
