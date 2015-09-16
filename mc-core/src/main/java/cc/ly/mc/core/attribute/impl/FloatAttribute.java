package cc.ly.mc.core.attribute.impl;

import cc.ly.mc.core.attribute.AttributeFlag;
import cc.ly.mc.core.attribute.DefaultAttribute;
import cc.ly.mc.core.util.NumberUtils;

/**
 * IEEE 754 单精度浮点数
 * Created by ly on 9/9/15.
 */
public class FloatAttribute extends DefaultAttribute<Float> {

    public FloatAttribute(){}

    public FloatAttribute(int code, Float data) {
        super(code, AttributeFlag.FLOAT, data);
    }

    public FloatAttribute(int code, byte[] dataPayload) {
        super(code, AttributeFlag.FLOAT, dataPayload);
    }

    @Override
    public Float dataFromBinary(byte[] dataPayload) {
        if (dataPayload.length != 4) {
            throw new IllegalArgumentException("IntAttribute data length must be 4 , but It's " + dataPayload.length);
        }
        return Float.intBitsToFloat(NumberUtils.bytes4ToInt(dataPayload));

    }

    @Override
    public byte[] dataToBinary(Float f) {
        if (f == null) {
            throw new NullPointerException("FloatAttribute dataToBinary f must not be null");
        }
        return NumberUtils.intToBytes4(Float.floatToRawIntBits(f));
    }

}
