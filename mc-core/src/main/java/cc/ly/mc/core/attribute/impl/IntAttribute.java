package cc.ly.mc.core.attribute.impl;

import cc.ly.mc.core.attribute.AttributeFlag;
import cc.ly.mc.core.attribute.DefaultAttribute;
import cc.ly.mc.core.util.NumberUtils;

/**
 * Created by ly on 9/9/15.
 */
public class IntAttribute extends DefaultAttribute<Integer> {

    public IntAttribute(){}

    public IntAttribute(int code, Integer data) {
        super(code, AttributeFlag.INT, data);
    }

    public IntAttribute(int code, byte[] dataPayload) {
        super(code, AttributeFlag.INT, dataPayload);
    }

    @Override
    public Integer dataFromBinary(byte[] dataPayload) {
        if (dataPayload.length != 4) {
            throw new IllegalArgumentException("IntAttribute data length must be 4 , but It's " + dataPayload.length);
        }
        return NumberUtils.bytes4ToInt(dataPayload);
    }

    @Override
    public byte[] dataToBinary(Integer i) {
        if (i == null) {
            throw new NullPointerException("IntAttribute dataToBinary i must not be null");
        }
        return NumberUtils.intToBytes4(i);
    }
}
