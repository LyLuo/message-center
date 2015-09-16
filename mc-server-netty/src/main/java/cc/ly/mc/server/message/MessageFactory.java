package cc.ly.mc.server.message;

import cc.ly.mc.core.message.DefaultMessage;
import cc.ly.mc.core.message.IdGenerator;
import cc.ly.mc.core.message.Message;
import cc.ly.mc.core.message.MessageFlag;
import cc.ly.mc.netty.common.Constant;

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
        Message answer = new DefaultMessage();
        answer.version(message.version());
        answer.code(message.code());
        answer.flag(message.flag().requestToAnswer(isError));
        answer.hopByHop(message.hopByHop());
        answer.endToEnd(message.endToEnd());
        message.attributes().forEach((integer, attribute) -> answer.addAttribute(attribute));
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
