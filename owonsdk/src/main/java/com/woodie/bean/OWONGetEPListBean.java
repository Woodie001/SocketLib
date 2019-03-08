package com.woodie.bean;

import java.util.List;

/**
 * ProjectName:    SocketLib
 * Package:        com.woodie.bean
 * ClassName:      OWONGetEPListBean
 * Description:
 * Author:         woodie
 * CreateDate:     2019/2/27 13:49
 * UpdateUser:     更新者
 * UpdateDate:     2019/2/27 13:49
 * UpdateRemark:   更新内容
 * Version:        1.0
 */
public class OWONGetEPListBean extends OWONBaseBean {

    private int total;
    private int start;
    private int count;
    private List<String> ieee;
    private List<Integer> ep;
    private List<Integer> netDeviceType;
    private List<Boolean> linkStatus;
    private List<Integer> deviceType;
    private List<String> name;
    private List<Integer> iasZoneType;
    private List<Integer> profileId;
    private List<Integer> clusterFlag;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<String> getListIeee() {
        return ieee;
    }

    public void setListIeee(List<String> ieee) {
        this.ieee = ieee;
    }

    public List<Integer> getListEp() {
        return ep;
    }

    public void setListEp(List<Integer> ep) {
        this.ep = ep;
    }

    public List<Integer> getNetDeviceType() {
        return netDeviceType;
    }

    public void setNetDeviceType(List<Integer> netDeviceType) {
        this.netDeviceType = netDeviceType;
    }

    public List<Boolean> getLinkStatus() {
        return linkStatus;
    }

    public void setLinkStatus(List<Boolean> linkStatus) {
        this.linkStatus = linkStatus;
    }

    public List<Integer> getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(List<Integer> deviceType) {
        this.deviceType = deviceType;
    }

    public List<String> getName() {
        return name;
    }

    public void setName(List<String> name) {
        this.name = name;
    }

    public List<Integer> getIasZoneType() {
        return iasZoneType;
    }

    public void setIasZoneType(List<Integer> iasZoneType) {
        this.iasZoneType = iasZoneType;
    }

    public List<Integer> getProfileId() {
        return profileId;
    }

    public void setProfileId(List<Integer> profileId) {
        this.profileId = profileId;
    }

    public List<Integer> getClusterFlag() {
        return clusterFlag;
    }

    public void setClusterFlag(List<Integer> clusterFlag) {
        this.clusterFlag = clusterFlag;
    }
}
