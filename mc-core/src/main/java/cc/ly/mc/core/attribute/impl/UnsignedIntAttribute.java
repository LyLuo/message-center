package cc.ly.mc.core.attribute.impl;

import cc.ly.mc.core.attribute.AttributeFlag;
import cc.ly.mc.core.attribute.DefaultAttribute;
import cc.ly.mc.core.util.NumberUtils;

/**
 * Created by ly on 9/9/15.
 */
public class UnsignedIntAttribute extends DefaultAttribute<Long> {

    public UnsignedIntAttribute(){}

    public UnsignedIntAttribute(int code, Long data) {
        super(code, AttributeFlag.UNSIGNED_INT, data);
    }

    public UnsignedIntAttribute(int code, byte[] dataPayload) {
        super(code, AttributeFlag.UNSIGNED_INT, dataPayload);
    }

    @Override
    public Long dataFromBinary(byte[] dataPayload) {
        if (dataPayload.length != 4) {
            throw new IllegalArgumentException("UnsignedIntAttribute data length must be 4 , but It's " + dataPayload.length);
        }
        return NumberUtils.bytes4ToUnsignedInt(dataPayload);
    }

    @Override
    public byte[] dataToBinary(Long l) {
        if (l == null) {
            throw new NullPointerException("UnsignedIntAttribute dataToBinary l must not be null");
        }
        return NumberUtils.unsignedIntToBytes4(l);
    }
}
