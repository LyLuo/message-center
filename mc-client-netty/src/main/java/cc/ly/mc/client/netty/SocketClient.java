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
package cc.ly.mc.client.netty;

import cc.ly.mc.core.message.Message;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.oio.OioEventLoopGroup;
import io.netty.channel.socket.oio.OioSocketChannel;

/**
 * Echoes back any received data from a client.
 */
public class SocketClient extends Thread {

    private final String host;
    private final int port;

    private OioEventLoopGroup worker;

    private Channel channel;

    public SocketClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void run() {
        // Configure the server.
        worker = new OioEventLoopGroup();
        Bootstrap b = new Bootstrap();
        b.group(worker)
                .channel(OioSocketChannel.class)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.SO_REUSEADDR, true)
                .handler(new SocketClientInitializer());
        // Start the client.
        channel = b.connect(host, port).channel();
    }

    public void close() {
        channel.close();
        worker.shutdownGracefully();
    }

    public boolean ready(){
        if(channel != null){
            return channel.isOpen();
        }
        return false;
    }

    public void write(Message message){
       channel.writeAndFlush(message);
    }
}
