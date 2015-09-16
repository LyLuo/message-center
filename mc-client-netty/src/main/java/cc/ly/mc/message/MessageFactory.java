package cc.ly.mc.message;

import cc.ly.mc.core.attribute.impl.StringAttribute;
import cc.ly.mc.core.message.DefaultMessage;
import cc.ly.mc.core.message.IdGenerator;
import cc.ly.mc.core.message.Message;
import cc.ly.mc.core.message.MessageFlag;
import cc.ly.mc.netty.common.Constant;

/**
 * Created by ly on 9/15/15.
 */
public class MessageFactory {

    public static Message createRegister(String id, String name){
        Message message = create();
        message.flag(MessageFlag.REQUEST);
        StringAttribute idAttribute = new StringAttribute(Constant.ATTRIBUTE_SENDER_ID_CODE, id);
        StringAttribute nameAttribute = new StringAttribute(Constant.ATTRIBUTE_SENDER_NAME_CODE, id);
        message.code(Constant.MESSAGE_REGISTER_CODE);
        message.addAttribute(idAttribute);
        message.addAttribute(nameAttribute);
        return message;
    }

    private static Message create() {
        Message message = new DefaultMessage();
        message.version((byte) 1);
        message.hopByHop(IdGenerator.nextHopByHop());
        message.endToEnd(IdGenerator.nextEndToEnd());
        return message;
    }
}
