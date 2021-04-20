package com.thoughtwork.base.utils;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.net.Uri;
import android.os.Build;

import com.xbj.base.R;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Time :2020/6/3
 * Author:xbj
 * Description :
 */
public class SoundPoolPlayer {
    //保存音效ID和对应的音效时长
    public Map<Integer, Integer> soundIdMap = new HashMap<>();
    private Context context;
    private SoundPool soundPool;
    // 能同时播放的最大声音数
    private static final int MAX_SREAMS = 1;
    // 单杀的声音
    public static int SCAN_SUCCESS;
    public static int SCAN_VERIFYING;
    public static int SCAN_DEEP;
    //(604, "通缉件"),
    public static int INTERCEPT;
    //(609, "已做派件"),
    public static int DISPATCHED;
    //(612, "已做签收"),
    public static int SIGNED;
    //    (6001, "未派件"),
    public static int NOT_DELIVERY;
    //(6002, "非当前网点运单"),
    public static int  NOT_CURRENT_ORG;

    //非法单号
    public static  int ILLEGAL_WAYBILLNO;
    //条码信息不正确
    public static  int INCORRECT_BARCODE;
    //已扫描
    public static  int ALREADY_SCANED;
    //重复扫描
    public static  int REPEAT_SCAN;
    //异常单号，请核查
    public static  int CHECK_ILLEGAL_BILL;
    public static  int WAYBILL_VERIFYING;
    public static  int WAYBILL_VERIFY_FAIL;

    public static  int YTO;
    public static  int ZTO;
    public static  int STO;
    public static  int YUNDA;
    public static  int BAISHI;
    public static  int TIANTIAN;
    public static  int DEBANG;
    public static  int UNKNOW;
    public static  int SCAN_PHONE;
    public static  int OCR_PHONE;
    public static  int COD;
    public static  int PREPAY;
    public static  int REFRESH;
    public static  int HAS_CHANGED_DELIVERY;

    public SoundPoolPlayer(Context context) {
        this(context, null);
    }

