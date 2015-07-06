package cc.ly.mc.core.context;

import cc.ly.mc.core.message.Message;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

public class Identity {
    public static final Long SERVER_ID = 0L;

    private Long id;
    private ChannelHandlerContext context;

    public Identity(ChannelHandlerContext context) {
        this.id = SERVER_ID;
        this.context = context;
    }

    public Identity(long id, ChannelHandlerContext context) {
        this.id = id;
        this.context = context;
    }

    public Long id() {
        return id;
    }

    public void id(Long id) {
        this.id = id;
    }

    public boolean isServer(){
        return id == SERVER_ID;
    }

    public void context(ChannelHandlerContext context) {
        this.context = context;
    }

    public ChannelHandlerContext context() {
        return context;
    }

    public void write(final Object obj) {
        this.context.writeAndFlush(obj).addListener(new GenericFutureListener<Future<? super Void>>() {

            @Override
            public void operationComplete(Future<? super Void> future)
                    throws Exception {
                Message message = (Message) obj;
                if (future.isSuccess()) {
                    if (message.flag().get().isRequest()) {
                        message.context(context);
                        MessageContext.INSTANCE.register(message);
                    }
                } else {
                    System.out.println(future.cause());
                }
            }
        });
    }
}
