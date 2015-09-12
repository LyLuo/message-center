package cc.ly.mc.netty.server;

import cc.ly.mc.core.message.DefaultMessage;
import cc.ly.mc.core.message.Message;
import cc.ly.mc.core.message.Messages;
import cc.ly.mc.core.util.NumberUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SocketServerDecoder extends ByteToMessageDecoder {

	private static final Logger LOGGER = LoggerFactory .getLogger(SocketServerDecoder.class);

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf buffer,
			List<Object> messages) throws Exception {
		if (buffer.readableBytes() < Messages.MESSAGE_FIELDS_LENGTH) {
			LOGGER.debug( "buffer readableBytes {} is less than {},it is not enough to decode ", buffer.readableBytes(), Messages.MESSAGE_FIELDS_LENGTH);
			return;
		} else {
			byte[] lengthPayload = new byte[Messages.LENGTH_FIELD_LENGTH];
			byte[] messagePayload;
			int length;
			buffer.markReaderIndex();
			buffer.skipBytes(Messages.VERSION_FIELD_LENGTH);
			buffer.readBytes(lengthPayload);
			length = NumberUtils.bytes3ToInt(lengthPayload);
			if (buffer.readableBytes() < length) {
				LOGGER.debug( "buffer readableBytes {} is less than message's length {},it is not enough to decode ", buffer.readableBytes(), length);
				buffer.resetReaderIndex();
				return;
			}
			messagePayload = new byte[length];
			buffer.resetReaderIndex();
			Message message = new DefaultMessage();
			buffer.readBytes(messagePayload);
			message.fromBinary(messagePayload);
			messages.add(message);
		}
	}

}
