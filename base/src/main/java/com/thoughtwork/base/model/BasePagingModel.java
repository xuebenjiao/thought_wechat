package com.thoughtwork.base.model;

import java.lang.ref.WeakReference;

/**
 * author : xbj
 * e-mail : xbjzhu@163.com
 * date   : 2019/10/22 18:02
 * desc   :分页展示需要的model
 */
public abstract class BasePagingModel<T> extends SuperBaseModel<T> {
    protected boolean isRefresh = true;
    protected int pageNumber = 0;

    /**
     * 发消息给UI线程
     *
     * @param isEmpty     model的整个数据是否为空
     * @param isFirstPage 是否是第一页
     * @param hasNextPage 是否还有下一页
     */
    protected void loadSuccess(T data, final boolean isEmpty, final boolean isFirstPage,
                               final boolean hasNextPage) {
        synchronized (this) {
            mUiHandler.postDelayed(() -> {
                for (WeakReference<IBaseModelListener> weakListener : mWeakListenerArrayList) {
                    if (weakListener.get() instanceof IModelListener) {
                        IModelListener listenerItem = (IModelListener) weakListener.get();
                        if (listenerItem != null) {
                            listenerItem.onLoadFinish(BasePagingModel.this, data, isEmpty, isFirstPage, hasNextPage);
                        }
                    }
                }
                if (getCachedPreferenceKey() != null && isFirstPage) {//缓存第一页的数据，避免界面出现白屏，同时也让用户知道界面数据展示的结构与方式。
//                    saveDataToPreference(data);
                }
            }, 0);
        }
    }

    protected void loadFail(final String prompt, final boolean isFirstPage) {
        synchronized (this) {
            mUiHandler.postDelayed(() -> {
                for (WeakReference<IBaseModelListener> weakListener : mWeakListenerArrayList) {
                    if (weakListener.get() instanceof IModelListener) {
                        IModelListener listenerItem = (IModelListener) weakListener.get();
                        if (listenerItem != null) {
                            listenerItem.onLoadFail(BasePagingModel.this, prompt, isFirstPage);
                        }
                    }
                }
            }, 0);
        }
    }

    @Override
    protected void notifyCachedData(T data) {
        loadSuccess(data, false, true, true);
    }

    public interface IModelListener<T> extends IBaseModelListener {
        void onLoadFinish(BasePagingModel model, T data, boolean isEmpty, boolean isFirstPage, boolean hasNextPage);
        void onLoadFail(BasePagingModel model, String prompt, boolean isFirstPage);
    }
}
