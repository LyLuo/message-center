package cc.ly.mc.core.attribute.impl;

import cc.ly.mc.core.attribute.AttributeFlag;
import cc.ly.mc.core.attribute.DefaultAttribute;
import cc.ly.mc.core.util.NumberUtils;

/**
 * Created by ly on 9/9/15.
 */
public class LongAttribute extends DefaultAttribute<Long> {

    public LongAttribute(){}

    public LongAttribute(int code, Long data) {
        super(code, AttributeFlag.LONG, data);
    }

    public LongAttribute(int code, byte[] dataPayload) {
        super(code, AttributeFlag.LONG, dataPayload);
    }

    @Override
    public Long dataFromBinary(byte[] dataPayload) {
        if (dataPayload.length != 8) {
            throw new IllegalArgumentException("LongAttribute data length must be 8 , but It's " + dataPayload.length);
        }
        return NumberUtils.bytes8ToLong(dataPayload);
    }

    @Override
    public byte[] dataToBinary(Long l) {
        if (l == null) {
            throw new NullPointerException("LongAttribute dataToBinary l must not be null");
        }
        return NumberUtils.longToBytes8(l);
    }
}
