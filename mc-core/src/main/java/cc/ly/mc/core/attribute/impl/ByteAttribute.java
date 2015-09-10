package cc.ly.mc.core.attribute.impl;

import cc.ly.mc.core.attribute.DefaultAttribute;

/**
 * Created by ly on 9/9/15.
 */
public class ByteAttribute extends DefaultAttribute<Byte> {
    /**
     * 二进制数据转ByteAttribute的data
     *
     * @param payload 具体数据，长度必须为1
     * @return payload[0]
     */
    @Override
    public Byte dataFromBinary(byte[] payload) {
        if (payload.length != 1) {
            throw new IllegalArgumentException("UnsignedByteAttribute data length must be 1 , It's " + payload.length);
        }
        return payload[0];
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
            throw new IllegalArgumentException("ByteAttribute dataToBinary b must not be null");
        }
        return new byte[]{b.byteValue()};
    }
}
