package cc.ly.mc.server.message;

import cc.ly.mc.common.netty.Constant;
import cc.ly.mc.core.message.*;

/**
 * Created by ly on 9/13/15.
 */
public class MessageFactory {

    public static Message createHeartbeat() {
        return Messages.create(Constant.MESSAGE_HEARTBEAT_CODE, MessageFlag.REQUEST);
    }

    public static Message createKickOut() {
        return Messages.create(Constant.MESSAGE_KICK_OUT_CODE, MessageFlag.REQUEST);
    }

    public static Message requestToAnswer(Message message, boolean isError){
        Message answer = Messages.copy(message);
        answer.flag(message.flag().requestToAnswer(isError));
        return answer;
    }

}
