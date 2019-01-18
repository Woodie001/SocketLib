package com.xuhao.didi.core.protocol;

import java.nio.ByteOrder;

/**
 * 包头数据格式
 * Created by xuhao on 2017/5/22.
 */
public class CustomIReaderProtocol implements IReaderProtocol{

    @Override
    public int getHeaderLength() {
        return 1;
    }

    @Override
    public int getBodyLength(byte[] header, ByteOrder byteOrder) {
        return header.length;
    }
}
