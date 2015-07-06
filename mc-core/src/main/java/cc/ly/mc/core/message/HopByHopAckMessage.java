package cc.ly.mc.core.message;

import cc.ly.mc.core.context.MessageContext;
import cc.ly.mc.core.data.impl.Unsigned16;
import cc.ly.mc.core.event.EventManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HopByHopAckMessage extends GenericMessage{
	
	private static final Logger LOGGER = LoggerFactory
			.getLogger(HopByHopAckMessage.class);

	public static final Unsigned16 CODE = Messages.HOP_BY_HOP_ACK_MESSAGE.getCode();

	public HopByHopAckMessage() {
		code = CODE;
	}
	
	@Override
	public void onReceived() {
		LOGGER.debug("received a {}", this);
		if(arriveAtEndPoint()) {
			MessageContext.INSTANCE.deregisterAckTimeout(this);
			EventManager.getInstance().notifyListeners(this);
		}else{
			LOGGER.error("not suppose to receive a hop by hop message");
		}
	}
}
