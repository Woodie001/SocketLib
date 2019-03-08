package com.woodie.socketlib;

import android.content.Context;
import android.os.Handler;
import com.blankj.utilcode.util.ToastUtils;
import com.orhanobut.logger.Logger;
import com.xuhao.didi.core.iocore.interfaces.IPulseSendable;
import com.xuhao.didi.core.iocore.interfaces.ISendable;
import com.xuhao.didi.core.pojo.OriginalData;
import com.xuhao.didi.core.protocol.CustomIReaderProtocol;
import com.xuhao.didi.socket.client.impl.client.action.ActionDispatcher;
import com.xuhao.didi.socket.client.sdk.OkSocket;
import com.xuhao.didi.socket.client.sdk.client.ConnectionInfo;
import com.xuhao.didi.socket.client.sdk.client.OkSocketOptions;
import com.xuhao.didi.socket.client.sdk.client.OkSocketSSLConfig;
import com.xuhao.didi.socket.client.sdk.client.action.SocketActionAdapter;
import com.xuhao.didi.socket.client.sdk.client.connection.DefaultReconnectManager;
import com.xuhao.didi.socket.client.sdk.client.connection.IConnectionManager;
import org.greenrobot.eventbus.EventBus;
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
    private boolean mOpenWriteDateToastFlag = false; //是否开启写数据土司打印
    private boolean mOpenReadDateToastFalg = false; //是否开启发数据土司打印

    public void getInstance(Context context){
        this.mContext = context;
    }

    public void setOpenWriteDateToast(boolean flag) {
        this.mOpenWriteDateToastFlag = flag;
    }

    public void setOpenReadDateToast(boolean flag){
        this.mOpenReadDateToastFalg = flag;
    }

    public void creatSocket(String ip,int port) {
        this.mIP = ip;
        this.mPort = port;
        this.mPackageHead = 0x02;
        this.mPackageEnd = 0x03;
        initManager();
        if (mManager == null || !mManager.isConnect()) {
            mManager.connect();
        }
    }

    public void creatSSLSocket(String ip,int port) {
        this.mIP = ip;
        this.mPort = port;
        this.mPackageHead = 0x02;
        this.mPackageEnd = 0x03;
        initSSLManager();
        if (mManager == null || !mManager.isConnect()) {
            mManager.connect();
        }
    }

    public void destroy(){
        if (mManager != null) {
            mManager.disconnect();
            mManager.unRegisterReceiver(adapter);
        }
    }

    private void initManager() {
        final Handler handler = new Handler();
        mInfo = new ConnectionInfo(this.mIP, this.mPort);
        mOkOptions = new OkSocketOptions.Builder()
                .setReconnectionManager(new DefaultReconnectManager())
                .setConnectTimeoutSecond(3)
                .setWritePackageBytes(1024*6)
                .setReadPackageBytes(1024*1024*6)
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

    private void initSSLManager() {
        final Handler handler = new Handler();
        mInfo = new ConnectionInfo(this.mIP, this.mPort);
        mOkOptions = new OkSocketOptions.Builder()
                .setSSLConfig(new OkSocketSSLConfig.Builder().build())
                .setReconnectionManager(new DefaultReconnectManager())
                .setConnectTimeoutSecond(3)
                .setWritePackageBytes(1024*6)
                .setReadPackageBytes(1024*1024*6)
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
            SocketListenerEvent socketListenerEvent = new SocketListenerEvent();
            socketListenerEvent.setType(0);
            socketListenerEvent.setAction(action);
            socketListenerEvent.setInfo(info);
            EventBus.getDefault().post(socketListenerEvent);
        }

        @Override
        public void onSocketDisconnection(ConnectionInfo info, String action, Exception e) {
            if (e != null) {
                Logger.d("异常断开(Disconnected with exception):" + e.getMessage());
            } else {
                Logger.d("正常断开(Disconnect Manually)");
            }
            SocketListenerEvent socketListenerEvent = new SocketListenerEvent();
            socketListenerEvent.setType(1);
            socketListenerEvent.setInfo(info);
            socketListenerEvent.setAction(action);
            socketListenerEvent.setE(e);
            EventBus.getDefault().post(socketListenerEvent);
        }

        @Override
        public void onSocketConnectionFailed(ConnectionInfo info, String action, Exception e) {
            Logger.d("连接失败(Connecting Failed): " + e.getMessage());
            SocketListenerEvent socketListenerEvent = new SocketListenerEvent();
            socketListenerEvent.setType(2);
            socketListenerEvent.setInfo(info);
            socketListenerEvent.setAction(action);
            socketListenerEvent.setE(e);
            EventBus.getDefault().post(socketListenerEvent);
        }

        @Override
        public void onSocketReadResponse(ConnectionInfo info, String action, OriginalData data) {
            List<String> message = receiveMsg(data.getBodyBytes());
            for(int i = 0; i < message.size(); i++) {
                Logger.d("Owon SDK Response Data: " + message.get(i));
                if(mOpenReadDateToastFalg){
                    ToastUtils.showShort(message.get(i));
                }
            }
            if(message.size() > 0) {
                SocketListenerEvent socketListenerEvent = new SocketListenerEvent();
                socketListenerEvent.setType(3);
                socketListenerEvent.setAction(action);
                socketListenerEvent.setInfo(info);
                socketListenerEvent.setData(message);
                EventBus.getDefault().post(socketListenerEvent);
            }
        }

        @Override
        public void onSocketWriteResponse(ConnectionInfo info, String action, ISendable data) {
            String str = new String(data.parse(), Charset.forName("utf-8"));
            Logger.d("Owon SDK Send Data: "+str);
            if(mOpenWriteDateToastFlag) {
                ToastUtils.showShort(str);
            }
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
            mStickyPacketFlag = false;
            mStickyPacketData.clear();
            mStickyPacketData = null;
        }

        if (datas.length==0) {
            return receiveJSONData;
        }
        boolean flag = false; //true收到头，false收到尾
        List<Byte> dataBuf =new ArrayList<>(); //用于缓存收到的值
        for(int i = 0; i < datas.length; i++){
            if(datas[i] == mPackageHead && !flag){
                flag = true;
                dataBuf = new ArrayList<>();
                mStickyPacketData = new ArrayList<>();
                dataBuf.add(datas[i]);//把Head存起来
            }else{
                if(datas[i] == mPackageEnd) {//单纯收到包尾
                    flag = false;//收到完整包，继续收下个包
                    dataBuf.add(datas[i]);//把包尾存起来
                    byte[] bufdata = new byte[dataBuf.size()];
                    for(int l = 0 ;l< dataBuf.size();l++){
                        bufdata[l] = dataBuf.get(l);
                    }
                    //必须大于2，不然只剩包头包尾，那就没必要发出去解析等
                    if(bufdata.length > 2) {
                        receiveJSONData.add(byteArrayToStr(bufdata));
                    }
                } else if(datas[i] == mPackageHead && dataBuf.size() >= 2) {
                    flag = false;//收到完整包，继续收下个包
                    dataBuf.add((byte) mPackageEnd);//伪装一个包尾存起来
                    byte[] bufdata = new byte[dataBuf.size()];
                    for(int l = 0 ;l< dataBuf.size();l++){
                        bufdata[l] = dataBuf.get(l);
                    }
                    //必须大于2，不然只剩包头包尾，那就没必要发出去解析等
                    if(bufdata.length > 2) {
                        receiveJSONData.add(byteArrayToStr(bufdata));
                    }
                } else if(datas[i] == mPackageHead && dataBuf.size() == 1){
                    //连续收到两个head，抛弃第一个head，从第二个开始接收
                    dataBuf = new ArrayList<>();
                    mStickyPacketData = new ArrayList<>();
                    dataBuf.add(datas[i]);
                }else{//收到了head，开始把后面数据存起来
                    dataBuf.add(datas[i]);
                }
            }
        }
        //只有头，没有尾的分包处理。
        //包括整个包中只有一个head，没有end
        //或者整个包中，有多个完整包，但是最后一个包只有head，没有end
        if(dataBuf.size() > 0 && flag) {
            mStickyPacketFlag = true;
            if(mStickyPacketData != null){
                mStickyPacketData.clear();
                mStickyPacketData = null;
            }
            mStickyPacketData = new ArrayList<>();
            mStickyPacketData.addAll(dataBuf);

//            byte[] bufdata = new byte[dataBuf.size()];
//            for(int l = 0 ;l< dataBuf.size();l++){
//                bufdata[l] = dataBuf.get(l);
//            }
//            if(bufdata.length > 0) {
//                Logger.e("mStickyPacketData:  "+byteArrayToStr(bufdata));
//            }
        }
        return receiveJSONData;
    }

    public void closeSocket(){
        if(mManager != null) {
            mManager.disconnect();
        }
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
