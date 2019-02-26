package com.woodie.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.woodie.bean.ServerLoginBean;
import com.woodie.protocol.Sequence;
import com.woodie.protocol.SocketAPI;
import com.woodie.http.OkHttpTool;
import com.woodie.protocol.SocketMessageLisenter;
import com.woodie.socketlib.SocketListenerEvent;
import com.woodie.socketlib.SocketTool;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import com.orhanobut.logger.Logger;
import java.util.Timer;
import java.util.TimerTask;

/**
 * ProjectName:    SocketLib
 * Package:        com.woodie.base
 * ClassName:      BaseActivity
 * Description:
 * Author:         woodie
 * CreateDate:     2019/1/31 14:37
 * UpdateUser:     更新者
 * UpdateDate:     2019/1/31 14:37
 * UpdateRemark:   更新内容
 * Version:        1.0
 */
public class BaseActivity extends AppCompatActivity {

    public SocketTool mSocketTool;//全局sokcet变量
    public SocketAPI mSocketAPI;
    public OkHttpTool mOkHttpTool;

    public String mUsername;
    public String mPassword;
    public String mSession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EventBus.getDefault().register(this);

        mOkHttpTool = OkHttpTool.getInstance();

        mSocketTool = new SocketTool();
        mSocketTool.getInstance(this);
        mSocketAPI = new SocketAPI();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //反注册
        if(EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        //取消心跳发送定时器
        sendPulseDataClose();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void SocketListenerEvent(SocketListenerEvent event){
        switch (event.getType()){
            case 0://SocketConnectionSuccess
//                Logger.d("已连接 IP："+event.getInfo().getIp() + "  port:"+event.getInfo().getPort());
                mSocketTool.sendData(mSocketAPI.LoginServerSocket(mUsername, mPassword));
                break;
            case 1://SocketDisconnection
                if (event.getE() != null) {
//                    Logger.d("异常断开(Disconnected with exception):" + event.getE().getMessage());
                } else {
//                    Logger.d("正常断开(Disconnect Manually)");
                }
                break;
            case 2://SocketConnectionFailed
                break;
            case 3://SocketReadResponse
                for(int i = 0; i < event.getData().size(); i++){
                    SocketMessageLisenter socketMessageLisenter = mSocketAPI.RecieveData(event.getData().get(i));
                    getMessage(socketMessageLisenter);
                }
                break;
        }
    }

    private void getMessage(SocketMessageLisenter socketMessageLisenter){
        int seq = socketMessageLisenter.getSeq();
        Object bean = socketMessageLisenter.getObject();
        switch (seq) {
            case Sequence.ServerLogin:
                ServerLoginBean serverLoginBean = (ServerLoginBean) bean;
                mSession = serverLoginBean.getSession();
                sendPulseData();
                break;

        }
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
