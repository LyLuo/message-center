package cc.ly.mc.demo.client.listener;

import cc.ly.mc.core.event.EventListener;
import cc.ly.mc.core.event.EventSource;

public class HopByHopAckMessageListener implements EventListener {

	public HopByHopAckMessageListener(){
	}
	
	@Override
	public void update(EventSource event, Object object) {
		System.out.println("deal with HopByHopAckMessage");
	}
	
}
