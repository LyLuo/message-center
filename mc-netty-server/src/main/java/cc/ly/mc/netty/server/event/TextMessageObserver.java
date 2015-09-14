package cc.ly.mc.netty.server.event;

import cc.ly.mc.core.event.EventObserver;
import cc.ly.mc.core.message.Message;
import cc.ly.mc.netty.server.context.Context;

/**
 * Created by ly on 9/13/15.
 */
public class TextMessageObserver implements EventObserver {
    @Override
    public void update(Object source) {
        Message message = (Message) source;
        String to = null;
        Context.getInstance().get(to).write(message);
    }
}
