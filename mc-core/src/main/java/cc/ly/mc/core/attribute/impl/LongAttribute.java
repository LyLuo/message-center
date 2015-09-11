package cc.ly.mc.core.attribute.impl;

import cc.ly.mc.core.attribute.AttributeFlag;
import cc.ly.mc.core.attribute.DefaultAttribute;
import cc.ly.mc.core.util.NumberUtils;

/**
 * Created by ly on 9/9/15.
 */
public class LongAttribute extends DefaultAttribute<Long> {

    public LongAttribute(){
        this.length = 11;
        this.flag = AttributeFlag.LONG;
    }

    @Override
    public Long dataFromBinary(byte[] payload) {
        if (payload.length != 8) {
            throw new IllegalArgumentException("LongAttribute data length must be 8 , but It's " + payload.length);
        }
        return NumberUtils.bytes8ToLong(payload);
    }

    @Override
    public byte[] dataToBinary(Long l) {
        if (l == null) {
            throw new IllegalArgumentException("LongAttribute dataToBinary l must not be null");
        }
        return NumberUtils.longToBytes8(l);
    }
}
