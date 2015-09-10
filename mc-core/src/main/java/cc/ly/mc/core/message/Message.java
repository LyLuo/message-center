package cc.ly.mc.core.message;

import cc.ly.mc.core.attribute.Attribute;
import cc.ly.mc.core.io.Binary;
import cc.ly.mc.core.io.Handler;

import java.util.Map;

/**
 * 消息基本接口
 *
 * @author ly
 */
public interface Message extends Binary, Handler {

    /**
     * @return 消息版本号
     */
    byte version();

    /**
     * 设置消息版本号
     *
     * @param version 消息版本号
     */
    void version(byte version);

    /**
     * @return 消息长度 无符号24位 java int表示，单位字节
     */
    int length();

    /**
     * @param length 消息长度 无符号24位 java int表示，单位字节，包含所有
     */
    void length(int length);

    /**
     * @return 消息状态
     * @see MessageFlag
     */
    MessageFlag flag();

    /**
     * @param flag 消息状态
     * @see MessageFlag
     */
    void flag(MessageFlag flag);

    /**
     * @return 消息编码 无符号24位 java int表示
     */
    int code();

    /**
     * @param code 消息编码 无符号24位 java int表示
     */
    void code(int code);

    /**
     * @return HopByHop标识 32位数字 java int表示
     */
    int hopByHop();

    /**
     * @param hopByHop 标识 64位数字 java long表示
     */
    void hopByHop(int hopByHop);

    /**
     * @return EndToEnd标识
     */
    int endToEnd();

    /**
     * @param endToEnd 标识
     */
    void endToEnd(int endToEnd);

    /**
     * @param code 属性code
     * @return 返回消息中对应属性的attribute
     */
    Attribute<?> attribute(int code);

    /**
     * @param codes 需要检查的属性code
     * @return 如果消息中包含对应的属性返回true，否则false
     */
    boolean hasAttribute(int... codes);

    /**
     * @return 返回所有的attribute
     */
    Map<Integer, Attribute<?>> attributes();

    /**
     * @param attribute 属性值
     */
    void addAttribute(Attribute<?> attribute);

    /**
     * 附加相关的属性
     *
     * @param key
     * @param value
     */
    void attach(Object key, Object value);

    /**
     * 返回附加的属性
     *
     * @param key
     * @return 对应的值
     */
    Object attach(Object key);

    /**
     * 消息格式是否合法
     *
     * @return 如果消息格式合法返回true，否则false
     */
    boolean valid();
}
