package cc.ly.mc.demo.client.listener;

import cc.ly.mc.core.event.EventListener;
import cc.ly.mc.core.event.EventSource;

public class EndToEndAckMessageListener implements EventListener {

	public EndToEndAckMessageListener(){
	}
	
	@Override
	public void update(EventSource event, Object object) {
		System.out.println("deal with EndToEndAckMessage");
	}
	
}
