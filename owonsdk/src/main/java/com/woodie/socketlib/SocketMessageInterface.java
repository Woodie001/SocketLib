package com.woodie.socketlib;

import com.woodie.protocol.SocketMessageLisenter;

/**
 * ProjectName:    SocketLib
 * Package:        com.woodie.socketlib
 * ClassName:      SocketMessageInterface
 * Description:
 * Author:         woodie
 * CreateDate:     2019/2/28 14:06
 * UpdateUser:     更新者
 * UpdateDate:     2019/2/28 14:06
 * UpdateRemark:   更新内容
 * Version:        1.0
 */
public interface SocketMessageInterface {
    void SocketConnectionSuccess();
    void SocketDisConnection(Exception e);
    void SocketConnectionFailed(Exception e);
    void getMessage(SocketMessageLisenter socketMessageLisenter);
}
