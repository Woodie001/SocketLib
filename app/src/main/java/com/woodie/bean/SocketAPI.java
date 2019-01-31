package com.woodie.bean;

import com.blankj.utilcode.util.EncryptUtils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * ProjectName:    SocketLib
 * Package:        com.woodie.socketlib
 * ClassName:      SocketAPI
 * Description:
 * Author:         woodie
 * CreateDate:     2019/1/17 14:07
 * UpdateUser:     更新者
 * UpdateDate:     2019/1/17 14:07
 * UpdateRemark:   更新内容
 * Version:        1.0
 */
public class SocketAPI {

    private byte[] startflag = {0x02};
    private byte[] endflag = {0x03};
    private String start = new String(startflag);
    private String end = new String(endflag);

    private Sequence mSequence = new Sequence();

    public String LoginSocket(String username, String password) throws JSONException {
        JSONObject mapbuf = new JSONObject();
        mapbuf.put("type", "login");
        mapbuf.put("sequence", mSequence.Login);
        JSONObject buf1 = new JSONObject();
        buf1.put("username", username);
        buf1.put("password", password);
        mapbuf.put("argument", buf1);
        String value = start + mapbuf.toString() + end;
        return value;
    }
    public String LoginAccount(String account, String password) {
        JSONObject mapbuf = new JSONObject();
        try {
            mapbuf.put("type", "/nt/user/applogin");
            mapbuf.put("token", "");
            mapbuf.put("ts", getTimeinterval());
            JSONObject buf1 = new JSONObject();
            buf1.put("account", account);
            buf1.put("password", EncryptUtils.encryptMD5ToString(password).toLowerCase());
            buf1.put("os", "Android");
            buf1.put("cversion", "0.0.1");
            mapbuf.put("param", buf1);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return mapbuf.toString();
    }

    private String getTimeinterval(){
        long time=System.currentTimeMillis();
        time=time/1000-946656000;
        return time+"";
    }
}
