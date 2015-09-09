package cc.ly.mc.core.attribute.impl;

import cc.ly.mc.core.attribute.DefaultAttribute;

/**
 * Created by ly on 9/9/15.
 */
public class BooleanAttribute extends DefaultAttribute<Boolean> {

    /**
     * 二进制数据转BooleanAttribute的data
     *
     * @param payload 具体数据，长度必须为1
     * @return payload[0]为0返回false, 否则true
     */
    @Override
    public Boolean dataFromBinary(byte[] payload) {
        if (payload.length != 1) {
            throw new IllegalArgumentException("BooleanAttribute data length must be 1 , It's " + payload.length);
        }
        return payload[0] != 0;
    }

    /**
     * BooleanAttribute的data转二进制数据
     *
     * @param bool
     * @return bool为false返回0，否则1
     */
    @Override
    public byte[] dataToBinary(Boolean bool) {
        if (bool.booleanValue()) {
            return new byte[]{1};
        }
        return new byte[]{0};
    }
}
