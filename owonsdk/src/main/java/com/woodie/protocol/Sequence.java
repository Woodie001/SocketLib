package com.woodie.protocol;

/**
 * ProjectName:    SocketLib
 * Package:        com.woodie.socketlib
 * ClassName:      Sequence
 * Description:
 * Author:         woodie
 * CreateDate:     2019/1/17 14:09
 * UpdateUser:     更新者
 * UpdateDate:     2019/1/17 14:09
 * UpdateRemark:   更新内容
 * Version:        1.0
 */
public class Sequence {

    public static final int OWONLocalLogin = 1000;
    public static final int OWONServerLogin = OWONLocalLogin + 1;
    public static final int OWONGetEPList = OWONServerLogin + 1;

    public static final int OWONWIFIConfigSTA = OWONGetEPList + 1;
    public static final int OWONWIFISetupDomainName = OWONWIFIConfigSTA + 1;
    public static final int OWONWIFIRegisterDevice = OWONWIFISetupDomainName + 1;

    public static final int OWONQueryGatewayList = OWONWIFIRegisterDevice + 1;
    public static final int OWONUnbindGateway = OWONQueryGatewayList + 1;
    public static final int OWONRenameGateway = OWONUnbindGateway + 1;

    public static final int OWONUpdateEPList = 50000;
    public static final int OWONUpdatePCT501 = OWONUpdateEPList + 1;

    public static final int OWONWarningSensor = 60000;
    public static final int OWONWarningSensorNum = OWONWarningSensor + 1;
}
