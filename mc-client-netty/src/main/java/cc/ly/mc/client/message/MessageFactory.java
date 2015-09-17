package cc.ly.mc.client.message;

import cc.ly.mc.common.netty.Constant;
import cc.ly.mc.core.attribute.impl.StringAttribute;
import cc.ly.mc.core.message.*;

/**
 * Created by ly on 9/15/15.
 */
public class MessageFactory {

    public static Message createRegister(String id, String name){
        Message message = Messages.create(Constant.MESSAGE_REGISTER_CODE, MessageFlag.REQUEST);
        StringAttribute idAttribute = new StringAttribute(Constant.ATTRIBUTE_SENDER_ID_CODE, id);
        StringAttribute nameAttribute = new StringAttribute(Constant.ATTRIBUTE_SENDER_NAME_CODE, name);
        message.addAttribute(idAttribute);
        message.addAttribute(nameAttribute);
        return message;
    }

    public static Message createText(String senderId, String receiverId, String text) {
        Message message = Messages.create(Constant.MESSAGE_TEXT_CODE, MessageFlag.REQUEST);
        StringAttribute senderIdAttribute = new StringAttribute(Constant.ATTRIBUTE_SENDER_ID_CODE, senderId);
        StringAttribute receiverIdAttribute = new StringAttribute(Constant.ATTRIBUTE_RECEIVER_ID_CODE, receiverId);
        StringAttribute textAttribute = new StringAttribute(Constant.ATTRIBUTE_TEXT_CODE, text);
        message.addAttribute(senderIdAttribute);
        message.addAttribute(receiverIdAttribute);
        message.addAttribute(textAttribute);
        return message;
    }

    public static Message createBacklog() {
        return Messages.create(Constant.MESSAGE_BACKLOG_CODE, MessageFlag.REQUEST);
    }
}
