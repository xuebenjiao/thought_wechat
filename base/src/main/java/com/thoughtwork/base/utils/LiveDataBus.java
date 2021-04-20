package com.thoughtwork.base.utils;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Time :2021/1/11
 * Author:xbj
 * Description :LiveData数据总线
 */
public class LiveDataBus {
    public final static String SCAN_MUDULE_PAGE_ENTITY = "SCAN_MUDULE_PAGE_ENTITY";
    public final static String UPDATE_INIT_PWD = "UPDATE_INIT_PWD";
    public final static String WAYBILL_INFOR = "WAYBILL_INFOR";
    public final static String VIEW_STATUS = "VIEW_STATUS";
    private static  LiveDataBus liveDataBus = new LiveDataBus();
    private Map<String, BusMutiableLiveData<Object>> bus;
    private LiveDataBus(){
        bus = new HashMap<>();
    }
    public static LiveDataBus getInstance(){
        return liveDataBus;
    }

    /**
     * 注册订阅者
     * @param key
     * @param type
     * @param <T>
     * @return
     */
    public synchronized<T> BusMutiableLiveData<T> with(String key,Class<T> type){
        if(!bus.containsKey(key)){
            bus.put(key, new BusMutiableLiveData<Object>());
        }
        return (BusMutiableLiveData<T>) bus.get(key);
    }


    public class BusMutiableLiveData<T> extends MutableLiveData{
        @Override
        public void observe(@NonNull LifecycleOwner owner, @NonNull Observer observer) {
            super.observe(owner, observer);
            hook(observer);
        }

        public void hook(Observer<? super T> observer)  {
            try {
                //1.得到mLastVersion
                Class<LiveData> liveDataClass= LiveData.class;
                Field mObserversFeild =liveDataClass.getDeclaredField("mObservers");

                mObserversFeild.setAccessible(true);
                //获取到这个成员变量的对象
                Object mObserversObject = mObserversFeild.get(this);

                //获取mObservers对应的Class
                Class<?> clazz = mObserversObject.getClass();
                //获取map的get方法
                Method get = clazz.getDeclaredMethod("get",Object.class);
                get.setAccessible(true);
                Object observerWraper=null;
                Object invokeEntry  =  get.invoke(mObserversObject,observer);
                if(invokeEntry!=null && invokeEntry instanceof Map.Entry){
                    observerWraper=((Map.Entry)invokeEntry).getValue();
                }
                if(observerWraper==null){
                    throw new NullPointerException("observerWraper is null!");
                }
                if(observerWraper != null){
                    //observerWraper 是 LifecycleBoundObserver  extend  ObserverWrapper
                    Class wrapperClass = observerWraper.getClass().getSuperclass();
                    Field mLastVersionField =   wrapperClass.getDeclaredField("mLastVersion");
                    mLastVersionField.setAccessible(true);

                    //获取LiveData中的mVersion值
                    Field mVersionField =     liveDataClass.getDeclaredField("mVersion");
                    mVersionField.setAccessible(true);
                    Object value = mVersionField.get(this);
                    //赋值
                    mLastVersionField.set(observerWraper,value);
                }
            } catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    /**
     * ren订阅者
     * @param key
     * @return
     */
    public void  removeObserver(String key,LifecycleOwner owner){
        if(bus != null && bus.containsKey(key)){
            bus.get(key).removeObservers(owner);
            bus.remove(key);
        }
    }
}
