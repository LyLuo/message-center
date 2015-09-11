package cc.ly.mc.core.attribute.impl;

import cc.ly.mc.core.attribute.AttributeFlag;
import cc.ly.mc.core.attribute.DefaultAttribute;
import cc.ly.mc.core.util.NumberUtils;

/**
 * Created by ly on 9/9/15.
 */
public class UnsignedIntAttribute extends DefaultAttribute<Long> {

    public UnsignedIntAttribute(){
        this.length = 7;
        this.flag = AttributeFlag.UNSIGNED_INT;
    }

    @Override
    public Long dataFromBinary(byte[] payload) {
        if (payload.length != 4) {
            throw new IllegalArgumentException("UnsignedIntAttribute data length must be 4 , but It's " + payload.length);
        }
        return NumberUtils.bytes4ToUnsignedInt(payload);
    }

    @Override
    public byte[] dataToBinary(Long l) {
        if (l == null) {
            throw new IllegalArgumentException("UnsignedIntAttribute dataToBinary l must not be null");
        }
        return NumberUtils.unsignedIntToBytes4(l);
    }
}
