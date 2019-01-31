package com.woodie.socketlib;

import android.content.Context;
import android.os.Handler;
import com.orhanobut.logger.Logger;
import com.woodie.bean.SocketAPI;
import com.xuhao.didi.core.iocore.interfaces.IPulseSendable;
import com.xuhao.didi.core.iocore.interfaces.ISendable;
import com.xuhao.didi.core.pojo.OriginalData;
import com.xuhao.didi.core.protocol.CustomIReaderProtocol;
import com.xuhao.didi.socket.client.impl.client.action.ActionDispatcher;
import com.xuhao.didi.socket.client.sdk.OkSocket;
import com.xuhao.didi.socket.client.sdk.client.ConnectionInfo;
import com.xuhao.didi.socket.client.sdk.client.OkSocketOptions;
import com.xuhao.didi.socket.client.sdk.client.action.SocketActionAdapter;
import com.xuhao.didi.socket.client.sdk.client.connection.IConnectionManager;
import com.xuhao.didi.socket.client.sdk.client.connection.NoneReconnect;
import java.nio.ByteOrder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * ProjectName:    SocketLib
 * Package:        com.woodie.socketlib
 * ClassName:      SocketTool
 * Description:
 * Author:         woodie
 * CreateDate:     2019/1/16 10:55
 * UpdateUser:     更新者
 * UpdateDate:     2019/1/16 10:55
 * UpdateRemark:   更新内容
 * Version:        1.0
 */
public class SocketTool {

    private Context mContext;
    private String  mIP;
    private int     mPort;
    private int     mPackageHead;
    private int     mPackageEnd;
    private IConnectionManager mManager;
    private OkSocketOptions mOkOptions;
    private ConnectionInfo mInfo;
    private boolean mStickyPacketFlag = false;//是否粘包标志位
    private List<Byte> mStickyPacketData =new ArrayList<>(); //粘包数据存放

    public void getInstance(Context context){
        this.mContext = context;
    }

    public void creatSocket(String ip,int port,int packageHead,int packageEnd) {
        this.mIP = ip;
        this.mPort = port;
        this.mPackageHead = packageHead;
        this.mPackageEnd = packageEnd;
        if (mManager == null || !mManager.isConnect()) {
            initManager();
            mManager.connect();
        }
    }

    private void initManager() {
        final Handler handler = new Handler();
        mInfo = new ConnectionInfo(this.mIP, this.mPort);
        mOkOptions = new OkSocketOptions.Builder()
                .setReconnectionManager(new NoneReconnect())
                .setConnectTimeoutSecond(10)
                .setWritePackageBytes(1024*6)
                .setReadPackageBytes(1024*6)
                .setReaderProtocol(new CustomIReaderProtocol() {
                    @Override
                    public int getHeaderLength() {
                        return 1;
                    }

                    @Override
                    public int getBodyLength(byte[] header, ByteOrder byteOrder) {
                        return header.length;
                    }
                })
                .setCallbackThreadModeToken(new OkSocketOptions.ThreadModeToken() {
                    @Override
                    public void handleCallbackEvent(ActionDispatcher.ActionRunnable runnable) {
                        handler.post(runnable);
                    }
                }).build();
        mManager = OkSocket.open(mInfo).option(mOkOptions);
        mManager.registerReceiver(adapter);
    }

