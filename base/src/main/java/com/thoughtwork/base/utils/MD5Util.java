package com.thoughtwork.base.utils;

import android.util.Base64;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {

    public static String getMD5Str(String str) {
        if (str == null || str.equalsIgnoreCase(""))
            return "";
        MessageDigest messageDigest = null;

        try {
            messageDigest = MessageDigest.getInstance("MD5");

            messageDigest.reset();

            messageDigest.update(str.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            System.out.println("NoSuchAlgorithmException caught!");
            System.exit(-1);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        byte[] byteArray = messageDigest.digest();

        StringBuffer md5StrBuff = new StringBuffer();

        for (int i = 0; i < byteArray.length; i++) {
            if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
                md5StrBuff.append("0").append(
                        Integer.toHexString(0xFF & byteArray[i]));
            else
                md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
        }

        return md5StrBuff.toString();
    }



    public static String MD5(String str)
    {
        MessageDigest md5 = null;
        try
        {
            md5 = MessageDigest.getInstance("MD5");
        }catch(Exception e)
        {
            e.printStackTrace();
            return "";
        }

        char[] charArray = str.toCharArray();
        byte[] byteArray = new byte[charArray.length];

        for(int i = 0; i < charArray.length; i++)
        {
            byteArray[i] = (byte)charArray[i];
        }
        byte[] md5Bytes = md5.digest(byteArray);

        StringBuffer hexValue = new StringBuffer();
        for( int i = 0; i < md5Bytes.length; i++)
        {
            int val = ((int)md5Bytes[i])&0xff;
            if(val < 16)
            {
                hexValue.append("0");
            }
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();
    }




    /**
     * ???????????????????????????MD5????????????
     *
     * @param origin
     * @return ?????????????????????
     */
    public static String MD5Encode(String psw) {
        return MD5Encode(psw, "UTF8");
        /*
         * MessageDigest messagedigest = null; try { messagedigest =
         * MessageDigest.getInstance("MD5");
         * messagedigest.update(psw.getBytes("UTF8")); byte[] abyte0 =
         * messagedigest.digest();
         *
         * String password = Base64.encodeBase64String(abyte0); // return
         * password; return password; } catch (Exception e) { throw new
         * RuntimeException("????????????????????????!", e); }
         */
    }

    /**
     * ???????????????????????????MD5????????????
     *
     * @param origin
     * @return ?????????????????????
     */
    public static String MD5EncodeGBK(String psw) {
        return MD5Encode(psw, "GBK");
        /*
         * MessageDigest messagedigest = null; try { messagedigest =
         * MessageDigest.getInstance("MD5");
         * messagedigest.update(psw.getBytes("UTF8")); byte[] abyte0 =
         * messagedigest.digest();
         *
         * String password = Base64.encodeBase64String(abyte0); // return
         * password; return password; } catch (Exception e) { throw new
         * RuntimeException("????????????????????????!", e); }
         */
    }

    /**
     * ???????????????????????????MD5????????????,????????????????????????????????????????????????charset.
     *
     * @param psw
     *            ??????????????????.
     * @param charset
     *            ?????????.
     * @return ???????????????????????????.
     */
    public static String MD5Encode(String psw, String charset) {
        MessageDigest messagedigest = null;
        try {
            messagedigest = MessageDigest.getInstance("MD5"); // ??????????????????
            messagedigest.update(psw.getBytes(charset)); // ???????????????????????????????????????
            byte[] abyte0 = messagedigest.digest(); // ?????????????????????

//            String password = new String(Base64.encodeBase64(abyte0));
            return "";
        } catch (Exception e) {
            throw new RuntimeException("????????????????????????!", e);
        }
    }

    public static void main(String[] args) {
        System.out.println(MD5Encode("YTO315017"));

    }

//	/**
//	 * BASE64??????
//	 * 
//	 * @param key
//	 * @return
//	 * @throws Exception
//	 */
//	public static String encryptBASE64(byte[] key) throws Exception {
//		return (new BASE64Encoder()).encodeBuffer(key);
//	}
//
//	/**
//	 * BASE64??????
//	 * 
//	 * @param key
//	 * @return
//	 * @throws Exception
//	 */
//	public static byte[] decryptBASE64(String key) throws Exception {
//		return (new BASE64Decoder()).decodeBuffer(key);
//	}
    /**
     * ??????MD5??????
     * @param s
     * @return
     */
    public static String MD5Encryption(String s) {
        char hexDigits[]={'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
        try {
            byte[] btInput = s.getBytes("UTF-8");
            // ??????MD5??????????????? MessageDigest ??????
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // ?????????????????????????????????
            mdInst.update(btInput);
            // ????????????
            byte[] md = mdInst.digest();
            // ????????????????????????????????????????????????
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


}
