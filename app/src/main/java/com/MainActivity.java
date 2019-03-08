package com;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.orhanobut.logger.Logger;
import com.woodie.base.OwonSDKBaseActivity;
import com.woodie.bean.OWONLoginAccountBean;
import com.woodie.bean.OWONServerLoginBean;
import com.woodie.protocol.Resolve;
import com.woodie.http.HttpEvent;
import com.woodie.http.OkHttpDataCallBack;
import com.woodie.protocol.Sequence;
import com.woodie.protocol.SocketMessageLisenter;
import com.woodie.socketlib.R;
import com.woodie.socketlib.SocketMessageInterface;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import java.io.IOException;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Request;

public class MainActivity extends OwonSDKBaseActivity implements SocketMessageInterface {

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

        //是否土司显示发送的数据
        mSocketTool.setOpenWriteDateToast(false);
        //是否土司显示收到的数据
        mSocketTool.setOpenReadDateToast(false);
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
        if(mSocketTool != null){
            mSocketTool.closeSocket();
        }
        assert mSocketTool != null;
        mSocketTool.creatSSLSocket(owonLoginAccountBean.getTcpserver(), owonLoginAccountBean.getTcpsslport());
    }

    private void Login(){
        mUsername = mUserNameET.getText().toString();
        mPassword = mPasswordET.getText().toString();
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
        mSocketTool.sendData(mSocketAPI.OWONLoginServerSocket(mUsername, mPassword));
    }

    @Override
    public void SocketDisConnection(Exception e) {
        if (e != null) {
//                    Logger.d("异常断开(Disconnected with exception):" + event.getE().getMessage());
        } else {
//                    Logger.d("正常断开(Disconnect Manually)");
        }
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
                break;

        }
    }
}
