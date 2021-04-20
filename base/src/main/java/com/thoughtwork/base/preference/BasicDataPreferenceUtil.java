package com.thoughtwork.base.preference;

import android.app.Application;

/**
 * author : xbj
 * e-mail : xbjzhu@163.com
 * date   : 2019/10/22 17:32
 * desc   :
 */
public class BasicDataPreferenceUtil extends BasePreferences {
    private static final String BASIC_DATA_PREFERENCE_FILE_NAME = "network_api_module_basic_data_preference";
    private static BasicDataPreferenceUtil sInstance;

    public static BasicDataPreferenceUtil getInstance() {
        if (sInstance == null) {
            synchronized (BasicDataPreferenceUtil.class) {
                if (sInstance == null) {
                    sInstance = new BasicDataPreferenceUtil();
                }
            }
        }
        return sInstance;
    }

    @Override
    protected String getSPFileName() {
        return BASIC_DATA_PREFERENCE_FILE_NAME;
    }
    public static void init(Application application) {
        sApplication = application;
    }
}
