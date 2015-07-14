package cc.ly.mc.core.client.io;

import cc.ly.mc.core.event.EventManager;
import cc.ly.mc.core.io.ConnectedListener;
import cc.ly.mc.core.io.DisconnectedListener;
import cc.ly.mc.core.message.*;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class SocketClientHandler extends SimpleChannelInboundHandler<Message> {

	private static final Logger LOGGER = LoggerFactory.getLogger(SocketClientHandler.class);

	private List<ConnectedListener> connectedListeners;
	
	private List<DisconnectedListener> disconnectedListeners;
	
	public SocketClientHandler(List<ConnectedListener> connectedListeners, List<DisconnectedListener> disconnectedListeners){
		this.connectedListeners = connectedListeners;
		this.disconnectedListeners = disconnectedListeners;
	}
	
	@Override
	protected void messageReceived(ChannelHandlerContext context, Message message) throws Exception {
		if(!message.valid()){
			LOGGER.info("received a invalid message", message);
			EventManager.getInstance().notifyListeners("InvalidMessage", message);
		}else {
			if (message instanceof RelayMessage || message instanceof EndToEndAckMessage
					|| message instanceof HopByHopAckMessage || message instanceof SystemResponseMessage) {
				message.arriveAtEndPoint(true);
			}
			message.context(context);
			message.onReceived();
		}
	}
	
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		for (ConnectedListener listener : connectedListeners) {
			listener.onConnect(ctx);
		}
	}
	
	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		for (DisconnectedListener listener : disconnectedListeners) {
			listener.onDisconnect(ctx);
		}
	}

}
