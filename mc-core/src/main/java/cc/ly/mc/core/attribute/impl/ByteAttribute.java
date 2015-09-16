package cc.ly.mc.core.attribute.impl;

import cc.ly.mc.core.attribute.AttributeFlag;
import cc.ly.mc.core.attribute.DefaultAttribute;

/**
 * Created by ly on 9/9/15.
 */
public class ByteAttribute extends DefaultAttribute<Byte> {

    public ByteAttribute(){}

    public ByteAttribute(int code, Byte data) {
        super(code, AttributeFlag.BYTE, data);
    }

    public ByteAttribute(int code, byte[] dataPayload) {
        super(code, AttributeFlag.BYTE, dataPayload);
    }

    /**
     * 二进制数据转ByteAttribute的data
     *
     * @param dataPayload 具体数据，长度必须为1
     * @return payload[0]
     */
    @Override
    public Byte dataFromBinary(byte[] dataPayload) {
        if (dataPayload.length != 1) {
            throw new IllegalArgumentException("UnsignedByteAttribute data length must be 1 , but It's " + dataPayload.length);
        }
        return dataPayload[0];
    }

    /**
     * ByteAttribute的data转二进制数据
     *
     * @param b Byte类型
     * @return b
     */
    @Override
    public byte[] dataToBinary(Byte b) {
        if (b == null) {
            throw new NullPointerException("ByteAttribute dataToBinary b must not be null");
        }
        return new byte[]{b.byteValue()};
    }
}
