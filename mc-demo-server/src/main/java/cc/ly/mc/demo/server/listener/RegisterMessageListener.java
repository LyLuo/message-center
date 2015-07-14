package cc.ly.mc.demo.server.listener;

import cc.ly.mc.core.attribute.Attributes;
import cc.ly.mc.core.attribute.impl.BoolAttribute;
import cc.ly.mc.core.context.Identity;
import cc.ly.mc.core.context.IdentityContext;
import cc.ly.mc.core.context.MessageContext;
import cc.ly.mc.core.data.impl.*;
import cc.ly.mc.core.event.EventListener;
import cc.ly.mc.core.event.EventSource;
import cc.ly.mc.core.message.RegisterMessage;
import cc.ly.mc.core.message.RegisterResponseMessage;
import cc.ly.mc.demo.server.cc.ly.mc.demo.http.ApiServer;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RegisterMessageListener implements EventListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(RegisterMessageListener.class);

    @SuppressWarnings("unchecked")
    @Override
    public void update(EventSource event, Object object) {
        RegisterMessage message = (RegisterMessage) object;
        ApiServer api = new ApiServer();
        final Integer32 id = (Integer32) message.attribute(Attributes.SENDER_ID.getCode()).data();
        UTF8 token = (UTF8) message.attribute(Attributes.TOKEN.getCode()).data();
        BoolAttribute success = new BoolAttribute(Bool.FALSE);
        if (api.validateToken(id.get(), token.get()).equals(ApiServer.SUCCESS)) {
            LOGGER.info("user {} with {} register successfully", id.get(), token.get());
            Identity identity = new Identity(id.get(), message.context());
            if (IdentityContext.INSTANCE.add(identity)) {
                MessageContext.INSTANCE.register(message.context().channel().id());
            }
            success = new BoolAttribute(Bool.TRUE);
        }
        RegisterResponseMessage response = new RegisterResponseMessage();
        response.endToEnd(message.endToEnd());
        response.hopByHop(message.hopByHop());
        response.flag(new FlagData(FlagImpl.ANSWER));
        response.addAttribute(success);
        message.context().writeAndFlush(response).addListener(new GenericFutureListener() {
            @Override
            public void operationComplete(Future future) throws Exception {
                if (future.isSuccess()) {
                    LOGGER.info("send response message to user {} successfully", id.get());
                } else {
                    LOGGER.info("failed to send response message to user {}", id.get());
                }
            }
        });
        if (success.data() == Bool.TRUE) {
            LOGGER.info("failed to register user {} with {},then close channel", id.get(), token.get());
            message.context().close();
        }
    }
}
