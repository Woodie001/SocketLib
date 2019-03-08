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
                    map = resolve.OWONUpdateEPList(data);
                } else if (comm.equals("pct501")) {
                    map = resolve.OWONUpdatePCT501(data);
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
                    map = resolve.OWONSecurityReadRecordInfo(data);
                } else {
                    try {
                        JSONObject job = jsonObj.getJSONObject("response");
                        if (job.optString("report").equals("status")) {
                            map = resolve.OWONWarningSensor(data);
                        } else if (job.optString("report").equals("eventNum")) {
                            map = resolve.OWONWarningSensorNum(data);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            } else if (type.equals("sensor")) {
                Resolve resolve = new Resolve();
                map = resolve.OWONSecurityReadRecordInfo(data);
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

    private SocketMessageLisenter Resolve(String data) throws JSONException {
        SocketMessageLisenter map = new SocketMessageLisenter();
        Resolve resolve = new Resolve();
        JSONTokener jsonParser = new JSONTokener(data);
        JSONObject jsonObj = (JSONObject) jsonParser.nextValue();
            int sequence = jsonObj.optInt("sequence");
            String desc = jsonObj.optString("description");
            if (desc.equals("login success") || desc.equals("login failed"))
                sequence = Sequence.OWONLocalLogin;
            switch (sequence) {
                case Sequence.OWONLocalLogin:
                    map = resolve.OWONLocalLogin(data);
                    break;
                case Sequence.OWONServerLogin:
                    map = resolve.OWONServerLogin(data);
                    break;
                case Sequence.OWONWIFIConfigSTA:
                case Sequence.OWONWIFIRegisterDevice:
                case Sequence.OWONWIFISetupDomainName:
                    map = resolve.OWONBaseBean(data);
                    break;
                case Sequence.OWONQueryGatewayList:
                    map = resolve.OWONQueryGatewayList(data);
                    break;
                case Sequence.OWONUnbindGateway:
                    map = resolve.OWONUnbindGateway(data);
                    break;
                case Sequence.OWONRenameGateway:
                    map = resolve.OWONRenameGateway(data);
                    break;
                case Sequence.OWONGetEPList:
                    map = resolve.OWONGetEPList(data);
                    break;
            }
        return map;
    }

    public String OWONLoginLocalSocket(String username, String password) {
        JSONObject mapbuf = new JSONObject();
        try {
            mapbuf.put("type", "login");
            mapbuf.put("sequence", Sequence.OWONLocalLogin);
            JSONObject buf1 = new JSONObject();
            buf1.put("username", username);
            buf1.put("password", password);
            mapbuf.put("argument", buf1);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return start + mapbuf.toString() + end;
    }
    public String OWONLoginServerSocket(String account, String password) {
        JSONObject mapbuf = new JSONObject();
        try {
            mapbuf.put("type", "/nt/user/slogin");
            mapbuf.put("token", "");
            mapbuf.put("ts", getTimeinterval());
            mapbuf.put("sequence", Sequence.OWONServerLogin);
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
    public String OWONLoginServerAccountHttp(String account, String password) {
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

    public String OWONLogoutServerAccountHttp(String account, String token) {
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

    public String OWONWIFIConfigSTA(String session, String ssid, String sskey) {
        JSONObject mapbuf = new JSONObject();
        try {
            mapbuf.put("type", "wifiConfig");
            mapbuf.put("session", session);
            mapbuf.put("command", "sta");
            mapbuf.put("sequence", Sequence.OWONWIFIConfigSTA);
            JSONObject buf1 = new JSONObject();
            buf1.put("ssid", ssid);
            buf1.put("sskey", sskey);
            mapbuf.put("argument", buf1);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return start + mapbuf.toString() + end;
    }
    public String OWONWIFISetupDomainName(String session, String name, String ip, String port, String sslport) {
        JSONObject mapbuf = new JSONObject();
        try {
            mapbuf.put("type", "netConfig");
            mapbuf.put("session", session);
            mapbuf.put("command", "config");
            mapbuf.put("sequence", Sequence.OWONWIFISetupDomainName);
            JSONObject buf1 = new JSONObject();
            if (name != null)
                buf1.put("web", name);
            else
                buf1.put("web", "");
            if (port != null)
                buf1.put("portNum", port);
            else
                buf1.put("portNum", "");
            if (ip != null)
                buf1.put("ipAddr", ip);
            else
                buf1.put("ipAddr", "");
            if (sslport != null)
                buf1.put("sslPortNum", sslport);
            else
                buf1.put("sslPortNum", "");
            mapbuf.put("argument", buf1);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return start + mapbuf.toString() + end;
    }

    public String OWONWIFIRegisterDevice(String session, String name, String password,String agentid) {
        JSONObject mapbuf = new JSONObject();
        try {
            mapbuf.put("type", "netConfig");
            mapbuf.put("session", session);
            mapbuf.put("command", "register");
            mapbuf.put("sequence", Sequence.OWONWIFIRegisterDevice);
            JSONObject buf1 = new JSONObject();
            buf1.put("username", name);
            buf1.put("password", password);
            buf1.put("agentid", agentid);
            mapbuf.put("argument", buf1);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return start + mapbuf.toString() + end;
    }

    public String OWONQueryGatewayList(String session, int pageno, int pagesize) {
        JSONObject mapbuf = new JSONObject();
        try {
            mapbuf.put("type", "/user/gateway");
            mapbuf.put("session", session);
            mapbuf.put("ts", getTimeinterval());
            mapbuf.put("sequence", Sequence.OWONQueryGatewayList);
            JSONObject buf1 = new JSONObject();
            buf1.put("pageno", pageno);
            buf1.put("pagesize", pagesize);
            mapbuf.put("param", buf1);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return start + mapbuf.toString() + end;
    }

    public String OWONBindGateway(String account, String password, String gnamne, String mac ,
                              String session,float timezone,String area) {
        JSONObject mapbuf = new JSONObject();
        try {
            mapbuf.put("type", "/nt/gateway/bind");
            mapbuf.put("ts", getTimeinterval());
            JSONObject buf1 = new JSONObject();
            buf1.put("session", session);
            buf1.put("account", account);
            buf1.put("password", password);
            buf1.put("gname", gnamne);
            buf1.put("mac", mac);
            buf1.put("timezone", timezone);
            buf1.put("area", area);
            mapbuf.put("param", buf1);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return mapbuf.toString();
    }

    public String OWONUnbindGateway(String session, String account, String mac, String token) {
        JSONObject mapbuf = new JSONObject();
        try {
            mapbuf.put("type", "/gateway/unbind");
            mapbuf.put("session", session);
            mapbuf.put("token", token);
            mapbuf.put("ts", getTimeinterval());
            mapbuf.put("sequence", Sequence.OWONUnbindGateway);
            JSONObject buf1 = new JSONObject();
            buf1.put("account", account);
            buf1.put("mac", mac);
            mapbuf.put("param", buf1);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return start + mapbuf.toString() + end;
    }

    public String OWONRenameGateway(String session, String mac, String gname, String token) {
        JSONObject mapbuf = new JSONObject();
        try {
            mapbuf.put("type", "/gateway/rename");
            mapbuf.put("session", session);
            mapbuf.put("token", token);
            mapbuf.put("ts", getTimeinterval());
            mapbuf.put("sequence", Sequence.OWONRenameGateway);
            JSONObject buf1 = new JSONObject();
            buf1.put("gname", gname);
            buf1.put("mac", mac);
            mapbuf.put("param", buf1);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return start + mapbuf.toString() + end;
    }

    public String OWONQueryGatewayState(String account, String password, String mac) {
        JSONObject mapbuf = new JSONObject();
        try {
            mapbuf.put("type", "/nt/gateway/bindstatus");
            mapbuf.put("ts", getTimeinterval());
            JSONObject buf1 = new JSONObject();
            buf1.put("account", account);
            buf1.put("password", password);
            buf1.put("mac", mac);
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
