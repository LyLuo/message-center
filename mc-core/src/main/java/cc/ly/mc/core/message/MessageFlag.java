package cc.ly.mc.core.message;

import java.util.HashMap;

/**
 * 消息标志位表示类
 * <p>
 * Created by ly on 9/8/15.
 */
public enum MessageFlag {
    REQUEST((byte) 0b10000000),
    PROXIABLE_REQUEST((byte) 0b11000000),
    GROUP_PROXIABLE_REQUEST((byte) 0b11100000),
    ANSWER((byte) 0b00000000),
    ERROR_ANSWER((byte) 0b00000001),
    PROXIABLE_ANSWER((byte) 0b01000000),
    ERROR_PROXIABLE_ANSWER((byte) 0b01000001),
    GROUP_PROXIABLE_ANSWER((byte) 0b01100000),
    ERROR_GROUP_PROXIABLE_ANSWER((byte) 0b01100001);
    private static final short REQUEST_MASK = 0b10000000;
    private static final byte PROXIABLE_MASK = 0b01000000;
    private static final byte GROUP_MASK = 0b00100000;
    private static final byte ERROR_MASK = 0b00000001;
    private byte value;
    private static HashMap<Byte, MessageFlag> MESSAGE_FLAGS = new HashMap<Byte, MessageFlag>() {{
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
        return (value & REQUEST_MASK) != 0;
    }

    /**
     * 是否需要本地处理，如果true表示可以转发，本地处理，否则表示必须本地处理
     */
    public boolean isProxiable() {
        return (value & PROXIABLE_MASK) != 0;
    }

    /**
     * 是否群组消息，如果true表示是群组消息，否则点对点消息
     */
    public boolean isGroup() {
        return (value & GROUP_MASK) != 0;
    }

    /**
     * 是否错误，如果true表示错误,否则表示正确
     */
    public boolean isError() {
        return (value & ERROR_MASK) != 0;
    }


    public byte value() {
        return value;
    }

    /**
     * 翻转是否请求
     * @return 如果是请求返回响应,success表示是否错误
     */
    public MessageFlag requestToAnswer(boolean isError){
        if(!isRequest()){
            throw new IllegalStateException("answer can not reverse to request");
        }
        byte payload = (byte) (value ^ REQUEST_MASK);
        if(isError){
            payload = (byte) (payload ^ ERROR_MASK);
        }
        return fromBinary(payload);
    }

}
