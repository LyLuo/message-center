package cc.ly.mc.server;

import io.netty.util.AttributeKey;

/**
 * Created by ly on 9/14/15.
 */
public class ServerConstant {

    //server事件
    public static final String CONNECTED_EVENT = "Connected";
    public static final String DISCONNECTED_EVENT = "Disconnected";
    //netty attribute key
    public static final AttributeKey<String> ID = AttributeKey.newInstance("id");
    public static final AttributeKey<Boolean> SUSPECT = AttributeKey.newInstance("SUSPECT");
    public static final AttributeKey<Boolean> REGISTERED = AttributeKey.newInstance("REGISTERED");
    //other
    public static final String CHANNEL_HANDLER_CONTEXT = "ChannelHandlerContext";
}
