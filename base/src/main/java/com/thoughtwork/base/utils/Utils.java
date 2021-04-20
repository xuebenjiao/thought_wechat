package com.thoughtwork.base.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import com.thoughtwork.base.BaseApplication;
import com.xbj.base.R;
import com.thoughtwork.base.constants.Constants;
import com.thoughtwork.base.constants.DeliveryDeviceEnum;
import com.thoughtwork.base.constants.ExpressTypeEnum;
import com.thoughtwork.base.constants.TaskTypeEnum;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



/**
 * 一些项目中的公共方法
 *
 * @author ShenHua 2015年10月20日上午10:27:52
 */
public class Utils {
    private static final String TAG = "Utils";
    private static MediaPlayer player;

    /**
     * 显示toast，带语音播报
     */
    public static void showToast(Context activity, String content) {
		/*FUtils.showToast(activity, content);
		BaiduTTSUtil.getInstance().speak(content);*/
    }

    public static void showToast(Context activity, String content, int time) {
	/*	FUtils.showToast(activity, content, time);
		BaiduTTSUtil.getInstance().speak(content);*/
    }

    public static void showToast(Context activity, String content, int gravity, int xOffset, int yOffset) {
	/*	FUtils.showToast(activity, content, gravity, xOffset, yOffset);
		BaiduTTSUtil.getInstance().speak(content);
		*/

    }

