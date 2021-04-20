package com.thoughtwork.base.utils;


import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.util.ArrayList;

public class SPUtils {

    private static Context con;
    private static SharedPreferences sp;
    public static String fileName = "ytoPreferences";
    public static void initCon(Context con) {
        SPUtils.con = con;
    }
    /**
     * 保存信息
     *
     * @param name
     * @param value
     */
    public static void saveStringValue(String name, String value) {
        if (con == null) {
            LogUtils.d("UtilAndroid,saveStringValue() con is null: " + name);
            return;
        }
        if (sp == null) {
            sp = con.getSharedPreferences(fileName, 0);
        }
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(name, value);
        editor.commit();
    }

    /**
     * 根据key把SharedPreferences中的value取出
     *
     * @param key
     * @return
     */
    public static String getStringValue(String key) {
        String value = "";
        if (con == null) {
            LogUtils.d("UtilAndroid,getStringValue() con is null");
            return value;
        }
        if (sp == null) {
            sp = con.getSharedPreferences(fileName, 0);
        }
        value = sp.getString(key, value);
        LogUtils.i("DeviceTool","getSMSI:value :"+value);
        return value;
    }

    /**
     * 保存ArrayList<String>到sp
     * @createdBy bq
     * @param key
     * @param sArray
     */
    public static void saveArray(String key,ArrayList<String> sArray) {
        if (con == null) {
            LogUtils.d("UtilAndroid,saveStringValue() con is null: " + key);
            return;
        }
        if (sp == null) {
            sp = con.getSharedPreferences(fileName, 0);
        }
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(key+"_size",sArray.size());
        for(int i=0;i<sArray.size();i++) {
            editor.remove(key+"_" + i);
            editor.putString(key+"_" + i, sArray.get(i));
        }
        editor.commit();
    }

    /**
     * 根据key把sp中的ArrayList<String>取出
     * @createdBy bq
     * @param key
     * @return
     */
    public static ArrayList<String> getArray(String key) {
        ArrayList<String> value = null;
        if (con == null) {
            LogUtils.d("UtilAndroid,getStringValue() con is null");
            return value;
        }
        if (sp == null) {
            sp = con.getSharedPreferences(fileName, 0);
        }
        ArrayList<String> sArray = new ArrayList<String>();
        int size = sp.getInt(key+"_size",0);
        for(int i=0;i<size;i++) {
            sArray.add(sp.getString(key+"_" + i, null));
        }
        return sArray;
    }


    /**
     * 保存ArrayList<bean>到sp
     * 使用sp缓存ArrayList<bean>
     */
    public static<T> void saveArrayEntity(String key, ArrayList<T> mDatas) {
        if (con == null) {
            LogUtils.d("UtilAndroid,saveStringValue() con is null: ");
            return;
        }
        if (sp == null) {
            sp = con.getSharedPreferences(fileName, 0);
        }
        SharedPreferences.Editor editor = sp.edit();
        Gson gson = new Gson();
        String json = gson.toJson(mDatas);
        Log.d("使用sp缓存ArrayList<bean>", "saved json is " + json);
        editor.putString(key, json);
        editor.commit();
    }

    /**
     * 从sp中取出缓存ArrayList<bean>
     * @param key
     * @return
     */
    public static<T> ArrayList<T> getArrayEntity(String key, Class<T> cls) {
        ArrayList<T> mDatas =  new ArrayList<T>();
        if (con == null) {
            LogUtils.d("UtilAndroid,getStringValue() con is null");
            return mDatas;
        }
        if (sp == null) {
            sp = con.getSharedPreferences(fileName, 0);
        }
        String json = sp.getString(key, null);
        if (json != null) {
            Gson gson = new Gson();
/*
           使用泛型解析数据会出错，返回的数据类型是LinkedTreeMap
 /*           mDatas = gson.fromJson(json, new TypeToken<List<T>>() {
            }.getType());*/

            JsonArray arry = new JsonParser().parse(json).getAsJsonArray();
            for (JsonElement jsonElement : arry) {
                mDatas.add(gson.fromJson(jsonElement, cls));
            }
            return mDatas;
        }
        return mDatas;
    }

    /**
     * 保存信息
     *
     * @param name
     * @param value
     */
    public static void saveBooleanValue(String name, boolean value) {
        if (con == null) {
            LogUtils.d("UtilAndroid,saveStringValue() con is null: " + name);
            return;
        }
        if (sp == null) {
            sp = con.getSharedPreferences(fileName, 0);
        }
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(name, value);
        editor.commit();
    }

    /**
     * 根据key把SharedPreferences中的value取出
     * 默认值为false
     * @param key
     * @return
     */
    public static boolean getBooleanValue(String key) {
        boolean value = false;
        if (con == null) {
            LogUtils.d("UtilAndroid,getStringValue() con is null");
            return false;
        }
        if (sp == null) {
            sp = con.getSharedPreferences(fileName, 0);
        }
        value = sp.getBoolean(key, value);
        return value;
    }
    /**
     * 根据key把SharedPreferences中的value取出
     * 默认值为true
     * @param key
     * @return
     */
    public static boolean getBooleanValueWithCustomDefaults(String key) {
        boolean value = true;
        if (con == null) {
            LogUtils.d("UtilAndroid,getStringValue() con is null");
            return false;
        }
        if (sp == null) {
            sp = con.getSharedPreferences(fileName, 0);
        }
        value = sp.getBoolean(key, value);
        return value;
    }


    /**
     * 保存信息
     *
     * @param name
     * @param value
     */
    public static void saveIntValue(String name, int value) {
        if (con == null) {
            LogUtils.d("UtilAndroid,save() con is null: " + name);
            return;
        }
        if (sp == null) {
            sp = con.getSharedPreferences(fileName, 0);
        }
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(name, value);
        editor.commit();
    }
    /**
     * 保存信息
     *
     * @param name
     * @param value
     */
    public static void saveLongValue(String name, long value) {
        if (con == null) {
            LogUtils.d("UtilAndroid,save() con is null: " + name);
            return;
        }
        if (sp == null) {
            sp = con.getSharedPreferences(fileName, 0);
        }
        SharedPreferences.Editor editor = sp.edit();
        editor.putLong(name, value);
        editor.commit();
    }

    /**
     * 根据key把SharedPreferences中的value取出
     *
     * @param key
     * @return
     */
    public static int getIntValue(String key) {
        int value = 0;
        if (con == null) {
            LogUtils.d("UtilAndroid,getValue() con is null");
            return value;
        }
        if (sp == null) {
            sp = con.getSharedPreferences(fileName, 0);
        }
        value = sp.getInt(key, value);
        return value;
    }

    /**
     * 根据key把SharedPreferences中的value取出
     *
     * @param key
     * @return
     */
    public static long getLongValue(String key) {
        long value = 0;
        if (con == null) {
            LogUtils.d("UtilAndroid,getValue() con is null");
            return value;
        }
        if (sp == null) {
            sp = con.getSharedPreferences(fileName, 0);
        }
        value = sp.getLong(key, value);
        return value;
    }
}
