package cc.ly.mc.core.attribute.impl;

import cc.ly.mc.core.attribute.AttributeFlag;
import cc.ly.mc.core.attribute.DefaultAttribute;

import java.nio.charset.Charset;

/**
 * Created by ly on 9/9/15.
 */
public class StringAttribute extends DefaultAttribute<String> {

    public StringAttribute(){}

    private static final Charset UTF_8 = Charset.forName("UTF-8");

    public StringAttribute(int code, String data) {
        super(code, AttributeFlag.STRING, data.getBytes(UTF_8).length, data);
    }

    public StringAttribute(int code, byte[] dataPayload) {
        super(code, AttributeFlag.STRING, dataPayload);
    }

    /**
     * 二进制数据转StringAttribute的data
     *
     * @param dataPayload 具体数据
     * @return string UTF-8
     */
    @Override
    public String dataFromBinary(byte[] dataPayload) {
        if (dataPayload == null) {
            throw new NullPointerException("StringAttribute dataFromBinary payload must not be null");
        }
        return new String(dataPayload, UTF_8);
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
            throw new NullPointerException("StringAttribute dataToBinary s must not be null");
        }
        return s.getBytes(UTF_8);
    }
}
