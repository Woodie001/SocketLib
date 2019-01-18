package com;

import android.app.Application;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.BuildConfig;
import com.orhanobut.logger.CsvFormatStrategy;
import com.orhanobut.logger.DiskLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;

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

    @Override
    public void onCreate() {
        super.onCreate();
        //      以下是打印自定义日志到控制台
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .tag("MyCustomTag")   // 自定义TAG全部标签，默认PRETTY_LOGGER
                .build();

        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy) {
            @Override
            public boolean isLoggable(int priority, String tag) {
                return BuildConfig.DEBUG;
            }
        });
        //      以下是保存自定义日志
        FormatStrategy formatStrateg = CsvFormatStrategy.newBuilder()
                .tag("custom")
                .build();
        Logger.addLogAdapter(new DiskLogAdapter(formatStrateg));


    }

    //应用层的单例模式
    public static AppManager getInstance() {
        if (instance==null){
            synchronized (AppManager.class){
                if (instance==null){
                    instance=new AppManager();
                }
            }
        }
        return instance;
    }
}
