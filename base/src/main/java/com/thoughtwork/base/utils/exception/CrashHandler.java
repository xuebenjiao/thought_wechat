package com.thoughtwork.base.utils.exception;

import android.content.Context;

import com.thoughtwork.base.utils.LogUtils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.Thread.UncaughtExceptionHandler;

public class CrashHandler implements UncaughtExceptionHandler {

  private Context mContext;
  private static CrashHandler INSTANCE = new CrashHandler();
  private UncaughtExceptionHandler mDefaultHandler;

  private CrashHandler() {
  }

  public static CrashHandler getInstance() {
    return INSTANCE;
  }

  public void init(Context mContext) {
    this.mContext = mContext;
    mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
    Thread.setDefaultUncaughtExceptionHandler(this);
  }

  @Override
  public void uncaughtException(Thread thread, Throwable ex) {
    try {
      //保存日志
      LogUtils.writeLogtoFile("e", "Crash", obtainExceptionInfo(ex));
      LogUtils.i(obtainExceptionInfo(ex));
      LogUtils.e(obtainExceptionInfo(ex));

      ex.printStackTrace();
      if(mDefaultHandler != null){
        mDefaultHandler.uncaughtException(thread, ex);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    //退出应用
    android.os.Process.killProcess(android.os.Process.myPid());
  }

  private String obtainExceptionInfo(Throwable throwable) {
    StringWriter mStringWriter = new StringWriter();
    PrintWriter mPrintWriter = new PrintWriter(mStringWriter);
    throwable.printStackTrace(mPrintWriter);
    mPrintWriter.close();
    return mStringWriter.toString();
  }
}