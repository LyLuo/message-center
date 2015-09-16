package cc.ly.mc.client.netty;

import cc.ly.mc.core.message.*;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SocketClientHandler extends SimpleChannelInboundHandler<Message> {

	private static final Logger LOGGER = LoggerFactory.getLogger(SocketClientHandler.class);

	@Override
	protected void messageReceived(ChannelHandlerContext context, Message message) throws Exception {
		if(!message.valid()){
			LOGGER.info("received a invalid message", message);
		}else {
			message.onReceived();
		}
	}
	
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
	}
	
	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
	}

}
