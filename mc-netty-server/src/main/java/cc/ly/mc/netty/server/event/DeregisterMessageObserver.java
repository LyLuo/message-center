package cc.ly.mc.netty.server.event;

import cc.ly.mc.core.event.EventObserver;
import cc.ly.mc.netty.server.Constant;
import cc.ly.mc.netty.server.context.Context;
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
        ChannelHandlerContext channelHandlerContext = (ChannelHandlerContext) source;
        LOGGER.info("channel from {} disconnected", channelHandlerContext.channel().remoteAddress());
        if(channelHandlerContext.hasAttr(Constant.ID)) {
            Context.getInstance().deregister(channelHandlerContext);
        }
    }
}
