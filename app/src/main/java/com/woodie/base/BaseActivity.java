package com.woodie.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.woodie.bean.SocketAPI;
import com.woodie.http.OkHttpTool;
import com.woodie.socketlib.SocketTool;
import org.greenrobot.eventbus.EventBus;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EventBus.getDefault().register(this);

        mOkHttpTool = OkHttpTool.getInstance();

        mSocketTool = new SocketTool();
        mSocketTool.getInstance(this);
        mSocketAPI = new SocketAPI();
    }

}
