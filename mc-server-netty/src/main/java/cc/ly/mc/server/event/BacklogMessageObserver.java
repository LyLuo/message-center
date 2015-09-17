package cc.ly.mc.server.event;

import cc.ly.mc.core.event.EventObserver;
import cc.ly.mc.core.message.Message;
import cc.ly.mc.server.ServerConstant;
import cc.ly.mc.server.context.DefaultBacklog;
import cc.ly.mc.server.netty.DefaultWriteAndFlushListener;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by ly on 9/13/15.
 */
public class BacklogMessageObserver implements EventObserver {
    private static final Logger LOGGER = LoggerFactory.getLogger(BacklogMessageObserver.class);

    @Override
    public void update(Object source) {
        Message message = (Message) source;
        ChannelHandlerContext context = (ChannelHandlerContext) message.attach(ServerConstant.CHANNEL_HANDLER_CONTEXT);
        String id = context.attr(ServerConstant.ID).get();
        List<Message> backlog = DefaultBacklog.getInstance().consume(id);
        LOGGER.info("{} has {} backlog messages", id, backlog.size());
        backlog.forEach(m -> context.write(m).addListener(new DefaultWriteAndFlushListener(id, m)));
        context.flush();
    }
}
