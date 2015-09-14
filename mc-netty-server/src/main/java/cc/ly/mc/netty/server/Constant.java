package cc.ly.mc.netty.server;

import cc.ly.mc.netty.server.message.MessageFactory;
import io.netty.util.AttributeKey;

/**
 * Created by ly on 9/14/15.
 */
public class Constant {
    public static final AttributeKey<String> ID = AttributeKey.newInstance("id");

    public static final String CONNECTED = "Connected";
    public static final String DISCONNECTED = "Disconnected";
    public static final String HEARTBEAT = String.valueOf(MessageFactory.HEARTBEAT_CODE);
    public static final String REGISTER = String.valueOf(MessageFactory.REGISTER_CODE);
    public static final String DEREGISTER = String.valueOf(MessageFactory.DEREGISTER_CODE);
    public static final String TEXT = String.valueOf(MessageFactory.TEXT_CODE);
}
