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

    public static final int LocalLogin = 1000;
    public static final int ServerLogin = LocalLogin + 1;
    public static final int GetEPList = ServerLogin + 1;

    public static final int UpdateEPList = 50000;
    public static final int UpdatePCT501 = UpdateEPList + 1;

    public static final int WarningSensor = 60000;
    public static final int WarningSensorNum = WarningSensor + 1;
}
