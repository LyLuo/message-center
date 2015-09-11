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

    @Override
    public int code() {
        return code;
    }

    @Override
    public void code(int code) {
        this.code = code;
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
    public void length(int length) {
        if(isFixedLength()){
            throw new IllegalStateException("this attribute is fixed length, can not set length");
        }
        this.length = length;
    }

    @Override
    public boolean isFixedLength(){
        return true;
    }

    @Override
    public T data() {
        return data;
    }

    @Override
    public void data(T data) {
        this.data = data;
    }

    /**
     * Attribute = code(2) + flag(1) + length(3) + data
     * Attributes.parse已经处理code, flag, length,
     * 只有处理flag才知道当前attribute的数据类型
     * @param payload data 二进制数据
     */
    @Override
    public void fromBinary(byte[] payload) {
        data(dataFromBinary(payload));
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
        if(!isFixedLength()) {
            buffer.put(NumberUtils.intToBytes3(length()));
        }
        buffer.put(dataToBinary(data()));
        return buffer.array();
    }

    public abstract byte[] dataToBinary(T t);

}

