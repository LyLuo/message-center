package cc.ly.mc.demo.server.listener;

import cc.ly.mc.core.context.IdentityContext;
import cc.ly.mc.core.event.EventListener;
import cc.ly.mc.core.event.EventSource;
import cc.ly.mc.core.message.DeregisterMessage;

public class DeregisterMessageListener implements EventListener {
	@Override
	public void update(EventSource event, Object object) {
		DeregisterMessage message = (DeregisterMessage) object;
		IdentityContext.INSTANCE.remove(message.context().channel().id());
	}
}
