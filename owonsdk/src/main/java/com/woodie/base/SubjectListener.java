package com.woodie.base;

import com.woodie.protocol.SocketMessageLisenter;
import com.woodie.socketlib.SocketMessageInterface;

/**
 * ProjectName:    SocketLib
 * Package:        com.woodie.base
 * ClassName:      SubjectListener
 * Description:
 * Author:         woodie
 * CreateDate:     2019/3/12 15:22
 * UpdateUser:     更新者
 * UpdateDate:     2019/3/12 15:22
 * UpdateRemark:   更新内容
 * Version:        1.0
 */
public interface  SubjectListener {
    void add(SocketMessageInterface observerListener);
    void remove(SocketMessageInterface observerListener);
    void SocketConnectionSuccess();
    void SocketDisConnection(Exception e);
    void SocketConnectionFailed(Exception e);
    void getMessage(SocketMessageLisenter socketMessageLisenter);
}
