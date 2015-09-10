package cc.ly.mc.core.attribute.impl;

import cc.ly.mc.core.attribute.DefaultAttribute;
import cc.ly.mc.core.util.NumberUtils;

/**
 * IEEE 754 双精度浮点数
 * Created by ly on 9/9/15.
 */
public class DoubleAttribute extends DefaultAttribute<Double> {

    @Override
    public Double dataFromBinary(byte[] payload) {
        if (payload.length != 8) {
            throw new IllegalArgumentException("LongAttribute data length must be 8 , It's " + payload.length);
        }
        return Double.longBitsToDouble(NumberUtils.bytes8ToLong(payload));
    }

    @Override
    public byte[] dataToBinary(Double d) {
        if (d == null) {
            throw new IllegalArgumentException("DoubleAttribute dataToBinary d must not be null");
        }
        return NumberUtils.longToBytes8(Double.doubleToRawLongBits(d));
    }
}
