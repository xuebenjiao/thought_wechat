package com.thoughtwork.comment.viewmodel;
import com.thoughtwork.base.BaseApplication;
import com.thoughtwork.base.activity.IBaseView;
import com.thoughtwork.base.model.SuperBaseModel;
import com.thoughtwork.base.utils.LogUtils;
import com.thoughtwork.base.viewmodel.MvvmBaseViewModel;
import com.thoughtwork.base.utils.LiveDataBus;
import com.xbj.comment.R;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Time :2021/1/24
 * Author:xbj
 * Description :
 */
public class NetworkTestViewModel extends MvvmBaseViewModel<IBaseView, SuperBaseModel>{
    private static final String TAG = "NetworkTestActivity";
    private ArrayList<CommonItemViewModel> mList = new ArrayList<>();
    private HashMap<String,CommonItemViewModel> modelHashMap = new HashMap<>();

    public NetworkTestViewModel() {
        startPingThread();
    }
    public void startPingThread(){
        mList.clear();
        modelHashMap.clear();
        String[] domain =    BaseApplication.getmContext().getResources().getStringArray(com.xbj.common.R.array.network_domain_array);
        if(domain != null && domain.length >0) {
            for (int i = 0;i< domain.length;i++) {
                collectListData(domain, i);
            }
        }
        String[] ip =    BaseApplication.getmContext().getResources().getStringArray(com.xbj.common.R.array.network_ip_array);
        if(ip != null && ip.length >0) {
            for (int i = 0;i< domain.length;i++) {
                collectListData(ip, i);
            }
        }
        LiveDataBus.getInstance().with(TAG,ArrayList.class).postValue(mList);
    }
    private void collectListData(String[] domain, int i) {
        if (i == 0) {
            //标题不开线程
            mList.add(new CommonItemViewModel(domain[i]));
        } else {
            startNetworkTest(i, domain[i]);
        }
    }
    private void  startNetworkTest(int i, String str) {
        CommonItemViewModel model = new CommonItemViewModel(i + "、" + getTitleStr(str), "测试中…");
        modelHashMap.put(str, model);
        mList.add(model);
        createAndStartThread(str);
    }

    private String  getTitleStr(String str) {
        StringBuilder stringBuilder = new StringBuilder();
        if ("101.37.150.123".equals(str)) {
            stringBuilder.append( "yjtgp-api.yto.net.cn:" + str);
        }
        else if("58.246.44.29".equals(str)){
            stringBuilder.append("ann.yto.net.cn:"+str);
        }
        else{
            stringBuilder.append(str);
        }
        return stringBuilder.toString();
    }
    private void createAndStartThread(final String str) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                long startTime = System.currentTimeMillis();
                boolean pingFlag = pingIpOrDomain(str);
                long entTime = System.currentTimeMillis() - startTime;
                LogUtils.i(TAG, "ping result:" + pingFlag + "，" + entTime);
                if( modelHashMap != null ) {
                    CommonItemViewModel model =  modelHashMap.get(str);
                    int index = mList.indexOf(model);
                    if(model != null){
                        model.setRightContent((pingFlag?"成功:":"失败:")+entTime+"ms");
                        model.setContentColor(pingFlag?BaseApplication.getmContext().getResources().getColor(R.color.color_5242FF):BaseApplication.getmContext().getResources().getColor(R.color.color_FF584F));
                    }
                }
            }
        }).start();
    }
    /**
     * 判断是否有外网连接（普通方法不能判断外网的网络是否连接，比如连接上局域网）
     * 不要在主线程使用，会阻塞线程
     */
    public   boolean  pingIpOrDomain(  String ipOrDomain ) {
        String result = null;
        try {
            // ping 的地址，可以换成任何一种可靠的外网
            Process p = Runtime.getRuntime().exec("ping -c 1 -w 100 " + ipOrDomain);// ping网址3次
            // 读取ping的内容，可以不加
            InputStream input = p.getInputStream();
            BufferedReader in = new BufferedReader(new InputStreamReader(input));
            StringBuffer stringBuffer = new StringBuffer();
            String content = "";
            while ((content = in.readLine()) != null) {
                stringBuffer.append(content);
            }
//            Log.d("------ping-----", "result content : " + stringBuffer.toString());
            // ping的状态
            int status = p.waitFor();
            if (status == 0) {
                result = "success";
                return true;
            } else {
                result = "failed";
            }
        } catch (IOException e) {
            result = "IOException";
        } catch (InterruptedException e) {
            result = "InterruptedException";
        } finally {
            LogUtils.i("----result---", "result = " + result);
        }
        return false;
    }

}
