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
		if(message.flag().get().isError()){
			System.out.println("failed to register");
		}else{
			System.out.println("register successfully");
		}
		MessageContext.INSTANCE.deregisterAckTimeout(message);
	}
	
}
