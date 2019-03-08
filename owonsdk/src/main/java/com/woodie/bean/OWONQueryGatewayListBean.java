package com.woodie.bean;

import java.util.List;

/**
 * ProjectName:    SocketLib
 * Package:        com.woodie.bean
 * ClassName:      OWONQueryGatewayListBean
 * Description:
 * Author:         woodie
 * CreateDate:     2019/3/6 17:07
 * UpdateUser:     更新者
 * UpdateDate:     2019/3/6 17:07
 * UpdateRemark:   更新内容
 * Version:        1.0
 */
public class OWONQueryGatewayListBean extends OWONBaseBean{
    private int code;
    private String msg;
    private int pageno;
    private int pagesize;
    private int total;
    private List<String> name;
    private List<String> mac;
    private List<Integer> online;
    private List<String> gversion;
    private List<String> area;
    private List<String> chiptype;
    private List<Integer> devicetype;
    private List<String> devmodel;
    private List<Double> timezone;
    private List<Integer> versionnum;
    private List<Integer> wifitype;

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

    public int getPageno() {
        return pageno;
    }

    public void setPageno(int pageno) {
        this.pageno = pageno;
    }

    public int getPagesize() {
        return pagesize;
    }

    public void setPagesize(int pagesize) {
        this.pagesize = pagesize;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<String> getName() {
        return name;
    }

    public void setName(List<String> name) {
        this.name = name;
    }

    public List<String> getMac() {
        return mac;
    }

    public void setMac(List<String> mac) {
        this.mac = mac;
    }

    public List<Integer> getOnline() {
        return online;
    }

    public void setOnline(List<Integer> online) {
        this.online = online;
    }

    public List<String> getGversion() {
        return gversion;
    }

    public void setGversion(List<String> gversion) {
        this.gversion = gversion;
    }

    public List<String> getArea() {
        return area;
    }

    public void setArea(List<String> area) {
        this.area = area;
    }

    public List<String> getChiptype() {
        return chiptype;
    }

    public void setChiptype(List<String> chiptype) {
        this.chiptype = chiptype;
    }

    public List<Integer> getDevicetype() {
        return devicetype;
    }

    public void setDevicetype(List<Integer> devicetype) {
        this.devicetype = devicetype;
    }

    public List<String> getDevmodel() {
        return devmodel;
    }

    public void setDevmodel(List<String> devmodel) {
        this.devmodel = devmodel;
    }

    public List<Double> getTimezone() {
        return timezone;
    }

    public void setTimezone(List<Double> timezone) {
        this.timezone = timezone;
    }

    public List<Integer> getVersionnum() {
        return versionnum;
    }

    public void setVersionnum(List<Integer> versionnum) {
        this.versionnum = versionnum;
    }

    public List<Integer> getWifitype() {
        return wifitype;
    }

    public void setWifitype(List<Integer> wifitype) {
        this.wifitype = wifitype;
    }
}
