package com.thoughtwork.base.utils;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class ArrayListUtils {

    //private static SharedPreferences mSp;

   /* private static SharedPreferences getSp() {
        if (Application.getContext() == null)
            return null;
        if (sharedPreferences == null)
            sharedPreferences = Application.getContext().getSharedPreferences("key", MODE_PRIVATE);
        return sharedPreferences;
    }*/

    public static void saveArray(Context mContext,String key,ArrayList<String> sArray) {

        SharedPreferences mSp = mContext.getSharedPreferences("key",MODE_PRIVATE);

        SharedPreferences.Editor mEdit1 = mSp.edit();
        mEdit1.putInt(key+"_size",sArray.size());
        for(int i=0;i<sArray.size();i++) {
            mEdit1.remove(key+"_" + i);
            mEdit1.putString(key+"_" + i, sArray.get(i));
        }
        mEdit1.commit();
    }

    public static ArrayList<String> getArray(Context mContext,String key) {
        SharedPreferences mSp = mContext.getSharedPreferences("key",MODE_PRIVATE);
        ArrayList<String> sArray = new ArrayList<String>();
        int size = mSp.getInt(key+"_size",0);
        for(int i=0;i<size;i++) {
            sArray.add(mSp.getString(key+"_" + i, null));
        }
        return sArray;
    }



}
