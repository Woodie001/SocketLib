package com.woodie.protocol;

import com.woodie.bean.OWONGetEPListBean;
import com.woodie.bean.OWONBaseBean;
import com.woodie.bean.OWONLocalLoginBean;
import com.woodie.bean.OWONLoginAccountBean;
import com.woodie.bean.OWONQueryGatewayListBean;
import com.woodie.bean.OWONRenameGatewayBean;
import com.woodie.bean.OWONSecurityReadRecordInfoBean;
import com.woodie.bean.OWONServerLoginBean;
import com.woodie.bean.OWONUpdateEPListBean;
import com.woodie.bean.OWONUpdateGatewayStateBean;
import com.woodie.bean.OWONUpdatePCT501Bean;
import com.woodie.bean.OWONWarningSensorBean;
import com.woodie.bean.OWONWarningSensorNumBean;

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
    public SocketMessageLisenter OWONLocalLogin(String data) throws JSONException{
        SocketMessageLisenter socketMessageLisenter = new SocketMessageLisenter();
        OWONLocalLoginBean owonLocalLoginBean = new OWONLocalLoginBean();
        JSONTokener jsonParser = new JSONTokener(data);
        JSONObject jsonObj = (JSONObject) jsonParser.nextValue();
        owonLocalLoginBean.setResult(jsonObj.optBoolean("result"));
        owonLocalLoginBean.setDescription(jsonObj.optString("description"));
        owonLocalLoginBean.setSequence(Sequence.OWONLocalLogin);
        socketMessageLisenter.setSeq(Sequence.OWONLocalLogin);
        if((jsonObj.optBoolean("result"))){
            JSONObject job=jsonObj.getJSONObject("response");
            owonLocalLoginBean.setSession(job.optString("session"));
            owonLocalLoginBean.setGmac(job.optString("mac"));
            owonLocalLoginBean.setVersion(job.optString("version"));
            owonLocalLoginBean.setVersionnum(job.optInt("versionnum"));
            owonLocalLoginBean.setDeviceType(job.optInt("deviceType"));
            owonLocalLoginBean.setChiptype(job.optString("chiptype"));
            owonLocalLoginBean.setWifitype(job.optInt("wifitype"));
            owonLocalLoginBean.setUtc0(job.optInt("utc0"));
            owonLocalLoginBean.setTimezone(job.optDouble("timezone"));
            owonLocalLoginBean.setDst(job.optBoolean("dst"));
            owonLocalLoginBean.setArea(job.optString("area"));
            owonLocalLoginBean.setDevModel(job.optString("devModel"));
            socketMessageLisenter.setObject(owonLocalLoginBean);
        }
        return socketMessageLisenter;
    }
    public SocketMessageLisenter OWONLoginAccount(String data){
        SocketMessageLisenter socketMessageLisenter = new SocketMessageLisenter();
        OWONLoginAccountBean loginAccountBean = new OWONLoginAccountBean();
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
    public SocketMessageLisenter OWONServerLogin(String data) throws JSONException{
        SocketMessageLisenter socketMessageLisenter = new SocketMessageLisenter();
        OWONServerLoginBean serverLoginBean = new OWONServerLoginBean();
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
            socketMessageLisenter.setSeq(serverLoginBean.getSequence());
            socketMessageLisenter.setObject(serverLoginBean);
        return socketMessageLisenter;
    }

    public SocketMessageLisenter OWONBaseBean(String data){
        SocketMessageLisenter socketMessageLisenter=new SocketMessageLisenter();
        JSONTokener jsonParser = new JSONTokener(data);
        OWONBaseBean baseBean = new OWONBaseBean();
        try {
            JSONObject jsonObj = (JSONObject) jsonParser.nextValue();
            baseBean.setResult(jsonObj.optBoolean("result"));
            baseBean.setDescription(jsonObj.optString("description"));
            baseBean.setSequence(jsonObj.optInt("sequence"));
            socketMessageLisenter.setSeq(jsonObj.optInt("sequence"));
            socketMessageLisenter.setObject(baseBean);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return socketMessageLisenter;
    }

    public SocketMessageLisenter OWONQueryGatewayList(String data) throws JSONException{
        SocketMessageLisenter socketMessageLisenter=new SocketMessageLisenter();
        OWONQueryGatewayListBean owonQueryGatewayListBean = new OWONQueryGatewayListBean();
        JSONTokener jsonParser = new JSONTokener(data);
        JSONObject jsonObj = (JSONObject) jsonParser.nextValue();
        owonQueryGatewayListBean.setCode(jsonObj.optInt("code"));
        owonQueryGatewayListBean.setMsg(jsonObj.optString("msg"));
        owonQueryGatewayListBean.setSequence(jsonObj.optInt("sequence"));
        socketMessageLisenter.setSeq(jsonObj.optInt("sequence"));
        JSONObject res=jsonObj.getJSONObject("response");
        owonQueryGatewayListBean.setPageno(res.optInt("pageno"));
        owonQueryGatewayListBean.setPagesize(res.optInt("pagesize"));
        owonQueryGatewayListBean.setTotal(res.optInt("total"));
        JSONArray jsonArray=res.optJSONArray("rows");
        List<String> name = new ArrayList<>();
        List<String> mac = new ArrayList<>();
        List<Integer> online = new ArrayList<>();
        List<String> gversion = new ArrayList<>();
        List<String> area = new ArrayList<>();
        List<String> chiptype = new ArrayList<>();
        List<Integer> devicetype = new ArrayList<>();
        List<String> devmodel = new ArrayList<>();
        List<Double> timezone = new ArrayList<>();
        List<Integer> versionnum = new ArrayList<>();
        List<Integer> wifitype = new ArrayList<>();
        for(int i=0;i<jsonArray.length();i++){
            JSONObject jsonobj = jsonArray.getJSONObject(i);
            name.add(jsonobj.optString("name"));
            mac.add(jsonobj.optString("mac"));
            online.add(jsonobj.optInt("online"));
            gversion.add(jsonobj.optString("gversion"));
            area.add(jsonobj.optString("area"));
            chiptype.add(jsonobj.optString("chiptype"));
            devicetype.add(jsonobj.optInt("devicetype"));
            devmodel.add(jsonobj.optString("devmodel"));
            timezone.add(jsonObj.optDouble("timezone"));
            versionnum.add(jsonobj.optInt("versionnum"));
            wifitype.add(jsonobj.optInt("wifitype"));
        }
        owonQueryGatewayListBean.setName(name);
        owonQueryGatewayListBean.setMac(mac);
        owonQueryGatewayListBean.setOnline(online);
        owonQueryGatewayListBean.setGversion(gversion);
        owonQueryGatewayListBean.setArea(area);
        owonQueryGatewayListBean.setChiptype(chiptype);
        owonQueryGatewayListBean.setDevicetype(devicetype);
        owonQueryGatewayListBean.setDevmodel(devmodel);
        owonQueryGatewayListBean.setTimezone(timezone);
        owonQueryGatewayListBean.setVersionnum(versionnum);
        owonQueryGatewayListBean.setWifitype(wifitype);
        socketMessageLisenter.setObject(owonQueryGatewayListBean);
        return socketMessageLisenter;
    }
    public SocketMessageLisenter OWONRenameGateway(String data) throws JSONException{
        SocketMessageLisenter socketMessageLisenter = new SocketMessageLisenter();
        OWONRenameGatewayBean owonRenameGatewayBean = new OWONRenameGatewayBean();
        JSONTokener jsonParser = new JSONTokener(data);
        JSONObject jsonObj = (JSONObject) jsonParser.nextValue();
        owonRenameGatewayBean.setCode(jsonObj.optInt("code"));
        owonRenameGatewayBean.setMsg(jsonObj.optString("msg"));
        owonRenameGatewayBean.setSequence(jsonObj.optInt("sequence"));
        owonRenameGatewayBean.setTs(jsonObj.optString("ts"));
        socketMessageLisenter.setSeq(jsonObj.optInt("sequence"));
        socketMessageLisenter.setObject(owonRenameGatewayBean);
        return socketMessageLisenter;
    }
    public SocketMessageLisenter OWONUnbindGateway(String data) throws JSONException{
        SocketMessageLisenter socketMessageLisenter = new SocketMessageLisenter();
        OWONRenameGatewayBean owonUnbindGatewayBean = new OWONRenameGatewayBean();
        JSONTokener jsonParser = new JSONTokener(data);
        JSONObject jsonObj = (JSONObject) jsonParser.nextValue();
        owonUnbindGatewayBean.setCode(jsonObj.optInt("code"));
        owonUnbindGatewayBean.setMsg(jsonObj.optString("msg"));
        owonUnbindGatewayBean.setSequence(jsonObj.optInt("sequence"));
        owonUnbindGatewayBean.setTs(jsonObj.optString("ts"));
        socketMessageLisenter.setSeq(jsonObj.optInt("sequence"));
        socketMessageLisenter.setObject(owonUnbindGatewayBean);
        return socketMessageLisenter;
    }

    public SocketMessageLisenter OWONUpdateGatewayState(String data) throws JSONException{
        SocketMessageLisenter socketMessageLisenter=new SocketMessageLisenter();
        OWONUpdateGatewayStateBean owonUpdateGatewayStateBean = new OWONUpdateGatewayStateBean();
        JSONTokener jsonParser = new JSONTokener(data);
        JSONObject jsonObj = (JSONObject) jsonParser.nextValue();
        owonUpdateGatewayStateBean.setMac(jsonObj.optString("mac"));
        owonUpdateGatewayStateBean.setCode(jsonObj.optInt("code"));
        owonUpdateGatewayStateBean.setMsg(jsonObj.optString("msg"));
        owonUpdateGatewayStateBean.setSequence(jsonObj.optInt("sequence"));
        owonUpdateGatewayStateBean.setTs(jsonObj.optString("ts"));
        socketMessageLisenter.setSeq(jsonObj.optInt("sequence"));
        socketMessageLisenter.setObject(owonUpdateGatewayStateBean);
        return socketMessageLisenter;
    }

    public SocketMessageLisenter OWONGetEPList(String data){
        SocketMessageLisenter socketMessageLisenter = new SocketMessageLisenter();
        OWONGetEPListBean getEPListBean = new OWONGetEPListBean();
        JSONTokener jsonParser = new JSONTokener(data);
        JSONObject jsonObj = null;
        try {
            jsonObj = (JSONObject) jsonParser.nextValue();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        assert jsonObj != null;
        if (jsonObj.optString("mac")!=null){
            getEPListBean.setGmac(jsonObj.optString("mac"));
        }
        getEPListBean.setResult(true);
        getEPListBean.setDescription(jsonObj.optString("description"));
        getEPListBean.setSequence(jsonObj.optInt("sequence"));
        socketMessageLisenter.setSeq(jsonObj.optInt("sequence"));
        try {
            JSONObject job = jsonObj.getJSONObject("response");
            getEPListBean.setTotal(job.getInt("total"));
            getEPListBean.setStart(job.getInt("start"));
            getEPListBean.setCount(job.getInt("count"));
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
            getEPListBean.setListIeee(ieee);
            getEPListBean.setListEp(ep);
            getEPListBean.setNetDeviceType(netDeviceType);
            getEPListBean.setLinkStatus(linkStatus);
            getEPListBean.setDeviceType(deviceType);
            getEPListBean.setName(name);
            getEPListBean.setIasZoneType(iasZoneType);
            getEPListBean.setProfileId(profileid);
            getEPListBean.setClusterFlag(clusterFlag);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        socketMessageLisenter.setObject(getEPListBean);
        return socketMessageLisenter;
    }

    public SocketMessageLisenter OWONUpdateEPList(String data){
        SocketMessageLisenter socketMessageLisenter = new SocketMessageLisenter();
        OWONUpdateEPListBean updateEPListBean = new OWONUpdateEPListBean();
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
        updateEPListBean.setSequence(Sequence.OWONUpdateEPList);
        socketMessageLisenter.setSeq(Sequence.OWONUpdateEPList);
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

    public SocketMessageLisenter OWONUpdatePCT501(String data) {
        SocketMessageLisenter socketMessageLisenter = new SocketMessageLisenter();
        OWONUpdatePCT501Bean updatePCT501Bean = new OWONUpdatePCT501Bean();
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
        updatePCT501Bean.setSequence(Sequence.OWONUpdatePCT501);
        socketMessageLisenter.setSeq(Sequence.OWONUpdatePCT501);
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

    public SocketMessageLisenter OWONWarningSensor(String data){
        SocketMessageLisenter socketMessageLisenter = new SocketMessageLisenter();
        OWONWarningSensorBean warningSensorBean = new OWONWarningSensorBean();
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
        warningSensorBean.setSequence(Sequence.OWONWarningSensor);
        socketMessageLisenter.setSeq(Sequence.OWONWarningSensor);
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
    public SocketMessageLisenter OWONWarningSensorNum(String data){
        SocketMessageLisenter socketMessageLisenter = new SocketMessageLisenter();
        OWONWarningSensorNumBean warningSensorNumBean = new OWONWarningSensorNumBean();
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
        warningSensorNumBean.setSequence(Sequence.OWONWarningSensorNum);
        socketMessageLisenter.setSeq(Sequence.OWONWarningSensorNum);
        try {
            JSONObject job = jsonObj.getJSONObject("argument");
            warningSensorNumBean.setEventNum(job.optDouble("eventNum"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        socketMessageLisenter.setObject(warningSensorNumBean);
        return socketMessageLisenter;
    }

    public SocketMessageLisenter OWONSecurityReadRecordInfo(String data){
        SocketMessageLisenter socketMessageLisenter = new SocketMessageLisenter();
        OWONSecurityReadRecordInfoBean securityReadRecordInfoBean = new OWONSecurityReadRecordInfoBean();
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
