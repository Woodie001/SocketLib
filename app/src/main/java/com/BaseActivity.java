package com;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.woodie.base.OwonSDK;
import com.woodie.bean.OWONServerLoginBean;
import com.woodie.http.OkHttpTool;
import com.woodie.protocol.Sequence;
import com.woodie.protocol.SocketAPI;
import com.woodie.protocol.SocketMessageLisenter;
import com.woodie.socketlib.R;
import com.woodie.socketlib.SocketMessageInterface;
import com.orhanobut.logger.Logger;

/**
 * ProjectName:    SocketLib
 * Package:        com
 * ClassName:      BaseActivity
 * Description:
 * Author:         woodie
 * CreateDate:     2019/3/8 17:58
 * UpdateUser:     更新者
 * UpdateDate:     2019/3/8 17:58
 * UpdateRemark:   更新内容
 * Version:        1.0
 */
public class BaseActivity extends AppCompatActivity implements SocketMessageInterface {

    public OwonSDK mOwonSDK;
    public OkHttpTool mOkHttpTool;
    public SocketAPI mSocketAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mOwonSDK = AppManager.getInstance().getmOwonSDK();
        mOkHttpTool = AppManager.getInstance().getmOkHttpTool();
        mSocketAPI = AppManager.getInstance().getmSocketAPI();
        mOwonSDK.registerSocketMessageListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mOwonSDK.unregisterOwonSDK();
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
    }

    @Override
    public void SocketDisConnection(Exception e) {
    }

    @Override
    public void SocketConnectionFailed(Exception e) {
    }

    @Override
    public void getMessage(SocketMessageLisenter socketMessageLisenter) {
        int seq = socketMessageLisenter.getSeq();
        Object bean = socketMessageLisenter.getObject();
        switch (seq) {
            case Sequence.OWONServerLogin:
                OWONServerLoginBean serverLoginBean = (OWONServerLoginBean) bean;
                AppManager.getInstance().setmSession(serverLoginBean.getSession());
                break;
        }
    }
}
