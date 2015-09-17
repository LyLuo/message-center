package cc.ly.mc.client.event;

import cc.ly.mc.client.App;
import cc.ly.mc.core.event.EventObserver;
import cc.ly.mc.core.message.Message;

/**
 * Created by ly on 9/16/15.
 */
public class HeartbeatMessageObserver implements EventObserver {

    private App app;

    public HeartbeatMessageObserver(App app) {
        this.app = app;
    }

    public App app(){
        return app;
    }

    @Override
    public void update(Object source) {
        Message message = (Message)source;
        message.flag(message.flag().requestToAnswer(false));
        app.socketClient().write(message);
    }
}
