package com.thoughtwork.base.model;

import java.io.Serializable;

public class BaseCachedData<T> implements Serializable {
    public long updateTimeInMills;//是否更新本地缓存
    public T data;
}
