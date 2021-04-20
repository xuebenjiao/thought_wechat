/*
 * Copyright (C) 2012 www.amsoft.cn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.thoughtwork.base.utils;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.TextAppearanceSpan;

import com.xbj.base.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


// TODO: Auto-generated Javadoc

/**
 * 名称：AbStrUtil.java 
 * 描述：字符串处理类.
 *
 * @author HQR
 * @version v1.0
 * @date：
 */
public class StrUtil {

    /**
     * 描述：将null转化为“”.
     *
     * @param str 指定的字符串
     * @return 字符串的String类型
     */
    public static String parseEmpty(String str) {
        if(str==null || "null".equals(str.trim())){
            str = "";
        }
        return str.trim();
    }

    /**
     * 描述：判断一个字符串是否为null或空值.
     *
     * @param str 指定的字符串
     * @return true or false
     */
    public static boolean isEmpty(String str) {
        return str == null || str.trim().length() == 0;
    }

    /**
     * 获取字符串中文字符的长度（每个中文算2个字符）.
     *
     * @param str 指定的字符串
     * @return 中文字符的长度
     */
    public static int chineseLength(String str) {
        int valueLength = 0;
        String chinese = "[\u0391-\uFFE5]";
        /* 获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1 */
        if(!isEmpty(str)){
            for (int i = 0; i < str.length(); i++) {
                /* 获取一个字符 */
                String temp = str.substring(i, i + 1);
                /* 判断是否为中文字符 */
                if (temp.matches(chinese)) {
                    valueLength += 2;
                }
            }
        }
        return valueLength;
    }

    /**
     * 描述：获取字符串的长度.
     *
     * @param str 指定的字符串
     * @return  字符串的长度（中文字符计2个）
     */
    public static int strLength(String str) {
        int valueLength = 0;
        String chinese = "[\u0391-\uFFE5]";
        if(!isEmpty(str)){
            //获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1
            for (int i = 0; i < str.length(); i++) {
                //获取一个字符
                String temp = str.substring(i, i + 1);
                //判断是否为中文字符
                if (temp.matches(chinese)) {
                    //中文字符长度为2
                    valueLength += 2;
                } else {
                    //其他字符长度为1
                    valueLength += 1;
                }
            }
        }
        return valueLength;
    }

    /**
     * 描述：获取指定长度的字符所在位置.
     *
     * @param str 指定的字符串
     * @param maxL 要取到的长度（字符长度，中文字符计2个）
     * @return 字符的所在位置
     */
    public static int subStringLength(String str,int maxL) {
        int currentIndex = 0;
        int valueLength = 0;
        String chinese = "[\u0391-\uFFE5]";
        //获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1
        for (int i = 0; i < str.length(); i++) {
            //获取一个字符
            String temp = str.substring(i, i + 1);
            //判断是否为中文字符
            if (temp.matches(chinese)) {
                //中文字符长度为2
                valueLength += 2;
            } else {
                //其他字符长度为1
                valueLength += 1;
            }
            if(valueLength >= maxL){
                currentIndex = i;
                break;
            }
        }
        return currentIndex;
    }

    /**
     * 描述：手机号格式验证.
     *
     * @param str 指定的手机号码字符串
     * @return 是否为手机号码格式:是为true，否则false
     */
    public static Boolean isMobileNo(String str) {
        Boolean isMobileNo = false;
        try {
            //Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
            Pattern p = Pattern.compile("[1][3578]\\d{9}");
            Matcher m = p.matcher(str);
            isMobileNo = m.matches();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isMobileNo;
    }

    /**
     * 描述：是否只是字母和数字.
     *
     * @param str 指定的字符串
     * @return 是否只是字母和数字:是为true，否则false
     */
    public static Boolean isNumberLetter(String str) {
        Boolean isNoLetter = false;
        String expr = "^[A-Za-z0-9]+$";
        if (str.matches(expr)) {
            isNoLetter = true;
        }
        return isNoLetter;
    }

    /**
     * 描述：是否只是字母和数字.
     *
     * @param str 指定的字符串
     * @return 是否只是字母和数字:是为true，否则false
     */
    public static Boolean isNumberFoalt(String str) {
        Boolean isNoLetter = false;
        String expr = "^([1-9][0-9]*(,[0-9]{3})*(.[0-9]+)?)$";
        if (str.matches(expr)) {
            isNoLetter = true;
        }
        return isNoLetter;
    }



    /**
     * 描述：是否只是数字.
     *
     * @param str 指定的字符串
     * @return 是否只是数字:是为true，否则false
     */
    public static Boolean isNumber(String str) {
        Boolean isNumber = false;
        String expr = "^[0-9]+$";
        if (str.matches(expr)) {
            isNumber = true;
        }
        return isNumber;
    }

    /**
     * 描述：是否是邮箱.
     *
     * @param str 指定的字符串
     * @return 是否是邮箱:是为true，否则false
     */
    public static Boolean isEmail(String str) {
        Boolean isEmail = false;
        String expr = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        if (str.matches(expr)) {
            isEmail = true;
        }
        return isEmail;
    }

    /**
     * 描述：是否是中文.
     *
     * @param str 指定的字符串
     * @return  是否是中文:是为true，否则false
     */
    public static Boolean isChinese(String str) {
        Boolean isChinese = true;
        String chinese = "[\u0391-\uFFE5]";
        if(!isEmpty(str)){
            //获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1
            for (int i = 0; i < str.length(); i++) {
                //获取一个字符
                String temp = str.substring(i, i + 1);
                //判断是否为中文字符
                if (temp.matches(chinese)) {
                }else{
                    isChinese = false;
                }
            }
        }
        return isChinese;
    }

    /**
     * 描述：是否包含中文.
     *
     * @param str 指定的字符串
     * @return  是否包含中文:是为true，否则false
     */
    public static Boolean isContainChinese(String str) {
        Boolean isChinese = false;
        String chinese = "[\u0391-\uFFE5]";
        if(!isEmpty(str)){
            //获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1
            for (int i = 0; i < str.length(); i++) {
                //获取一个字符
                String temp = str.substring(i, i + 1);
                //判断是否为中文字符
                if (temp.matches(chinese)) {
                    isChinese = true;
                }else{

                }
            }
        }
        return isChinese;
    }

    /**
     * 描述：从输入流中获得String.
     *
     * @param is 输入流
     * @return 获得的String
     */
    public static String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }

