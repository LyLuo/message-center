package cc.ly.mc.core.message;

import cc.ly.mc.core.data.impl.FlagImpl;
import cc.ly.mc.core.data.impl.Unsigned16;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HeartbeatMessage extends GenericMessage{

	private static final Logger LOGGER = LoggerFactory.getLogger(HeartbeatMessage.class);

	public static final Unsigned16 CODE = Messages.HEARTBEAT.getCode();

	public HeartbeatMessage() {
		code = CODE;
	}
	
	@Override
	public void onReceived() {
		LOGGER.debug("received a {}", this);
		this.flag().set(FlagImpl.ANSWER);
		this.context().writeAndFlush(this);
	}
}
