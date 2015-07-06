package cc.ly.mc.core.io;

import io.netty.channel.ChannelHandlerContext;

public interface ConnectedListener {

	void onConnect(ChannelHandlerContext ctx);
	
}