    private SocketActionAdapter adapter = new SocketActionAdapter() {

        @Override
        public void onSocketConnectionSuccess(ConnectionInfo info, String action) {
            Logger.d("已连接 IP："+info.getIp()+"  port:"+info.getPort()+"  action:"+action);
        }

        @Override
        public void onSocketDisconnection(ConnectionInfo info, String action, Exception e) {
            if (e != null) {
                Logger.d("异常断开(Disconnected with exception):" + e.getMessage());
            } else {
                Logger.d("正常断开(Disconnect Manually)");
            }
        }

        @Override
        public void onSocketConnectionFailed(ConnectionInfo info, String action, Exception e) {
            Logger.d("连接失败(Connecting Failed)");
        }

        @Override
        public void onSocketReadResponse(ConnectionInfo info, String action, OriginalData data) {
            List<String> message = receiveMsg(data.getBodyBytes());
            Logger.d("buf buf buf " + message);
//            for(String msg : message){
//                Resolve(msg);
//            }


//            String str = new String(data.getBodyBytes(), Charset.forName("utf-8"));
//            Logger.d("Str " + str);
//            String testDataSrc = "\u0002{\n" +
//                    "\t\"type\":\t\"update\",\n" +
//                    "\t\"command\":\t\"sensor\",\n" +
//                    "\t\"argument\":\t{\n" +
//                    "\t\t\"utc\":\t601091392,\n" +
//                    "\t\t\"ieee\":\t\"5F9F221FC9435000\",\n" +
//                    "\t\t\"ep\":\t1,\n" +
//                    "\t\t\"zoneType\":\t21,\n" +
//                    "\t\t\"name\":\t\"Sensor-229F5F\",\n" +
//                    "\t\t\"status\":\t33\n" +
//                    "\t}\n" +
//                    "}";
//            byte[] srcdata = testDataSrc.getBytes();
//            byte[] testData = new byte[srcdata.length + 10];
//            for(int i = 0 ;i< testData.length;){
//                if(i == 5){
//                    for(int m = 0;m< srcdata.length;m++){
//                        testData[i] = srcdata[m];
//                        i++;
//                    }
//                }
//                testData[i] = 0x02;
//                i++;
//            }
//            Logger.d("test test test" + receiveMsg(testData));
        }

        @Override
        public void onSocketWriteResponse(ConnectionInfo info, String action, ISendable data) {
            String str = new String(data.parse(), Charset.forName("utf-8"));
            Logger.d(str);
        }

        @Override
        public void onPulseSend(ConnectionInfo info, IPulseSendable data) {
            String str = new String(data.parse(), Charset.forName("utf-8"));
            Logger.d(str);
        }
    };

    //接收
    private List<String> receiveMsg(byte[] datas) {
        List<String> receiveJSONData = new ArrayList<>();

        //判断之前是否有粘包,把当前收到的包拼接之前粘包部分数据
        if(mStickyPacketFlag & mStickyPacketData !=null & mStickyPacketData.size() != 0){
            for (byte data : datas) {
                mStickyPacketData.add(data);
            }
            datas = new byte[mStickyPacketData.size()];
            for(int ii = 0; ii<mStickyPacketData.size(); ii++){
                datas[ii] = mStickyPacketData.get(ii);
            }
        }

        if (datas.length==0){
//            Logger.d("receiveMsg == null " +byteArrayToStr(datas));
            return receiveJSONData;
        }else{
//            Logger.d("receiveMsg == " + byteArrayToStr(datas));
        }
        boolean flag = false; //true收到头，false收到尾
        List<Byte> dataBuf =new ArrayList<>(); //用于缓存收到的值
        for(int i = 0; i < datas.length; i++){
            if(datas[i] == mPackageHead && !flag){
                flag = true;
                dataBuf = new ArrayList<>();
                mStickyPacketData = new ArrayList<>();
            }else{
                if(datas[i] == mPackageEnd ||
                        (datas[i] == mPackageHead && dataBuf.size() > 0) ){
                    flag = false;
                    if(i < datas.length-1){
                        mStickyPacketFlag = true;
                        if(mStickyPacketData != null){
                            mStickyPacketData.clear();
                            mStickyPacketData = null;
                        }
                        mStickyPacketData = new ArrayList<>();
                        for(int m = i+1,n=0; m< datas.length;m++,n++){
                            mStickyPacketData.add(datas[m]);
                        }
                    }else {
                        mStickyPacketFlag = false;
                    }
                    byte[] bufdata = new byte[dataBuf.size()];
                    for(int l = 0 ;l< dataBuf.size();l++){
                        bufdata[l] = dataBuf.get(l);
                    }
                    if(bufdata.length > 0) {
                        receiveJSONData.add(byteArrayToStr(bufdata));
                    }
                }else if(datas[i] == mPackageHead &&
                        dataBuf.size() == 0){
                    dataBuf = new ArrayList<>();
                    mStickyPacketData = new ArrayList<>();
                }else{
                    dataBuf.add(datas[i]);
                }
            }
        }
        return receiveJSONData;
    }

    public void sendData(String content){
        if (mManager == null) {
            return;
        }
        if (!mManager.isConnect()) {
//            Toast.makeText(getApplicationContext(), "Unconnected", LENGTH_SHORT).show();
            Logger.v("Unconnected");
        } else {
            MsgDataBean msgDataBean = new MsgDataBean(content);
            mManager.send(msgDataBean);
        }
    }

    private class MsgDataBean implements ISendable {
        private String content = "";

        private MsgDataBean(String content) {
            this.content = content;
        }

        @Override
        public byte[] parse() {
            return content.getBytes(Charset.defaultCharset());
        }
    }

    private static String byteArrayToStr(byte[] byteArray) {
        if (byteArray == null) {
            return null;
        }
        return new String(byteArray, Charset.forName("utf-8"));
    }
}
