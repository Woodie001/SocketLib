package com.woodie.protocol;

import java.io.Serializable;

/**
 * ProjectName:    SocketLib
 * Package:        com.woodie.protocol
 * ClassName:      SocketMessageLisenter
 * Description:
 * Author:         woodie
 * CreateDate:     2019/2/26 9:49
 * UpdateUser:     更新者
 * UpdateDate:     2019/2/26 9:49
 * UpdateRemark:   更新内容
 * Version:        1.0
 */
public class SocketMessageLisenter {

    private int seq;
    private Object object;

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }
}
