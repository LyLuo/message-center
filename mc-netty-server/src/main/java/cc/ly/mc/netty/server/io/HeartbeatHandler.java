package cc.ly.mc.netty.server.io;

import cc.ly.mc.netty.server.message.MessageFactory;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * Created by ly on 9/13/15.
 */
public class HeartbeatHandler extends ChannelHandlerAdapter {

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent e = (IdleStateEvent) evt;
            if (e.state() == IdleState.READER_IDLE) {
                ctx.close();
            }
            if (e.state() == IdleState.WRITER_IDLE) {
                ctx.writeAndFlush(MessageFactory.createHeartbeat()).addListener(future -> {
                    if (future.isSuccess()) {

                    } else {
                        ctx.close().addListener(closeFuture -> {
                            if(closeFuture.isSuccess()){

                            }
                        });
                    }
                });
            }
        }
    }
}
