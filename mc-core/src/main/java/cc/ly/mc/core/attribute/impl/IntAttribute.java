package cc.ly.mc.core.attribute.impl;

import cc.ly.mc.core.attribute.DefaultAttribute;
import cc.ly.mc.core.util.NumberUtils;

/**
 * Created by ly on 9/9/15.
 */
public class IntAttribute extends DefaultAttribute<Integer> {

    @Override
    public Integer dataFromBinary(byte[] payload) {
        if (payload.length != 4) {
            throw new IllegalArgumentException("IntAttribute data length must be 4 , It's " + payload.length);
        }
        return NumberUtils.bytes4ToInt(payload);
    }

    @Override
    public byte[] dataToBinary(Integer i) {
        if (i == null) {
            throw new IllegalArgumentException("IntAttribute dataToBinary i must not be null");
        }
        return NumberUtils.intToBytes4(i);
    }
}
