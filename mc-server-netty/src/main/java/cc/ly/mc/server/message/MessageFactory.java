package cc.ly.mc.server.message;

import cc.ly.mc.common.netty.Constant;
import cc.ly.mc.core.message.*;

/**
 * Created by ly on 9/13/15.
 */
public class MessageFactory {

    public static Message createHeartbeat() {
        Message message = create();
        message.code(Constant.MESSAGE_HEARTBEAT_CODE);
        message.flag(MessageFlag.REQUEST);
        return message;
    }

    public static Message createKickOut() {
        Message message = create();
        message.code(Constant.MESSAGE_KICK_OUT_CODE);
        message.flag(MessageFlag.REQUEST);
        return message;
    }

    public static Message requestToAnswer(Message message, boolean isError){
        Message answer = Messages.copy(message);
        answer.flag(message.flag().requestToAnswer(isError));
        return answer;
    }

    private static Message create() {
        Message message = new DefaultMessage();
        message.version((byte) 1);
        message.hopByHop(IdGenerator.nextHopByHop());
        message.endToEnd(IdGenerator.nextEndToEnd());
        return message;
    }
}
