package cc.ly.mc.server.netty;

import cc.ly.mc.core.message.Message;
import cc.ly.mc.server.context.DefaultBacklog;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by ly on 9/14/15.
 */
public class DefaultWriteAndFlushListener implements GenericFutureListener<Future<Void>>{

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultWriteAndFlushListener.class);

    private String to;

    private Message message;

    public DefaultWriteAndFlushListener(String to, Message message){
        this.to = to;
        this.message = message;
    }

    @Override
    public void operationComplete(Future future) throws Exception {
        if(!future.isSuccess()){
            LOGGER.info("failed to send message to {}, cause by", to, future.cause());
            DefaultBacklog.getInstance().product(to, message);
        }
    }
}