            //最后一个\n删除
            if(sb.indexOf("\n")!=-1 && sb.lastIndexOf("\n") == sb.length()-1){
                sb.delete(sb.lastIndexOf("\n"), sb.lastIndexOf("\n")+1);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    /**
     * 描述：标准化日期时间类型的数据，不足两位的补0.
     *
     * @param dateTime 预格式的时间字符串，如:2012-3-2 12:2:20
     * @return String 格式化好的时间字符串，如:2012-03-20 12:02:20
     */
    public static String dateTimeFormat(String dateTime) {
        StringBuilder sb = new StringBuilder();
        try {
            if(isEmpty(dateTime)){
                return null;
            }
            String[] dateAndTime = dateTime.split(" ");
            if(dateAndTime.length>0){
                for(String str : dateAndTime){
                    if(str.indexOf("-")!=-1){
                        String[] date =  str.split("-");
                        for(int i=0;i<date.length;i++){
                            String str1 = date[i];
                            sb.append(strFormat2(str1));
                            if(i< date.length-1){
                                sb.append("-");
                            }
                        }
                    }else if(str.indexOf(":")!=-1){
                        sb.append(" ");
                        String[] date =  str.split(":");
                        for(int i=0;i<date.length;i++){
                            String str1 = date[i];
                            sb.append(strFormat2(str1));
                            if(i< date.length-1){
                                sb.append(":");
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return sb.toString();
    }

    /**
     * 描述：不足2个字符的在前面补“0”.
     *
     * @param str 指定的字符串
     * @return 至少2个字符的字符串
     */
    public static String strFormat2(String str) {
        try {
            if(str.length()<=1){
                str = "0"+str;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }

    /**
     * 描述：截取字符串到指定字节长度.
     *
     * @param str the str
     * @param length 指定字节长度
     * @return 截取后的字符串
     */
    public static String cutString(String str,int length){
        return cutString(str, length,"");
    }

    /**
     * 描述：截取字符串到指定字节长度.
     *
     * @param str 文本
     * @param length 字节长度
     * @param dot 省略符号
     * @return 截取后的字符串
     */
    public static String cutString(String str,int length,String dot){
        int strBLen = strlen(str,"GBK");
        if( strBLen <= length ){
            return str;
        }
        int temp = 0;
        StringBuffer sb = new StringBuffer(length);
        char[] ch = str.toCharArray();
        for ( char c : ch ) {
            sb.append( c );
            if ( c > 256 ) {
                temp += 2;
            } else {
                temp += 1;
            }
            if (temp >= length) {
                if( dot != null) {
                    sb.append( dot );
                }
                break;
            }
        }
        return sb.toString();
    }

    /**
     * 描述：截取字符串从第一个指定字符.
     *
     * @param str1 原文本
     * @param str2 指定字符
     * @param offset 偏移的索引
     * @return 截取后的字符串
     */
    public static String cutStringFromChar(String str1,String str2,int offset){
        if(isEmpty(str1)){
            return "";
        }
        int start = str1.indexOf(str2);
        if(start!=-1){
            if(str1.length()>start+offset){
                return str1.substring(start+offset);
            }
        }
        return "";
    }

    /**
     * 描述：获取字节长度.
     *
     * @param str 文本
     * @param charset 字符集（GBK）
     * @return the int
     */
    public static int strlen(String str,String charset){
        if(str==null||str.length()==0){
            return 0;
        }
        int length=0;
        try {
            length = str.getBytes(charset).length;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return length;
    }

    /**
     * 获取大小的描述.
     *
     * @param size 字节个数
     * @return  大小的描述
     */
    public static String getSizeDesc(long size) {
        String suffix = "B";
        if (size >= 1024){
            suffix = "K";
            size = size>>10;
            if (size >= 1024){
                suffix = "M";
                //size /= 1024;
                size = size>>10;
                if (size >= 1024){
                    suffix = "G";
                    size = size>>10;
                    //size /= 1024;
                }
            }
        }
        return size+suffix;
    }

    /**
     * 描述：ip地址转换为10进制数.
     *
     * @param ip the ip
     * @return the long
     */
    public static long ip2int(String ip){
        ip = ip.replace(".", ",");
        String[]items = ip.split(",");
        return Long.valueOf(items[0])<<24 |Long.valueOf(items[1])<<16 |Long.valueOf(items[2])<<8 |Long.valueOf(items[3]);
    }

    /**
     * The main method.
     *
     * @param args the arguments
     */
    public static void main(String[] args) {
        System.out.println(dateTimeFormat("2012-3-2 12:2:20"));
    }

    /**
     * 字符串特定字符变色
     * @param text 数据源
     * @param keyword 要变颜色的字符串
     * color_ff8040 要变的颜色  橙色color_ff8040
     * style_color_ff8040 也可以改变字体的size和其他的熟悉,自己设置
     */
    public static SpannableString matcherSearchText(Context context, String text, String keyword) {
        SpannableString ss = new SpannableString(text);
        Pattern pattern = Pattern.compile(keyword);
        Matcher matcher = pattern.matcher(ss);
        while (matcher.find()) {
            int start = matcher.start();
            int end = matcher.end();
            ss.setSpan(new TextAppearanceSpan(context, R.style.style_color_ff8040), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);//new ForegroundColorSpan(color)
        }
        return ss;
    }

    /**
     * 字符串特定字符变色
     * @param text 数据源
     * @param keyword 要变颜色的字符串
     * color_ff8040 要变的颜色  绿色color_15B214
     * style_color_ff8040 也可以改变字体的size和其他的熟悉,自己设置
     */
    public static SpannableString matcherText(Context context,String text, String keyword) {
        SpannableString ss = new SpannableString(text);
        Pattern pattern = Pattern.compile(keyword);
        Matcher matcher = pattern.matcher(ss);
        while (matcher.find()) {
            int start = matcher.start();
            int end = matcher.end();
            ss.setSpan(new TextAppearanceSpan(context, R.style.style_color_15B214), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);//new ForegroundColorSpan(color)
        }
        return ss;
    }

    public static SpannableString matcherMultipleText(Context mContext,String standardTemplate,String keywordAddress,String keywordBrand,String keywordList){
        SpannableString ss = new SpannableString(standardTemplate);
        Pattern pattern = Pattern.compile(keywordAddress);
        Matcher matcher = pattern.matcher(ss);
        while (matcher.find()) {
            int start = matcher.start();
            int end = matcher.end();
            ss.setSpan(new TextAppearanceSpan(mContext, com.xbj.base.R.style.style_color_ff8040), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);//new ForegroundColorSpan(color)
        }

        Pattern pattern2 = Pattern.compile(keywordBrand);
        Matcher matcher2 = pattern2.matcher(ss);
        while (matcher2.find()) {
            int start = matcher2.start();
            int end = matcher2.end();
            ss.setSpan(new TextAppearanceSpan(mContext, com.xbj.base.R.style.style_color_15B214), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);//new ForegroundColorSpan(color)
        }

        Pattern pattern3 = Pattern.compile(keywordList);
        Matcher matcher3 = pattern3.matcher(ss);
        while (matcher3.find()) {
            int start = matcher3.start();
            int end = matcher3.end();
            ss.setSpan(new TextAppearanceSpan(mContext, com.xbj.base.R.style.style_color_15B214), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);//new ForegroundColorSpan(color)
        }
        return ss;
    }

    public static SpannableString matcher(Context mContext,String keyword){
        SpannableString spanText = new SpannableString(keyword);
        //若需自定义TextAppearance，可以在系统样式上进行修改
        spanText.setSpan(new TextAppearanceSpan(mContext, R.style.style_color_ff8040),
                0, 4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spanText;
    }

    public static SpannableString matcherSix(Context mContext,String keyword){
        SpannableString spanText = new SpannableString(keyword);
        //若需自定义TextAppearance，可以在系统样式上进行修改
        spanText.setSpan(new TextAppearanceSpan(mContext, R.style.style_color_15B214),
                0, 6, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spanText;
    }

    /**
     * 获取2个特定字符串之间的内容
     * @param str 源字符串
     * @param strStart 起始字符串
     * @param strEnd 结束字符串
     * @return
     */
    public static String  getInsideString(String  str, String strStart, String strEnd ) {
        /* index 为负数 即表示该字符串中 没有该字符 */
        if ( str.indexOf(strStart) < 0 ){
            //return "字符串 :---->" + str + "<---- 中不存在 " + strStart + ", 无法截取目标字符串";
            return "";
        }
        if ( str.indexOf(strEnd) < 0 ){
            //return "字符串 :---->" + str + "<---- 中不存在 " + strEnd + ", 无法截取目标字符串";
            return "";
        }
        return str.substring(str.indexOf(strStart) + strStart.length(), str.indexOf(strEnd));
    }


}
