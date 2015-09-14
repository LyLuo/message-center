package cc.ly.mc.netty.server.event;

import cc.ly.mc.core.event.EventObserver;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by ly on 9/13/15.
 */
public class ConnectedEventObserver implements EventObserver{
    private static final Logger LOGGER = LoggerFactory.getLogger(ConnectedEventObserver.class);
    @Override
    public void update(Object source) {
        ChannelHandlerContext channelHandlerContext = (ChannelHandlerContext) source;
        LOGGER.info("channel from {} connected", channelHandlerContext.channel().remoteAddress());
    }
}
