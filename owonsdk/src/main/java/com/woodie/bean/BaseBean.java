package com.woodie.bean;

/**
 * ProjectName:    SocketLib
 * Package:        com.woodie.bean
 * ClassName:      BaseBean
 * Description:
 * Author:         woodie
 * CreateDate:     2019/2/25 14:10
 * UpdateUser:     更新者
 * UpdateDate:     2019/2/25 14:10
 * UpdateRemark:   更新内容
 * Version:        1.0
 */
public class BaseBean {

    private boolean result;
    private String description;
    private int sequence;
    private String gmac;
    private String ieee;
    private int ep;

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    public String getGmac() {
        return gmac;
    }

    public void setGmac(String gmac) {
        this.gmac = gmac;
    }

    public String getIeee() {
        return ieee;
    }

    public void setIeee(String ieee) {
        this.ieee = ieee;
    }

    public int getEp() {
        return ep;
    }

    public void setEp(int ep) {
        this.ep = ep;
    }
}
