package com.woodie.bean;

/**
 * ProjectName:    SocketLib
 * Package:        com.woodie.bean
 * ClassName:      UpdatePCT501Bean
 * Description:
 * Author:         woodie
 * CreateDate:     2019/2/25 15:06
 * UpdateUser:     更新者
 * UpdateDate:     2019/2/25 15:06
 * UpdateRemark:   更新内容
 * Version:        1.0
 */
public class UpdatePCT501Bean extends BaseBean {

    private double heatTemp;
    private double coolTemp;
    private String holdMode;
    private String holdDuration;
    private String mode;
    private String fanMode;
    private double localTemp;
    private String running;
    private int humidity;
    private int displayMode;

    public double getHeatTemp() {
        return heatTemp;
    }

    public void setHeatTemp(double heatTemp) {
        this.heatTemp = heatTemp;
    }

    public double getCoolTemp() {
        return coolTemp;
    }

    public void setCoolTemp(double coolTemp) {
        this.coolTemp = coolTemp;
    }

    public String getHoldMode() {
        return holdMode;
    }

    public void setHoldMode(String holdMode) {
        this.holdMode = holdMode;
    }

    public String getHoldDuration() {
        return holdDuration;
    }

    public void setHoldDuration(String holdDuration) {
        this.holdDuration = holdDuration;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getFanMode() {
        return fanMode;
    }

    public void setFanMode(String fanMode) {
        this.fanMode = fanMode;
    }

    public double getLocalTemp() {
        return localTemp;
    }

    public void setLocalTemp(double localTemp) {
        this.localTemp = localTemp;
    }

    public String getRunning() {
        return running;
    }

    public void setRunning(String running) {
        this.running = running;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public int getDisplayMode() {
        return displayMode;
    }

    public void setDisplayMode(int displayMode) {
        this.displayMode = displayMode;
    }
}
