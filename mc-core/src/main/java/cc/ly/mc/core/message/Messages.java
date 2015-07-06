package cc.ly.mc.core.message;

import cc.ly.mc.core.data.impl.Unsigned16;

/**
 * Created by Administrator on 2015/6/18.
 */
public enum Messages {
    RESPONSE_MESSAGE(0, ResponseMessage.class),
    HOP_BY_HOP_ACK_MESSAGE(1, HopByHopAckMessage.class),
    END_TO_END_ACK_MESSAGE(2, EndToEndAckMessage.class),
    REGISTER_MESSAGE(3, RegisterMessage.class),
    DEREGISTER_MESSAGE(4, DeregisterMessage.class),
    TEXT_MESSAGE(5, TextMessage.class),
    BORROWING_MESSAGE(6, BorrowingMessage.class);
    private Unsigned16 code;
    private Class<? extends Message> clazz;

    Messages(int code, Class<? extends Message> clazz) {
        this.code = Unsigned16.get(code);
        this.clazz = clazz;
    }

    public Unsigned16 getCode() {
        return code;
    }

    public Class<? extends Message> getClazz() {
        return clazz;
    }
}
