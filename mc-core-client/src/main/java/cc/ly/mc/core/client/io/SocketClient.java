/*
 * Copyright 2012 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package cc.ly.mc.core.client.io;

import cc.ly.mc.core.data.Data;
import cc.ly.mc.core.data.impl.Unsigned16;
import cc.ly.mc.core.io.ConnectedListener;
import cc.ly.mc.core.message.MessageFactory;
import cc.ly.mc.core.message.RelayMessage;
import cc.ly.mc.core.message.Message;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.oio.OioEventLoopGroup;
import io.netty.channel.socket.oio.OioSocketChannel;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

import java.util.LinkedList;
import java.util.List;

import cc.ly.mc.core.attribute.Attribute;
import cc.ly.mc.core.io.DisconnectedListener;

/**
 * Echoes back any received data from a client.
 */
public class SocketClient extends Thread {

    private final String host;
    private final int port;

    private List<ConnectedListener> connectedListeners;

    private List<DisconnectedListener> disconnectedListeners;

    private static final MessageFactory factory = MessageFactory.getInstance();

    private OioEventLoopGroup worker;

    private Channel channel;

    public SocketClient(String host, int port) {
        this.host = host;
        this.port = port;
        this.connectedListeners = new LinkedList<>();
        this.disconnectedListeners = new LinkedList<>();
        init();
    }

    private void init() {
        try {
            factory.initMessages();
            factory.initAttributes();
        } catch (Exception e) {
            throw new RuntimeException("init error");
        }
    }

    public static void register(Class<? extends Message> clazz) {
        factory.register(clazz);
    }

    public static void register(Unsigned16 code, Class<? extends Message> clazz) {
        factory.register(code, clazz);
    }

    public static void registerAttribute(Unsigned16 code,
                                         Class<? extends Attribute<? extends Data<?>>> clazz) {
        factory.registerAttribute(code, clazz);
    }

    public void run() {
        // Configure the server.
        worker = new OioEventLoopGroup();
        Bootstrap b = new Bootstrap();
        b.group(worker)
                .channel(OioSocketChannel.class)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.SO_REUSEADDR, true)
                .handler(
                        new SocketClientInitializer(connectedListeners,
                                disconnectedListeners));
        // Start the client.
        channel = b.connect(host, port).channel();
    }

    public void addConnectedListener(ConnectedListener listener) {
        this.connectedListeners.add(listener);
    }

    public void addDisconnectedListener(DisconnectedListener listener) {
        this.disconnectedListeners.add(listener);
    }

    public void close() {
        channel.close();
        worker.shutdownGracefully();
    }

}
