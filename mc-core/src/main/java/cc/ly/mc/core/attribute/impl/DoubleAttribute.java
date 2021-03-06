package cc.ly.mc.core.attribute.impl;

import cc.ly.mc.core.attribute.AttributeFlag;
import cc.ly.mc.core.attribute.DefaultAttribute;
import cc.ly.mc.core.util.NumberUtils;

/**
 * IEEE 754 双精度浮点数
 * Created by ly on 9/9/15.
 */
public class DoubleAttribute extends DefaultAttribute<Double> {

    public DoubleAttribute(){}

    public DoubleAttribute(int code, Double data) {
        super(code, AttributeFlag.DOUBLE, data);
    }

    public DoubleAttribute(int code, byte[] dataPayload) {
        super(code, AttributeFlag.DOUBLE, dataPayload);
    }

    @Override
    public Double dataFromBinary(byte[] dataPayload) {
        if (dataPayload.length != 8) {
            throw new IllegalArgumentException("LongAttribute data length must be 8 , but It's " + dataPayload.length);
        }
        return Double.longBitsToDouble(NumberUtils.bytes8ToLong(dataPayload));
    }

    @Override
    public byte[] dataToBinary(Double d) {
        if (d == null) {
            throw new NullPointerException("DoubleAttribute dataToBinary d must not be null");
        }
        return NumberUtils.longToBytes8(Double.doubleToRawLongBits(d));
    }
}
