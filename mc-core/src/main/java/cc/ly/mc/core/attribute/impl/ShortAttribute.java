package cc.ly.mc.core.attribute.impl;

import cc.ly.mc.core.attribute.DefaultAttribute;
import cc.ly.mc.core.util.NumberUtils;

/**
 * Created by ly on 9/9/15.
 */
public class ShortAttribute extends DefaultAttribute<Short> {

    @Override
    public Short dataFromBinary(byte[] payload) {
        if (payload.length != 2) {
            throw new IllegalArgumentException("ShortAttribute data length must be 2 , It's " + payload.length);
        }
        return NumberUtils.bytes2ToShort(payload);
    }

    @Override
    public byte[] dataToBinary(Short s) {
        if (s == null) {
            throw new IllegalArgumentException("ShortAttribute dataToBinary s must not be null");
        }
        return NumberUtils.shortToBytes2(s);
    }
}
