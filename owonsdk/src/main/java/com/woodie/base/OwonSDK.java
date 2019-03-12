package com.woodie.base;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;
import com.woodie.bean.OWONServerLoginBean;
import com.woodie.http.OkHttpTool;
import com.woodie.protocol.Sequence;
import com.woodie.protocol.SocketAPI;
import com.woodie.protocol.SocketMessageLisenter;
import com.woodie.socketlib.SocketMessageInterface;
import com.woodie.socketlib.SocketTool;
import com.xuhao.didi.socket.client.sdk.client.ConnectionInfo;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * ProjectName:    SocketLib
 * Package:        com.woodie.base
 * ClassName:      OwonSDK
 * Description:
 * Author:         woodie
 * CreateDate:     2019/1/31 14:37
 * UpdateUser:     更新者
 * UpdateDate:     2019/1/31 14:37
 * UpdateRemark:   更新内容
 * Version:        1.0
 */
public class OwonSDK extends ObserverManager{
    private volatile static OwonSDK instance;//防止多个线程同时访问

    public SocketTool mSocketTool;//全局sokcet变量
    public SocketAPI mSocketAPI;
    public OkHttpTool mOkHttpTool;
    private String mSession;

    /**
     * 懒汉式加锁单例模式
     * @return
     */
    public static OwonSDK getInstance() {
        if (instance == null) {
            synchronized (OwonSDK.class) {
                if (instance == null) {
                    instance = new OwonSDK();
                }
            }
        }
        return instance;
    }

    public void initOwonSDK(){
        mOkHttpTool = OkHttpTool.getInstance();

        mSocketTool = new SocketTool() {
            @Override
            public void getMessage(ConnectionInfo info, String action, List<String> data) {
                for(int i = 0; i < data.size(); i++){
                    SocketMessageLisenter socketMessageLisenter = mSocketAPI.RecieveData(data.get(i));
                    if(socketMessageLisenter != null) {
                        //自动开启心跳
                        if(socketMessageLisenter.getSeq() == Sequence.OWONServerLogin) {
                            OWONServerLoginBean serverLoginBean = (OWONServerLoginBean) socketMessageLisenter.getObject();
                            mSession = serverLoginBean.getSession();
                            sendPulseData();
                        }
                        ObserverManager.getInstance().getMessage(socketMessageLisenter);
                    }
                }
            }

            @Override
            public void SocketConnectionSuccess(ConnectionInfo info, String action) {
                ObserverManager.getInstance().SocketConnectionSuccess();
            }

            @Override
            public void SocketDisConnection(ConnectionInfo info, String action, Exception e) {
                ObserverManager.getInstance().SocketDisConnection(e);
            }

            @Override
            public void SocketConnectionFailed(ConnectionInfo info, String action, Exception e) {
                ObserverManager.getInstance().SocketConnectionFailed(e);
            }
        };
        mSocketAPI = new SocketAPI();

        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(false)  // (Optional) Whether to show thread info or not. Default true
                .methodCount(0)         // (Optional) How many method line to show. Default 2
                .methodOffset(7)        // (Optional) Hides internal method calls up to offset. Default 5
//                .logStrategy(customLog) // (Optional) Changes the log strategy to print out. Default LogCat
                .tag("Owon SDK Log: ")   // (Optional) Global tag for every log. Default PRETTY_LOGGER
                .build();
        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy));
    }

    public void unregisterOwonSDK() {
        //取消心跳发送定时器
        sendPulseDataClose();
        //销毁socket
        if(mSocketTool != null) {
            mSocketTool.destroy();
        }
        //反注册监听
        removeAll();
    }

    public void registerSocketMessageListener(SocketMessageInterface mSocketMessageInterface) {
        //注册
        add(mSocketMessageInterface);
    }

    public void unregisterSocketMessageListener(SocketMessageInterface mSocketMessageInterface) {
        //反注册
        remove(mSocketMessageInterface);
    }

    public void setOpenWriteDateToast(boolean flag){
        mSocketTool.setOpenWriteDateToast(flag);
    }

    public void setOpenReadDateToast(boolean flag){
        mSocketTool.setOpenReadDateToast(flag);
    }

    public void creatSocket(String ip,int port) {
        mSocketTool.creatSocket(ip,port);
    }

    public void creatSSLSocket(String ip,int port) {
        mSocketTool.creatSSLSocket(ip,port);
    }

    public void closeSocket(){
        mSocketTool.closeSocket();
    }

    public void sendData(String content){
        mSocketTool.sendData(content);
    }

    /*
     * 一分钟发送一次心跳
     */
    private TimerTask mSendPulseTask;
    private Timer mSendPulseTimer;
    public void sendPulseData(){
        if (mSendPulseTimer != null) {
            mSendPulseTimer.cancel();
            mSendPulseTimer = null;
        }
        if (mSendPulseTask != null) {
            mSendPulseTask.cancel();
            mSendPulseTask = null;
        }
        mSendPulseTask=new TimerTask() {

            @Override
            public void run() {
                mSocketTool.sendData(mSocketAPI.sendHeartFromPhoneToServer(mSession));
            }
        };
        mSendPulseTimer=new Timer();
        mSendPulseTimer.schedule(mSendPulseTask,0,60*1000);
    }
    public void sendPulseDataClose(){
        if (mSendPulseTimer != null) {
            mSendPulseTimer.cancel();
            mSendPulseTimer = null;
        }
        if (mSendPulseTask != null) {
            mSendPulseTask.cancel();
            mSendPulseTask = null;
        }
    }

}

