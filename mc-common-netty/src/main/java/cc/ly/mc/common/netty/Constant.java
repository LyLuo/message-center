package cc.ly.mc.common.netty;

/**
 * Created by ly on 9/15/15.
 */
public class Constant {

    //message code
    public static final int MESSAGE_HEARTBEAT_CODE = 0;
    public static final int MESSAGE_REGISTER_CODE = 1;
    public static final int MESSAGE_DEREGISTER_CODE = 2;
    public static final int MESSAGE_KICK_OUT_CODE = 3;
    public static final int MESSAGE_TEXT_CODE = 100;
    //attribute code
    public static final int ATTRIBUTE_TOKEN_CODE = 1;
    public static final int ATTRIBUTE_SENDER_ID_CODE = 2;
    public static final int ATTRIBUTE_RECEIVER_ID_CODE = 3;
    public static final int ATTRIBUTE_SENDER_NAME_CODE = 100;
    public static final int ATTRIBUTE_RECEIVER_NAME_CODE = 101;
    public static final int ATTRIBUTE_TEXT_CODE = 102;

    //消息事件
    public static final String REGISTER_EVENT = String.valueOf(Constant.MESSAGE_REGISTER_CODE);
    public static final String DEREGISTER_EVENT = String.valueOf(Constant.MESSAGE_DEREGISTER_CODE);
    public static final String TEXT_EVENT = String.valueOf(Constant.MESSAGE_TEXT_CODE);
}
