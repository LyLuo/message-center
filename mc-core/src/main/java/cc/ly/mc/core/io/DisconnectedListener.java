package cc.ly.mc.core.io;

import io.netty.channel.ChannelHandlerContext;

public interface DisconnectedListener {

	void onDisconnect(ChannelHandlerContext ctx);
	
}
