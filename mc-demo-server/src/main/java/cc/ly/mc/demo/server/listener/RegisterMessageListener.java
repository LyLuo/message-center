package cc.ly.mc.demo.server.listener;

import cc.ly.mc.core.attribute.Attributes;
import cc.ly.mc.core.context.MessageContext;
import cc.ly.mc.core.data.impl.FlagImpl;
import cc.ly.mc.core.event.EventSource;
import cc.ly.mc.core.context.Identity;
import cc.ly.mc.core.context.IdentityContext;
import cc.ly.mc.core.event.EventListener;
import cc.ly.mc.core.message.ResponseMessage;
import cc.ly.mc.core.message.RegisterMessage;
import cc.ly.mc.core.data.impl.FlagData;
import cc.ly.mc.core.data.impl.Integer64;

public class RegisterMessageListener implements EventListener {
	@Override
	public void update(EventSource event, Object object) {
		RegisterMessage message = (RegisterMessage) object;
		Integer64 id = (Integer64) message.attribute(Attributes.SENDER_ID.getCode()).data();
		Identity identity = new Identity(id.get(), message.context());
		if (IdentityContext.INSTANCE.add(identity)) {
			MessageContext.INSTANCE.register(message.context().channel().id());
		}
		ResponseMessage response = new ResponseMessage();
		response.endToEnd(message.endToEnd());
		response.hopByHop(message.hopByHop());
		response.flag(new FlagData(FlagImpl.ANSWER));
		IdentityContext.INSTANCE.get(id.get()).write(response);
	}
}
