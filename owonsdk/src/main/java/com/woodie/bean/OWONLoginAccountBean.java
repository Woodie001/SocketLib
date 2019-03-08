package com.woodie.bean;

/**
 * ProjectName:    SocketLib
 * Package:        com.woodie.bean
 * ClassName:      OWONLoginAccountBean
 * Description:
 * Author:         woodie
 * CreateDate:     2019/2/11 10:59
 * UpdateUser:     更新者
 * UpdateDate:     2019/2/11 10:59
 * UpdateRemark:   更新内容
 * Version:        1.0
 */
public class OWONLoginAccountBean {
    private int code;
    private String msg;
    private long ts;
    private int retryPswRemainCout;
    private int retryRemainTime;
    private String token;
    private String refreshToken;
    private int update;
    private String uversion;
    private String tcpserver;
    private int tcpsslport;
    private int tcpport;
    private String httpserver;
    private int httpport;
    private String httpsserver;
    private int httpsport;
    private String pushserver;
    private int pushport;

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

    public long getTs() {
        return ts;
    }

    public void setTs(long ts) {
        this.ts = ts;
    }

    public int getRetryPswRemainCout() {
        return retryPswRemainCout;
    }

    public void setRetryPswRemainCout(int retryPswRemainCout) {
        this.retryPswRemainCout = retryPswRemainCout;
    }

    public int getRetryRemainTime() {
        return retryRemainTime;
    }

    public void setRetryRemainTime(int retryRemainTime) {
        this.retryRemainTime = retryRemainTime;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
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

    public String getTcpserver() {
        return tcpserver;
    }

    public void setTcpserver(String tcpserver) {
        this.tcpserver = tcpserver;
    }

    public int getTcpsslport() {
        return tcpsslport;
    }

    public void setTcpsslport(int tcpsslport) {
        this.tcpsslport = tcpsslport;
    }

    public int getTcpport() {
        return tcpport;
    }

    public void setTcpport(int tcpport) {
        this.tcpport = tcpport;
    }

    public String getHttpserver() {
        return httpserver;
    }

    public void setHttpserver(String httpserver) {
        this.httpserver = httpserver;
    }

    public int getHttpport() {
        return httpport;
    }

    public void setHttpport(int httpport) {
        this.httpport = httpport;
    }

    public String getHttpsserver() {
        return httpsserver;
    }

    public void setHttpsserver(String httpsserver) {
        this.httpsserver = httpsserver;
    }

    public int getHttpsport() {
        return httpsport;
    }

    public void setHttpsport(int httpsport) {
        this.httpsport = httpsport;
    }

    public String getPushserver() {
        return pushserver;
    }

    public void setPushserver(String pushserver) {
        this.pushserver = pushserver;
    }

    public int getPushport() {
        return pushport;
    }

    public void setPushport(int pushport) {
        this.pushport = pushport;
    }
}
