package com.woodie.http;

import com.google.gson.internal.$Gson$Types;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.Request;

/**
 * ProjectName:    SocketLib
 * Package:        com.woodie.socketlib
 * ClassName:      OkHttpDataCallBack
 * Description:
 * Author:         woodie
 * CreateDate:     2019/1/18 16:14
 * UpdateUser:     更新者
 * UpdateDate:     2019/1/18 16:14
 * UpdateRemark:   更新内容
 * Version:        1.0
 */
public abstract class OkHttpDataCallBack<T> {
    //请求数据的返回类型，包含常见的（Bean，List等）
    Type mType;

    public OkHttpDataCallBack() {
//        mType=getSuperclassTypeParameter(getClass());
    }

    /**
     * 通过反射返回想要的类型
     * @param subclass
     * @return
     */

    static Type getSuperclassTypeParameter(Class<?> subclass) {
        Type superclass = subclass.getGenericSuperclass();
        if (superclass instanceof Class) {
            throw new RuntimeException("未知返回类型");
        }
        ParameterizedType parameterized = (ParameterizedType) superclass;
        return $Gson$Types.canonicalize(parameterized.getActualTypeArguments()[0]);
    }
//    /**
//     * 在请求之前的方法，一般用于加载进度框ProgressBar展示
//     *
//     * @param request
//     */
//    public abstract void onBefore(Request request);

    public abstract void requestSuccess(T result);

//    void requestSuccess(Reader result) throws Exception;
//    void requestSuccess(InputStream result) throws Exception;
//    void requestSuccess(byte[] result) throws Exception;

    public abstract void requestFailure(Request request, IOException e);

    /**
     * 在请求之后的方法，一般用于加载框隐藏
     */
//    public abstract void onAfter();
}
