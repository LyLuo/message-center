package cc.ly.mc.core.attribute.impl;

import cc.ly.mc.core.attribute.AttributeFlag;
import cc.ly.mc.core.attribute.DefaultAttribute;

/**
 * Created by ly on 9/9/15.
 */
public class BooleanAttribute extends DefaultAttribute<Boolean> {

    public BooleanAttribute(){
        this.length = 4;
        this.flag = AttributeFlag.BOOLEAN;
    }

    /**
     * 二进制数据转BooleanAttribute的data
     *
     * @param payload 具体数据，长度必须为1
     * @return payload[0]为0返回false, 否则true
     */
    @Override
    public Boolean dataFromBinary(byte[] payload) {
        if (payload.length != 1) {
            throw new IllegalArgumentException("BooleanAttribute data length must be 1 , but It's " + payload.length);
        }
        return payload[0] != 0;
    }

    /**
     * BooleanAttribute的data转二进制数据
     *
     * @param b boolean类型
     * @return bool为false返回0，否则1
     */
    @Override
    public byte[] dataToBinary(Boolean b) {
        if (b == null) {
            throw new NullPointerException("BooleanAttribute dataToBinary b must not be null");
        }
        if (b) {
            return new byte[]{1};
        }
        return new byte[]{0};
    }
}
