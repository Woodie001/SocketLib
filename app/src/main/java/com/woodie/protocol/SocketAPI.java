package com.woodie.protocol;

import com.blankj.utilcode.util.EncryptUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

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

    public SocketMessageLisenter RecieveData(String data) {
        SocketMessageLisenter map = new SocketMessageLisenter();
        int start1 = data.indexOf(start);
        int end1 = data.indexOf(end);
        if (start1 < 0 || end1 < 0) {
            return null;
        }
        String startBuf = data.substring(start1, start1 + 1);
        String endBuf = data.substring(end1, end1 + 1);
        if (startBuf.equals(start) && endBuf.equals(end)) {
            data = data.substring(1, data.length() - 1);
            JSONTokener jsonParser = new JSONTokener(data);
            JSONObject jsonObj = null;
            try {
                jsonObj = (JSONObject) jsonParser.nextValue();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            assert jsonObj != null;
            String type = jsonObj.optString("type");
            if (type.equals("update")) {
                String comm = jsonObj.optString("command");
                Resolve resolve = new Resolve();
                if (comm.equals("epList")) {
                    map = resolve.UpdateEPList(data);
                } else if (comm.equals("pct501")) {
                    map = resolve.UpdatePCT501(data);
                }
//                } else if (comm.equals("pct503")) {
//                    map = resolve.UpdatePCT503(data);
//                } else if (comm.equals("sensor")) {
//                    map = resolve.UpdateSensor(data);
//                } else if (comm.equals("switchgear")) {
//                    map = resolve.UpdateSwitchgear(data);
//                } else if (comm.equals("epList")) {
//                    map = resolve.UpdateEPList(data);
//                } else if (comm.equals("wa201")) {
//                    map = resolve.UpdateWa201(data);
//                } else if (comm.equals("wa201Scan")) {
//                    map = resolve.UpdateWa201Scan(data);
//                } else if (comm.equals("wa201Learn")) {
//                    map = resolve.UpdateWa201Learn(data);
//                } else if (comm.equals("defence")) {
//                    map = resolve.UpdateSensorStatus(data);
//                } else if (comm.equals("energy")) {
//                    map = resolve.UpdateSmartPlugPower(data);
//                } else if (comm.equals("upgrade")) {
//                    map = resolve.UpdateUpgradeStatus(data);
//                } else if (comm.equals("timeInfo")) {
//                    map = resolve.UpdateTimeInfo(data);
//                } else if (comm.equals("sirenWarning")) {
//                    map = resolve.UpdateSirenSensor(data);
//                } else if (comm.equals("temp")) {
//                    map = resolve.UpdateTempSensor(data);
//                } else if (comm.equals("humi")) {
//                    map = resolve.UpdateHumSensor(data);
//                } else if (comm.equals("IllumNotify")) {
//                    map = resolve.UpdateIllunimationSensor(data);
//                }  else if (comm.equals("SumEnergyConsumed")) {
//                    map = resolve.UpdateThreePhasePlugPower(data);
//                } else if (comm.equals("InsActivePower")) {
//                    map = resolve.UpdateThreePhasePlugPower(data);
//                } else if (comm.equals("ac211")) {
//                    map = resolve.UpdateWa211(data);
//                } else if (comm.equals("brandSearch")) {
//                    map = resolve.UpdateWa211BrandSearch(data);
//                } else if (comm.equals("oneKeyPairing")) {
//                    map = resolve.UpdateWa211OneKeyPairing(data);
//                } else if (comm.equals("pctStatus")) {
//                    map = resolve.UpdatePCT504Status(data);
//                } else if (comm.equals("keypadLock")) {
//                    map = resolve.UpdatePCT504KeypadLock(data);
//                } else if (comm.equals("operationalMode")) {
//                    map = resolve.UpdatePCT504OperationalMode(data);
//                }
            } else if (type.equals("warning")) {
                Resolve resolve = new Resolve();
                if (jsonObj.optString("command").equals("sync")) {
                    map = resolve.SecurityReadRecordInfo(data);
                } else {
                    try {
                        JSONObject job = jsonObj.getJSONObject("response");
                        if (job.optString("report").equals("status")) {
                            map = resolve.WarningSensor(data);
                        } else if (job.optString("report").equals("eventNum")) {
                            map = resolve.WarningSensorNum(data);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            } else if (type.equals("sensor")) {
                Resolve resolve = new Resolve();
                map = resolve.SecurityReadRecordInfo(data);
            } else {
                Resolve resolve = new Resolve();
                switch (jsonObj.optInt("sequence")) {
//                    case Sequence.SecurityQuerySchedule:
//                        map = resolve.SecurityQuerySchedule(data, timezone, dst, area);
//                        break;
//                    case Sequence.SmartLightQuerySchedule:
//                        map = resolve.SmartLightQuerySchedule(data, timezone, dst, area);
//                        break;
//                    case Sequence.SmartPlugQuerySchedule:
//                        map = resolve.SmartPlugQuerySchedule(data, timezone, dst, area);
//                        break;
//                    case Sequence.TrackPlugQuerySchedule:
//                        map = resolve.TrackPlugQuerySchedule(data, timezone, dst, area);
//                        break;
//                    case Sequence.WA201QuerySchedule:
//                        map = resolve.WA201QuerySchedule(data, timezone, dst, area);
//                        break;
//                    case Sequence.WA211QuerySchedule:
//                        map = resolve.WA211QuerySchedule(data, timezone, dst, area);
//                        break;
//                    case Sequence.GeneralLinkageQuerySchedule:
//                        map =  resolve.GeneralLinkageQuerySchedule(data,timezone,dst,area);
//                        break;
//                    case  Sequence.SmartLightQueryLinkageSchedule:
//                        map =  resolve.SmartLightQueryLinkageSchedule(data,timezone,dst,area);
//                        break;
                    default:
                        try {
                            map = Resolve(data);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        break;
                }
            }
        }
        return map;
    }

    public Object RecieveData(String data, float timezone, boolean dst, String area) throws JSONException {
        Object map = null;
        int start1 = data.indexOf(start);
        int end1 = data.indexOf(end);
        if (start1 < 0 || end1 < 0) {
            return map;
        }
        String startBuf = data.substring(start1, start1 + 1);
        String endBuf = data.substring(end1, end1 + 1);
        if (startBuf.equals(start) && endBuf.equals(end)) {
            data = data.substring(1, data.length() - 1);
            JSONTokener jsonParser = new JSONTokener(data);
            JSONObject jsonObj = (JSONObject) jsonParser.nextValue();
            String type = jsonObj.optString("type");
            if (type.equals("update")) {
                String comm = jsonObj.optString("command");
//                Resolve resolve = new Resolve();
//                if (comm.equals("pct501")) {
//                    map = resolve.UpdatePCT501(data);
//                } else if (comm.equals("sensor")) {
//                    map = resolve.UpdateSensor(data);
//                } else if (comm.equals("switchgear")) {
//                    map = resolve.UpdateSwitchgear(data);
//                } else if (comm.equals("epList")) {
//                    map = resolve.UpdateEPList(data);
//                } else if (comm.equals("wa201")) {
//                    map = resolve.UpdateWa201(data);
//                } else if (comm.equals("wa201Scan")) {
//                    map = resolve.UpdateWa201Scan(data);
//                } else if (comm.equals("wa201Learn")) {
//                    map = resolve.UpdateWa201Learn(data);
//                } else if (comm.equals("defence")) {
//                    map = resolve.UpdateSensorStatus(data);
//                } else if (comm.equals("energy")) {
//                    map = resolve.UpdateSmartPlugPower(data);
//                } else if (comm.equals("upgrade")) {
//                    map = resolve.UpdateUpgradeStatus(data);
//                } else if (comm.equals("timeInfo")) {
//                    map = resolve.UpdateTimeInfo(data);
//                } else if (comm.equals("sirenWarning")) {
//                    map = resolve.UpdateSirenSensor(data);
//                } else if (comm.equals("sensorTemp ")) {
//                    map = resolve.UpdateTempSensor(data);
//                } else if (comm.equals("sensorHumi")) {
//                    map = resolve.UpdateHumSensor(data);
//                } else if (comm.equals("ac211")) {
//                    map = resolve.UpdateWa211(data);
//                } else if (comm.equals("brandSearch")) {
//                    map = resolve.UpdateWa211BrandSearch(data);
//                } else if (comm.equals("oneKeyPairing")) {
//                    map = resolve.UpdateWa211OneKeyPairing(data);
//                } else if (comm.equals("pctStatus")) {
//                    map = resolve.UpdatePCT504Status(data);
//                } else if (comm.equals("keypadLock")) {
//                    map = resolve.UpdatePCT504KeypadLock(data);
//                } else if (comm.equals("operationalMode")) {
//                    map = resolve.UpdatePCT504OperationalMode(data);
//                }
            } else if (type.equals("warning")) {
                Resolve resolve = new Resolve();
                if (jsonObj.optString("command").equals("sync")) {
                    map = resolve.SecurityReadRecordInfo(data);
                } else {
                    JSONObject job = jsonObj.getJSONObject("response");
                    if (job.optString("report").equals("status")) {
                        map = resolve.WarningSensor(data);
                    } else if (job.optString("report").equals("eventNum")) {
                        map = resolve.WarningSensorNum(data);
                    }
                }
            } else if (type.equals("sensor")) {
//                Resolve resolve = new Resolve();
//                map = resolve.SecurityReadRecordInfo(data);
            } else {
//
            }
        }
        return map;
    }

    private SocketMessageLisenter Resolve(String data) throws JSONException {
        SocketMessageLisenter map = new SocketMessageLisenter();
        Resolve resolve = new Resolve();
        JSONTokener jsonParser = new JSONTokener(data);
        JSONObject jsonObj = (JSONObject) jsonParser.nextValue();
            int sequence = jsonObj.optInt("sequence");
            String desc = jsonObj.optString("description");
            if (desc.equals("login success") || desc.equals("login failed"))
                sequence = Sequence.LocalLogin;
            switch (sequence) {
                case Sequence.LocalLogin:
//                    map = resolve.LocalLogin(data);
                    break;
                case Sequence.ServerLogin:
                    map = resolve.ServerLogin(data);
                    break;
                case Sequence.GetEPList:
                    map = resolve.GetEPList(data);
                    break;
            }
        return map;
    }

    public String LoginLocalSocket(String username, String password) {
        JSONObject mapbuf = new JSONObject();
        try {
            mapbuf.put("type", "login");
            mapbuf.put("sequence", Sequence.LocalLogin);
            JSONObject buf1 = new JSONObject();
            buf1.put("username", username);
            buf1.put("password", password);
            mapbuf.put("argument", buf1);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return start + mapbuf.toString() + end;
    }
    public String LoginServerSocket(String account, String password) {
        JSONObject mapbuf = new JSONObject();
        try {
            mapbuf.put("type", "/nt/user/slogin");
            mapbuf.put("token", "");
            mapbuf.put("ts", getTimeinterval());
            mapbuf.put("sequence", Sequence.ServerLogin);
            JSONObject buf1 = new JSONObject();
            buf1.put("account", account);
            buf1.put("password", EncryptUtils.encryptMD5ToString(password).toLowerCase());
            buf1.put("os", "Android");
            buf1.put("cversion", "0.0.1");
            buf1.put("rcvall", 1);
            mapbuf.put("param", buf1);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return start + mapbuf.toString() + end;
    }
    public String LoginServerAccountHttp(String account, String password) {
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

    public String LogoutServerAccountHttp(String account, String token) {
        JSONObject mapbuf = new JSONObject();
        try {
            mapbuf.put("type", "/user/applogout");
            mapbuf.put("token", token);
            mapbuf.put("ts", getTimeinterval());
            JSONObject buf1 = new JSONObject();
            buf1.put("account", account);
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

    /**
     * 与服务器的心跳包发送
     */
    public String sendHeartFromPhoneToServer(String session) {
        JSONObject mapbuf = new JSONObject();
        try {
            mapbuf.put("type", "heart");
            mapbuf.put("session", session);
            mapbuf.put("mac", "");
            mapbuf.put("phoneType", "android");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return start + mapbuf.toString() + end;
    }
}
