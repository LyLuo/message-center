package cc.ly.mc.core.message;

import cc.ly.mc.core.attribute.Attribute;
import cc.ly.mc.core.attribute.Attributes;
import cc.ly.mc.core.attribute.impl.Integer64Attribute;
import cc.ly.mc.core.context.IdentityContext;
import cc.ly.mc.core.context.MessageContext;
import cc.ly.mc.core.data.impl.FlagData;
import cc.ly.mc.core.data.impl.FlagImpl;
import cc.ly.mc.core.data.impl.Integer64;
import cc.ly.mc.core.event.EventManager;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class RelayMessage extends GenericMessage {

    private static final Logger LOGGER = LoggerFactory.getLogger(RelayMessage.class);

    @Override
    public void onReceived() {
        LOGGER.debug("received a {}", this);
        if (arriveAtEndPoint()) {
            EndToEndAckMessage message = new EndToEndAckMessage();
            message.hopByHop(this.hopByHop);
            message.endToEnd(this.endToEnd);
            Attribute<Integer64> att = (Attribute<Integer64>) this.attribute(Attributes.SENDER_ID.getCode());
            att.code(Attributes.RECEIVER_ID.getCode());
            message.addAttribute(att);
            message.flag(new FlagData(FlagImpl.ANSWER));
            context().writeAndFlush(message).addListener(new GenericFutureListener<Future<? super Void>>() {

                @Override
                public void operationComplete(Future<? super Void> future)
                        throws Exception {
                    if (future.isSuccess()) {
                        System.out.println("send end to end ack success");
                    } else {
                        System.out.println(future.cause());
                        System.out.println("send end to end ack failed");
                    }
                }
            });
            message.context(this.context());
            MessageContext.INSTANCE.register(message);
            MessageContext.INSTANCE.registerAckTimeout(message);
        } else {
            HopByHopAckMessage message = new HopByHopAckMessage();
            message.hopByHop(this.hopByHop);
            message.endToEnd(this.endToEnd);
            message.flag(new FlagData(FlagImpl.ANSWER));
            context().writeAndFlush(message).addListener(new GenericFutureListener<Future<? super Void>>() {

                @Override
                public void operationComplete(Future<? super Void> future)
                        throws Exception {
                    if (future.isSuccess()) {
                        System.out.println("send hop by hop ack success");
                    } else {
                        System.out.println(future.cause());
                        System.out.println("send hop by hop ack failed");
                    }
                }
            });
            IdentityContext.INSTANCE.forward(this);
        }
        EventManager.getInstance().notifyListeners(this);
    }

    @Override
    public boolean valid() {
        return hasAttribute(Attributes.SENDER_ID.getCode(), Attributes.RECEIVER_ID.getCode());
    }
}
