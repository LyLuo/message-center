package cc.ly.mc.core.attribute.impl;

import cc.ly.mc.core.attribute.DefaultAttribute;
import cc.ly.mc.core.util.NumberUtils;

/**
 * Created by ly on 9/9/15.
 */
public class UnsignedLongAttribute extends DefaultAttribute<String> {

    @Override
    public String dataFromBinary(byte[] payload) {
        if (payload.length != 8) {
            throw new IllegalArgumentException("UnsignedIntAttribute data length must be 8 , It's " + payload.length);
        }
        return NumberUtils.bytes8ToUnsignedLong(payload);
    }

    @Override
    public byte[] dataToBinary(String s) {
        return NumberUtils.unsignedLongToBytes8(s);
    }
}
