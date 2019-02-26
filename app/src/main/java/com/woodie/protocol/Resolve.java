package com.woodie.protocol;

import com.woodie.bean.LoginAccountBean;
import com.woodie.bean.SecurityReadRecordInfoBean;
import com.woodie.bean.ServerLoginBean;
import com.woodie.bean.UpdateEPListBean;
import com.woodie.bean.UpdatePCT501Bean;
import com.woodie.bean.WarningSensorBean;
import com.woodie.bean.WarningSensorNumBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.List;

/**
 * ProjectName:    SocketLib
 * Package:        com.woodie.socketlib
 * ClassName:      Resolve
 * Description:
 * Author:         woodie
 * CreateDate:     2019/1/18 11:01
 * UpdateUser:     更新者
 * UpdateDate:     2019/1/18 11:01
 * UpdateRemark:   更新内容
 * Version:        1.0
 */
public class Resolve {
    public SocketMessageLisenter LoginAccount(String data){
        SocketMessageLisenter socketMessageLisenter = new SocketMessageLisenter();
        LoginAccountBean loginAccountBean = new LoginAccountBean();
        JSONTokener jsonParser = new JSONTokener(data);
        try {
            JSONObject jsonObj = (JSONObject) jsonParser.nextValue();
            loginAccountBean.setCode(jsonObj.getInt("code"));
            loginAccountBean.setMsg(jsonObj.getString("msg"));
            loginAccountBean.setTs(jsonObj.getLong("ts"));
            JSONObject responseobj = jsonObj.getJSONObject("response");
            loginAccountBean.setRetryPswRemainCout(responseobj.optInt("retryPswRemainCout"));
            loginAccountBean.setRetryRemainTime(responseobj.optInt("retryRemainTime"));
            loginAccountBean.setToken(responseobj.optString("token"));
            loginAccountBean.setRefreshToken(responseobj.optString("refreshToken"));
            loginAccountBean.setUpdate(responseobj.optInt("update"));
            loginAccountBean.setUversion(responseobj.optString("uversion"));
            loginAccountBean.setTcpserver(responseobj.optString("tcpserver"));
            loginAccountBean.setTcpsslport(responseobj.optInt("tcpsslport"));
            loginAccountBean.setTcpport(responseobj.optInt("tcpport"));
            loginAccountBean.setHttpserver(responseobj.optString("httpserver"));
            loginAccountBean.setHttpsserver(responseobj.optString("httpsserver"));
            loginAccountBean.setHttpsport(responseobj.optInt("httpsport"));
            loginAccountBean.setHttpport(responseobj.optInt("httpport"));
            loginAccountBean.setPushserver(responseobj.optString("pushserver"));
            loginAccountBean.setPushport(responseobj.optInt("pushport"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        socketMessageLisenter.setObject(loginAccountBean);
        return socketMessageLisenter;
    }
    public SocketMessageLisenter ServerLogin(String data) throws JSONException{
        SocketMessageLisenter socketMessageLisenter = new SocketMessageLisenter();
        ServerLoginBean serverLoginBean = new ServerLoginBean();
        JSONTokener jsonParser = new JSONTokener(data);
        JSONObject jsonObj = (JSONObject) jsonParser.nextValue();
            serverLoginBean.setDescription(jsonObj.optString("msg"));
            serverLoginBean.setSequence(jsonObj.getInt("sequence"));
            serverLoginBean.setCode(jsonObj.getInt("code"));
            serverLoginBean.setType(jsonObj.getString("type"));
            serverLoginBean.setTs(jsonObj.getString("ts"));
        JSONObject job=jsonObj.getJSONObject("response");
            serverLoginBean.setSession(job.getString("session"));
            serverLoginBean.setUpdate(job.getInt("update"));
            serverLoginBean.setUversion(job.getString("uversion"));
            socketMessageLisenter.setObject(serverLoginBean);
        return socketMessageLisenter;
    }

    public SocketMessageLisenter UpdateEPList(String data){
        SocketMessageLisenter socketMessageLisenter = new SocketMessageLisenter();
        UpdateEPListBean updateEPListBean = new UpdateEPListBean();
        JSONTokener jsonParser = new JSONTokener(data);
        JSONObject jsonObj = null;
        try {
            jsonObj = (JSONObject) jsonParser.nextValue();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        assert jsonObj != null;
        if (jsonObj.optString("mac")!=null){
            updateEPListBean.setGmac(jsonObj.optString("mac"));
        }
        updateEPListBean.setResult(true);
        updateEPListBean.setDescription("Update EPList Success");
        updateEPListBean.setSequence(Sequence.UpdateEPList);
        socketMessageLisenter.setSeq(Sequence.UpdateEPList);
        try {
            JSONObject job = jsonObj.getJSONObject("argument");
            updateEPListBean.setTotal(job.getInt("total"));
            updateEPListBean.setStart(job.getInt("start"));
            updateEPListBean.setCount(job.getInt("count"));
            JSONArray  jobarray=job.getJSONArray("epList");
            List<String> ieee = new ArrayList<>();
            List<Integer> ep = new ArrayList<>();
            List<Integer> netDeviceType = new ArrayList<>();
            List<Boolean> linkStatus = new ArrayList<>();
            List<Integer> deviceType = new ArrayList<>();
            List<String> name = new ArrayList<>();
            List<Integer> iasZoneType = new ArrayList<>();
            List<Integer> profileid = new ArrayList<>();
            List<Integer> clusterFlag = new ArrayList<>();
            for(int i=0;i<jobarray.length();i++){
                JSONObject jsonobj = jobarray.getJSONObject(i);
                ieee.add(jsonobj.optString("ieee"));
                ep.add(jsonobj.optInt("ep"));
                netDeviceType.add(jsonobj.getInt("netDeviceType"));
                linkStatus.add(jsonobj.optBoolean("linkStatus"));
                deviceType.add(jsonobj.optInt("deviceType"));
                name.add(jsonobj.optString("name"));
                iasZoneType.add(jsonobj.optInt("IASZoneType"));
                profileid.add(jsonobj.optInt("ProfileId"));
                clusterFlag.add(jsonObj.optInt("clusterFlag"));
            }
            updateEPListBean.setListIeee(ieee);
            updateEPListBean.setListEp(ep);
            updateEPListBean.setNetDeviceType(netDeviceType);
            updateEPListBean.setLinkStatus(linkStatus);
            updateEPListBean.setDeviceType(deviceType);
            updateEPListBean.setName(name);
            updateEPListBean.setIasZoneType(iasZoneType);
            updateEPListBean.setProfileId(profileid);
            updateEPListBean.setClusterFlag(clusterFlag);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        socketMessageLisenter.setObject(updateEPListBean);
        return socketMessageLisenter;
    }

    public SocketMessageLisenter UpdatePCT501(String data) {
        SocketMessageLisenter socketMessageLisenter = new SocketMessageLisenter();
        UpdatePCT501Bean updatePCT501Bean = new UpdatePCT501Bean();
        JSONTokener jsonParser = new JSONTokener(data);
        JSONObject jsonObj = null;
        try {
            jsonObj = (JSONObject) jsonParser.nextValue();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (jsonObj.opt("mac")!=null){
            updatePCT501Bean.setGmac(jsonObj.optString("mac"));
        }
        updatePCT501Bean.setResult(true);
        updatePCT501Bean.setDescription("Update PCT501 Success");
        updatePCT501Bean.setSequence(Sequence.UpdatePCT501);
        socketMessageLisenter.setSeq(Sequence.UpdatePCT501);
        try {
            JSONObject job = jsonObj.getJSONObject("argument");
            updatePCT501Bean.setIeee(job.optString("ieee"));
            updatePCT501Bean.setEp(job.optInt("ep"));
            updatePCT501Bean.setHeatTemp(job.optDouble("heatTemp"));
            updatePCT501Bean.setCoolTemp(job.optDouble("coolTemp"));
            updatePCT501Bean.setHoldMode(job.optString("holdMode"));
            updatePCT501Bean.setHoldDuration(job.optString("holdDuration"));
            updatePCT501Bean.setMode(job.optString("mode"));
            updatePCT501Bean.setFanMode(job.optString("fanMode"));
            updatePCT501Bean.setLocalTemp(job.optDouble("localTemp"));
            updatePCT501Bean.setRunning(job.optString("running"));
            updatePCT501Bean.setHumidity(job.optInt("humidity"));
            updatePCT501Bean.setDisplayMode(job.optInt("displayMode"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        socketMessageLisenter.setObject(updatePCT501Bean);
        return socketMessageLisenter;
    }

    public SocketMessageLisenter WarningSensor(String data){
        SocketMessageLisenter socketMessageLisenter = new SocketMessageLisenter();
        WarningSensorBean warningSensorBean = new WarningSensorBean();
        JSONTokener jsonParser = new JSONTokener(data);
        JSONObject jsonObj = null;
        try {
            jsonObj = (JSONObject) jsonParser.nextValue();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        assert jsonObj != null;
        if (jsonObj.optString("mac")!=null){
            warningSensorBean.setGmac(jsonObj.optString("mac"));
        }
        warningSensorBean.setResult(true);
        warningSensorBean.setDescription("Warning Sensor Success");
        warningSensorBean.setSequence(Sequence.WarningSensor);
        socketMessageLisenter.setSeq(Sequence.WarningSensor);
        try {
            JSONObject job = jsonObj.getJSONObject("argument");
            warningSensorBean.setIeee(job.optString("ieee"));
            warningSensorBean.setEp(job.optInt("ep"));
            warningSensorBean.setStatus(job.optDouble("status"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        socketMessageLisenter.setObject(warningSensorBean);
        return socketMessageLisenter;
    }
    public SocketMessageLisenter WarningSensorNum(String data){
        SocketMessageLisenter socketMessageLisenter = new SocketMessageLisenter();
        WarningSensorNumBean warningSensorNumBean = new WarningSensorNumBean();
        JSONTokener jsonParser = new JSONTokener(data);
        JSONObject jsonObj = null;
        try {
            jsonObj = (JSONObject) jsonParser.nextValue();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        assert jsonObj != null;
        if (jsonObj.opt("mac")!=null){
            warningSensorNumBean.setGmac(jsonObj.optString("mac"));
        }
        warningSensorNumBean.setResult(true);
        warningSensorNumBean.setDescription("WarningNum Sensor Success");
        warningSensorNumBean.setSequence(Sequence.WarningSensorNum);
        socketMessageLisenter.setSeq(Sequence.WarningSensorNum);
        try {
            JSONObject job = jsonObj.getJSONObject("argument");
            warningSensorNumBean.setEventNum(job.optDouble("eventNum"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        socketMessageLisenter.setObject(warningSensorNumBean);
        return socketMessageLisenter;
    }

    public SocketMessageLisenter SecurityReadRecordInfo(String data){
        SocketMessageLisenter socketMessageLisenter = new SocketMessageLisenter();
        SecurityReadRecordInfoBean securityReadRecordInfoBean = new SecurityReadRecordInfoBean();
        JSONTokener jsonParser = new JSONTokener(data);
        JSONObject jsonObj = null;
        try {
            jsonObj = (JSONObject) jsonParser.nextValue();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        assert jsonObj != null;
        if (jsonObj.optString("mac")!=null){
            securityReadRecordInfoBean.setGmac(jsonObj.optString("mac"));
        }
        securityReadRecordInfoBean.setResult(jsonObj.optBoolean("result"));
        securityReadRecordInfoBean.setDescription(jsonObj.optString("description"));
        securityReadRecordInfoBean.setSequence(jsonObj.optInt("sequence"));
        securityReadRecordInfoBean.setTotal(jsonObj.optInt("total"));
        securityReadRecordInfoBean.setStart(jsonObj.optInt("start"));
        securityReadRecordInfoBean.setEnd(jsonObj.optInt("end"));
        if(jsonObj.optBoolean("result")){
            try {
                JSONObject job = jsonObj.getJSONObject("response");
                JSONArray jobarray=job.getJSONArray("content");
                List<String> content = new ArrayList<>();
                for(int i=0;i<jobarray.length();i++){
                    JSONObject jsonobj = jobarray.getJSONObject(i);
                    content.add(jsonobj.optString("log"));
                }
                securityReadRecordInfoBean.setContent(content);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        socketMessageLisenter.setObject(securityReadRecordInfoBean);
        return socketMessageLisenter;
    }
}
