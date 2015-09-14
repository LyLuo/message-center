package cc.ly.mc.netty.server.event;

import cc.ly.mc.core.event.EventObserver;
import cc.ly.mc.core.message.Message;
import cc.ly.mc.netty.server.Constant;
import cc.ly.mc.netty.server.context.Context;
import io.netty.channel.ChannelHandlerContext;

/**
 * Created by ly on 9/13/15.
 */
public class RegisterMessageObserver implements EventObserver {
    @Override
    public void update(Object source) {
        Message message = (Message) source;
        //TODO 验证合法性
        //如果合法，否则关闭
        ChannelHandlerContext channelHandlerContext = (ChannelHandlerContext) message.attach("ChannelHandlerContext");
        String id = null;
        channelHandlerContext.attr(Constant.ID).set(id);
        Context.getInstance().register(channelHandlerContext);
    }
}