    /**
     * 获得状态栏的高度
     *
     * @param context
     * @return
     */
    public static int getStatusHeight(Context context) {
        int statusHeight = -1;
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height")
                    .get(object).toString());
            statusHeight = context.getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusHeight;
    }

    /**
     * 更新下载，创建下载的文件
     */
    public static File mkFile(Context context) {
        String PATH_LOGCAT = null;
        File fi = null;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {// 优先保存到SD卡中
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
                if (context != null) {
                    PATH_LOGCAT = context.getExternalFilesDir("").getAbsolutePath()+ File.separator
                            + AppConstants.DownloadFolderName;
                }
            }
            else {
                PATH_LOGCAT = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator
                        + AppConstants.DownloadFolderName;
            }
        } else {// 如果SD卡不存在，就保存到本应用的目录下
            if (context != null) {
                PATH_LOGCAT = context.getFilesDir().getAbsolutePath() + File.separator
                        + AppConstants.DownloadFolderName;
            }
        }
        File file = new File(PATH_LOGCAT);
        if (!file.exists()) {
            file.mkdirs();
        }
        if (file.exists()) {
            fi = new File(PATH_LOGCAT + File.separator + AppConstants.DownloadFileName + ".apk");
            try {
                fi.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return fi;
    }

    /**
     * app的目录
     */
    public static String getFilePath(Context context, String fileName) {
        String pathFolder = null;
        String path = null;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {// 优先保存到SD卡中
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
                if (context != null) {
                    pathFolder = context.getExternalFilesDir("") + File.separator
                            + AppConstants.DownloadFolderName;
                }
            }
            else{
                pathFolder = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator
                        + AppConstants.DownloadFolderName;
            }

        } else {// 如果SD卡不存在，就保存到本应用的目录下
            if (context != null) {
                pathFolder = context.getFilesDir().getAbsolutePath() + File.separator
                        + AppConstants.DownloadFolderName;
            }
        }
        File file = new File(pathFolder);
        if (!file.exists()) {
            file.mkdirs();
        }
        path = pathFolder + File.separator + fileName;
        return path;
    }

    /**
     * app的文件夹目录
     */
    public static String getFolderPath(Context context) {
        String pathFolder = null;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {// 优先保存到SD卡中
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
                if (context != null) {
                    pathFolder = context.getExternalFilesDir("").getAbsolutePath() + File.separator
                            + AppConstants.DownloadFolderName;
                }
            }else {
                pathFolder = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator
                        + AppConstants.DownloadFolderName;
            }
        } else {// 如果SD卡不存在，就保存到本应用的目录下
            if (context != null) {
                pathFolder = context.getFilesDir().getAbsolutePath() + File.separator
                        + AppConstants.DownloadFolderName;
            }
        }
        File file = new File(pathFolder);
        if (!file.exists()) {
            file.mkdirs();
        }
        return pathFolder;
    }

    /**
     * @param saveDir
     * @return
     * @throws IOException 判断下载目录是否存在，并返回需要保存文件的路径
     */
    public static String isExistDir(String saveDir) throws IOException {
        // 下载位置
        File downloadFile;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
                downloadFile = new File(BaseApplication.getmContext().getExternalFilesDir("").getAbsolutePath(), saveDir);
            }
            else {
                downloadFile = new File(Environment.getExternalStorageDirectory(), saveDir);
            }
        } else {
            downloadFile = new File(BaseApplication.getmContext().getFilesDir().getAbsolutePath(), saveDir);
        }
        if (!downloadFile.mkdirs()) {
            downloadFile.createNewFile();
        }
        String savePath = downloadFile.getAbsolutePath();
        return savePath;
    }

    /**
     * json解析
     *//*
	public static CResponseBody<?> jsonToBean(String json) {
		LogUtils.i("json--" + json);
		try{
			JsonObject jsonObject = GsonUtil.getBean(json, JsonObject.class);
			if (jsonObject.get("cmd") != null) {
				int cmd = jsonObject.get("cmd").getAsByte();

				CResponseBody<?> res = null;
				res = GsonUtil.getBean(jsonObject, CMDFactory.buildRespTypeTokenByCmd(cmd).getType());
				return res;
			} else {
				CResponseBody<?> res = GsonUtil.getBean(jsonObject, CResponseBody.class);
				return res;
			}
		} catch (Exception e){
			e.printStackTrace();
			return null;
		}
	}*/

    /**
     * 获取应用的版本
     */
    public static int getVersionCode(Context context) {
        try {
            String pkName = context.getPackageName();
            int versionCode = context.getPackageManager().getPackageInfo(pkName, 0).versionCode;
            return versionCode;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * 获取应用的版本名称
     */
    public static String getVersionName(Context context) {
        try {
            String pkName = context.getPackageName();
            String versionName = context.getPackageManager().getPackageInfo(pkName, 0).versionName;
            return versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取手机的mac地址
     */
    public static String getMacAddress() {
        String result = "";
        String Mac = "";
        result = callCmd("busybox ifconfig", "HWaddr");

        if (result == null) {
            return "网络出错，请检查网络";
        }
        if (result.length() > 0 && result.contains("HWaddr")) {
            Mac = result.substring(result.indexOf("HWaddr") + 6, result.length() - 1);
            if (Mac.length() > 1) {
                result = Mac.toLowerCase();
            }
        }
        return result.trim();
    }

    public static String callCmd(String cmd, String filter) {
        String result = "";
        String line = "";
        try {
            Process proc = Runtime.getRuntime().exec(cmd);
            InputStreamReader is = new InputStreamReader(proc.getInputStream());
            BufferedReader br = new BufferedReader(is);

            // 执行命令cmd，只取结果中含有filter的这一行
            while ((line = br.readLine()) != null && line.contains(filter) == false) {

            }

            result = line;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 字符串前面补0的方法
     *
     * @param type 0:前面补零 其他：后面补零
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
     * 改变手机号显示，中间加空格
     */
    public static String changePhone(String oldPhone) {
        StringBuffer str = new StringBuffer(oldPhone);
        str.insert(3, " ");
        str.insert(8, " ");
        return str.toString();
    }

    /**
     * 将字符串全部转换为全角字符，解决textview的排版问题
     */
    public static String ToSBC(String input) {
        char c[] = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == ' ') {
                c[i] = '\u3000';
            } else if (c[i] < '\177') {
                c[i] = (char) (c[i] + 65248);
            }
        }
        return new String(c);
    }

    /**
     * 防止按钮重复点击
     */
    private static long lastClickTime;

    public synchronized static boolean isFastClick() {
        long time = System.currentTimeMillis();
        if (time - lastClickTime < 500) {
            return true;
        }
        lastClickTime = time;
        return false;
    }

    /**
     * 数字金额加入，
     */
    public static String toMoneyType(String money) {
        StringBuffer newMoney = null;
        String other = "";
        if (money.indexOf(".") >= 0) {
            newMoney = new StringBuffer(money.substring(0, money.indexOf(".")));
            other = money.substring(money.indexOf("."), money.length());
        } else {
            newMoney = new StringBuffer(money);
        }
        for (int i = 1; newMoney.length() - ((3 * i) + (i - 1)) > 0; i++) {
            newMoney = newMoney.insert(newMoney.length() - ((3 * i) + (i - 1)), ",");
        }
        return newMoney.toString() + other;
    }

    /**
     * 中文转换成阿拉伯数字
     */
    public static String cntoCount(String cnCount) {
        String[] cn = {"一", "二", "三", "四", "五", "六", "七", "八", "九"};
        String[] ct = {"1", "2", "3", "4", "5", "6", "7", "8", "9"};
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < cnCount.length(); i++) {
            String index = String.valueOf(cnCount.charAt(i));
            boolean isSame = false;
            for (int j = 0; j < cn.length; j++) {
                if (index.equals(cn[j])) {
                    sb = sb.append(ct[j]);
                    isSame = true;
                }
            }
            if (!isSame) {
                sb = sb.append(index);
            }
        }
        return sb.toString();
    }

    /**
     * 替换空格和换行符
     *
     * @param str
     * @return
     */
    public static String getStringNoBlank(String str) {
        if (str != null && !"".equals(str)) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            String strNoBlank = m.replaceAll("");
            return strNoBlank;
        } else {
            return str;
        }
    }

    /**
     * 判断字符串中是否存在特殊字符
     *
     * @param str
     * @return
     */
    public static boolean isExistSpecificSymbol(String str) {
        if (TextUtils.isEmpty(str)) {

            return false;
        } else {
            //[^a-zA-Z0-9\u4e00-\u9fa5\\.\\-\\—\\:\\：\\，\\,\\，\\，\\[\\] \\{\\}\\(\\)\\（\\）\\【\\】\\/\\;\\；\\\ \\#\\*\\%\\“\\”\\.\\、\\。\\_\\‘\\'\\<\\>\\《\\》\\&\\~\\……\\^\\+\\=\\| ]
            String regex = "[^a-zA-Z0-9\\u4e00-\\u9fa5\\.\\-\\—\\:\\：\\，\\,\\，\\，\\[\\]\\{\\}\\(\\)\\（\\）\\【\\】\\/\\;\\；\\\\\\ \\#\\*\\“\\”\\.\\、\\。\\_\\‘\\'\\<\\>\\《\\》\\&\\~\\……\\^\\+\\=\\| ]";
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(str);
            return m.find();
        }
    }

    /**
     * 通过名字判断是否是qr打印机
     */
    public static boolean isQRPrinter(String printerName) {
        if (printerName != null
                && printerName.length() > 2
                && ("QR".equals(printerName.substring(0, 2))
                || "HM".equals(printerName.substring(0, 2)))) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 手机号加密(后四位)
     *
     * @param s 加密前手机号
     * @return 加密后手机号
     */
    public static String encryptMobile(String s) {
        if (!FUtils.isStringNull(s) && FUtils.isPhoneNum(s)) {
            String x = "****";
            StringBuffer buffer = new StringBuffer(s);
            buffer.replace(s.length() - 8, s.length() - 4, x);
            return buffer.toString();
        } else {
            return s;
        }
    }

    /**
     * 手机号加密
     *
     * @param s     手机号
     * @param start 开始位置
     * @param end   结束位置
     * @return
     */
    public static String encryptMobileSE(String s, int start, int end) {
        if (FUtils.isStringNull(s)) return null;
        StringBuilder sb = new StringBuilder();
        //将字符串转换成字符数组
        char[] arrays = s.toCharArray();
        for (int i = 0; i < arrays.length; i++) {
            if (i >= start && i < end) {
                sb.append("*");
            } else {
                sb.append(arrays[i]);
            }
        }
        return sb.toString();
    }

    /**
     * 号码加密(第六位到第九位)
     *
     * @param s 加密前手机号
     * @return 加密后手机号
     */
    public static String encryptMobile69(String s) {
        if (!FUtils.isStringNull(s) && s.length() > 6) {
            String x = "****";
            StringBuffer buffer = new StringBuffer(s);
            buffer.replace(s.length() - 6, s.length() - 2, x);
            return buffer.toString();
        } else if (!FUtils.isStringNull(s) && s.length() <= 6 && s.length() > 2) {
            String x = "";
            for (int i = 0; i < s.length() - 2; i++) {
                x += "*";
            }
            StringBuffer buffer = new StringBuffer(s);
            buffer.replace(1, s.length() - 1, x);
            return buffer.toString();
        } else {
            return s;
        }
    }

    /**
     * 姓名加密(两个字加密第二个，三个及以上加密中间)
     *
     * @param s 加密前姓名
     * @return 加密后姓名
     */
    public static String encryptName(String s) {
        if (!FUtils.isStringNull(s) && s.length() > 2) {
            String x = "";
            for (int i = 0; i < s.length() - 2; i++) {
                x += "*";
            }
            StringBuffer buffer = new StringBuffer(s);
            buffer.replace(1, s.length() - 1, x);
            return buffer.toString();
        } else if (!FUtils.isStringNull(s) && s.length() == 2) {
            StringBuffer buffer = new StringBuffer(s);
            buffer.replace(1, 2, "*");
            return buffer.toString();
        } else {
            return s;
        }
    }

    /**
     * 面单地址最后数字隐藏显示
     */
    public static String filterStr(String str) {
        if (TextUtils.isEmpty(str)) return str;
        StringBuffer stringBuffer = new StringBuffer(str);
        int countNum = 0;//计算器
        for (int i = str.length() - 1; i > -1; i--) {
            int c = str.charAt(i);
            if (c >= 48 && c <= 57) {
                stringBuffer.replace(i, i + 1, "*");
                if (i - 1 > -1) {
                    if (((int) str.charAt(i - 1)) < 48 || ((int) str.charAt(i - 1)) > 57) {
                        break;
                    }
                }
                if (++countNum > 1) break;
            }
        }
        return stringBuffer.toString();
    }


    /**
     * 拨打电话
     *
     * @param phoneNum
     */
    @SuppressLint("MissingPermission")
    public static void call(Context context, String phoneNum) {
        if (context == null) {
            LogUtils.i("UtilAndroid,call() con is null");
            return;
        }
        phoneNum = phoneNum.replaceAll(" ", "");
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNum));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    /**
     * 发送自提短信（添加发送失败的广播）
     */
    public static void sendSMSCode(Activity ac, String phone, final String content) {
        //去除空格
//		phone = phone.replaceAll(" ","");
////		Intent it = new Intent(SMSSentBroadcastReceiver.SENT);
//		it.putExtra("flag", 0);
//		it.putExtra("telephone", phone);
//		it.putExtra("content", content);
//		PendingIntent sentPI = PendingIntent.getBroadcast(ac, 0, it, PendingIntent.FLAG_UPDATE_CURRENT);
//		FUtils.SendSMSDirect(phone, content, sentPI, null);
    }

    /**
     * 截取工号后四位
     *
     * @return
     */
    public static String getJobNoFourCount(String jobNo) {
        String s = FUtils.addZeroForNum(jobNo, 8, 0);
        return s.substring(s.length() - 4, s.length());
    }

    /**
     * 离签收时间剩余的时间
     *
     * @param setTime 应该较大的时间
     * @param date    应该较小的时间
     * @return
     */
    public static long haveTime(Date setTime, Date date) {
        long s = setTime.getTime() - date.getTime();
        return s;
    }

    /**
     * 离取件时间剩余的时间
     *
     * @param setTime 应该较大的时间
     * @param date    应该较小的时间
     * @return
     */
    public static long haveTime(String setTime, Date date) {
        int Hset = Integer.parseInt(setTime.substring(0, setTime.indexOf(":")));
        int mset = Integer.parseInt(setTime.substring(setTime.indexOf(":") + 1, setTime.length()));
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, Hset % 24);
        cal.set(Calendar.MINUTE, mset);
        cal.set(Calendar.SECOND, 0);
        return cal.getTimeInMillis() - date.getTime();
    }

    /**
     * 离签收时间剩余的时间
     *
     * @param setTime 应该较大的时间
     * @param date    应该较小的时间
     * @return
     */
    public static long haveTimedelivery(String setTime, Date date) {
        int Hset = Integer.parseInt(setTime.substring(0, setTime.indexOf(":")));
        int mset = Integer.parseInt(setTime.substring(setTime.indexOf(":") + 1, setTime.length()));
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        if (Hset / 24 > 0) cal.add(Calendar.DAY_OF_MONTH, (Hset / 24));
        cal.set(Calendar.HOUR_OF_DAY, Hset % 24);
        cal.set(Calendar.MINUTE, mset);
        cal.set(Calendar.SECOND, 0);
        return cal.getTimeInMillis() - (new Date()).getTime();
    }

    /**
     * 比较String（HH:mm）的时间是否大于Date类型时间
     *
     * @param bigTime
     * @param smallTime
     * @return
     */
    public static boolean compareTime(Date bigTime, Date smallTime) {
        if (bigTime.getTime() > smallTime.getTime()) return true;
        else return false;
    }

    /**
     * 比较String（HH:mm）的时间是否大于Date类型时间
     *
     * @param bigTime
     * @param smallTime
     * @return
     */
    public static boolean compareTime(String bigTime, Date smallTime) {
        int Hset = Integer.parseInt(bigTime.substring(0, bigTime.indexOf(":")));
        int mset = Integer.parseInt(bigTime.substring(bigTime.indexOf(":") + 1, bigTime.length()));
        Calendar cal = Calendar.getInstance();
        cal.setTime(smallTime);
        cal.set(Calendar.HOUR_OF_DAY, Hset);
        cal.set(Calendar.MINUTE, mset);
        cal.set(Calendar.SECOND, 0);
        if (cal.getTimeInMillis() >= smallTime.getTime()) return true;
        else return false;
    }

    /**
     * double类型金额转成String类型金额（去0）
     *
     * @param money 传入double类型金额
     * @return String类型金额
     */
    public static String formatDecimal(Double money) {
        if (money != null) {
            BigDecimal bd = new BigDecimal(money);
            bd.setScale(2, BigDecimal.ROUND_HALF_UP);
            money = bd.doubleValue();
            String m = money.toString();
            String sMoney = m.substring(m.indexOf("."));
            if (".0".equals(sMoney) || ".00".equals(sMoney)) {
                return m.substring(0, m.indexOf("."));
            } else {
                return m;
            }
        } else {
            return "";
        }
    }

    /**
     * 将金额转换为保留小数点后两位
     *
     * @param num
     * @return
     */
    public static String getFormatterNum(double num) {
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(num);
    }

    /**
     * 判断double是否需要保留小数点后数字，小数点后为0时，去除小数点后面数字
     *
     * @param num
     * @return
     */
    public static String getNumForDouble(double num) {
        Double numD = Double.valueOf(num);
        if (numD == null) String.valueOf(num);
        if (numD.intValue() - num == 0) {//判断是否符合取整条件
            return String.valueOf(numD.intValue());
        } else {
            return String.valueOf(numD);
        }
    }

    public static String getPrettyNumberS(String number) {
        return BigDecimal.valueOf(Double.parseDouble(number)).stripTrailingZeros().toPlainString();
    }

    public static String getPrettyNumberS(Double number) {
        return BigDecimal.valueOf(number).stripTrailingZeros().toPlainString();
    }

    public static Double getPrettyNumberD(String number) {
        String s = BigDecimal.valueOf(Double.parseDouble(number)).stripTrailingZeros().toPlainString();
        return Double.parseDouble(s);
    }

    public static Double getPrettyNumberD(Double number) {
        String s = BigDecimal.valueOf(number).stripTrailingZeros().toPlainString();
        return Double.parseDouble(s);
    }

    /**
     * 判断是否大于0
     *
     * @param str
     * @return true：大于0；false：小于等于0
     */
    public static boolean isExistMatchString(String str) {
        boolean isExist = false;
        if (str == null || str.length() == 0) return isExist;
        double d = Double.parseDouble(str);
        if (d > 0) {
            isExist = true;
        }
        return isExist;
    }

    /**
     * 分钟转换成小时和分钟
     *
     * @param minutes
     * @return
     */
    public static String convertMinutesToHM(Integer minutes) {
        if (null == minutes || minutes <= 0) {
            return null;
        }
        int h = (minutes / 60) % 24;    //小时
        int m = minutes % 60;    //分钟
        if (m != 0) {
            return h + ":" + m;
        } else {
            return h + ":00";
        }
    }

    /**
     * 将16:30的时间格式转换为分钟数
     *
     * @param input
     * @return
     */
    public static Integer convertHMToMins(String input) {
        if (TextUtils.isEmpty(input)) return null;

        if (!input.contains(":")) return null;
        String[] arrays = input.split(":");
        if (arrays == null || arrays.length != 2) return null;

        int hour = Integer.valueOf(arrays[0]);          //小时
        int minute = Integer.valueOf(arrays[1]);        //分

        return hour * 60 + minute;
    }

    /**
     * PM值转换
     */
    public static final String PM_A = "优";
    public static final String PM_B = "良";
    public static final String PM_C = "轻度污染";
    public static final String PM_D = "中度污染";
    public static final String PM_E = "重度污染";
    public static final String PM_F = "严重污染";

    public static String pmValueToDepict(int value) {
        if (value >= 0 && value <= 50) return PM_A;
        if (value > 50 && value <= 100) return PM_B;
        if (value > 100 && value <= 150) return PM_C;
        if (value > 150 && value <= 200) return PM_D;
        if (value > 200 && value <= 300) return PM_E;
        if (value > 300) return PM_F;
        return "";
    }


    /**
     * 判断是否是Emoji
     * @param string
     * @return
     */
	/*public static boolean isEmoji(String string) {
		Pattern p = Pattern.compile("[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]",
				Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(string);
		return m.find();
	}*/

    /**
     * 判断是否是Emoji表情
     *
     * @param source
     * @return true：该字符是Emoji表情
     */
    public static boolean isEmoji(String source) {
        int len = source.length();
        for (int i = 0; i < len; i++) {
            char codePoint = source.charAt(i);
            if (isEmojiCharacter(codePoint)) { //返回为true,则该字符是Emoji表情
                return true;
            }
        }
        return false;
    }

    /**
     * 判断是否是Emoji
     *
     * @param codePoint 比较的单个字符
     * @return
     */
    public static boolean isEmojiCharacter(char codePoint) {
        //如果不能匹配,则该字符是Emoji表情
        if ((codePoint == 0x0) || (codePoint == 0x9) || (codePoint == 0xA)
                || (codePoint == 0xD)
                || ((codePoint >= 0x20) && (codePoint <= 0xD7FF))
                || ((codePoint >= 0xE000) && (codePoint <= 0xFFFD))
                || ((codePoint >= 0x10000) && (codePoint <= 0x10FFFF)))
            return false;
        return true;
    }

    /**
     * 是否适配指定型号的工业手机
     *
     * @param phoneModel
     * @return
     */
    public static boolean isAdaptationPhone(String phoneModel) {
        if (DeliveryDeviceEnum.PhoneModel.I6310C.getPhoneModel().equalsIgnoreCase(phoneModel)
                || DeliveryDeviceEnum.PhoneModel.CRUISE.getPhoneModel().equalsIgnoreCase(phoneModel)
                || DeliveryDeviceEnum.PhoneModel.P31.getPhoneModel().equalsIgnoreCase(phoneModel)
                || DeliveryDeviceEnum.PhoneModel.NEOLIX.getPhoneModel().equalsIgnoreCase(phoneModel)
                || DeliveryDeviceEnum.PhoneModel.IWRIST.getPhoneModel().equalsIgnoreCase(phoneModel)
                || DeliveryDeviceEnum.PhoneModel.NLSNFT1.getPhoneModel().equalsIgnoreCase(phoneModel)
        ) {

            return true;
        }
        return false;
    }


    /**
     * 菜鸟面单处理（如：330-204 112-883818689395785182）
     *
     * @param mailNo 面单号
     * @return
     */
    public static String CNMailNoHandle(String mailNo) {
        if (FUtils.isMailNo(mailNo)) return mailNo;
        if (mailNo.length() < 12) return mailNo;
        char[] chars = mailNo.toCharArray();
        if (chars[3] == 45 && chars[7] == 32 && chars[11] == 45) {//菜鸟面单规则
            String[] strs = mailNo.split("[-\\s]");
            if (strs.length == 4 && FUtils.isNumber(strs[0] + strs[1] + strs[2])) {
                //前面三段是数字，第四段是面单号
                return strs[3];
            }
        }
        return mailNo;
    }

    /**
     * 将date类型转为int的时间，（时*60*60）+ （分*60）+ 秒
     *
     * @param time
     * @return
     */
    public static int dateToInt(Date time) {
        int timeCount = -1;
        try {
            String hours = DateUtil.getStringByFormat(time, "HH");
            String minutes = DateUtil.getStringByFormat(time, "mm");
            String seconds = DateUtil.getStringByFormat(time, "ss");
            if (!TextUtils.isEmpty(hours)) timeCount = Integer.parseInt(hours) * 60 * 60;
            if (!TextUtils.isEmpty(minutes)) timeCount += Integer.parseInt(minutes) * 60;
            if (!TextUtils.isEmpty(seconds)) timeCount += Integer.parseInt(seconds);
        } catch (Exception e) {
        } finally {
            return timeCount;
        }
    }

    /**
     * 获取OkHttpClient对象
     *
     * @return
     *//*
	public static com.squareup.okhttp.OkHttpClient getUnsafeOkHttpClient(){
		try {
			final TrustManager[] trustAllCerts = new TrustManager[]{
					new X509TrustManager() {
						@Override
						public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

						}

						@Override
						public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

						}

						@Override
						public X509Certificate[] getAcceptedIssuers() {
							return null;
						}
					}
			};

			final SSLContext sslContext = SSLContext.getInstance("SSL");
			sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
			final SSLSocketFactory sslSocketFactory = sslContext .getSocketFactory();
			com.squareup.okhttp.OkHttpClient okHttpClient = new com.squareup.okhttp.OkHttpClient();
			okHttpClient.setSslSocketFactory(sslSocketFactory);

			okHttpClient.setHostnameVerifier(new HostnameVerifier() {
				@Override
				public boolean verify(String s, SSLSession sslSession) {
					return true;
				}
			});
			return okHttpClient;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
*/
    //是否是一票多件运单号
    public static boolean isMoreWaybillNo(String str) {
        String regex = "^M\\d{15}$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(str);
        if (m.find()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断是哪家快递公司
     */
    public static ExpressTypeEnum judgeExpressFromScanCode(String scanCode) {
        String ytoRegex = "^((YT44)[0-9]{11})$|^((DD24)[0-9]{8})$|^((70)[0-9]{10})$|^((60)[0-9]{10})$|^((YT11)[0-9]{11})$|^((YT30)[0-9]{11})$|^((YT43)[0-9]{11})$|^((YT91)[0-9]{11})$|^((D20)[0-9]{9})$|^((YTG00)[0-9]{10})$|^((DD80)[0-9]{14})$|^((M00)[0-9]{9})$|^((81)[0-9]{10})$|^((YT20)[0-9]{11})$|^((DD25)[0-9]{8})$|^((G01)[0-9]{9})$|^((G80)[0-9]{15})$|^((82)[0-9]{10})$|^((YT39)[0-9]{11})$|^((YT31)[0-9]{11})$";
        String stoRegex = "^((40)[0-9]{10})$|^((55)[0-9]{11})$|^((77)[0-9]{13})$|^((22)[0-9]{10})$|^((LP00)[0-9]{12})$";
        String yundaRegex = "^((43)[0-9]{11})$|^((42)[0-9]{11})$|^((35)[0-9]{11})$|^((77)[0-9]{11})$|^((53)[0-9]{11})$|^((31)[0-9]{11})$|^((46)[0-9]{11})$";
        String ztoRegex = "^((64)[0-9]{10})$|^((73)[0-9]{120})$|^((54)[0-9]{10})$|^((75)[0-9]{12})$|^((78)[0-9]{12})$|^((77)[0-9]{12})$";
        String baishiRegex = "^((52)[0-9]{12})$|^((55)[0-9]{13})$";
        String sfRegex = "^((32)[0-9]{10})$|^((SF18)[0-9]{11})$|^((SF19)[0-9]{11})$|^((29)[0-9]{10})$|^((SF10)[0-9]{11})$|^((SF11)[0-9]{11})$";
        //邮政EMS
        String emsRegex = "^((10)[0-9]{110})$|^((97)[0-9]{110})$|^((11)[0-9]{110})$";
        String tiantianRegex = "^((TT88)[0-9]{11})$|^((TT99)[0-9]{11})$|^((TT77)[0-9]{11})$";
        //宅急送
        String zhaijisongRegex = "^((ZJS00)[0-9]{10})$";
        String debangRegex = "^((95)[0-9]{8})$|^((DPK33)[0-9]{10})$";
        //邮政小包
        String emsxRegex = "^((99)[0-9]{11})$|^((98)[0-9]{11})$";
        //优速快递
        String yousuRegex = "^((90)[0-9]{10})$";
        String jdRegex = "^((JDVB03)[0-9]{9})$|^((JD00)[0-9]{11})$|^((JDVC03)[0-9]{9})$|^((11)[0-9]{10})$|^((JDVA02)[0-9]{9})$|^((JD00)[0-9]{13})$";
        //其他快递
        String otherRegex = "^((JT50)[0-9]{11})$|^((63)[0-9]{13})$";
        //丹鸟快递
        String danniaoRegex = "^((61)[0-9]{13})$";


        if (Pattern.matches("^YT[A-Za-z0-9]+$", scanCode) ||
                Pattern.matches("^8\\d{17}$", scanCode) ||
                Pattern.matches("^DD[A-Za-z0-9]{16}$", scanCode) ||
                Pattern.matches("^8\\d{11}$", scanCode) ||
                Pattern.matches("^6\\d{17}$", scanCode) ||
                Pattern.matches("^DD[A-Za-z0-9]{10}$", scanCode) ||
                Pattern.matches("^79\\d{16}$", scanCode) ||
                Pattern.matches("^70\\d{16}$", scanCode) ||
                Pattern.matches("^M[A-Za-z0-9]{11}$", scanCode) ||
                Pattern.matches("^DB[A-Za-z0-9]{10}$", scanCode) ||
                Pattern.matches("^B9[A-Za-z0-9]{10}$", scanCode) ||
                Pattern.matches("^M[A-Za-z0-9]{14}$", scanCode) ||
                Pattern.matches("^78\\d{16}$", scanCode)) {
//		if(Pattern.matches(ytoRegex,scanCode)){
//		if(scanCode.matches(ytoRegex)){
            return ExpressTypeEnum.YTO;
        }
        if (Pattern.matches("^543\\d{9}$", scanCode) ||
                Pattern.matches("^751\\d{11}$", scanCode) ||
                Pattern.matches("^753\\d{11}$", scanCode) ||
                Pattern.matches("^731\\d{11}$", scanCode) ||
                Pattern.matches("^781\\d{11}$", scanCode) ||
                Pattern.matches("^640\\d{9}$", scanCode) ||
                Pattern.matches("^771\\d{11}$", scanCode)
        ) {
//		if(Pattern.matches(ztoRegex,scanCode)){
//		if(scanCode.matches(ztoRegex)){
            return ExpressTypeEnum.ZTO;
        }
        if (Pattern.matches("^430\\d{10}$", scanCode) ||
                Pattern.matches("^310\\d{10}$", scanCode) ||
                Pattern.matches("^460\\d{10}$", scanCode) ||
                Pattern.matches("^190\\d{10}$", scanCode) ||
                Pattern.matches("^350\\d{10}$", scanCode) ||
                Pattern.matches("^530\\d{10}$", scanCode) ||
                Pattern.matches("^55\\d{13}$", scanCode) ||
                Pattern.matches("^355\\d{10}$", scanCode)
        ) {
//		if(Pattern.matches(yundaRegex,scanCode)){
//		if(scanCode.matches(yundaRegex)){
            return ExpressTypeEnum.YUNDA;
        }
        if (Pattern.matches("^221\\d{9}$", scanCode) ||
                Pattern.matches("^372\\d{10}$", scanCode) ||
                Pattern.matches("^770\\d{12}$", scanCode) ||
                Pattern.matches("^772\\d{12}$", scanCode) ||
                Pattern.matches("^776\\d{12}$", scanCode) ||
                Pattern.matches("^773\\d{12}$", scanCode) ||
                Pattern.matches("^777\\d{12}$", scanCode)

        ) {
//		if(Pattern.matches(stoRegex,scanCode)){
//		if(scanCode.matches(stoRegex)){
            return ExpressTypeEnum.STO;
        }
        if (Pattern.matches("^350\\d{9}$", scanCode) ||
                Pattern.matches("^50\\d{12}$", scanCode) ||
                Pattern.matches("^51\\d{12}$", scanCode) ||
                Pattern.matches("^52\\d{12}$", scanCode) ||//上次现在放回来添加的
                Pattern.matches("^70\\d{12}$", scanCode) ||
                Pattern.matches("^71\\d{12}$", scanCode) ||
                Pattern.matches("^73\\d{12}$", scanCode) ||
                Pattern.matches("^55\\d{14}$", scanCode)
        ) {
//		if(Pattern.matches(baishiRegex,scanCode)){
//		if(scanCode.matches(baishiRegex)){
            return ExpressTypeEnum.BAISHITONG;
        }
        if (Pattern.matches("^SF[A-Za-z0-9]+$", scanCode) ||
                Pattern.matches("^234\\d{9}$", scanCode) ||
                Pattern.matches("^259\\d{9}$", scanCode) ||
                Pattern.matches("^262\\d{9}$", scanCode) ||
                Pattern.matches("^288\\d{9}$", scanCode) ||
                Pattern.matches("^36\\d{10}$", scanCode) ||
                Pattern.matches("^46\\d{10}$", scanCode) ||
                Pattern.matches("^96\\d{10}$", scanCode)

        ) {
//		if(Pattern.matches(sfRegex,scanCode)){
//		if(scanCode.matches(sfRegex)){
            return ExpressTypeEnum.SF;
        }
        if (Pattern.matches("^10\\d{11}$", scanCode) ||
                Pattern.matches("^234\\d{9}$", scanCode) ||
                Pattern.matches("^96\\d{11}$", scanCode) ||
                Pattern.matches("^97\\d{11}$", scanCode) ||
                Pattern.matches("^98\\d{11}$", scanCode) ||
                Pattern.matches("^99\\d{11}$", scanCode) ||
                Pattern.matches("^11\\d{11}$", scanCode)

        ) {
//		if(Pattern.matches(emsRegex,scanCode)){
//		if(scanCode.matches(emsRegex)){
            return ExpressTypeEnum.EMS;
        }
//		if(Pattern.matches(emsxRegex,scanCode)){
        if(scanCode.matches(emsxRegex)){
            return ExpressTypeEnum.EMSX;
        }
        if (Pattern.matches("^611\\d{12}$", scanCode)) {
//		if(Pattern.matches(danniaoRegex,scanCode)){
//		if(scanCode.matches(danniaoRegex)){
            return ExpressTypeEnum.DANNIAO;
        }


//		if(Pattern.matches(zhaijisongRegex,scanCode)){
        if(scanCode.matches(zhaijisongRegex)){
            return ExpressTypeEnum.ZHAIJISONG;
        }
//		if(Pattern.matches(debangRegex,scanCode)){
        if(scanCode.matches(debangRegex)){
            return ExpressTypeEnum.DEBANG;
        }
//		if(Pattern.matches(yousuRegex,scanCode)){
        if(scanCode.matches(yousuRegex)){
            return ExpressTypeEnum.YOUSU;
        }
//		if(Pattern.matches(jdRegex,scanCode)){
        if(scanCode.matches(jdRegex)){
            return ExpressTypeEnum.JD;
        }
//		if(Pattern.matches(otherRegex,scanCode)){
        if(scanCode.matches(otherRegex)){
            return ExpressTypeEnum.OTHER;
        }
        if (Pattern.matches("^TT[A-Za-z0-9]+$", scanCode)) {
//		if (Pattern.matches(tiantianRegex, scanCode)) {
//		if(scanCode.matches(tiantianRegex)){
            return ExpressTypeEnum.TIANTIAN;
        }
        return ExpressTypeEnum.UNKNOWN;
    }

    /**
     * 根据快递公司编码获取对应的图标
     *
     * @param expressCode
     * @return
     */
    public static int getExpressPicFromExpressCode(String expressCode) {
        if (ExpressTypeEnum.YTO.getExpressCode().equals(expressCode)) {
            return R.drawable.img_yto;
        } else if (ExpressTypeEnum.SF.getExpressCode().equals(expressCode)) {
            return R.drawable.sf;
        } else if (ExpressTypeEnum.YUNDA.getExpressCode().equals(expressCode)) {
            return R.drawable.img_yd;
        } else if (ExpressTypeEnum.STO.getExpressCode().equals(expressCode)) {
            return R.drawable.img_sto;
        } else if (ExpressTypeEnum.ZTO.getExpressCode().equals(expressCode)) {
            return R.drawable.img_zto;
        } else if (ExpressTypeEnum.BAISHITONG.getExpressCode().equals(expressCode)) {
            return R.drawable.img_best;
        }
        else if (ExpressTypeEnum.TIANTIAN.getExpressCode().equals(expressCode)) {
            return R.drawable.img_tiantian;
        }
        else if (ExpressTypeEnum.DEBANG.getExpressCode().equals(expressCode)) {
            return R.drawable.img_debang;
        }else {
            return R.drawable.img_other;
        }
    }

    /**
     * 根据快递公司编码获取对应的图标
     *
     * @param expressCode
     * @return
     */
    public static ExpressTypeEnum getExpressTypeEnumFromExpressCode(String expressCode) {
        if (ExpressTypeEnum.YTO.getExpressCode().equals(expressCode)) {
            return ExpressTypeEnum.YTO;
        } else if (ExpressTypeEnum.SF.getExpressCode().equals(expressCode)) {
            return ExpressTypeEnum.SF;
        } else if (ExpressTypeEnum.YUNDA.getExpressCode().equals(expressCode)) {
            return ExpressTypeEnum.YUNDA;
        } else if (ExpressTypeEnum.STO.getExpressCode().equals(expressCode)) {
            return ExpressTypeEnum.STO;
        } else if (ExpressTypeEnum.ZTO.getExpressCode().equals(expressCode)) {
            return ExpressTypeEnum.ZTO;
        } else if (ExpressTypeEnum.BAISHITONG.getExpressCode().equals(expressCode)) {
            return ExpressTypeEnum.BAISHITONG;
        }
        else if (ExpressTypeEnum.TIANTIAN.getExpressCode().equals(expressCode)) {
            return ExpressTypeEnum.TIANTIAN;
        }
        else if (ExpressTypeEnum.DEBANG.getExpressCode().equals(expressCode)) {
            return ExpressTypeEnum.DEBANG;
        }
        else if (ExpressTypeEnum.DANNIAO.getExpressCode().equals(expressCode)) {
            return ExpressTypeEnum.DANNIAO;
        }else {
            return ExpressTypeEnum.OTHER;
        }
    }

    /**
     * 根据快递公司名称获取对应的图标
     *
     * @param expressName
     * @return
     */
    public static int getExpressPicFromChinaName(String expressName) {
        if (expressName.contains("圆通")) {
            return R.drawable.img_yto;
        } else if (expressName.contains("顺丰")) {
            return R.drawable.sf;
        } else if (expressName.contains("韵达")) {
            return R.drawable.img_yd;
        } else if (expressName.contains("申通")) {
            return R.drawable.img_sto;
        } else if (expressName.contains("中通")) {
            return R.drawable.img_zto;
        } else if (expressName.contains("百世")) {
            return R.drawable.img_best;
        }
        else if (expressName.contains("天天")) {
            return R.drawable.img_tiantian;
        }
        else if (expressName.contains("德邦")) {
            return R.drawable.img_debang;
        }
        else if (expressName.contains("京东")) {
            return R.drawable.img_jd;
        }
        else if (expressName.contains("极兔")) {
            return R.drawable.img_jt;
        }
        else if (expressName.contains("邮政")) {
            return R.drawable.img_ems;
        }
        else if (expressName.contains("宅急送")) {
            return R.drawable.img_zjs;
        }
        else if (expressName.contains("优速")) {
            return R.drawable.img_ys;
        }
        else {
            return R.drawable.img_other;
        }
    }


    /**
     * 根据快递公司名称获取对应的图标
     *
     * @param expressName
     * @return
     */
    public static int getStationPicFromChinaName(String expressName) {
        if (expressName.contains("百世邻里")) {
            return R.drawable.img_station_lingli;
        } else if (expressName.contains("菜鸟")) {
            return R.drawable.img_station_cainiao;
        } else if (expressName.contains("快宝驿站")) {
            return R.drawable.img_station_kuaibao;
        } else if (expressName.contains("蓝店")) {
            return R.drawable.img_station_landian;
        } else if (expressName.contains("妈妈驿站")) {
            return R.drawable.img_station_mamyizhan;
        } else if (expressName.contains("小兵驿站")) {
            return R.drawable.img_station_xiaobing;
        }
        else if (expressName.contains("熊猫快收")) {
            return R.drawable.img_station_xmks;
        }
        else if (expressName.contains("中通快递超市")) {
            return R.drawable.img_station_ztkdcs;
        }else {
            return R.drawable.img_other;
        }
    }

    public static ExpressTypeEnum getExpressTypeEnumFromChinaName(String expressName) {
        if (expressName.contains("圆通")) {
            return ExpressTypeEnum.YTO;
        } else if (expressName.contains("顺丰")) {
            return ExpressTypeEnum.SF;
        } else if (expressName.contains("韵达")) {
            return ExpressTypeEnum.YUNDA;
        } else if (expressName.contains("申通")) {
            return ExpressTypeEnum.STO;
        } else if (expressName.contains("中通")) {
            return ExpressTypeEnum.ZTO;
        } else if (expressName.contains("百世")) {
            return ExpressTypeEnum.BAISHITONG;
        }
        else if (expressName.contains("德邦")) {
            return ExpressTypeEnum.DEBANG;
        }
        else if (expressName.contains("天天")) {
            return ExpressTypeEnum.TIANTIAN;
        }else {
            return ExpressTypeEnum.OTHER;
        }
    }

    public static TaskTypeEnum getTaskTypeNnumFromIndex(int index) {
        if (TaskTypeEnum.TASK_1.getTaskCode() == index) {
            return TaskTypeEnum.TASK_1;
        } else if (TaskTypeEnum.TASK_2.getTaskCode() == index) {
            return TaskTypeEnum.TASK_2;
        } else if (TaskTypeEnum.TASK_3.getTaskCode() == index) {
            return TaskTypeEnum.TASK_3;
        } else if (TaskTypeEnum.TASK_4.getTaskCode() == index) {
            return TaskTypeEnum.TASK_4;
        } else if (TaskTypeEnum.TASK_5.getTaskCode() == index) {
            return TaskTypeEnum.TASK_5;
        }
        return TaskTypeEnum.TASK_1;
    }

    /**
     * 根据服务端返回的快递公司名称 筛选图片
     *
     * @return
     */
    public static int getExpressPicFromExpressName(String name) {
        if ("yuantong".equals(name)) {
            return R.drawable.img_yto;
        } else if ("shunfeng".equals(name)) {
            return R.drawable.sf;
        } else if ("yunda".equals(name)) {
            return R.drawable.img_yd;
        } else if ("shentong".equals(name)) {
            return R.drawable.img_sto;
        } else if ("zhongtong".equals(name)) {
            return R.drawable.img_zto;
        } else if ("huitongkuaidi".equals(name)) {
            return R.drawable.img_best;
        }
        else if ("tiantian".equals(name)) {
            return R.drawable.img_tiantian;
        }else if ("debangwuliu".equals(name)) {
            return R.drawable.img_debang;
        }else {
            return R.drawable.img_other;
        }
    }

    /**
     * 返回当前程序版本号
     */
    public static String getAppVersionCode(Context context) {
        int versioncode = 0;
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            // versionName = pi.versionName;
            versioncode = pi.versionCode;
        } catch (Exception e) {
            LogUtils.e("VersionInfo_Exception", e);
        }
        return versioncode + "";
    }

    /**
     * 返回当前程序版本名
     */
    public static String getAppVersionName(Context context) {
        String versionName = null;
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versionName = pi.versionName;
        } catch (Exception e) {
            LogUtils.e("VersionInfo_Exception", e);
        }
        return versionName;
    }

    /**
     * @param by 字节数据转化为字符串
     * @return
     */

    public static String byteASCIIstr(byte[] by) {
        String str = "";
        for (int i = 0; i < by.length; i++) {
            char b = (char) by[i];
            str += b;
        }
        return str;
    }

    /**
     * 根据tabName 返回对应的otpType
     *
     * @param mTabName
     * @return
     */

    public static String getOtpTypeFromTabName(String mTabName) {
        StringBuilder stringBuilder = new StringBuilder();
        if (!TextUtils.isEmpty(mTabName)) {
            if (mTabName.contains("到件")) {
                stringBuilder.append("11");
            } else if (mTabName.contains("派件")||mTabName.contains("指派")) {
                stringBuilder.append("21");
            } else if (mTabName.contains("签收")||mTabName.contains("其他渠道"
            )||mTabName.contains("今日待派")) {
                stringBuilder.append("31");
            } else if (mTabName.contains("发往")) {
                stringBuilder.append("41");
            } else if (mTabName.contains("到派")) {
                stringBuilder.append("51");
            } else if (mTabName.contains("指派")) {
//				stringBuilder.append("61");
                stringBuilder.append("21");
            }
            else if (mTabName.contains("入库")) {
                stringBuilder.append("301");
            }
        }
        return stringBuilder.toString();
    }
    /**
     * 根据tabName 返回对应的ScanType
     *
     * @param mTabName
     * @return
     */

    public static int getScanTypeFromTabName(String mTabName) {
//		StringBuilder stringBuilder = new StringBuilder();
        if (!TextUtils.isEmpty(mTabName)) {
            if (mTabName.contains("到件")) {
                return 1;
            } else if (mTabName.contains("派件")){
                return 2;
            } else if (mTabName.contains("签收")){
                return 3;
            } else if (mTabName.contains("发往")) {
                return 4;
            } else if (mTabName.contains("到派")) {
                return 5;
            } else if (mTabName.contains("指派")) {
                return 6;
            }
        }
        return -1;
//		return stringBuilder.toString();
    }

    /**
     * 是否显示短信发送状态 1 显示  2、不显示
     * @param mTabName
     * @return
     */
    public static int getShowSmsStatusFlag(String mTabName) {
        if (!TextUtils.isEmpty(mTabName) && (mTabName.contains("派件")||mTabName.contains("待派")||mTabName.contains("到派")||mTabName.contains("签收"))) {
            return 1;
        }
        return 2;
    }

    /**
     * 将电话号码中间四位替换为*号
     */
    public static String setMaskNumberForStr(String str) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        if (str.length() > 7) {
            String maskNumber = str.substring(0, 3) + "****" + str.substring(7, str.length());
            return maskNumber;
        }
        return "";
    }

    /**
     * 替换字符种的制表位、换行符、空格、回车
     *
     * @param src
     * @return
     */
    public static String replaceSpecialChar(String src) {
        String dest = "";
        if (!TextUtils.isEmpty(src)) {
            Pattern pattern = Pattern.compile("\t|\r|\n|\\s*");
            Matcher matcher = pattern.matcher(src);
            dest = matcher.replaceAll("");
        }
        return dest;
    }

    /**
     * 根据单号对应的code 返回对应的
     * CURRENT_PLATFORM_NOT_LOGIN(-1, "当前平台用户未登录或登录失效"),
     * <p>
     * NORMAL(200, "正常单"),
     * INTERCEPT(604, "通缉件"),
     * ARRIVED(627, "已做过到件"),
     * DISPATCHED(609, "已做过派件"),
     * SIGNED(612, "已做过签收"),
     * <p>
     * PUSH_ACTION_ERROR(-2, "pushAction指定有误")
     */
    public static String getOrderStatusFromCode(int code) {
        if (code == 200) {
            return "正常单";
        } else if (code == 604) {
            return "通缉件";
        } else if (code == 627) {
            return "已做过到件";
        } else if (code == 609) {
            return "已做过派件";
        } else if (code == 612) {
            return "已做过签收";
        } else {
            return "未校验";
        }
	/*	else if(code == 604){
			return "通缉件";
		}*/

    }

    /**
     * 播放本地语音文件
     *
     * @param rawId
     */
    public static void playFromRawFile(int rawId) {
        try {
            if(player == null) {
                player = new MediaPlayer();
            }
		/*	else{
				player.reset();
			}*/
            AssetFileDescriptor file = BaseApplication.getmContext().getResources().openRawResourceFd(rawId);
            try {
                player.setDataSource(file.getFileDescriptor(), file.getStartOffset(), file.getLength());
                player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        mp.reset();
                    }
                });
                player.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                    @Override
                    public boolean onError(MediaPlayer mp, int what, int extra) {
                        Log.i(TAG, "onError: what-->"+what +"extra-->"+extra);
                        mp.stop();
                        mp.release();
                        return false;
                    }
                });
                player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        mp.start();
                    }
                });

                // 设置音量
                //player.setVolume();
                file.close();
                if (!player.isPlaying()) {
                    player.prepareAsync();
                }
            } catch (IOException e) {
                player = null;
            }
            finally {
                player = null;
                file = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 释放音频播放器
     */
    public static void releaseMediaPlayer() {
        if(player != null ){
            player.stop();
            player.release();
            player = null;
        }
    }

    /**
     * 读取asset文件夹中的音频文件
     * @param assetId
     */
    public static void playFromAssetFile(int assetId) {
        AssetManager assetManager;
        MediaPlayer player = null;
        player = new MediaPlayer();
        assetManager = BaseApplication.getmContext().getResources().getAssets();
        try {
            AssetFileDescriptor fileDescriptor = assetManager.openFd("scan_success.mp3");
            player.setDataSource(fileDescriptor.getFileDescriptor(), fileDescriptor.getStartOffset(), fileDescriptor.getStartOffset());
            player.prepare();
            player.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 解释一下三个参数
     *
     * 第一个streamType是需要调整音量的类型,这里设的是媒体音量,可以是:
     *
     * STREAM_ALARM 警报
     * STREAM_MUSIC 音乐回放即媒体音量
     * STREAM_NOTIFICATION 窗口顶部状态栏Notification,
     * STREAM_RING 铃声
     * STREAM_SYSTEM 系统
     * STREAM_VOICE_CALL 通话
     * STREAM_DTMF 双音多频,不是很明白什么东西
     *
     * 第二个direction,是调整的方向,增加或减少,可以是:
     *
     * ADJUST_LOWER 降低音量
     * ADJUST_RAISE 升高音量
     * ADJUST_SAME 保持不变,这个主要用于向用户展示当前的音量
     *
     * 第三个flags是一些附加参数,只介绍两个常用的
     *
     * FLAG_PLAY_SOUND 调整音量时播放声音
     * FLAG_SHOW_UI 调整时显示音量条,就是按音量键出现的那个
     * @param streamType
     * @param index
     * @param flags
     */

    public static void setStreamVolume(int streamType, int index, int flags){
        AudioManager am=(AudioManager)BaseApplication.getmContext().getSystemService(Context.AUDIO_SERVICE);
        am.setStreamVolume(streamType != 0 ?streamType:AudioManager.STREAM_MUSIC,am.getStreamMaxVolume(AudioManager.STREAM_MUSIC),AudioManager.FLAG_PLAY_SOUND);
        //得到听筒模式的最大值
//		am.getStreamMaxVolume(AudioManager.STREAM_VOICE_CALL);
        //得到听筒模式的当前值
//		am.getStreamVolume(AudioManager.STREAM_VOICE_CALL);
    }

    /**

      * 监听系统静音模式

      * @param mContext

      */

    private static void modeIndicater(){
        AudioManager am = (AudioManager)BaseApplication.getmContext().getSystemService(Context.AUDIO_SERVICE);
        final int ringerMode = am.getRingerMode();
        switch (ringerMode) {
            //普通模式
            case AudioManager.RINGER_MODE_NORMAL:
//				playFromRawFile();
                break;
            //静音模式
            case AudioManager.RINGER_MODE_VIBRATE:
//				setStreamVolume()
                break;
            //震动模式
            case AudioManager.RINGER_MODE_SILENT:
//				setStreamVolume();
                break;
        }
    }


    /**
     * 拼接webView需要的参数
     */
    public static  String packageParamsForWebView(String url,int screenHeight){
        StringBuilder stringBuilder = new StringBuilder();
        if(!TextUtils.isEmpty(url)){
            stringBuilder.append(url);
        }
        appendCommonParameters(stringBuilder);

        stringBuilder.append("&screenHeight="+screenHeight);

        return stringBuilder.toString();
    }

    /**
     * 拼接webView需要的参数
     */
    public static  String packageParamsForWebView(String url,int screenHeight,int id,int itemType){
        StringBuilder stringBuilder = new StringBuilder();
        if(!TextUtils.isEmpty(url)){
            stringBuilder.append(url);
        }
        appendCommonParameters(stringBuilder);
        stringBuilder.append("&id="+id);
        //1 网点，2 驿站
        stringBuilder.append("&itemType="+itemType);

        int backHeight = (int) (ScreenUtil.getStatusBarHeight(BaseApplication.getmContext()) );//+ BaseApplication.getmContext().getResources().getDimension(R.dimen.dp_10)
        //给H5传递时无需转化为px,直接给dp
        stringBuilder.append("&backBtnMarginTop="+28);
        return stringBuilder.toString();
    }

    private static void appendCommonParameters(StringBuilder stringBuilder) {
        if(stringBuilder  == null){
            stringBuilder = new StringBuilder();
        }
        stringBuilder.append("?token=" + SPUtils.getStringValue(Constants.USER_TOKEN));
        stringBuilder.append("&roleType=" + SPUtils.getIntValue(Constants.ROLE_TYPE));
    }


    /**
     * 暂停所有ocr识别的线程
     */
    public static  void interruptAllOcrThread(String threadName){
        Map<Thread, StackTraceElement[]> threadMap = Thread.getAllStackTraces();
        Log.e("albertThreadDebug","all start==============================================");
        for (Map.Entry<Thread, StackTraceElement[]> entry : threadMap.entrySet()) {
            Thread thread = entry.getKey();
            if(thread.getName().startsWith(threadName)){
                thread.interrupt();
            }
        }
    }

    /**
     * 暂停所有ocr识别的线程
     */
    public static  void stopAllOcrThread(String threadName){
        Map<Thread, StackTraceElement[]> threadMap = Thread.getAllStackTraces();
        Log.e("albertThreadDebug","all start==============================================");
        for (Map.Entry<Thread, StackTraceElement[]> entry : threadMap.entrySet()) {
            Thread thread = entry.getKey();
            if(thread.getName().startsWith(threadName)){
                try {
                    thread.stop();
                }catch (UnsupportedOperationException e){
                    e.printStackTrace();
                }

            }
        }
    }
    /**
     * 获取短信发送状态
     */
    public static  String getSmsStatusMsg(int smsStatus){
        switch (smsStatus){
            case 0:
                return  "未发送";
            case 1:
                return "发送中";
            case 2:
                return "发送成功";
            case 3:
                return "发送失败";
        }
        return "未发送";
    }

    /**
     * 检查手机上是否安装了指定的软件
     * @param context
     * @param packageName：应用包名
     * @return
     */
    public static  boolean isAvilible(Context context, String packageName){
        //获取packagemanager
        final PackageManager packageManager = context.getPackageManager();
        //获取所有已安装程序的包信息
        List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);
        //用于存储所有已安装程序的包名
        List<String> packageNames = new ArrayList<String>();
        //从pinfo中将包名字逐一取出，压入pName list中
        if(packageInfos != null){
            for(int i = 0; i < packageInfos.size(); i++){
                String packName = packageInfos.get(i).packageName;
                packageNames.add(packName);
            }
        }
        //判断packageNames中是否有目标程序的包名，有TRUE，没有FALSE
        return packageNames.contains(packageName);
    }

}
