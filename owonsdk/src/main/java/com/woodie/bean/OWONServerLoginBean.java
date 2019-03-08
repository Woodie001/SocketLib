package com.woodie.bean;

/**
 * ProjectName:    SocketLib
 * Package:        com.woodie.bean
 * ClassName:      OWONServerLoginBean
 * Description:
 * Author:         woodie
 * CreateDate:     2019/2/26 10:35
 * UpdateUser:     更新者
 * UpdateDate:     2019/2/26 10:35
 * UpdateRemark:   更新内容
 * Version:        1.0
 */
public class OWONServerLoginBean extends OWONBaseBean {

    private int code;
    private String session;
    private int update;
    private String uversion;
    private String type;
    private String ts;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public int getUpdate() {
        return update;
    }

    public void setUpdate(int update) {
        this.update = update;
    }

    public String getUversion() {
        return uversion;
    }

    public void setUversion(String uversion) {
        this.uversion = uversion;
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
