package cc.ly.mc.client.event;

import cc.ly.mc.client.App;
import cc.ly.mc.client.MainPanel;
import cc.ly.mc.common.netty.Constant;
import cc.ly.mc.core.event.EventBus;
import cc.ly.mc.core.event.EventObserver;
import cc.ly.mc.core.message.Message;

/**
 * Created by ly on 9/16/15.
 */
public class RegisterMessageObserver implements EventObserver {
    private final App app;

    public RegisterMessageObserver(App app) {
        this.app = app;
    }

    public App app(){
        return app;
    }

    @Override
    public void update(Object source) {
        Message message = (Message)source;
        if(!message.flag().isError()){
            app.getContentPane().removeAll();
            app.id((String) message.attribute(Constant.ATTRIBUTE_SENDER_ID_CODE).data());
            MainPanel mainPanel = new MainPanel(app, (String) message.attribute(Constant.ATTRIBUTE_SENDER_NAME_CODE).data());
            EventBus.getInstance().register(Constant.TEXT_EVENT, new TextMessageObserver(app, mainPanel));
            app.getContentPane().add(mainPanel);
            app.getContentPane().validate();
            app.getContentPane().repaint();
        }
    }
}
