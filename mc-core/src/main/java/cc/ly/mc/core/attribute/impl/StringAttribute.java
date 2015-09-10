package cc.ly.mc.core.attribute.impl;

import cc.ly.mc.core.attribute.DefaultAttribute;

import java.nio.charset.Charset;

/**
 * Created by ly on 9/9/15.
 */
public class StringAttribute extends DefaultAttribute<String> {

    private static final Charset UTF_8 = Charset.forName("UTF-8");

    /**
     * 二进制数据转StringAttribute的data
     *
     * @param payload 具体数据
     * @return string UTF-8
     */
    @Override
    public String dataFromBinary(byte[] payload) {
        if (payload == null) {
            throw new IllegalArgumentException("StringAttribute dataFromBinary payload must not be null");
        }
        return new String(payload, UTF_8);
    }

    /**
     * StringAttribute的data转二进制数据
     *
     * @param s 具体的数据，类型String
     * @return String UTF-8 字节
     */
    @Override
    public byte[] dataToBinary(String s) {
        if (s == null) {
            throw new IllegalArgumentException("StringAttribute dataToBinary s must not be null");
        }
        return s.getBytes(UTF_8);
    }
}
