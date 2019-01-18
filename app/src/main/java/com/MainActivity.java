package com;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.blankj.utilcode.util.NetworkUtils;
import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;
import com.woodie.socketlib.OkHttpTool;
import com.woodie.socketlib.SocketAPI;
import com.woodie.socketlib.SocketTool;

import org.json.JSONException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.OkHttpClient;

public class MainActivity extends AppCompatActivity {

    private SocketTool mSocketTool;//全局sokcet变量
    private SocketAPI mSocketAPI;
    private OkHttpTool mOkHttpTool;

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

        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(false)  // (Optional) Whether to show thread info or not. Default true
                .methodCount(0)         // (Optional) How many method line to show. Default 2
                .methodOffset(7)        // (Optional) Hides internal method calls up to offset. Default 5
//                .logStrategy(customLog) // (Optional) Changes the log strategy to print out. Default LogCat
                .tag("My custom tag")   // (Optional) Global tag for every log. Default PRETTY_LOGGER
                .build();
        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy));

        ButterKnife.bind(this);

        mOkHttpTool = new OkHttpTool();
        mOkHttpTool.getInstance();

        mSocketTool = new SocketTool();
        mSocketAPI = mSocketTool.mSocketAPI;
        mSocketTool.getInstance(this,"192.168.168.104",11500,0x02,0x03);
    }

    private void Login(){
        String username = mUserNameET.getText().toString();
        String password = mPasswordET.getText().toString();
        if(username.isEmpty() || password.isEmpty()){
            ToastUtils.showShort("username and password can't be empty!");
            return;
        }
        if(!NetworkUtils.isAvailableByPing()){
            ToastUtils.showShort("Network Unavailable");
            return;
        }
        if(!RegexUtils.isTel(username) || !RegexUtils.isEmail(username)){
            ToastUtils.showShort("Username and password must be mobile number or email");
            return;
        }

        String url = "https://connect.owon.com:443/accsystem/api/json";
        mOkHttpTool.postAsynBackString(url,)
    }
}
