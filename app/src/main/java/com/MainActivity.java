package com;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.orhanobut.logger.Logger;
import com.woodie.bean.OWONLoginAccountBean;
import com.woodie.bean.OWONServerLoginBean;
import com.woodie.protocol.Resolve;
import com.woodie.http.HttpEvent;
import com.woodie.http.OkHttpDataCallBack;
import com.woodie.protocol.Sequence;
import com.woodie.protocol.SocketMessageLisenter;
import com.woodie.socketlib.R;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import java.io.IOException;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Request;

public class MainActivity extends BaseActivity {

    private String mUsername;
    private String mPassword;

    @BindView(R.id.editText) EditText mUserNameET;
    @BindView(R.id.editText2) EditText mPasswordET;

    @OnClick(R.id.button)
    void sendData(Button button) {
        Login();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //反注册
        if(EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    /**
     * 事件响应方法
     * 接收消息
     * @param event
     * */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void HttpEvent(HttpEvent event) {
        OWONLoginAccountBean owonLoginAccountBean= (OWONLoginAccountBean) new Resolve().
                OWONLoginAccount(event.getmMsg()).getObject();
        mOwonSDK.closeSocket();
        mOwonSDK.creatSSLSocket(owonLoginAccountBean.getTcpserver(), owonLoginAccountBean.getTcpsslport());
    }

    private void Login(){
        mUsername = mUserNameET.getText().toString();
        mPassword = mPasswordET.getText().toString();
        AppManager.getInstance().setmUserName(mUsername);
        AppManager.getInstance().setmPassword(mPassword);
        if(mUsername.isEmpty() || mPassword.isEmpty()){
            ToastUtils.showShort("mUsername and mPassword can't be empty!");
            return;
        }
        if(!RegexUtils.isEmail(mUsername)){
            if(RegexUtils.isMobileExact(mUsername) ){
                mUsername = "86-"+ mUsername;
            } else {
                ToastUtils.showShort("Username must be mobile number or email");
                return;
            }
        }

        final String url = "https://connect.owon.com:443/accsystem/api/json";
        mOkHttpTool.postAsynRequireJson(url,
                mSocketAPI.OWONLoginServerAccountHttp(mUsername, mPassword),
                new OkHttpDataCallBack() {
            @Override
            public void requestSuccess(Object result) {
                Logger.d(result.toString());
                EventBus.getDefault().post(new HttpEvent(result.toString()));
            }
            @Override
            public void requestFailure(Request request, IOException e) {
                Logger.d(request.toString()+"IOException:"+e.toString());
            }
        });
    }

    @Override
    public void SocketConnectionSuccess() {
        super.SocketConnectionSuccess();
        mOwonSDK.sendData(mSocketAPI.OWONLoginServerSocket(mUsername, mPassword));
    }

    @Override
    public void SocketDisConnection(Exception e) {
        super.SocketDisConnection(e);
    }

    @Override
    public void SocketConnectionFailed(Exception e) {
        super.SocketConnectionFailed(e);
    }

    @Override
    public void getMessage(SocketMessageLisenter socketMessageLisenter) {
        super.getMessage(socketMessageLisenter);
        int seq = socketMessageLisenter.getSeq();
        Object bean = socketMessageLisenter.getObject();
        switch (seq) {
            case Sequence.OWONServerLogin:
                OWONServerLoginBean serverLoginBean = (OWONServerLoginBean) bean;
                if(serverLoginBean.getCode() == 100){
                    Intent intent = new Intent(this, GatewayListActivity.class);
                    startActivity(intent);
                }
                break;
        }
    }

}
