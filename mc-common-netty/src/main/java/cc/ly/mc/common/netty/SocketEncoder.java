package cc.ly.mc.common.netty;

import cc.ly.mc.core.io.ToBinary;
import cc.ly.mc.core.message.Message;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SocketEncoder extends MessageToByteEncoder<Message> {

    private static final Logger LOGGER = LoggerFactory.getLogger(SocketEncoder.class);

    @Override
    protected void encode(ChannelHandlerContext ctx, Message message,
                          ByteBuf buf) throws Exception {
        if (message instanceof ToBinary) {
            if (message.valid()) {
                buf.writeBytes(message.toBinary());
            } else {
                LOGGER.error("message {} is not valid, can not encode", message);
            }
        }
    }

}
