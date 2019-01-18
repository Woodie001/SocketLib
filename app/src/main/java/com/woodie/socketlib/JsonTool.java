package com.woodie.socketlib;

import java.util.List;
import java.util.Map;
import com.alibaba.fastjson.JSON;
import static com.alibaba.fastjson.JSON.toJSONString;

/**
 * ProjectName:    SocketLib
 * Package:        com.woodie.socketlib
 * ClassName:      JsonTool
 * Description:
 * Author:         woodie
 * CreateDate:     2019/1/18 16:43
 * UpdateUser:     更新者
 * UpdateDate:     2019/1/18 16:43
 * UpdateRemark:   更新内容
 * Version:        1.0
 */
public class JsonTool {

    public static String createJsonString(Object value) {
        return toJSONString(value);
    }

    public static <T> T json2Object(String jsonString,Class<T> clazz){
        T t=null;
        t=JSON.parseObject(jsonString, clazz);
        return t;
    }

    public static <T>List<T> json2Array(String jsonString, Class<T> clazz){
        List<T> list=null;
        list=JSON.parseArray(jsonString, clazz);
        return list;
    }

    public static <T> T map2Object(Map<?, ?> map, Class<T> clazz){
        String jsonString=createJsonString(map);
        return json2Object(jsonString, clazz);
    }
}
