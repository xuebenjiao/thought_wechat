package com.thoughtwork.base.utils;

import java.io.File;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.PendingIntent.CanceledException;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

public class FUtils {
    /**
     * 这里都是一些通用的方法
     */
    private static Toast toast;
    private static int temS;
    private static int temFlat;
    private static byte[] b = new byte[0];
    private static boolean countdownFlat = true;
    private static Context context;
    private static SharedPreferences sp;
    private static String spName;
    private static Editor editor;

    private static double rad(double d) {
        return d * Math.PI / 180.0000;
    }

    private final static double EARTH_RADIUS = 6378137.0;

    public static void getInstance(Context context) {
        FUtils.context = context;
    }

    /**
     * @param activity
     * @param content
     *            主线程执行 ：显示长时间toast的方法
     */
    public static void onUiThreadShowToast(final Activity activity, final String content) {
        if(content != null && content.length() >0){
            if (toast == null) {

                activity.runOnUiThread(new Runnable()
                {
                    public void run()
                    {
                        toast = Toast.makeText(activity, content, Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    }
                });
            } else {
                activity.runOnUiThread(new Runnable()
                {
                    public void run()
                    {
                        toast.setText(content);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    }
                });
            }
        }
    }

    /**
     * @param activity
     * @param content
     *            显示长时间toast的方法
     */
    public static void showToast(Context activity, String content) {
        if(content != null && content.length() >0){
            if (toast == null) {
                toast = Toast.makeText(activity, content, Toast.LENGTH_LONG);
            } else {
                toast.setText(content);
            }
            toast.setGravity(Gravity.CENTER, 0, 0);
            //解决内容过长时，信息不居中显示的问题
            int tvToastId = Resources.getSystem().getIdentifier("message", "id", "android");
            TextView tvToast = ((TextView) toast.getView().findViewById(tvToastId));
            if(tvToast != null){
                tvToast.setGravity(Gravity.CENTER);
            }
            toast.show();
        }


    }

    /**
     * @param activity
     * @param content
     *            显示长时间toast的方法
     */
    public static void showToast(Context activity, String content, int time) {
        if(content != null && content.length() >0){
            if (toast == null) {
                toast = Toast.makeText(activity, content, time);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            } else {
                toast.setText(content);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
        }
    }

    /**
     * @param activity
     * @param content
     * @param gravity
     * @param xOffset
     * @param yOffset
     * 显示长时间,能自定义位置的toast的方法
     */
    public static void showToast(Context activity, String content, int gravity, int xOffset, int yOffset) {
        if(content != null && content.length() >0){
            if (toast == null) {
                toast = Toast.makeText(activity, content, Toast.LENGTH_LONG);
                toast.setGravity(gravity, xOffset, yOffset);
                toast.show();
            } else {
                toast.setText(content);
                toast.setGravity(gravity, xOffset, yOffset);
                toast.show();
            }
        }
    }

    /**
     * 取消toast
     */
    public static void cancleToast()
    {
        if(toast != null)
        {
            toast.cancel();
        }
    }

    /**
     * 建立一个sharePreference,返回EDITOR
     *
     * @param name
     *            SHAREPREFERENCE的名字
     * @return
     */
    @SuppressLint("CommitPrefEdits")
    public static void CreatSharePreference(String name) {
        spName = name;
        SharedPreferences share = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        editor = share.edit();
    }

    /**
     * 判断当前activity是否处于活动状态
     *
     * @param activity
     * @return
     */
    public static boolean isTopActivity(Activity activity, String packageName) {
        ActivityManager activityManager = (ActivityManager) activity.getSystemService(Context.ACTIVITY_SERVICE);
        List<RunningTaskInfo> tasksInfo = activityManager.getRunningTasks(1);
        if (tasksInfo.size() > 0) {
            // 应用程序位于堆栈的顶层
            if (packageName.equals(tasksInfo.get(0).topActivity.getPackageName())) {
                return true;
            }
        }
        return false;
    }

    /**
     *
     * 判断mainactivity是否处于栈顶
     * @return  true在栈顶false不在栈顶
     */
    public static boolean isTopActivity(Context context, String activityName){
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        String name = manager.getRunningTasks(1).get(0).topActivity.getClassName();
        return name.equals(activityName);
    }

    /**
     * 判断应用是否在前台
     * return true应用在前台
     */
    public static boolean isRunningApplication(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<RunningTaskInfo> taskList = am.getRunningTasks(1);
        if (taskList != null && !taskList.isEmpty()) {
            ComponentName topActivity = taskList.get(0).topActivity;
            if (topActivity != null && topActivity.getPackageName().equals(context.getPackageName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 传一个时间,返回当前时间与这个时间相加
     *
     * @param seconds
     *            秒
     * @returnString
     */
    public static String timeAddCurTime(int seconds) {
        String str = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        str = sdf.format(new Date().getTime() + seconds * 1000);
        return str;
    }

    /**
     * 把日期字符串转换成时间的方法
     *
     * @param time
     * @return
     */
    public static long dateToLong(String time) {
        long rand = 0;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date d2 = null;
            try {
                d2 = sdf.parse(time);// 将String to Date类型
                rand = d2.getTime();
            } catch (ParseException e) {
                e.printStackTrace(); // To change body of catch statement use
                // File | Settings | File Templates.
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rand;
    }

    /**
     * 判断当前的软件是否在运行
     *
     * @param context
     * @return
     */
    public static boolean isSoftWearRunning(Context context, String packageName) {
        @SuppressWarnings("static-access")
        ActivityManager am = (ActivityManager) context.getSystemService(context.ACTIVITY_SERVICE);
        // get the info from the currently running task
        List<RunningTaskInfo> taskInfo = am.getRunningTasks(1);
        if(taskInfo == null||taskInfo.size() == 0) return false;
        ComponentName componentInfo = taskInfo.get(0).topActivity;
        // 假如软件正在运行，里面的字符串是应用的包名
        if (componentInfo.getPackageName().equalsIgnoreCase(packageName)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 检测wifi是否可用
     *
     * @param inContext
     * @return
     */
    public static boolean isWiFiActive(Context inContext) {
        Context context = inContext.getApplicationContext();
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getTypeName().equals("WIFI") && info[i].isConnected()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }


    /**
     *  判断当前设备是手机还是平板，代码来自 Google I/O App for Android
     * @param context
     * @return 平板返回 True，手机返回 False
     */
    public static boolean isPad(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    /**
     * 获取唯一设备号
     */
    @SuppressLint("MissingPermission")
    public static String getDeviceIdOrMacAddress(Context context){
        if(isPad(context)){//平板就获取mac地址作为唯一识别
            return getMacAddress(context);
        }else{//手机获取DeviceId
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            if(TextUtils.isEmpty(tm.getDeviceId())){
                return getMacAddress(context);
            } else {
                return tm.getDeviceId();
            }
        }
    }

    /**
     * 获取mac地址
     * @param context
     * @return
     */
    public static String getMacAddress(Context context){
        WifiManager wm = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        return wm.getConnectionInfo().getMacAddress();
    }

    /**
     * 把DATE类型格式化成字符串
     *
     * @param date
     * @return
     */
    public static String DateToStr(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentDate = sdf.format(date); // 当期日期
        return currentDate;
    }

    /**
     * 把DATE类型格式化成字符串
     *
     * @param date 时间Date类型
     * @param format 转成String的格式
     * @return
     */
    public static String DateToString(Date date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        String currentDate = sdf.format(date); // 当期日期
        return currentDate;
    }

    /**
     * 判断是否为手机号
     *
     * @param phoneNum
     * @returnboolean
     */
    public static boolean isPhoneNum(String phoneNum) {
        //去掉+86/86
        if(!TextUtils.isEmpty(phoneNum)){
            if(phoneNum.startsWith("86")){
                phoneNum = phoneNum.substring(2,phoneNum.length());
            }else if (phoneNum.startsWith("+86")){
                phoneNum = phoneNum.substring(3,phoneNum.length());
            }
        }
        return matches(phoneNum, "^1\\d{10}$");
    }

    /**
     * 判断是否为固话
     * 区号、电话号码及分机号，中间用“-”分隔
     * @param phoneNum
     * @returnboolean
     */
    public static boolean isTelephoneNum(String phoneNum) {
        //
        return matches(phoneNum, "^[0-9]{3,4}-[0-9]{7,8}(-[0-9]{1,6})?$");
    }

    /**
     * 判断只有中英文字符的名字
     *
     * @param name
     * @returnboolean
     */
    public static boolean isName(String name) {
        return matches(name, "^[A-Za-z\u4e00-\u9fa5.]+$");
    }

    /**
     * 判断只有中英文字符的名字
     * @param s
     * @returnboolean
     */
    public static boolean isChinese(String s) {
        return matches(s, "[\u4e00-\u9fa5]");
    }

    /**
     * 判断只有中英文数字字符的名字
     *
     * @param name
     * @returnboolean
     */
    public static boolean isSignName(String name) {
        return matches(name, "^[0-9A-Za-z\u4e00-\u9fa5]+$");
    }

    /**
     * 身份证验证
     *
     * @param idc
     * @returnboolean
     */
    public static boolean isIDC(String idc) {
        return matches(idc, "^[1-9]\\d{5}(19|20)\\d{2}(0[1-9]|1[0-2])(0[1-9]|[12][0-9]|3[01])\\d{3}(\\d|X|x){1}$");
    }

    /**
     * 面单号判断
     * @param mailNo
     * @returnboolean
     */
    public static boolean isMailNo(String mailNo) {
        return matches(mailNo, "^[0-9A-Za-z]+$");
    }

    /**
     * 是不是( 9999.99 )形式的数据
     *
     * @param num
     * @returnboolean
     */
    public static boolean isOnlyTwoDecimals(String num) {
        return matches(num, "^(([1-9]{1}([0-9]{1,3})?(\\.[0-9]{1,2})?)|([0]{1}(\\.[1-9]{1,2})))$");
    }

    /**
     * 是不是( 9999.99 )形式的数据
     *
     * @param num
     * @returnboolean
     */
    public static boolean isNumber(String num) {
        return matches(num, "[0-9]+");
    }

    /**
     * 判断字符串是否包含中文
     * @param s
     * @return
     */
    public static boolean haveChinese(String s) {
        return matchesContains(s, "[\u4e00-\u9fa5]");
    }

    /**
     * 判断字符串是否包含英文
     * @param s
     * @return
     */
    public static boolean haveEnglish(String s) {
        return matchesContains(s, "[a-zA-Z]");
    }

    /**
     * 判断字符串是否包含斜杠
     * @param s
     * @return
     */
    public static boolean haveSlash(String s) {
        //return false;
        return matchesContains(s, "[\\.*/.*\']");
    }

    /**
     * 比对正则
     *
     * @param str
     * @param reg
     * @returnboolean
     */
    public static boolean matches(String str, String reg) {
        boolean boo = false;
        if(TextUtils.isEmpty(str)){
            return boo;
        }
        Pattern pat = Pattern.compile(reg);
        Matcher mat = pat.matcher(str);
        boo = mat.matches();
        return boo;
    }

    /**
     * 比对正则，判断是否包含某种字符
     * @param str
     * @param reg
     * @return boolean
     */
    public static boolean matchesContains(String str, String reg){
        boolean flg = false;
        Pattern pat = Pattern.compile(reg);
        Matcher matcher = pat.matcher(str);
        if (matcher.find()){
            flg = true;
        }
        return flg;
    }

    /**
     * 跳转到短信发送界面，
     *
     * @param activity
     * @param phoneNum
     */
    public static void SendSMS(Activity activity, String phoneNum, String msgBody) {
        Uri uri = Uri.parse("smsto:" + phoneNum);
        Intent it = new Intent(Intent.ACTION_SENDTO, uri);
        it.putExtra("sms_body", msgBody);
        activity.startActivity(it);
    }

    /**
     * 直接发送短信到指定号码
     * @param phoneNum
     * @param msgBody
     */
    public static void SendSMSDirect(String phoneNum, String msgBody, PendingIntent sentPI, PendingIntent deliveredPI){
        SmsManager smsMgr = SmsManager.getDefault();
        //smsMgr.sendTextMessage(phoneNum, null, msgBody, sentPI, deliveredPI);
        //防止在短信长度大于70的时候出现报空指针的情况（大于70时分多条短信发出）
        if(msgBody.length() > 70){
            ArrayList<String> msgs = smsMgr.divideMessage(msgBody);
            for(String msg : msgs) {
                if (msg != null) {
                    smsMgr.sendTextMessage(phoneNum, null, msg, sentPI, deliveredPI);
                }
            }
        } else {
            smsMgr.sendTextMessage(phoneNum, null, msgBody, sentPI, deliveredPI);
        }
    }

    /**
     * 获取号码打进入拨号界面
     *
     * @param phoneNum
     */
    public void tackCall(Activity activity, String phoneNum) {
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNum));
        activity.startActivity(intent);
    }

    /**
     * 判断gps是否开启的方法
     *
     * @param context
     * @return
     */
    public static boolean isGPSOn(Context context) {
        LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        boolean GPS_status = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        return GPS_status;
    }

    /**
     * 开启或关闭gps
     * @param context
     * @return
     */
    public static void openGPS(Context context) {
        Intent gpsIntent = new Intent();
        gpsIntent.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider");
        gpsIntent.addCategory("android.intent.category.ALTERNATIVE");
        gpsIntent.setData(Uri.parse("custom:3"));
        try {
            PendingIntent.getBroadcast(context, 0, gpsIntent, 0).send();
        } catch (CanceledException e) {
            e.printStackTrace();
        }
    }

    /**
     * 检查网络链接的方法
     *
     * @param activity
     * @return
     */
    public static boolean checkNetWork(Context activity) {
        ConnectivityManager cwjManager = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(cwjManager == null){
            return false;
        }
        NetworkInfo info = cwjManager.getActiveNetworkInfo();
        if (info != null && info.isAvailable()) {
            return true;
        } else {
            return false;
        }
    }

    /*
     * 检查手机网络连接类型 获取当前的网络状态 -1：没有网络 1：WIFI网络 2：wap网络 3：net网络
     */
    public static int getNetWorkType(Context context) {
        int netType = -1;
        final int CMNET = 3, CMWAP = 2, WIFI = 1;
        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo == null) {
            return netType;
        }
        int nType = networkInfo.getType();

        if (nType == ConnectivityManager.TYPE_MOBILE) {
            String extraInfo = networkInfo.getExtraInfo();
            if(!isStringNull(extraInfo)){
                if (extraInfo.toLowerCase().equals("cmnet")) {
                    netType = CMNET;
                } else {
                    netType = CMWAP;
                }
            }
        } else if (nType == ConnectivityManager.TYPE_WIFI) {
            netType = WIFI;
        }
        return netType;
    }

    /**
     * 判断服务是否已经启动
     */
    public static boolean isServiceRunning(Context mContext,String className) {
        boolean isRunning = false;
        ActivityManager activityManager = (ActivityManager)
                mContext.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> serviceList = activityManager.getRunningServices(30);
        if (serviceList == null) {
            return false;
        }
        if (!(serviceList.size()>0)) {
            return false;
        }
        for (int i=0; i<serviceList.size(); i++) {
            if (serviceList.get(i).service.getClassName().equals(className) == true) {
                isRunning = true;
                break;
            }
        }
        return isRunning;
    }

    /**
     * 判断当前进程是否是指定进程
     * @param context
     * @param processName
     * @return
     */
    public static boolean isSpecifyProcess(Context context, String processName){
        int pid = android.os.Process.myPid();
        ActivityManager mActivityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo appProcess:mActivityManager.getRunningAppProcesses()) {
            if(appProcess == null) continue;
            if (appProcess.processName.equals(processName)&&appProcess.pid == pid) {
                return true;
            }
        }
        return false;
    }

    /**
     * 下载好了APK跳转到安装程序的方法
     *
     * @param activity
     *            启动的activity
     * @param file
     *            软件下载的路径的文件包
     */
    public static void tackToInstall(Activity activity, File file) {
        Intent install = new Intent();
        install.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        install.setAction(Intent.ACTION_VIEW);
        String uString = "file://" + file.getPath();
        install.setDataAndType(Uri.parse(uString), "application/vnd.android.package-archive");
        activity.startActivity(install);// 安装
    }

    /**
     * 关闭键盘
     *
     * @param con
     */
    public static void closeKeyboard(Activity con) {
        View view = con.getWindow().peekDecorView();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) con.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }


    private static boolean getcountdownFlat() {
        synchronized (b) {
            return countdownFlat;
        }
    }

    /**
     * 根据key把SharedPreferences中的Stirng类型的value取出
     *
     * @param key
     * @return
     */
    public static String getSpString(String key) {
        String value = "";
        if (context == null) {
            return value;
        }
        if (sp == null) {
            sp = context.getSharedPreferences(spName, Context.MODE_PRIVATE);
        }
        value = sp.getString(key, value);
        return value;
    }

    /**
     * 根据key把SharedPreferences中的int类型value取出
     *
     * @param key
     * @return
     */
    public static int getSpInt(String key) {
        int value = 0;
        if (context == null) {
            return value;
        }
        if (sp == null) {
            sp = context.getSharedPreferences(spName, 0);
        }
        value = sp.getInt(key, value);
        return value;
    }

    /**
     * 根据key把SharedPreferences中的boolean类型value取出
     *
     * @param key
     * @return
     */
    public static boolean getSpBoolean(String key) {
        boolean value = false;
        if (context == null) {
            return value;
        }
        if (sp == null) {
            sp = context.getSharedPreferences(spName, 0);
        }
        value = sp.getBoolean(key, value);
        return value;
    }

    /**
     * 保存boolean信息
     *
     * @param name
     * @param value
     */
    public static void saveSpValue(String name, boolean value) {
        if (context == null) {
            return;
        }
        if (sp == null) {
            sp = context.getSharedPreferences(spName, 0);
        }
        editor = sp.edit();
        editor.putBoolean(name, value);
        editor.commit();
    }

    /**
     * 保存Int信息
     *
     * @param name
     * @param value
     */
    public static void saveSpValue(String name, int value) {
        if (context == null) {
            return;
        }
        if (sp == null) {
            sp = context.getSharedPreferences(spName, 0);

        }
        editor = sp.edit();
        editor.putInt(name, value);
        editor.commit();
    }

    /**
     * 保存SHAREPREFENCE 的string类型的信息
     *
     * @param name
     * @param value
     */
    public static void saveSpValue(String name, String value) {
        if (context == null) {
            return;
        }
        if (sp == null) {
            sp = context.getSharedPreferences(spName, 0);

        }
        editor = sp.edit();
        editor.putString(name, value);
        editor.commit();
    }

    /**
     * 通过经纬度计算距离
     *
     * @param lat_a
     * @param lng_a
     * @param lat_b
     * @param lng_b
     * @return
     */
    public static double getDistance(double lat_a, double lng_a, double lat_b, double lng_b) {
        double radLat1 = rad(lat_a);
        double radLat2 = rad(lat_b);
        double a = radLat1 - radLat2;
        double b = rad(lng_a) - rad(lng_b);
        double distance = 2 * Math
                .asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) + Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
        distance = distance * EARTH_RADIUS;
        distance = Math.round(distance * 10000) / 10000;
        return distance;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 实体类转Map
     *
     * @param obj
     * @return
     */
    public static HashMap<String, Object> ConvertObjToMap(Object obj) {
        HashMap<String, Object> reMap = new HashMap<String, Object>();
        if (obj == null)
            return null;
        Field[] fields_father = obj.getClass().getSuperclass().getDeclaredFields();
        Field[] fields = obj.getClass().getDeclaredFields();
        try {
            for (int i = 0; i < fields_father.length; i++) {

                Field f = obj.getClass().getSuperclass().getDeclaredField(fields_father[i].getName());
                f.setAccessible(true);
                Object o = f.get(obj);
                reMap.put(fields_father[i].getName(), o);
            }
            for (int i = 0; i < fields.length; i++) {
                Field f = obj.getClass().getDeclaredField(fields[i].getName());
                f.setAccessible(true);
                Object o = f.get(obj);
                reMap.put(fields[i].getName(), o);
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return reMap;
    }

    /**
     * 判断字符串是否为空
     */
    public static boolean isStringNull(String s){
        if(s == null||"".equals(s)){

            return true;
        }else{
            return false;
        }
    }

    /**
     * 判断字符串是否为空或者null或者NULL
     */
    public static boolean isStringEmptyOrNull(String s){
        if(TextUtils.isEmpty(s)) {
            return true;
        }else if("null".equals(s)||"NULL".equals(s)){
            return true;
        }else{
            return false;
        }
    }

    /**
     * 横竖屏判断
     * @param context
     */
    public static boolean isScreenOriatationPortrait(Context context) {
        return context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;
    }

    /**
     * 获取mac地址
     */
    public static String getMacAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface
                    .getNetworkInterfaces(); en.hasMoreElements();) {

                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf
                        .getInetAddresses(); enumIpAddr.hasMoreElements();) {

                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (SocketException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * 字符串前面补0的方法
     * @param type
     * 0:前面补零 其他：后面补零
     */
    public static String addZeroForNum(String str, int strLength, int type) {
        int strLen = str.length();
        StringBuffer sb = null;
        while (strLen < strLength) {
            sb = new StringBuffer();
            if (type == 0) {
                sb.append("0").append(str);// 左(前)补0
            } else {
                sb.append(str).append("0");// 右(后)补0
            }
            str = sb.toString();
            strLen = str.length();
        }
        return str;
    }

    /**
     * 将当前的date转换成当天0点的date
     */
    public static Date dateToDate(Date date){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String time = format.format(date);
        Date newDate = null;
        try {
            newDate = format.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return newDate;
    }

    //-------------两个double的加减乘除----------------------//
    /**
     * 提供精确的加法运算。
     * @param v1
     *            被加数
     * @param v2
     *            加数
     * @return 两个参数的和
     */
    public static double addBigDecimal(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.add(b2).doubleValue();
    }

    /**
     * 提供精确的减法运算。
     *
     * @param v1
     *            被减数
     * @param v2
     *            减数
     * @return 两个参数的差
     */

    public static double subBigDecimal(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.subtract(b2).doubleValue();
    }

    /**
     * 提供精确的乘法运算。
     * @param v1
     *            被乘数
     * @param v2
     *            乘数
     * @return 两个参数的积
     */
    public static double mulBigDecimal(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.multiply(b2).doubleValue();
    }

    /**
     * 提供（相对）精确的除法运算，当发生除不尽的情况时，精确到 小数点以后10位，以后的数字四舍五入。
     * @param v1
     *            被除数
     * @param v2
     *            除数
     * @return 两个参数的商
     */
    public static double divBigDecimal(double v1, double v2) {
        return divBigDecimal(v1, v2, 10);
    }

    /**
     * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指 定精度，以后的数字四舍五入。
     * @param v1
     *            被除数
     * @param v2
     *            除数
     * @param scale
     *            表示表示需要精确到小数点以后几位。
     * @return 两个参数的商
     */
    public static double divBigDecimal(double v1, double v2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException(
                    "The   scale   must   be   a   positive   integer   or   zero");
        }
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 提供精确的小数位四舍五入处理
     * @param v
     *            需要四舍五入的数字
     * @param scale
     *            小数点后保留几位
     * @return 四舍五入后的结果
     */
    public static double roundBigDecimal(double v, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException(
                    "The   scale   must   be   a   positive   integer   or   zero");
        }
        BigDecimal b = new BigDecimal(Double.toString(v));
        BigDecimal one = new BigDecimal("1");
        return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     *	精确的身份证号码检查
     *	idcNo 身份证号码
     *身份证校验码的计算方法
     1、将前面的身份证号码17位数分别乘以不同的系数。从第一位到第十七位的系数分别为：7－9－10－5－8－4－2－1－6－3－7－9－10－5－8－4－2。
     2、将这17位数字和系数相乘的结果相加。
     3、用加出来和除以11，看余数是多少？
     4、余数只可能有0－1－2－3－4－5－6－7－8－9－10这11个数字。其分别对应的最后一位身份证的号码为1－0－X －9－8－7－6－5－4－3－2。
     5、通过上面得知如果余数是3，就会在身份证的第18位数字上出现的是9。如果对应的数字是10，身份证的最后一位号码就是罗马数字x。
     例如：某男性的身份证号码为【53010219200508011x】， 我们看看这个身份证是不是合法的身份证。
     首先我们得出前17位的乘积和【(5*7)+(3*9)+(0*10)+(1*5)+(0*8)+(2*4)+(1*2)+(9*1)+(2*6)+(0*3)+(0*7)+(5*9)+(0*10)+(8*5)+(0*8)+(1*4)+(1*2)】是189，
     然后用189除以11得出的结果是189/11=17----2，也就是说其余数是2。最后通过对应规则就可以知道余数2对应的检验码是X。所以，可以判定这是一个正确的身份证号码。
     */
    public static int[] COEFFICIENT_IDC = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};
    public static String[] COMPARE_IDC = {"1", "0", "X", "9", "8", "7", "6", "5", "4", "3", "2"};
    public static boolean isExactIDCCheck(String idcNo){
        if(idcNo == null||idcNo.length() != 18) return false;
        //先正则表达式校验身份证是否正确
        if(!isIDC(idcNo)) return false;
        char[] chars = idcNo.toCharArray();
        int count = 0;
        for(int i=0;i < chars.length - 1;i++){//轮询前17位数
            try {
                int c = (Integer.parseInt(String.valueOf(chars[i])));
                count += (c * COEFFICIENT_IDC[i]);
            }catch (NumberFormatException numberFormatException) {//转换成int时抛异常，返回false
                return false;
            }
        }
        int remainder = count % 11;
        if(COMPARE_IDC[remainder].equals(idcNo.substring(idcNo.length() - 1)))return true;
        return false;
    }

    /**
     * 根据身份证自动识别性别
     * @param idcNo 身份证号码
     * @return 返回识别结果 男或女
     */
    public static String discernSexIdentityCard(String idcNo){
        if(!isExactIDCCheck(idcNo)) return null;
        char[] chars = idcNo.toCharArray();
        //身份证号码的倒数第二位是奇数的为男性，偶数的为女性
        int c = (Integer.parseInt(String.valueOf(chars[16])));
        if(c % 2 == 0){
            return "女";
        } else {
            return "男";
        }
    }

    /**
     * 精确性别校验
     * @param sex
     * @param idcNo
     * @return
     */
    public static boolean isExactSexCheck(String sex, String idcNo){
        if(TextUtils.isEmpty(sex)) return false;
        if(idcNo == null||idcNo.length() != 18) return false;
        String sexCode = idcNo.substring(idcNo.length()-2, idcNo.length()-1);
        try {
            int c = (Integer.parseInt(sexCode));
            if("男".equals(sex)&&(c%2 != 0))return true;//男的最后第二位不能被2整除
            if("女".equals(sex)&&(c%2 == 0))return true;//女的最后第二位能被2整除
        }catch (NumberFormatException numberFormatException) {//转换成int时抛异常，返回false
            return false;
        }
        return false;
    }

    /**
     * 企业组织机构代码校验
     * 前八位是数字或大写字母，第九位为-，第十位为校验码
     * 前八位在str中的下标和ws的积的总和，对于11的余数，是11的时候检验码为0，是10的时候检验码为X，其他的校验等于余数
     */
    public static boolean isValidEntpCode(String code) {
        int[] ws = {3, 7, 9, 10, 5, 8, 4, 2};
        String str = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String regex = "^([0-9A-Z]){8}-[0-9|X]$";

        if (!code.matches(regex)) {
            return false;
        }
        int sum = 0;
        for (int i = 0; i < 8; i++) {
            sum += str.indexOf(String.valueOf(code.charAt(i))) * ws[i];
        }


        int c9 = 11 - (sum % 11);

        String sc9 = String.valueOf(c9);
        if (11 == c9) {
            sc9 = "0";
        } else if (10 == c9) {
            sc9 = "X";
        }

        return sc9.equals(String.valueOf(code.charAt(9)));
    }

    /**
     * 统一社会信用代码校验
     * 统一代码为18位，统一代码由十八位的数字或大写英文字母（不适用I、O、Z、S、V）组成，由五个部分组成：
     * 第一部分（第1位）为登记管理部门代码，9表示工商部门；(数字或大写英文字母)
     * 第二部分（第2位）为机构类别代码;(数字或大写英文字母)
     * 第三部分（第3-8位）为登记管理机关行政区划码；(数字)
     * 第四部分（第9-17位）为全国组织机构代码；(数字或大写英文字母)
     * 第五部分（第18位）为校验码(数字或大写英文字母)
     * 前十七位在str中的下标和ws的积的总和，对于31的余数，余数为0是temp为31，最后在str中第（31-temp）位的字符，是否与校验码一样
     */
    public static boolean isValidCreditCode(String code){
        int[] ws = {1, 3, 9, 27, 19, 26, 16, 17, 20, 29, 25, 13, 8, 24, 10, 30, 28};
        String str = "0123456789ABCDEFGHJKLMNPQRTUWXY";//社会统一信用代码不含（I、O、S、V、Z） 等字母
        if(TextUtils.isEmpty(code)){
            return false;
        }else if(code.length() != 18){
            return false;
        }else{
            int sum = 0;
            for(int i=0; i < 17; i++){
                sum += str.indexOf(String.valueOf(code.charAt(i))) * ws[i];
            }
            int temp = sum % 31;
            temp = temp == 0 ? 31 : temp;
            return code.substring(17, 18).equals(String.valueOf(str.charAt(31 - temp)));
        }
    }

    /**
     * 验证港澳通行证
     * @return
     */
    public static boolean isValidateHongKongMacauPass(String s){
        String regex = "(^CSC)\\d{8}(.){1}<\\d{2}(0[1-9]|1[0-2])(0[1-9]|[12][0-9]|3[01])(.){1}<\\d{2}(0[1-9]|1[0-2])(0[1-9]|[12][0-9]|3[01])(.){1}<(.){1}$";
        return matches(s, regex);
    }

    /**
     * sql存储转义
     */
    public static String transferredSqlSet(String s){
        if(s.contains("'"))return s.replaceAll("'", "''");
        return s;
    }

    /**
     * sql获取转义
     */
    public static String transferredSqlGet(String s){
        if(s.contains("'"))return s.replaceAll("''", "'");
        return s;
    }
}
