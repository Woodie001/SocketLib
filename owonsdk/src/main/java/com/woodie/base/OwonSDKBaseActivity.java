package com.woodie.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;
import com.woodie.bean.OWONLoginAccountBean;
import com.woodie.bean.OWONServerLoginBean;
import com.woodie.http.OkHttpTool;
import com.woodie.protocol.Sequence;
import com.woodie.protocol.SocketAPI;
import com.woodie.protocol.SocketMessageLisenter;
import com.woodie.socketlib.SocketListenerEvent;
import com.woodie.socketlib.SocketTool;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Timer;
import java.util.TimerTask;

/**
 * ProjectName:    SocketLib
 * Package:        com.woodie.base
 * ClassName:      OwonSDKBaseActivity
 * Description:
 * Author:         woodie
 * CreateDate:     2019/1/31 14:37
 * UpdateUser:     更新者
 * UpdateDate:     2019/1/31 14:37
 * UpdateRemark:   更新内容
 * Version:        1.0
 */
public abstract class OwonSDKBaseActivity extends AppCompatActivity {

    public SocketTool mSocketTool;//全局sokcet变量
    public SocketAPI mSocketAPI;
    public OkHttpTool mOkHttpTool;
    private String mSession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EventBus.getDefault().register(this);

        mOkHttpTool = OkHttpTool.getInstance();

        mSocketTool = new SocketTool();
        mSocketTool.getInstance(this);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //反注册
        if(EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        //取消心跳发送定时器
        sendPulseDataClose();
        //销毁socket
        if(mSocketTool != null) {
            mSocketTool.destroy();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void SocketListenerEvent(SocketListenerEvent event){
        switch (event.getType()){
            case 0://SocketConnectionSuccess
                SocketConnectionSuccess();
                break;
            case 1://SocketDisconnection
                SocketDisConnection(event.getE());
                break;
            case 2://SocketConnectionFailed
                SocketConnectionFailed(event.getE());
                break;
            case 3://SocketReadResponse
                for(int i = 0; i < event.getData().size(); i++){
                    String data = event.getData().get(i);
                    if(data != null || !data.equals("")) {
                        SocketMessageLisenter socketMessageLisenter = mSocketAPI.RecieveData(data);
                        if(socketMessageLisenter != null) {
                            //自动开启心跳
                            if(socketMessageLisenter.getSeq() == Sequence.OWONServerLogin) {
                                OWONServerLoginBean serverLoginBean = (OWONServerLoginBean) socketMessageLisenter.getObject();
                                mSession = serverLoginBean.getSession();
                                sendPulseData();
                            } else {
                                getMessage(socketMessageLisenter);
                            }
                        }
                    }
                }
                break;
        }
    }
    public abstract void getMessage(SocketMessageLisenter socketMessageLisenter);

    public abstract void SocketConnectionSuccess();

    public abstract void SocketDisConnection(Exception e);

    public abstract void SocketConnectionFailed(Exception e);

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
