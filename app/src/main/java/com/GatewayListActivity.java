package com;

import android.app.Activity;
import android.os.Bundle;

import com.orhanobut.logger.Logger;
import com.woodie.protocol.SocketMessageLisenter;
import com.woodie.socketlib.R;
import com.woodie.socketlib.SocketMessageInterface;

/**
 * ProjectName:    SocketLib
 * Package:        com
 * ClassName:      GatewayListActivity
 * Description:
 * Author:         woodie
 * CreateDate:     2019/3/12 10:32
 * UpdateUser:     更新者
 * UpdateDate:     2019/3/12 10:32
 * UpdateRemark:   更新内容
 * Version:        1.0
 */
public class GatewayListActivity extends Activity implements SocketMessageInterface {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gateway_list_activity);

        AppManager.getInstance().getmOwonSDK().registerSocketMessageListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppManager.getInstance().getmOwonSDK().unregisterSocketMessageListener(this);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void SocketConnectionSuccess() {
        Logger.d("SocketConnectionSuccess");
    }

    @Override
    public void SocketDisConnection(Exception e) {
        Logger.d("SocketDisConnection");
    }

    @Override
    public void SocketConnectionFailed(Exception e) {
        Logger.d("SocketConnectionFailed");
    }

    @Override
    public void getMessage(SocketMessageLisenter socketMessageLisenter) {
        Logger.d("socketMessageLisenter");
    }
}