    public SoundPoolPlayer(Context context, SoundPool.OnLoadCompleteListener onLoadCompleteListener) {
        this.context = context;
        //sdk版本21是SoundPool 的一个分水岭
        if (Build.VERSION.SDK_INT >= 21) {
            SoundPool.Builder builder = new SoundPool.Builder();
            //传入最多播放音频数量,
            builder.setMaxStreams(1);
            //AudioAttributes是一个封装音频各种属性的方法
            AudioAttributes.Builder attrBuilder = new AudioAttributes.Builder();
            //设置音频流的合适的属性
            attrBuilder.setLegacyStreamType(AudioManager.STREAM_MUSIC);
            //加载一个AudioAttributes
            builder.setAudioAttributes(attrBuilder.build());
            soundPool = builder.build();
        } else {
            soundPool = new SoundPool(MAX_SREAMS, AudioManager.STREAM_MUSIC, 0);
        }
        SCAN_DEEP = soundPool.load(context, R.raw.scanbeep, 1);
        soundIdMap.put(SCAN_DEEP,getMp3Duration(context,R.raw.scanbeep));

        SCAN_SUCCESS = soundPool.load(context, R.raw.scan_success, 1);
        soundIdMap.put(SCAN_SUCCESS,getMp3Duration(context,R.raw.scan_success));

        INTERCEPT = soundPool.load(context, R.raw.intercept, 1);
        soundIdMap.put(INTERCEPT,getMp3Duration(context,R.raw.intercept));

        DISPATCHED = soundPool.load(context, R.raw.dispatched, 1);
        soundIdMap.put(DISPATCHED,getMp3Duration(context,R.raw.dispatched));

        SIGNED = soundPool.load(context, R.raw.signed, 1);
        soundIdMap.put(SIGNED,getMp3Duration(context,R.raw.signed));

        NOT_DELIVERY = soundPool.load(context, R.raw.not_delivery, 1);
        soundIdMap.put(NOT_DELIVERY,getMp3Duration(context,R.raw.not_delivery));

        NOT_CURRENT_ORG = soundPool.load(context, R.raw.not_current_outlet_waybil, 1);
        soundIdMap.put(NOT_CURRENT_ORG,getMp3Duration(context,R.raw.not_current_outlet_waybil));

        ILLEGAL_WAYBILLNO = soundPool.load(context, R.raw.illegal_waybillno, 1);
        soundIdMap.put(ILLEGAL_WAYBILLNO,getMp3Duration(context,R.raw.illegal_waybillno));

        INCORRECT_BARCODE = soundPool.load(context, R.raw.incorrect_barcode, 1);
        soundIdMap.put(INCORRECT_BARCODE,getMp3Duration(context,R.raw.incorrect_barcode));

        ALREADY_SCANED = soundPool.load(context, R.raw.already_scaned, 1);
        soundIdMap.put(ALREADY_SCANED,getMp3Duration(context,R.raw.already_scaned));

        CHECK_ILLEGAL_BILL = soundPool.load(context, R.raw.check_illegal_bill, 1);
        soundIdMap.put(CHECK_ILLEGAL_BILL,getMp3Duration(context,R.raw.check_illegal_bill));

        YTO = soundPool.load(context, R.raw.yto1, 1);
        soundIdMap.put(YTO,getMp3Duration(context,R.raw.yto1));

        ZTO = soundPool.load(context, R.raw.zto1, 1);
        soundIdMap.put(ZTO,getMp3Duration(context,R.raw.zto1));

        STO = soundPool.load(context, R.raw.sto1, 1);
        soundIdMap.put(STO,getMp3Duration(context,R.raw.sto1));

        YUNDA = soundPool.load(context, R.raw.yunda1, 1);
        soundIdMap.put(YUNDA,getMp3Duration(context,R.raw.yunda1));

        DEBANG = soundPool.load(context, R.raw.debang1, 1);
        soundIdMap.put(DEBANG,getMp3Duration(context,R.raw.debang1));

        TIANTIAN = soundPool.load(context, R.raw.tiantian1, 1);
        soundIdMap.put(TIANTIAN,getMp3Duration(context,R.raw.tiantian1));

        BAISHI = soundPool.load(context, R.raw.baishi1, 1);
        soundIdMap.put(BAISHI,getMp3Duration(context,R.raw.baishi1));

        UNKNOW = soundPool.load(context, R.raw.unkonw, 1);
        soundIdMap.put(UNKNOW,getMp3Duration(context,R.raw.unkonw));

        SCAN_PHONE = soundPool.load(context, R.raw.scan_phone, 1);
        soundIdMap.put(SCAN_PHONE,getMp3Duration(context,R.raw.scan_phone));

        OCR_PHONE = soundPool.load(context, R.raw.ocr_success, 1);
        soundIdMap.put(OCR_PHONE,getMp3Duration(context,R.raw.ocr_success));

        COD = soundPool.load(context, R.raw.cod, 1);
        soundIdMap.put(COD,getMp3Duration(context,R.raw.cod));

        PREPAY = soundPool.load(context, R.raw.prepay, 1);
        soundIdMap.put(PREPAY,getMp3Duration(context,R.raw.prepay));

        REFRESH = soundPool.load(context, R.raw.refresh, 1);
        soundIdMap.put(REFRESH,getMp3Duration(context,R.raw.refresh));

        HAS_CHANGED_DELIVERY = soundPool.load(context, R.raw.has_changed_delivery, 1);
        soundIdMap.put(HAS_CHANGED_DELIVERY,getMp3Duration(context,R.raw.has_changed_delivery));

        WAYBILL_VERIFYING = soundPool.load(context, R.raw.verifying, 1);
        soundIdMap.put(WAYBILL_VERIFYING,getMp3Duration(context,R.raw.verifying)) ;

        WAYBILL_VERIFY_FAIL = soundPool.load(context, R.raw.verigy_fail, 1);
        soundIdMap.put(WAYBILL_VERIFY_FAIL,getMp3Duration(context,R.raw.verigy_fail));

        SCAN_VERIFYING = soundPool.load(context, R.raw.scan_verifying, 1);
        soundIdMap.put(SCAN_VERIFYING,getMp3Duration(context,R.raw.scan_verifying));

        REPEAT_SCAN = soundPool.load(context, R.raw.repeat_scan, 1);
        soundIdMap.put(REPEAT_SCAN,getMp3Duration(context,R.raw.repeat_scan));


      /*  YTO_SCAN_SUCCESS = soundPool.load(context, R.raw.yto_scan_success, 1);
        soundIdMap.put(YTO_SCAN_SUCCESS,getMp3Duration(context,R.raw.yto_scan_success));

        ZTO_SCAN_SUCCESS = soundPool.load(context, R.raw.zto_scan_success, 1);
        soundIdMap.put(ZTO_SCAN_SUCCESS,getMp3Duration(context,R.raw.zto_scan_success));

        STO_SCAN_SUCCESS = soundPool.load(context, R.raw.sto_scan_success, 1);
        soundIdMap.put(STO_SCAN_SUCCESS,getMp3Duration(context,R.raw.sto_scan_success));

        YUNDA_SCAN_SUCCESS = soundPool.load(context, R.raw.yunda_scan_success, 1);
        soundIdMap.put(YUNDA_SCAN_SUCCESS,getMp3Duration(context,R.raw.yunda_scan_success));

        BAISHI_SCAN_SUCCESS = soundPool.load(context, R.raw.baishi_scan_success, 1);
        soundIdMap.put(BAISHI_SCAN_SUCCESS,getMp3Duration(context,R.raw.baishi_scan_success));

        TIANTIAN_SCAN_SUCCESS = soundPool.load(context, R.raw.tiantian_scan_success, 1);
        soundIdMap.put(TIANTIAN_SCAN_SUCCESS,getMp3Duration(context,R.raw.tiantian_scan_success));

        DEBANG_SCAN_SUCCESS = soundPool.load(context, R.raw.debang_scan_success, 1);
        soundIdMap.put(DEBANG_SCAN_SUCCESS,getMp3Duration(context,R.raw.debang_scan_success));*/


        soundPool.setOnLoadCompleteListener(onLoadCompleteListener);
    }


