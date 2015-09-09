package cc.ly.mc.core.attribute.impl;

import cc.ly.mc.core.attribute.DefaultAttribute;
import cc.ly.mc.core.util.NumberUtils;

/**
 * Created by ly on 9/9/15.
 */
public class LongAttribute extends DefaultAttribute<Long> {

    @Override
    public Long dataFromBinary(byte[] payload) {
        if (payload.length != 8) {
            throw new IllegalArgumentException("LongAttribute data length must be 8 , It's " + payload.length);
        }
        return NumberUtils.bytes8ToLong(payload);
    }

    @Override
    public byte[] dataToBinary(Long l) {
        return NumberUtils.longToBytes8(l);
    }
}
