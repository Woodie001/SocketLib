package com;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.orhanobut.logger.Logger;
import com.woodie.base.BaseActivity;
import com.woodie.http.HttpEvent;
import com.woodie.http.OkHttpDataCallBack;
import com.woodie.socketlib.R;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import java.io.IOException;
import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Request;

public class MainActivity extends BaseActivity {

    private String username;
    private String password;

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
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //反注册
        EventBus.getDefault().unregister(this);
    }

    /**
     * 事件响应方法
     * 接收消息
     * @param event
     * */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(HttpEvent event) {
        Logger.d( "onEventMainThread收到了消息：" + event.getmMsg());
    }

    private void Login(){
        username = mUserNameET.getText().toString();
        password = mPasswordET.getText().toString();
        if(username.isEmpty() || password.isEmpty()){
            ToastUtils.showShort("username and password can't be empty!");
            return;
        }
//        if(!NetworkUtils.isAvailableByPing()){
//            ToastUtils.showShort("Network Unavailable");
//            return;
//        }
        if(!RegexUtils.isEmail(username)){
            if(RegexUtils.isMobileExact(username) ){
                username = "86-"+username;
            } else {
                ToastUtils.showShort("Username must be mobile number or email");
                return;
            }
        }

        final String url = "https://connect.owon.com:443/accsystem/api/json";
        mOkHttpTool.postAsynRequireJson(url,
                mSocketAPI.LoginAccount(username,password),
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
