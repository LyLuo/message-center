package cc.ly.mc.core.attribute.impl;

import cc.ly.mc.core.attribute.AttributeFlag;
import cc.ly.mc.core.attribute.DefaultAttribute;
import cc.ly.mc.core.util.NumberUtils;

/**
 * Created by ly on 9/9/15.
 */
public class UnsignedLongAttribute extends DefaultAttribute<String> {

    public UnsignedLongAttribute(){}

    public UnsignedLongAttribute(int code, String data) {
        super(code, AttributeFlag.UNSIGNED_LONG, data);
    }

    public UnsignedLongAttribute(int code, byte[] dataPayload) {
        super(code, AttributeFlag.UNSIGNED_LONG, dataPayload);
    }

    @Override
    public String dataFromBinary(byte[] dataPayload) {
        if (dataPayload.length != 8) {
            throw new IllegalArgumentException("UnsignedIntAttribute data length must be 8 , but It's " + dataPayload.length);
        }
        return NumberUtils.bytes8ToUnsignedLong(dataPayload);
    }

    @Override
    public byte[] dataToBinary(String s) {
        if (s == null) {
            throw new NullPointerException("UnsignedLongAttribute dataToBinary s must not be null");
        }
        return NumberUtils.unsignedLongToBytes8(s);
    }
}
