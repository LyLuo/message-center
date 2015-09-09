package cc.ly.mc.core.io;

public interface FromBinary {
    /**
     * 通过二进制解析填充对象属性
     *
     * @param payload 二进制数据
     */
    void fromBinary(byte[] payload);
}
