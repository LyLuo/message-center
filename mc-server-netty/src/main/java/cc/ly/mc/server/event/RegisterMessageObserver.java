package cc.ly.mc.server.event;

import cc.ly.mc.core.event.EventObserver;
import cc.ly.mc.core.message.Message;
import cc.ly.mc.common.netty.Constant;
import cc.ly.mc.server.ServerConstant;
import cc.ly.mc.server.context.Context;
import cc.ly.mc.server.context.DefaultBacklog;
import cc.ly.mc.server.message.MessageFactory;
import cc.ly.mc.server.netty.DefaultWriteAndFlushListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleStateHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by ly on 9/13/15.
 */
public class RegisterMessageObserver implements EventObserver {
    private static final Logger LOGGER = LoggerFactory.getLogger(RegisterMessageObserver.class);

    @Override
    public void update(Object source) {
        Message message = (Message) source;
        ChannelHandlerContext context = (ChannelHandlerContext) message.attach(ServerConstant.CHANNEL_HANDLER_CONTEXT);
        String id = (String) message.attribute(Constant.ATTRIBUTE_SENDER_ID_CODE).data();
        LOGGER.info("id {} registered", id);
        context.pipeline().replace("idleStateHandler", "idleStateHandler", new IdleStateHandler(0, 30, 0));
        context.attr(ServerConstant.ID).set(id);
        context.attr(ServerConstant.SUSPECT).set(false);
        context.attr(ServerConstant.REGISTERED).set(true);
        Context.getInstance().register(context);
        Message answer = MessageFactory.requestToAnswer(message, false);
        context.writeAndFlush(answer);
        List<Message> backlog = DefaultBacklog.getInstance().consume(id);
        backlog.forEach(m -> context.write(m).addListener(new DefaultWriteAndFlushListener(id, m)));
        context.flush();
    }
}
