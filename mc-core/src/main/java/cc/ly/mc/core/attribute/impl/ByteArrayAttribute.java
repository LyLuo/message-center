package cc.ly.mc.core.attribute.impl;

import cc.ly.mc.core.attribute.AttributeFlag;
import cc.ly.mc.core.attribute.DefaultAttribute;

/**
 * Created by ly on 9/9/15.
 */
public class ByteArrayAttribute extends DefaultAttribute<byte[]> {

    public ByteArrayAttribute(){}

    public ByteArrayAttribute(int code, byte[] dataPayload) {
        super(code, AttributeFlag.BYTE_ARRAY, dataPayload.length, dataPayload);
    }

    /**
     * 二进制数据转IgnoredDataTypeAttribute的data
     *
     * @param dataPayload 具体数据
     * @return 复制payload数据并返回
     */
    @Override
    public byte[] dataFromBinary(byte[] dataPayload) {
        if (dataPayload == null) {
            throw new NullPointerException("IgnoredDataTypeAttribute dataFromBinary payload must not be null");
        }
        byte[] bytes = new byte[dataPayload.length];
        System.arraycopy(dataPayload, 0, bytes, 0, dataPayload.length);
        return bytes;
    }

    /**
     * IgnoredDataTypeAttribute的的data转二进制数据
     *
     * @param payload IgnoredDataTypeAttribute的数据，类型byte[]
     * @return 复制payload数据并返回
     */
    @Override
    public byte[] dataToBinary(byte[] payload) {
        if (payload == null) {
            throw new NullPointerException("IgnoredDataTypeAttribute dataToBinary payload must not be null");
        }
        byte[] bytes = new byte[payload.length];
        System.arraycopy(payload, 0, bytes, 0, payload.length);
        return bytes;
    }
}
