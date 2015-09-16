package cc.ly.mc.server.netty;

import cc.ly.mc.core.message.Message;
import cc.ly.mc.server.context.DefaultBacklog;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

/**
 * Created by ly on 9/14/15.
 */
public class DefaultWriteAndFlushListener implements GenericFutureListener{

    private String to;

    private Message message;

    public DefaultWriteAndFlushListener(String to, Message message){
        this.to = to;
        this.message = message;
    }

    @Override
    public void operationComplete(Future future) throws Exception {
        if(!future.isSuccess()){
            DefaultBacklog.getInstance().product(to, message);
        }
    }
}
