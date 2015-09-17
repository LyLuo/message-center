package cc.ly.mc.server.event;

import cc.ly.mc.common.netty.Constant;
import cc.ly.mc.core.event.EventObserver;
import cc.ly.mc.core.message.Message;
import cc.ly.mc.server.context.Context;
import cc.ly.mc.server.context.DefaultBacklog;
import cc.ly.mc.server.netty.DefaultWriteAndFlushListener;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by ly on 9/13/15.
 */
public class TextMessageObserver implements EventObserver {
    private static final Logger LOGGER = LoggerFactory.getLogger(TextMessageObserver.class);

    @Override
    public void update(Object source) {
        Message message = (Message) source;
        String from = (String) message.attribute(Constant.ATTRIBUTE_SENDER_ID_CODE).data();
        String to = (String) message.attribute(Constant.ATTRIBUTE_RECEIVER_ID_CODE).data();
        LOGGER.debug("{} send message to {}", from, to);
        ChannelHandlerContext toContext = Context.getInstance().get(to);
        if (toContext == null) {
            DefaultBacklog.getInstance().product(to, message);
        } else {
            toContext.writeAndFlush(message).addListener(new DefaultWriteAndFlushListener(to, message));
        }
    }
}
