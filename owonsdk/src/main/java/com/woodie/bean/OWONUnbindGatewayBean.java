package com.woodie.bean;

/**
 * ProjectName:    SocketLib
 * Package:        com.woodie.bean
 * ClassName:      OWONUnbindGatewayBean
 * Description:
 * Author:         woodie
 * CreateDate:     2019/3/7 8:58
 * UpdateUser:     更新者
 * UpdateDate:     2019/3/7 8:58
 * UpdateRemark:   更新内容
 * Version:        1.0
 */
public class OWONUnbindGatewayBean extends OWONBaseBean {
    private int code;
    private String msg;
    private String type;
    private String ts;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTs() {
        return ts;
    }

    public void setTs(String ts) {
        this.ts = ts;
    }
}
