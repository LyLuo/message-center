package cc.ly.mc.demo.server.listener;

import cc.ly.mc.core.attribute.Attributes;
import cc.ly.mc.core.context.IdentityContext;
import cc.ly.mc.core.data.impl.Unsigned16;
import cc.ly.mc.core.event.EventListener;
import cc.ly.mc.core.event.EventSource;
import cc.ly.mc.core.message.TextMessage;

/**
 * Created by Administrator on 2015/6/18.
 */
public class TextMessageListener implements EventListener {
    @Override
    public void update(EventSource event, Object object) {
        TextMessage message = (TextMessage) object;
        System.out.println("received a text " + message.attribute(Attributes.CHAT_CONTENT.getCode()).data().get());
//        IdentityContext.INSTANCE.forward(message);
    }
}
