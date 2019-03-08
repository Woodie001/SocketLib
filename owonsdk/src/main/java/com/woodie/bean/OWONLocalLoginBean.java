package com.woodie.bean;

/**
 * ProjectName:    SocketLib
 * Package:        com.woodie.bean
 * ClassName:      OWONLocalLoginBean
 * Description:
 * Author:         woodie
 * CreateDate:     2019/3/7 9:45
 * UpdateUser:     更新者
 * UpdateDate:     2019/3/7 9:45
 * UpdateRemark:   更新内容
 * Version:        1.0
 */
public class OWONLocalLoginBean extends OWONBaseBean{
    private String session;
    private String version;
    private int versionnum;
    private int deviceType;
    private String chiptype;
    private int wifitype;
    private int utc0;
    private double timezone;
    private boolean dst;
    private String area;
    private String devModel;

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public int getVersionnum() {
        return versionnum;
    }

    public void setVersionnum(int versionnum) {
        this.versionnum = versionnum;
    }

    public int getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(int deviceType) {
        this.deviceType = deviceType;
    }

    public String getChiptype() {
        return chiptype;
    }

    public void setChiptype(String chiptype) {
        this.chiptype = chiptype;
    }

    public int getWifitype() {
        return wifitype;
    }

    public void setWifitype(int wifitype) {
        this.wifitype = wifitype;
    }

    public int getUtc0() {
        return utc0;
    }

    public void setUtc0(int utc0) {
        this.utc0 = utc0;
    }

    public double getTimezone() {
        return timezone;
    }

    public void setTimezone(double timezone) {
        this.timezone = timezone;
    }

    public boolean isDst() {
        return dst;
    }

    public void setDst(boolean dst) {
        this.dst = dst;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getDevModel() {
        return devModel;
    }

    public void setDevModel(String devModel) {
        this.devModel = devModel;
    }
}
