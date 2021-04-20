package com.xbj.common.entity;

import java.io.Serializable;
import java.util.ArrayList;

public class WrapAddressItemEntity<T> implements Serializable {

    private static final long serialVersionUID = 10L;

    public ArrayList<T> mList;

    public ArrayList<T> getmList() {
        return mList;
    }

    public void setmList(ArrayList<T> mList) {
        this.mList = mList;
    }

    @Override
    public String toString() {
        return "WrapAddressItemEntity{" +
                "mList=" + mList +
                '}';
    }
}
