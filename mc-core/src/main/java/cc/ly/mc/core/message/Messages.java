package cc.ly.mc.core.message;

import cc.ly.mc.core.data.impl.Unsigned16;

/**
 * Created by Administrator on 2015/6/18.
 */
public enum Messages {
    HEARTBEAT(0, HeartbeatMessage.class),
    HOP_BY_HOP_ACK(1, HopByHopAckMessage.class),
    END_TO_END_ACK(2, EndToEndAckMessage.class),
    REGISTER(3, RegisterMessage.class),
    REGISTER_RESPONSE(4, RegisterResponseMessage.class),
    DEREGISTER(5, DeregisterMessage.class),
    DEREGISTER_RESPONSE(6, DeregisterResponseMessage.class),
    TEXT(7, TextMessage.class),
    BORROW(8, BorrowMessage.class),
    BORROW_RESPONSE(9, BorrowResponseMessage.class);
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
