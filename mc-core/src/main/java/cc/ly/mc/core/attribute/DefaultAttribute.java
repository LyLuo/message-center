package cc.ly.mc.core.attribute;

import cc.ly.mc.core.util.NumberUtils;

import java.nio.ByteBuffer;

/**
 * Created by ly on 9/8/15.
 */
public abstract class DefaultAttribute<T> implements Attribute<T> {

    private int code;

    private AttributeFlag flag;

    private int length;

    private T data;

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
    public void flag(AttributeFlag flag) {
        this.flag = flag;
    }

    @Override
    public int length() {
        return length;
    }

    @Override
    public void length(int length) {
        this.length = length;
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
     *
     * @param payload 二进制数据(包含所有)
     */
    @Override
    public void fromBinary(byte[] payload) {
        ByteBuffer buffer = ByteBuffer.wrap(payload);
        buffer.flip();
        //parse code
        byte[] codePayload = new byte[2];
        buffer.get(codePayload);
        code(NumberUtils.bytes2ToShort(codePayload));
        //parse flag
        flag(AttributeFlag.fromBinary(buffer.get()));
        //parse length
        byte[] lengthPayload = new byte[3];
        buffer.get(lengthPayload);
        length(NumberUtils.bytes3ToInt(lengthPayload));
        if (payload.length != length()) {
            throw new IllegalArgumentException("data's length " + payload.length + " + not equal length attribute " + length());
        }
        //parse data
        //3是code(2)+flag(1)的长度
        byte[] dataPayload = new byte[length - 3];
        buffer.get(dataPayload);
        data(dataFromBinary(dataPayload));
    }

    public abstract T dataFromBinary(byte[] payload);

    /**
     * Attribute = code(2) + flag(1) + length(3) + data
     *
     * @return 二进制数据(包含所有)
     */
    @Override
    public byte[] toBinary() {
        ByteBuffer buffer = ByteBuffer.allocate(length);
        buffer.put(NumberUtils.unsignedShortToBytes2(code()));
        buffer.put(flag().value());
        buffer.put(NumberUtils.intToBytes3(length()));
        buffer.put(dataToBinary(data()));
        return buffer.array();
    }

    public abstract byte[] dataToBinary(T t);
}

