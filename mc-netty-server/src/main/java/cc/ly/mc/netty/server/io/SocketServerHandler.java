package cc.ly.mc.netty.server.io;

import cc.ly.mc.core.event.EventBus;
import cc.ly.mc.core.message.Message;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SocketServerHandler extends SimpleChannelInboundHandler<Message> {

    private static final Logger LOGGER = LoggerFactory.getLogger(SocketServerHandler.class);

    public SocketServerHandler() {
    }

    @Override
    protected void messageReceived(ChannelHandlerContext context, Message message)
            throws Exception {
        if (!message.valid()) {
            LOGGER.info("received a invalid message", message);
        }else {
            message.attach("ChannelHandlerContext", context);
            message.onReceived();
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        LOGGER.debug("{} connected and notify observers", ctx);
        EventBus.getInstance().notify("Connected", ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        LOGGER.debug("{} disconnected and notify observers", ctx);
        EventBus.getInstance().notify("Disconnected", ctx);
    }


}
