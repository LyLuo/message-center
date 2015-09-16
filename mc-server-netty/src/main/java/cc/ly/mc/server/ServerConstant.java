package cc.ly.mc.server;

import cc.ly.mc.netty.common.Constant;
import io.netty.util.AttributeKey;

/**
 * Created by ly on 9/14/15.
 */
public class ServerConstant {

    //server事件
    public static final String CONNECTED_EVENT = "Connected";
    public static final String DISCONNECTED_EVENT = "Disconnected";
    //消息事件
    public static final String REGISTER_EVENT = String.valueOf(Constant.MESSAGE_REGISTER_CODE);
    public static final String DEREGISTER_EVENT = String.valueOf(Constant.MESSAGE_DEREGISTER_CODE);
    public static final String TEXT_EVENT = String.valueOf(Constant.MESSAGE_TEXT_CODE);
    //netty attribute key
    public static final AttributeKey<String> ID = AttributeKey.newInstance("id");
    public static final AttributeKey<Boolean> SUSPECT = AttributeKey.newInstance("SUSPECT");
    //other
    public static final String CHANNEL_HANDLER_CONTEXT = "ChannelHandlerContext";
}
