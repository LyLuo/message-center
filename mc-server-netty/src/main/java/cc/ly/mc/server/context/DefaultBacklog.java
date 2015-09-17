package cc.ly.mc.server.context;

import cc.ly.mc.core.message.Message;
import cc.ly.mc.server.netty.DefaultWriteAndFlushListener;
import io.netty.channel.ChannelHandlerContext;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by ly on 9/14/15.
 */
public class DefaultBacklog implements Backlog{
    
    private Map<String, LinkedBlockingQueue<Message>> allMessages = new ConcurrentHashMap<>();

    private static class DefaultBacklogHolder{
        private static final DefaultBacklog INSTANCE = new DefaultBacklog();
    }

    private DefaultBacklog(){}

    public static DefaultBacklog getInstance(){
        return DefaultBacklogHolder.INSTANCE;
    }

    @Override
    public void product(String id, Message message) {
        LinkedBlockingQueue<Message> queue = new LinkedBlockingQueue<>();
        queue.add(message);
        LinkedBlockingQueue<Message> old = allMessages.putIfAbsent(id, queue);
        if(old != null){
            old.add(message);
        }
        //当用户注册时候同时有其他用户给其发送消息，即product与consume同时发生的时候，
        //可能会出现drain之后产生新的message，部分消息丢失消息
        //所以每次product后进行检查
        ChannelHandlerContext context = Context.getInstance().get(id);
        if(context != null){
            List<Message> messages = consume(id);
            messages.forEach(m -> context.writeAndFlush(m).addListener(new DefaultWriteAndFlushListener(id, m)));
        }
    }

    @Override
    public List<Message> consume(String id) {
        LinkedList<Message> messages = new LinkedList<>();
        if(allMessages.containsKey(id)){
            allMessages.get(id).drainTo(messages);
        }
        return messages;
    }
}
