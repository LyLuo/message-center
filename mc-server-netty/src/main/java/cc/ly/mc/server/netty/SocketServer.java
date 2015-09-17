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
package cc.ly.mc.server.netty;

import cc.ly.mc.common.netty.Constant;
import cc.ly.mc.core.event.EventBus;
import cc.ly.mc.server.ServerConstant;
import cc.ly.mc.server.event.*;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * Echoes back any received data from a client.
 */
public class SocketServer extends Thread {

    private final int port;

    private EventLoopGroup bossGroup;

    private EventLoopGroup workerGroup;

    public SocketServer(int port) {
        this.port = port;
    }

    public void run() {
        // Configure the server.
        bossGroup = new NioEventLoopGroup(1);
        workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 100)
                    .option(ChannelOption.SO_REUSEADDR, true)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .childHandler(new SocketServerInitializer());
            // Start the server.
            // Wait until the server socket is closed.
            try {
                b.bind(port).sync().channel().closeFuture().sync();
            } catch (InterruptedException e) {
            }
        } finally {
            // Shut down all event loops to terminate all threads.
            close();
        }
    }

    public void close() {
        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
    }

    public static void main(String[] args) {
        EventBus.getInstance().register(ServerConstant.CONNECTED_EVENT, new ConnectedEventObserver());
        EventBus.getInstance().register(ServerConstant.DISCONNECTED_EVENT, new DisconnectedEventObserver());
        EventBus.getInstance().register(Constant.HEARTBEAT_EVENT, new RegisterMessageObserver());
        EventBus.getInstance().register(Constant.REGISTER_EVENT, new RegisterMessageObserver());
        EventBus.getInstance().register(Constant.DEREGISTER_EVENT, new DeregisterMessageObserver());
        EventBus.getInstance().register(Constant.TEXT_EVENT, new TextMessageObserver());
        new SocketServer(9090).start();
    }

}
