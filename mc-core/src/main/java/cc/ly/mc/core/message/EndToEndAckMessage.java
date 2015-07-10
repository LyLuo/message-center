package cc.ly.mc.core.message;

import cc.ly.mc.core.context.IdentityContext;
import cc.ly.mc.core.context.MessageContext;
import cc.ly.mc.core.data.impl.FlagData;
import cc.ly.mc.core.data.impl.FlagImpl;
import cc.ly.mc.core.data.impl.Unsigned16;
import cc.ly.mc.core.event.EventManager;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EndToEndAckMessage extends GenericMessage {

	private static final Logger LOGGER = LoggerFactory.getLogger(EndToEndAckMessage.class);

	public static final Unsigned16 CODE = Messages.END_TO_END_ACK.getCode();

	public EndToEndAckMessage() {
		code = CODE;
	}

	@Override
	public void onReceived() {
		LOGGER.debug("received a {}", this);
		if(arriveAtEndPoint()) {
			MessageContext.INSTANCE.deregisterAckTimeout(this);
			EventManager.getInstance().notifyListeners(this);
		}else{
			HopByHopAckMessage message = new HopByHopAckMessage();
			message.hopByHop(this.hopByHop);
			message.endToEnd(this.endToEnd);
			message.flag(new FlagData(FlagImpl.ANSWER));
			context().writeAndFlush(message).addListener(new GenericFutureListener<Future<? super Void>>() {

				@Override
				public void operationComplete(Future<? super Void> future)
						throws Exception {
					if (future.isSuccess()) {
						System.out.println("send hop by hop ack success");
					} else {
						System.out.println(future.cause());
						System.out.println("send hop by hop ack failed");
					}
				}
			});
			IdentityContext.INSTANCE.forward(this);
		}
	}
}
