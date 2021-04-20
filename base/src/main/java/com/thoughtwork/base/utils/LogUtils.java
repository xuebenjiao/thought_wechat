package com.thoughtwork.base.utils;

import android.content.Context;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.Toast;

import com.thoughtwork.base.utils.exception.F;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * DEBUG LOGCAT
 *
 * @author Cayte
 * @email xusw@dne.com.cn
 * @date 2012-1-1 上午00:00:00
 *
 */
public class LogUtils {
    public static  String TAG = "LogUtils";
    public static Boolean SWITCH_HIGH = true, SWITCH_LOW= true; // 日志文件总开关
    private static Boolean LOG_WRITE_TO_FILE = false;// 日志写入文件开关
    private static char LOG_TYPE = 'v';// 输入日志类型，w代表只输出告警信息等，v代表输出所有信息
    private static String LOG_FILENAME = "app_Log.txt";// 本类输出的日志文件名称
    private static int SDCARD_LOG_FILE_SAVE_DAYS = 1;// sd卡中日志文件的最多保存天数

    private static final String FORMAT_LOG = "yyyy-MM-dd HH:mm:ss";
    private static final String FORMAT_LOG_FILE = "yyyy-MM-dd";

    public static void w(Object msg) { // 警告信息
        log(TAG, msg.toString(), 'w');
    }

    public static void w(Throwable tr) { //
        Log.w(TAG, "---------------------------------------------------", tr);
    }

    public static void e(Object msg) { // 错误信息
        log(TAG, msg.toString(), 'e');
    }

    public static void e(Throwable tr) { //
        Log.e(TAG, "---------------------------------------------------", tr);
    }

    public static void d(Object msg) {// 调试信息
        log(TAG, msg.toString(), 'd');
    }

    public static void i(Object msg) {//
        log(TAG, msg.toString(), 'i');
    }

    public static void v(Object msg) {
        log(TAG, msg.toString(), 'v');
    }

    public static void w(String tag, Object msg) { // 警告信息
        log(tag, msg.toString(), 'w');
    }

    public static void w(String tag, Throwable tr) { //
        Log.w(tag, "---------------------------------------------------", tr);
    }

    public static void e(String tag, Object msg) { // 错误信息
        log(tag, msg.toString(), 'e');
    }

    public static void e(String tag, Throwable tr) { //
        Log.e(tag, "---------------------------------------------------", tr);
    }

    public static void d(String tag, Object msg) {// 调试信息
        log(tag, msg.toString(), 'd');
    }

    public static void i(String tag, Object msg) {//
        log(tag, msg.toString(), 'i');
    }

    public static void v(String tag, Object msg) {
        log(tag, msg.toString(), 'v');
    }

    /**
     * 根据tag, msg和等级，输出日志
     *
     * @param tag
     * @param msg
     * @param level
     * @return void
     * @since v 1.0
     */
    private static void log(String tag, String msg, char level) {
        if (SWITCH_HIGH&&SWITCH_LOW) {
            //日志全开
            if ('e' == level && ('e' == LOG_TYPE || 'v' == LOG_TYPE)) { // 输出错误信息
                Log.e(tag, msg);
            } else if ('w' == level && ('w' == LOG_TYPE || 'v' == LOG_TYPE)) {
                Log.w(tag, msg);
            } else if ('d' == level && ('d' == LOG_TYPE || 'v' == LOG_TYPE)) {
                Log.d(tag, msg);
            } else if ('i' == level && ('d' == LOG_TYPE || 'v' == LOG_TYPE)) {
                Log.i(tag, msg);
            } else {
                Log.v(tag, msg);
            }

        }else if (SWITCH_HIGH) {
            //高保密等级日志开关
            if ('w' == level && ('w' == LOG_TYPE || 'v' == LOG_TYPE)) {
                Log.w(tag, msg);
            }
        }else if(SWITCH_LOW)
        {
            //低保密级别日志开关
            if ('e' == level && ('e' == LOG_TYPE || 'v' == LOG_TYPE)) { // 输出错误信息
                Log.e(tag, msg);
            } else if ('w' == level && ('w' == LOG_TYPE || 'v' == LOG_TYPE)) {
                Log.w(tag, msg);
            } else if ('d' == level && ('d' == LOG_TYPE || 'v' == LOG_TYPE)) {
                Log.d(tag, msg);
            } else if ('i' == level && ('d' == LOG_TYPE || 'v' == LOG_TYPE)) {
                Log.i(tag, msg);
            } else {
                Log.v(tag, msg);
            }
        }

        if (LOG_WRITE_TO_FILE)
            writeLogtoFile(String.valueOf(level), tag, msg);
    }

    /**
     * 打开日志文件并写入日志
     *
     * @return
     * **/
    public static void writeLogtoFile(String mylogtype, String tag, String text) {// 新建或打开日志文件
        Date nowtime = new Date();
        String needWriteFiel = DateFormat.format(FORMAT_LOG_FILE, nowtime).toString();
        String needWriteMessage = "<" + DateFormat.format(FORMAT_LOG, nowtime) + ">" + mylogtype + "---" + tag + "\n" + text;
        File file = new File(F.getLogPath(), needWriteFiel + LOG_FILENAME);
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter filerWriter = new FileWriter(file, true);// 后面这个参数代表是不是要接上文件中原来的数据，不进行覆盖
            BufferedWriter bufWriter = new BufferedWriter(filerWriter);
            bufWriter.write(needWriteMessage);
            bufWriter.newLine();
            bufWriter.close();
            filerWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除制定的日志文件
     * */
    public static void delFile() {// 删除日志文件
        String needDelFiel = DateFormat.format(FORMAT_LOG_FILE, getDateBefore()).toString();
	/*	File file = new File(F.getAppPath(), needDelFiel + LOG_FILENAME);
		if (file.exists()) {
			file.delete();
		}*/
    }

    /**
     * 得到现在时间前的几天日期，用来得到需要删除的日志文件名
     * */
    private static Date getDateBefore() {
        Date nowtime = new Date();
        Calendar now = Calendar.getInstance();
        now.setTime(nowtime);
        now.set(Calendar.DATE, now.get(Calendar.DATE) - SDCARD_LOG_FILE_SAVE_DAYS);
        return now.getTime();
    }

    public static void show(Object text) {
//		show(C.context, text);
    }

    public static void show(Context con, Object text) {
        Toast.makeText(con, text.toString(), Toast.LENGTH_SHORT).show();
    }

    public static void show(Context con, int text) {
        Toast.makeText(con, text, Toast.LENGTH_SHORT).show();
    }

    /**
     * sleep thread
     *
     * @param time
     *            time
     */
    public static void sleep(long time) {
        if (time <= 0)
            return;
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            // e.printStackTrace();
        }
    }

}
