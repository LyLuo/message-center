package cc.ly.mc.server.netty;

import cc.ly.mc.common.netty.Constant;
import cc.ly.mc.core.event.EventBus;
import cc.ly.mc.core.message.Message;
import cc.ly.mc.server.ServerConstant;
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
            //tcp链接建立，未收到注册消息
            if (!context.hasAttr(ServerConstant.REGISTERED) && message.code() != Constant.MESSAGE_REGISTER_CODE){
                LOGGER.info("not registered ChannelHandlerContext only receive register message");
                context.close();
                return;
            }
            message.attach(ServerConstant.CHANNEL_HANDLER_CONTEXT, context);
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
