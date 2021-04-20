package com.thoughtwork.base.model;

import java.lang.ref.WeakReference;

/**
 * author : xbj
 * e-mail : xbjzhu@163.com
 * date   : 2019/10/22 18:02
 * desc   :不需要分页展示数据的model
 */
public abstract class BaseModel<T> extends SuperBaseModel<T> {
    /**
     *  加载网络数据成功
     *  通知所有的注册着加载结果
     *  */
    protected void loadSuccess(T data) {
        synchronized (this) {
           mUiHandler.postDelayed(() -> {
                for (WeakReference<IBaseModelListener> weakListener : mWeakListenerArrayList) {
                    if (weakListener.get() instanceof IModelListener) {
                        IModelListener listenerItem = (IModelListener) weakListener.get();
                        if (listenerItem != null) {
                            listenerItem.onLoadFinish(this, data);
                        }
                    }
                }
                /** 如果我们需要缓存数据，加载成功，让我们保存他到preference */
                if (getCachedPreferenceKey() != null) {
//                    saveDataToPreference(data);
                }
            }, 0);
        }
    }

    /**
     *  加载网络数据失败
     *  通知所有的注册着加载结果
     *  */
    protected void loadFail(final String prompt) {
        synchronized (this) {
            mUiHandler.postDelayed(() -> {
                for (WeakReference<IBaseModelListener> weakListener : mWeakListenerArrayList) {
                    if (weakListener.get() instanceof IModelListener) {
                        IModelListener listenerItem = (IModelListener) weakListener.get();
                        if (listenerItem != null) {

                            listenerItem.onLoadFail(BaseModel.this, prompt);
                        }
                    }
                }
            }, 0);
        }
    }
    /**
     *  加载网络数据失败
     *  通知所有的注册着加载结果
     *  */
    protected void loadFail(final BaseErrorResponse entity) {
        synchronized (this) {
            mUiHandler.postDelayed(() -> {
                for (WeakReference<IBaseModelListener> weakListener : mWeakListenerArrayList) {
                    if (weakListener.get() instanceof IModelListener) {
                        IModelListener listenerItem = (IModelListener) weakListener.get();
                        if (listenerItem != null) {

                            listenerItem.onLoadFail(BaseModel.this, entity);
                        }
                    }
                }
            }, 0);
        }
    }

    @Override
    protected void notifyCachedData(T data) {
        loadSuccess(data);
    }

    public interface IModelListener<T,E> extends SuperBaseModel.IBaseModelListener {
        void onLoadFinish(BaseModel model, T data);
        void onLoadFail(BaseModel model,E entity);
    }
}
