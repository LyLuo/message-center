package cc.ly.mc.client.event;

import cc.ly.mc.client.gui.App;
import cc.ly.mc.client.gui.MainPanel;
import cc.ly.mc.common.netty.Constant;
import cc.ly.mc.core.event.EventObserver;
import cc.ly.mc.core.message.Message;

/**
 * Created by ly on 9/16/15.
 */
public class TextMessageObserver implements EventObserver {
    private final App app;
    private final MainPanel mainPanel;

    public TextMessageObserver(App app, MainPanel mainPanel) {
        this.app = app;
        this.mainPanel = mainPanel;
    }

    public App app(){
        return app;
    }

    @Override
    public void update(Object source) {
        Message message = (Message)source;
        mainPanel.addText((String) message.attribute(Constant.ATTRIBUTE_TEXT_CODE).data());
    }
}
