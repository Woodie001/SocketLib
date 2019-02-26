package com;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.orhanobut.logger.Logger;
import com.woodie.base.BaseActivity;
import com.woodie.bean.LoginAccountBean;
import com.woodie.protocol.Resolve;
import com.woodie.http.HttpEvent;
import com.woodie.http.OkHttpDataCallBack;
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

    @BindView(R.id.editText) EditText mUserNameET;
    @BindView(R.id.editText2) EditText mPasswordET;

    @OnClick(R.id.button)
    void sendData(Button button) {
        Login();
//        try {
//            mSocketTool.sendData(mSocketAPI.Login("oopp","123456"));
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
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
//        Logger.d( "onEventMainThread收到了消息：" + event.getmMsg());
        LoginAccountBean loginAccountBean = (LoginAccountBean) new Resolve().LoginAccount(event.getmMsg()).getObject();
        Logger.d("loginAccountBean：" + loginAccountBean.toString());
        if(mSocketTool != null){
            mSocketTool.closeSocket();
        }
        mSocketTool.creatSocket(loginAccountBean.getTcpserver(), loginAccountBean.getTcpport(), 0x02, 0x03);
    }

    private void Login(){
        mUsername = mUserNameET.getText().toString();
        mPassword = mPasswordET.getText().toString();
        if(mUsername.isEmpty() || mPassword.isEmpty()){
            ToastUtils.showShort("mUsername and mPassword can't be empty!");
            return;
        }
//        if(!NetworkUtils.isAvailableByPing()){
//            ToastUtils.showShort("Network Unavailable");
//            return;
//        }
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
                mSocketAPI.LoginServerAccountHttp(mUsername, mPassword),
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
}
