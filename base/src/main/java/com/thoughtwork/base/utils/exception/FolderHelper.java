package com.thoughtwork.base.utils.exception;

import android.os.Environment;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * 文件夹操作helper类
 *
 * @author Cayte
 * @email xusw@dne.com.cn
 * @date 2012-1-1 上午00:00:00
 *
 */
public class FolderHelper {

    /** Check whether the sdcard is existed. */
    public static boolean isSdcardExist() {
        if (Environment.MEDIA_MOUNTED.equals(Environment
                .getExternalStorageState()))
            return true;
        return false;
    }

    /**
     * Create a folder in SD card for saving some log files.
     */
    public static void createFolder(String... paths) {
        for (String path : paths) {
            checkFolder(path);
        }
    }

    /**
     * Create a folder in SD card for saving some log files.
     */
    public static boolean checkFolder(String path) {
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

    /** 删除文件或文件夹 */
    public static boolean deleteFile(String path) {
        try {
            // 判空, 保证路径是有效的
            if (path != null && !path.equals("")) {
                File file = new File(path);
                // 判断文件是否存在
                if (file.exists()) {
                    // 如果是目录则递归计算其文件个数，如果是文件则直接返回1
                    if (file.isFile()) {
                        file.delete();
                    } else {
                        // 获取文件大小
                        File[] fl = file.listFiles();
                        if (fl != null && fl.length > 0) {
                            for (File f : fl) {
                                deleteFile(f.getAbsolutePath());
                            }
                        }
                        if (file != null) {
                            return file.delete();
                        }
                    }
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return false;
    }

    /** 删除文件或文件夹,不删除out path文件 */
    public static boolean deleteFile(String path, String... outPath) {
        try {
            List<String> outs = new ArrayList<String>();
            if (outPath != null)
                for (int i = 0; i < outPath.length; i++) {
                    outs.add(outPath[i]);
                }
            // 判空, 保证路径是有效的
            if (path != null && !path.equals("")) {
                File file = new File(path);
                // 判断文件是否存在
                if (file.exists() && !file.getName().equals("File")) {// TODO:
                    // if (!outs.contains(file.getAbsolutePath())) {//
                    // 如果是目录则递归计算其文件个数，如果是文件则直接返回1
                    if (file.isFile()) {
                        file.delete();
                    } else {
                        // 获取文件大小
                        File[] fl = file.listFiles();
                        if (fl != null && fl.length > 0) {
                            for (File f : fl) {
                                deleteFile(f.getAbsolutePath(), outPath);
                            }
                        }
                        if (file != null) {
                            return file.delete();
                        }
                    }
                    // }
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return false;
    }

}
