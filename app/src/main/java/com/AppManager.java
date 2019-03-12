package com;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import com.woodie.base.OwonSDK;
import com.woodie.http.OkHttpTool;
import com.woodie.protocol.SocketAPI;

/**
 * ProjectName:    SocketLib
 * Package:        com
 * ClassName:      AppManager
 * Description:
 * Author:         woodie
 * CreateDate:     2019/1/18 16:04
 * UpdateUser:     更新者
 * UpdateDate:     2019/1/18 16:04
 * UpdateRemark:   更新内容
 * Version:        1.0
 */
public class AppManager extends Application {
    private volatile static AppManager instance=null;//防止多个线程同时访问

    private String mUserName;
    private String mPassword;
    private String mSession;
    private OwonSDK mOwonSDK;
    private OkHttpTool mOkHttpTool;
    private SocketAPI mSocketAPI;

    @Override
    public void onCreate() {
        // 程序创建的时候执行
        super.onCreate();
        instance=this;
        initOwonSDK();
    }

    @Override
    public void onTerminate() {
        // 程序终止的时候执行
        super.onTerminate();
        //注销OwonSDK
        mOwonSDK.unregisterOwonSDK();
    }
    @Override
    public void onLowMemory() {
        // 低内存的时候执行
        super.onLowMemory();
    }
    @Override
    public void onTrimMemory(int level) {
        // 程序在内存清理的时候执行
        super.onTrimMemory(level);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    //应用层的单例模式
    public static AppManager getInstance() {
        if (instance == null){
            synchronized (AppManager.class){
                if (instance == null){
                    instance = new AppManager();
                }
            }
        }
        return instance;
    }

    private void initOwonSDK(){
        mOwonSDK = OwonSDK.getInstance();
        mOwonSDK.initOwonSDK();
        //初始化监听，写在需要监听的地方
//      AppManager.getInstance().getmOwonSDK().setmSocketMessageListener(this);
        mOkHttpTool = mOwonSDK.mOkHttpTool;
        mSocketAPI = mOwonSDK.mSocketAPI;
        //是否土司显示发送的数据
        mOwonSDK.setOpenWriteDateToast(true);
        //是否土司显示收到的数据
        mOwonSDK.setOpenReadDateToast(true);
    }

    public String getmUserName() {
        return mUserName;
    }

    public void setmUserName(String mUserName) {
        this.mUserName = mUserName;
    }

    public String getmPassword() {
        return mPassword;
    }

    public void setmPassword(String mPassword) {
        this.mPassword = mPassword;
    }

    public String getmSession() {
        return mSession;
    }

    public void setmSession(String mSession) {
        this.mSession = mSession;
    }

    public OwonSDK getmOwonSDK() {
        return this.mOwonSDK;
    }

    public void setmOwonSDK(OwonSDK mOwonSDK) {
        this.mOwonSDK = mOwonSDK;
    }

    public OkHttpTool getmOkHttpTool() {
        return this.mOkHttpTool;
    }

    public void setmOkHttpTool(OkHttpTool mOkHttpTool) {
        this.mOkHttpTool = mOkHttpTool;
    }

    public SocketAPI getmSocketAPI() {
        return this.mSocketAPI;
    }

    public void setmSocketAPI(SocketAPI mSocketAPI) {
        this.mSocketAPI = mSocketAPI;
    }


}
