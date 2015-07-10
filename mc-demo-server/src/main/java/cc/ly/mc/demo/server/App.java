package cc.ly.mc.demo.server;

import cc.ly.mc.core.context.IdentityContext;
import cc.ly.mc.core.event.EventManager;
import cc.ly.mc.core.io.DisconnectedListener;
import cc.ly.mc.core.message.DeregisterMessage;
import cc.ly.mc.core.message.RegisterMessage;
import cc.ly.mc.core.message.TextMessage;
import cc.ly.mc.demo.server.listener.DeregisterMessageListener;
import cc.ly.mc.demo.server.listener.RegisterMessageListener;
import cc.ly.mc.demo.server.listener.TextMessageListener;
import cc.ly.mc.core.server.io.SocketServer;
import io.netty.channel.ChannelHandlerContext;

import java.io.IOException;

public class App {
    public static void main(String[] args) throws IOException {
        if (args.length == 0) {
            args = new String[]{"9000"};
        }
        int port = Integer.parseInt(args[0]);
        EventManager.getInstance().registerListener(RegisterMessage.class, new RegisterMessageListener());
        EventManager.getInstance().registerListener(DeregisterMessage.class, new DeregisterMessageListener());
        EventManager.getInstance().registerListener(TextMessage.class, new TextMessageListener());
        SocketServer server = new SocketServer(port);
        server.addDisconnectedListener(new DisconnectedListener() {
            @Override
            public void onDisconnect(ChannelHandlerContext ctx) {
                IdentityContext.INSTANCE.remove(ctx.channel().id());
            }
        });
        server.start();
    }
}
