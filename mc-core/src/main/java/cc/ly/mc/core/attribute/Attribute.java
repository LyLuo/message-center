package cc.ly.mc.core.attribute;

import cc.ly.mc.core.io.ToBinary;

/**
 * 消息的属性的基础接口,未提供修改接口，Attribute为可变类型
 *
 * @author ly
 */
public interface Attribute<T> extends ToBinary {

    /**
     * @return 属性编码 无符号2个字节，java int表示
     */
    int code();

    /**
     * @return 属性的flag
     * @see AttributeFlag
     */
    AttributeFlag flag();

    /**
     * @return 属性长度 无符号3字节，java int表示，最大支持数据长度16M
     */
    int length();

    /**
     * @return 具体数据
     */
    T data();

    /**
     * 消息是否合法
     *
     * @return 消息合法返回true，否则false
     */
    default boolean valid() {
        if (code() == 0 || flag() == null || data() == null) {
            return false;
        }
        if (flag().isFixedLength() && length() <= Attributes.CODE_FLAG_FIELDS_LENGTH) {
            return false;
        }
        if (!flag().isFixedLength() && length() <= Attributes.CODE_FLAG_LENGTH_FIELDS_LENGTH) {
            return false;
        }
        return true;
    }
}
