package cc.ly.mc.server.event;

import cc.ly.mc.core.event.EventObserver;
import cc.ly.mc.core.message.Message;
import cc.ly.mc.netty.common.Constant;
import cc.ly.mc.server.ServerConstant;
import cc.ly.mc.netty.server.context.Context;
import cc.ly.mc.server.message.MessageFactory;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleStateHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by ly on 9/13/15.
 */
public class RegisterMessageObserver implements EventObserver {
    private static final Logger LOGGER = LoggerFactory.getLogger(RegisterMessageObserver.class);

    @Override
    public void update(Object source) {
        Message message = (Message) source;
        ChannelHandlerContext channelHandlerContext = (ChannelHandlerContext) message.attach(ServerConstant.CHANNEL_HANDLER_CONTEXT);
        String id = (String) message.attribute(Constant.ATTRIBUTE_SENDER_ID_CODE).data();
        LOGGER.info("id {} registered", id);
        channelHandlerContext.pipeline().replace("idleStateHandler", "idleStateHandler", new IdleStateHandler(0, 30, 0));
        channelHandlerContext.attr(ServerConstant.ID).set(id);
        channelHandlerContext.attr(ServerConstant.SUSPECT).set(false);
        Context.getInstance().register(channelHandlerContext);
        Message answer = MessageFactory.requestToAnswer(message, true);
        answer.flag();
        channelHandlerContext.writeAndFlush(answer);
    }
}
