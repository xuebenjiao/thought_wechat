package com.thoughtwork.base.utils.exception;

import android.os.Build;
import android.os.Environment;

import java.io.File;

public class F {
    /**
     * 程序在存储器上的文件夹名称
     */
    private static String AppFileName = "comment";


    /**
     * 保存日志文件的文件夹
     */
    private static String Logcat="log";

    /**
     * 保存图片的文件夹名称
     */
    private static String ImageFileName = "Image";

    /**
     * 保存html数据库缓存的文件夹名称
     */
    private static String DatabaseFileName = "Database";

    /**
     * 保存下载文件的文件夹名称
     */
    private static String DownloadFileName = "File";

    /**
     * 日志的路径
     */
    private static String logPath=null;
    /**
     * 程序在存储器上的存储路径
     */
    private static String appPath = null;

    /**
     * 图片在存储器上的存储路径
     */
    private static String imagePath = null;

    /**
     * 保存html数据库缓存的存储路径
     */
    private static String databasePath = null;

    /**
     * 用户个人文集在存储器上的存储路径
     */
    private static String userPath = null;

    /**
     * 下载的文件存储路径
     */
    private static String filePath = null;

    public static void initFoler() {
        if (isSdcardExist()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
                appPath = getCacheDir();
            }
            else {
                appPath = Environment.getExternalStorageDirectory().getPath()
                        + File.separator + AppFileName + File.separator;
            }
            setUpdateDir(appPath);

        } else {
            appPath = getCacheDir();
        }
        imagePath = appPath + ImageFileName + File.separator;
        logPath=appPath+Logcat+ File.separator;
    }

    public static String getCacheDir() {
        return C.context.getCacheDir().getPath() + File.separator + AppFileName
                + File.separator;
    }

    public static boolean isSdcardExist() {
        return FolderHelper.isSdcardExist();
    }

    public static String getAppPath() {
        if (appPath == null)
            initFoler();
        FolderHelper.checkFolder(appPath);
        return appPath;
    }

    public static String getLogPath()
    {
        if (logPath == null)
            initFoler();
        FolderHelper.checkFolder(logPath);
        System.out.println("logpath="+logPath);
        return logPath;
    }
    public static String getImagePath() {
        if (imagePath == null)
            initFoler();
        FolderHelper.checkFolder(imagePath);
        return imagePath;
    }

    public static String getDatabasePath() {
        if (getUserPath() == null)
            return null;
        if (databasePath == null)
            databasePath = getUserPath() + DatabaseFileName + File.separator;
        FolderHelper.checkFolder(databasePath);
        return databasePath;
    }

    public static String getFilePath() {
        if (getUserPath() == null)
            return null;
        // if (filePath == null)
        filePath = getUserPath() + DownloadFileName + File.separator;
        FolderHelper.checkFolder(filePath);
        return filePath;
    }

    public static String getUserPath() {
        return userPath;
    }

    public static void setUserPath(String userPath) {
        F.userPath = userPath;
    }

    public static void clearCacheDir() {
        FolderHelper.deleteFile(getCacheDir());
    }


    public static void deleteFile(String path) {
        FolderHelper.deleteFile(path);
    }

    public static void clear() {
        FolderHelper.deleteFile(getImagePath());

        getImagePath();
        if (getUserPath() != null)
            FolderHelper.deleteFile(getUserPath(), getFilePath());
        checkFolder(getUserPath());
        getDatabasePath();
    }

    private static String UPDATE_DIR_PATH = Environment
            .getExternalStorageDirectory().getPath()
            + File.separator
            + "Download";

    /**
     * 更新程序保存的文件夹地址
     */
    public static String getUpdateDir() {
        if (checkFolder(UPDATE_DIR_PATH)) {
            return UPDATE_DIR_PATH;
        } else {
            return Environment.getExternalStorageDirectory().getPath();
        }
    }

    public static void setUpdateDir(String updateDir) {
        UPDATE_DIR_PATH = updateDir;
    }

    private static boolean checkFolder(String path) {
        try {
            File dir = new File(path);
            if (dir.exists()) {
                if (dir.isDirectory()) {
                    return true;
                } else {
                    dir.delete();
                    return checkFolder(path);
                }
            } else {
                if (checkFolder(dir.getParent()))
                    return dir.mkdirs();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
