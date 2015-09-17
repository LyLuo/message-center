package cc.ly.mc.server.netty;

import cc.ly.mc.server.ServerConstant;
import cc.ly.mc.server.message.MessageFactory;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by ly on 9/13/15.
 */
public class HeartbeatHandler extends ChannelHandlerAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(HeartbeatHandler.class);

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent e = (IdleStateEvent) evt;
            if (e.state() == IdleState.WRITER_IDLE) {
                if(ctx.hasAttr(ServerConstant.SUSPECT) && ctx.attr(ServerConstant.SUSPECT).get()){
                    LOGGER.info("suspect {} write idle happened again and close", ctx);
                    ctx.close();
                }else{
                    LOGGER.info("{} write idle happened and set context suspect true", ctx);
                    ctx.attr(ServerConstant.SUSPECT).set(true);
                    ctx.writeAndFlush(MessageFactory.createHeartbeat());
                }
            }
        }
    }
}
