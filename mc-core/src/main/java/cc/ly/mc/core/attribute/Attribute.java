package cc.ly.mc.core.attribute;

import cc.ly.mc.core.io.Binary;

/**
 * 消息的属性的基础接口
 *
 * @author ly
 */
public interface Attribute<T> extends Binary {

    /**
     * @return 属性编码 无符号2个字节，java int表示
     */
    int code();

    /**
     * 设置属性编码
     *
     * @para code 属性编码
     */
    void code(int code);

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
     * 设置属性长度，包含所有
     *
     * @param length 属性长度
     */
    void length(int length);

    /**
     * 是否长度固定
     *
     * @return 如果长度固定返回true，否则false
     */
    boolean isFixedLength();

    /**
     * @return 具体数据
     */
    T data();

    /**
     * @param data 具体数据
     */
    void data(T data);

    /**
     * 消息是否合法
     *
     * @return 消息合法返回true，否则false
     */
    default boolean valid() {
        if (code() == 0 || flag() == null || data() == null) {
            return false;
        }
        if (isFixedLength() && length() <= Attributes.CODE_FLAG_FIELDS_LENGTH) {
            return false;
        }
        if (!isFixedLength() && length() <= Attributes.CODE_FLAG_LENGTH_FIELDS_LENGTH) {
            return false;
        }
        return true;
    }
}
