package cc.ly.mc.core.message;

import cc.ly.mc.core.data.impl.FlagData;
import cc.ly.mc.core.data.impl.FlagImpl;
import cc.ly.mc.core.event.EventManager;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class SystemMessage extends GenericMessage{

    private static final Logger LOGGER = LoggerFactory
            .getLogger(SystemMessage.class);

    @Override
    public void onReceived() {
        LOGGER.debug("received a {}", this);
        if (arriveAtEndPoint()) {
            EndToEndAckMessage message = new EndToEndAckMessage();
            message.hopByHop(this.hopByHop);
            message.endToEnd(this.endToEnd);
            message.flag(new FlagData(FlagImpl.ANSWER));
            context().writeAndFlush(message).addListener(new GenericFutureListener<Future<? super Void>>() {

                @Override
                public void operationComplete(Future<? super Void> future)
                        throws Exception {
                    if(future.isSuccess()){
                        System.out.println("send end to end ack success");
                    }else{
                        System.out.println(future.cause());
                        System.out.println("send end to end ack failed");
                    }
                }
            });
            EventManager.getInstance().notifyListeners(this);
        } else {
            LOGGER.error("not suppose to receive a system message");
        }
    }
}
