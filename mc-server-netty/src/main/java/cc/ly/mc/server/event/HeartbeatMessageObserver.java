package cc.ly.mc.server.event;

import cc.ly.mc.core.event.EventObserver;
import cc.ly.mc.core.message.Message;
import cc.ly.mc.server.ServerConstant;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by ly on 9/13/15.
 */
public class HeartbeatMessageObserver implements EventObserver{
    private static final Logger LOGGER = LoggerFactory.getLogger(HeartbeatMessageObserver.class);
    @Override
    public void update(Object source) {
        Message message = (Message) source;
        ChannelHandlerContext context = (ChannelHandlerContext) message.attach(ServerConstant.CHANNEL_HANDLER_CONTEXT);
        context.attr(ServerConstant.SUSPECT).set(false);
        LOGGER.info("channel from {} connected", context.channel().remoteAddress());
    }
}
