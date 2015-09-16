package cc.ly.mc.core.attribute.impl;

import cc.ly.mc.core.attribute.AttributeFlag;
import cc.ly.mc.core.attribute.DefaultAttribute;
import cc.ly.mc.core.util.NumberUtils;

/**
 * Created by ly on 9/9/15.
 */
public class ShortAttribute extends DefaultAttribute<Short> {

    public ShortAttribute(){}

    public ShortAttribute(int code, Short data) {
        super(code, AttributeFlag.SHORT, data);
    }

    public ShortAttribute(int code, byte[] dataPayload) {
        super(code, AttributeFlag.SHORT, dataPayload);
    }

    @Override
    public Short dataFromBinary(byte[] dataPayload) {
        if (dataPayload.length != 2) {
            throw new IllegalArgumentException("ShortAttribute data length must be 2 , but It's " + dataPayload.length);
        }
        return NumberUtils.bytes2ToShort(dataPayload);
    }

    @Override
    public byte[] dataToBinary(Short s) {
        if (s == null) {
            throw new NullPointerException("ShortAttribute dataToBinary s must not be null");
        }
        return NumberUtils.shortToBytes2(s);
    }
}
