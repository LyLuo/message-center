package cc.ly.mc.demo.client.listener;

import javax.swing.DefaultListModel;

import cc.ly.mc.core.attribute.Attributes;
import cc.ly.mc.core.message.TextMessage;
import cc.ly.mc.core.data.impl.Unsigned16;
import cc.ly.mc.core.event.EventListener;
import cc.ly.mc.core.event.EventSource;

public class TextMessageListener implements EventListener {

    private final DefaultListModel<String> dataModel;

    public TextMessageListener(DefaultListModel<String> dataModel) {
        this.dataModel = dataModel;
    }

    @Override
    public void update(EventSource event, Object object) {
        TextMessage message = (TextMessage) object;
        StringBuilder builder = new StringBuilder();
        builder.append(message.attribute(Attributes.SENDER_ID.getCode()).data().get());
        builder.append(":");
        builder.append(((String) message.attribute(Attributes.CHAT_CONTENT.getCode()).data().get()).split("\\:")[1]);
        dataModel.addElement(builder.toString());
    }

}
