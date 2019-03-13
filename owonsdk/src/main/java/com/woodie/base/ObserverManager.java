package com.woodie.base;

import com.woodie.protocol.SocketMessageLisenter;
import com.woodie.socketlib.SocketMessageInterface;
import java.util.ArrayList;
import java.util.List;

/**
 * ProjectName:    SocketLib
 * Package:        com.woodie.base
 * ClassName:      ObserverManager
 * Description:
 * Author:         woodie
 * CreateDate:     2019/3/12 15:26
 * UpdateUser:     更新者
 * UpdateDate:     2019/3/12 15:26
 * UpdateRemark:   更新内容
 * Version:        1.0
 */
public class ObserverManager implements SubjectListener {
    private static ObserverManager observerManager;
    //观察者接口集合
    private List<SocketMessageInterface> list = new ArrayList<>();
    /**
    * 单例
    * */
    public static ObserverManager getInstance() {
        if (null == observerManager) {
            synchronized (ObserverManager.class) {
                if (null == observerManager) {
                    observerManager = new ObserverManager();
                }
            }
        }
        return observerManager;
    }
    /**
    * 加入监听队列
    */
    @Override
    public void add(SocketMessageInterface observerListener) {
        if(!list.contains(observerListener)) {
            list.add(observerListener);
        }
    }

    /**
    * 监听队列中移除
    */
    @Override
    public void remove(SocketMessageInterface observerListener) {
         if (list.contains(observerListener)) {
             list.remove(observerListener);
         }
     }

    /**
     * 监听队列中移除所有
     */
    public void removeAll() {
        if (list != null) {
            list.clear();
            list = null;
        }
    }

    @Override
    public void SocketConnectionSuccess() {
        for (SocketMessageInterface observerListener : list) {
            observerListener.SocketConnectionSuccess();
        }
    }

    @Override
    public void SocketDisConnection(Exception e) {
        for (SocketMessageInterface observerListener : list) {
            observerListener.SocketDisConnection(e);
        }
    }

    @Override
    public void SocketConnectionFailed(Exception e) {
        for (SocketMessageInterface observerListener : list) {
            observerListener.SocketConnectionFailed(e);
        }
    }

    @Override
    public void getMessage(SocketMessageLisenter socketMessageLisenter) {
        for (SocketMessageInterface observerListener : list) {
            observerListener.getMessage(socketMessageLisenter);
        }
    }
}
