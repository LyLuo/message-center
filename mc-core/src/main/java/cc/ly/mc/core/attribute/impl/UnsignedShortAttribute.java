package cc.ly.mc.core.attribute.impl;

import cc.ly.mc.core.attribute.AttributeFlag;
import cc.ly.mc.core.attribute.DefaultAttribute;
import cc.ly.mc.core.util.NumberUtils;

/**
 * Created by ly on 9/9/15.
 */
public class UnsignedShortAttribute extends DefaultAttribute<Integer> {

    public UnsignedShortAttribute(){
        this.length = 5;
        this.flag = AttributeFlag.UNSIGNED_SHORT;
    }

    @Override
    public Integer dataFromBinary(byte[] payload) {
        if (payload.length != 2) {
            throw new IllegalArgumentException("UnsignedShortAttribute data length must be 2 , but It's " + payload.length);
        }
        return NumberUtils.bytes2ToUnsignedShort(payload);
    }

    @Override
    public byte[] dataToBinary(Integer i) {
        if (i == null) {
            throw new IllegalArgumentException("UnsignedShortAttribute dataToBinary i must not be null");
        }
        return NumberUtils.unsignedShortToBytes2(i);
    }
}
