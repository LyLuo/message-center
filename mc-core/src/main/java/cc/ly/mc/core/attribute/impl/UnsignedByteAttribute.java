package cc.ly.mc.core.attribute.impl;

import cc.ly.mc.core.attribute.AttributeFlag;
import cc.ly.mc.core.attribute.DefaultAttribute;
import cc.ly.mc.core.util.NumberUtils;

/**
 * Created by ly on 9/9/15.
 */
public class UnsignedByteAttribute extends DefaultAttribute<Short> {

    public UnsignedByteAttribute(){
        this.length = 4;
        this.flag = AttributeFlag.UNSIGNED_BYTE;
    }

    /**
     * 二进制数据转ByteAttribute的data
     *
     * @param payload 具体数据，长度必须为1
     * @return payload[0]表示的Short类型
     */
    @Override
    public Short dataFromBinary(byte[] payload) {
        if (payload.length != 1) {
            throw new IllegalArgumentException("UnsignedByteAttribute data length must be 1 , but It's " + payload.length);
        }
        return NumberUtils.bytes1ToUnsignedByte(payload);
    }

    /**
     * ByteAttribute的data转二进制数据
     *
     * @param s Short类型
     * @return s转化为byte
     */
    @Override
    public byte[] dataToBinary(Short s) {
        if (s == null) {
            throw new IllegalArgumentException("UnsignedByteAttribute dataToBinary s must not be null");
        }
        return NumberUtils.unsignedByteToBytes1(s);
    }
}
