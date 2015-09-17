package cc.ly.mc.server.event;

import cc.ly.mc.core.event.EventObserver;
import cc.ly.mc.core.message.Message;
import cc.ly.mc.server.ServerConstant;
import cc.ly.mc.server.context.Context;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by ly on 9/13/15.
 */
public class DeregisterMessageObserver implements EventObserver{
    private static final Logger LOGGER = LoggerFactory.getLogger(DeregisterMessageObserver.class);
    @Override
    public void update(Object source) {
        Message message = (Message) source;
        ChannelHandlerContext context = (ChannelHandlerContext) message.attach(ServerConstant.CHANNEL_HANDLER_CONTEXT);
        LOGGER.info("channel from {} disconnected", context.channel().remoteAddress());
        if(context.hasAttr(ServerConstant.ID)) {
            Context.getInstance().deregister(context);
        }
    }
}
