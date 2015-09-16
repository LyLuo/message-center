package cc.ly.mc.server.context;

import cc.ly.mc.core.message.Message;

import java.util.List;

/**
 * Created by ly on 9/14/15.
 */
public interface Backlog {

    void product(String id, Message message);

    List<Message> consume(String id);
}
