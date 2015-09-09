package cc.ly.mc.core.attribute.impl;

import cc.ly.mc.core.attribute.DefaultAttribute;
import cc.ly.mc.core.util.NumberUtils;

/**
 * Created by ly on 9/9/15.
 */
public class UnsignedShortAttribute extends DefaultAttribute<Integer> {

    @Override
    public Integer dataFromBinary(byte[] payload) {
        if (payload.length != 2) {
            throw new IllegalArgumentException("UnsignedShortAttribute data length must be 2 , It's " + payload.length);
        }
        return NumberUtils.bytes2ToUnsignedShort(payload);
    }

    @Override
    public byte[] dataToBinary(Integer i) {
        return NumberUtils.unsignedShortToBytes2(i);
    }
}
