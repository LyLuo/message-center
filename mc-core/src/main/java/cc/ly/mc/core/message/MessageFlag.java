package cc.ly.mc.core.message;

import java.util.HashMap;
import java.util.Map;

/**
 * 消息标志位表示类
 * <p>
 * Created by ly on 9/8/15.
 */
public enum MessageFlag {
    REQUEST((byte) 0b10000000),
    PROXIABLE_REQUEST((byte) 0b11000000),
    PROXIABLE_GROUP_REQUEST((byte) 0b11100000),
    ANSWER((byte) 0b00000000),
    ERROR((byte) 0b00001000),
    PROXIABLE_ERROR((byte) 0b01010000),
    PROXIABLE_GROUP_ERROR((byte) 0b01110000);
    private static final short REQUEST_MASK = 0b10000000;
    private static final byte PROXIABLE_MASK = 0b01000000;
    private static final byte GROUP_MASK = 0b00100000;
    private static final byte ERROR_MASK = 0b00010000;
    private byte value;
    private static Map<Byte, MessageFlag> MESSAGE_FLAGS = new HashMap() {{
        for (MessageFlag flag : MessageFlag.values()) {
            put(flag.value, flag);
        }
    }};

    MessageFlag(byte value) {
        this.value = value;
    }

    public static MessageFlag fromBinary(byte payload) {
        if (MESSAGE_FLAGS.containsKey(payload)) {
            return MESSAGE_FLAGS.get(payload);
        }
        throw new IllegalArgumentException("Payload " + payload + " not found in MessageFlag");
    }

    /**
     * 是否请求，如果true表示请求否则表示响应
     */
    public boolean isRequest() {
        return (value & REQUEST_MASK) == 1;
    }

    /**
     * 是否需要本地处理，如果true表示可以转发，本地处理，否则表示必须本地处理
     */
    public boolean isProxiable() {
        return (value & PROXIABLE_MASK) == 1;
    }

    /**
     * 是否群组消息，如果true表示是群组消息，否则点对点消息
     */
    public boolean isGroup() {
        return (value & GROUP_MASK) == 1;
    }

    /**
     * 是否错误，如果true表示错误,否则表示正确
     */
    public boolean isError() {
        return (value & ERROR_MASK) == 1;
    }

    public byte value() {
        return value;
    }
}
