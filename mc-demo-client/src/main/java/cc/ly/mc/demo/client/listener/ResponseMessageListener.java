package cc.ly.mc.demo.client.listener;

import cc.ly.mc.core.context.MessageContext;
import cc.ly.mc.core.event.EventSource;
import cc.ly.mc.core.event.EventListener;
import cc.ly.mc.core.message.ResponseMessage;

public class ResponseMessageListener implements EventListener {

	public ResponseMessageListener(){
	}
	
	@Override
	public void update(EventSource event, Object object) {
		ResponseMessage message = (ResponseMessage) object;
		MessageContext.INSTANCE.deregisterAckTimeout(message);
	}
	
}
