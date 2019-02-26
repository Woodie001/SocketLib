package com.woodie.socketlib;

import com.xuhao.didi.socket.client.sdk.client.ConnectionInfo;

import java.util.List;

/**
 * ProjectName:    SocketLib
 * Package:        com.woodie.socketlib
 * ClassName:      SocketListenerEvent
 * Description:
 * Author:         woodie
 * CreateDate:     2019/2/25 10:12
 * UpdateUser:     更新者
 * UpdateDate:     2019/2/25 10:12
 * UpdateRemark:   更新内容
 * Version:        1.0
 */
public class SocketListenerEvent {

    private int type;
    private ConnectionInfo info = null;
    private String action = null;
    private List<String> data = null;
    private Exception e = null;

    public ConnectionInfo getInfo() {
        return info;
    }

    public void setInfo(ConnectionInfo info) {
        this.info = info;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }

    public Exception getE() {
        return e;
    }

    public void setE(Exception e) {
        this.e = e;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
