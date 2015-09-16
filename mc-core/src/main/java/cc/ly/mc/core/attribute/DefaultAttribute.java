package cc.ly.mc.core.attribute;

import cc.ly.mc.core.util.NumberUtils;

import java.nio.ByteBuffer;

/**
 * Created by ly on 9/8/15.
 */
public abstract class DefaultAttribute<T> implements Attribute<T> {

    protected int code;

    protected AttributeFlag flag;

    protected int length;

    protected T data;

    public DefaultAttribute(){}

    public DefaultAttribute(int code, AttributeFlag flag, int dataLength, T data) {
        if (flag.isFixedLength()) {
            throw new IllegalArgumentException("this attribute is fixed length, can not set length");
        }
        this.code = code;
        this.flag = flag;
        this.length = Attributes.CODE_FLAG_LENGTH_FIELDS_LENGTH + dataLength;
        this.data = data;
    }

    public DefaultAttribute(int code, AttributeFlag flag, T data) {
        this.code = code;
        this.flag = flag;
        this.length = flag.length();
        this.data = data;
    }

    public DefaultAttribute(int code, AttributeFlag flag, byte[] payload) {
        this.code = code;
        this.flag = flag;
        if (this.flag.isFixedLength()) {
            this.length = Attributes.CODE_FLAG_FIELDS_LENGTH + payload.length;
        } else {
            this.length = Attributes.CODE_FLAG_LENGTH_FIELDS_LENGTH + payload.length;
        }
        this.data = dataFromBinary(payload);
    }

    @Override
    public int code() {
        return code;
    }

    @Override
    public AttributeFlag flag() {
        return flag;
    }

    @Override
    public int length() {
        return length;
    }

    @Override
    public T data() {
        return data;
    }

    public abstract T dataFromBinary(byte[] payload);

    /**
     * Attribute = code(2) + flag(1) + length(3) + data
     *
     * @return 二进制数据(包含所有)
     */
    @Override
    public byte[] toBinary() {
        ByteBuffer buffer = ByteBuffer.allocate(length());
        buffer.put(NumberUtils.unsignedShortToBytes2(code()));
        buffer.put(flag().value());
        if (!flag().isFixedLength()) {
            buffer.put(NumberUtils.intToBytes3(length()));
        }
        buffer.put(dataToBinary(data()));
        return buffer.array();
    }

    public abstract byte[] dataToBinary(T t);

}

