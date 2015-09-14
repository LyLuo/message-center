package cc.ly.mc.netty.server.context;

import cc.ly.mc.netty.server.Constant;
import io.netty.channel.ChannelHandlerContext;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by ly on 9/13/15.
 */
public class Context {

    private final Map<String, ChannelHandlerContext> idToChannel = new ConcurrentHashMap<>();


    private static class UnitsHolder {
        private static final Context INSTANCE = new Context();
    }

    private Context(){}

    public static Context getInstance(){
        return UnitsHolder.INSTANCE;
    }

    public void register(ChannelHandlerContext channelHandlerContext){
        String id = channelHandlerContext.attr(Constant.ID).get();
        ChannelHandlerContext old = idToChannel.putIfAbsent(id, channelHandlerContext);
        if(old != null){
            //TODO 新注册将之前注册的提下线

        }
    }

    public void deregister(ChannelHandlerContext channelHandlerContext){
        idToChannel.remove(channelHandlerContext.attr(Constant.ID).get());
    }

    public ChannelHandlerContext get(String id){
        return idToChannel.get(id);
    }
}