    /**
     * 获取音频文件的时长
     */
    private int getMp3Duration(Context context, int rawId) {
        try {
            Uri uri = Uri.parse("android.resource://" + context.getPackageName() + "/" + rawId);
            MediaPlayer mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource(context, uri);
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.prepare();
            return mediaPlayer.getDuration();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }
    // resId为放在raw文件夹下的音频文件
    public void load(int resId) {
        soundPool.load(context, resId, 1);
    }

    // 参数为SoundPool.load()方法返回的soundID
    public void unload(int soundID) {
        soundPool.unload(soundID);
    }

    // 播放扫描成功
    public void playScanSuccess() {
        play(SCAN_SUCCESS);
    }

    // 播放，soundID参数为SoundPool.load()方法返回的值
    public int play(int soundID, float leftVolume, float rightVolume, int priority, int loop, float rate) {
        return soundPool.play(soundID, leftVolume, rightVolume, priority, loop, rate);
    }

    // 默认参数的播放
    public int play(int soundID) {
        return play(soundID, 1, 1, 0, 0, 1);
    }

    // 循环播放
    public int loopPlay(int soundID) {
        return play(soundID, 1, 1, 0, -1, 1);
    }

    // 停止播放
    public void stop(int streamId) {
        soundPool.stop(streamId);
    }

    // 暂停播放
    public void pausea(int streamId) {
        soundPool.pause(streamId);
    }

    // 继续播放
    public void resume(int streamId) {
        soundPool.resume(streamId);
    }

    // 暂停所有播放
    public void pauseAll() {
        soundPool.autoPause();
    }

    // 继续所有播放
    public void resumeAll() {
        soundPool.autoResume();
    }

    // 释放
    public void release() {
        if (soundPool != null) {
            soundPool.release();
            soundPool = null;
        }
    }

}
