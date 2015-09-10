package cc.ly.mc.core.attribute.impl;

import cc.ly.mc.core.attribute.DefaultAttribute;
import cc.ly.mc.core.util.NumberUtils;

/**
 * IEEE 754 单精度浮点数
 * Created by ly on 9/9/15.
 */
public class FloatAttribute extends DefaultAttribute<Float> {

    @Override
    public Float dataFromBinary(byte[] payload) {
        if (payload.length != 4) {
            throw new IllegalArgumentException("IntAttribute data length must be 4 , It's " + payload.length);
        }
        return Float.intBitsToFloat(NumberUtils.bytes4ToInt(payload));

    }

    @Override
    public byte[] dataToBinary(Float f) {
        if (f == null) {
            throw new IllegalArgumentException("FloatAttribute dataToBinary f must not be null");
        }
        return NumberUtils.intToBytes4(Float.floatToRawIntBits(f));
    }

}
