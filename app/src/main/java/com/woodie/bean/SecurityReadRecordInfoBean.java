package com.woodie.bean;

import java.util.List;

/**
 * ProjectName:    SocketLib
 * Package:        com.woodie.bean
 * ClassName:      SecurityReadRecordInfoBean
 * Description:
 * Author:         woodie
 * CreateDate:     2019/2/25 14:08
 * UpdateUser:     更新者
 * UpdateDate:     2019/2/25 14:08
 * UpdateRemark:   更新内容
 * Version:        1.0
 */
public class SecurityReadRecordInfoBean extends BaseBean{

    private int total;
    private int start;
    private int end;
    private List<String> content;

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

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public List<String> getContent() {
        return content;
    }

    public void setContent(List<String> content) {
        this.content = content;
    }
}
