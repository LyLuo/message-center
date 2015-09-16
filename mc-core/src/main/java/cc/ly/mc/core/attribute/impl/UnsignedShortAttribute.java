package cc.ly.mc.core.attribute.impl;

import cc.ly.mc.core.attribute.AttributeFlag;
import cc.ly.mc.core.attribute.DefaultAttribute;
import cc.ly.mc.core.util.NumberUtils;

/**
 * Created by ly on 9/9/15.
 */
public class UnsignedShortAttribute extends DefaultAttribute<Integer> {

    public UnsignedShortAttribute(){};

    public UnsignedShortAttribute(int code, Integer data) {
        super(code, AttributeFlag.UNSIGNED_SHORT, data);
    }

    public UnsignedShortAttribute(int code, byte[] dataPayload) {
        super(code, AttributeFlag.UNSIGNED_SHORT, dataPayload);
    }

    @Override
    public Integer dataFromBinary(byte[] dataPayload) {
        if (dataPayload.length != 2) {
            throw new IllegalArgumentException("UnsignedShortAttribute data length must be 2 , but It's " + dataPayload.length);
        }
        return NumberUtils.bytes2ToUnsignedShort(dataPayload);
    }

    @Override
    public byte[] dataToBinary(Integer i) {
        if (i == null) {
            throw new NullPointerException("UnsignedShortAttribute dataToBinary i must not be null");
        }
        return NumberUtils.unsignedShortToBytes2(i);
    }
}
