package cc.ly.mc.core.server.io;

import cc.ly.mc.core.io.ToBinary;
import cc.ly.mc.core.message.Message;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class SocketServerEncoder extends MessageToByteEncoder<Message> {

	@Override
	protected void encode(ChannelHandlerContext ctx, Message message,
			ByteBuf buf) throws Exception {
		if(message instanceof ToBinary && message.valid()){
			buf.writeBytes(message.toBinary());
		}
	}

}
