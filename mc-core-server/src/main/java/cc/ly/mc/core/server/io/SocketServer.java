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
package cc.ly.mc.core.server.io;

import cc.ly.mc.core.attribute.Attribute;
import cc.ly.mc.core.data.impl.Unsigned16;
import cc.ly.mc.core.io.ConnectedListener;
import cc.ly.mc.core.message.MessageFactory;
import cc.ly.mc.core.data.Data;
import cc.ly.mc.core.io.DisconnectedListener;
import cc.ly.mc.core.message.Message;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.util.LinkedList;
import java.util.List;

/**
 * Echoes back any received data from a client.
 */
public class SocketServer extends Thread {

	private final int port;

	private EventLoopGroup bossGroup;

	private EventLoopGroup workerGroup;

	private static final MessageFactory factory = MessageFactory.getInstance();

	private List<ConnectedListener> connectedListeners;

	private List<DisconnectedListener> disconnectedListeners;

	public SocketServer(int port) {
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
			throw new RuntimeException("init error",e);
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

	public static void registerAttribute(
			Class<? extends Attribute<? extends Data<?>>> clazz) {
		factory.registerAttribute(clazz);
	}

	public void addConnectedListener(ConnectedListener listener) {
		this.connectedListeners.add(listener);
	}

	public void addDisconnectedListener(DisconnectedListener listener) {
		this.disconnectedListeners.add(listener);
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
					.childHandler(
							new SocketServerInitializer(connectedListeners,
									disconnectedListeners));
			// Start the server.
			// Wait until the server socket is closed.
			try {
				b.bind(port).sync().channel().closeFuture().sync();
			} catch (InterruptedException e) {
			}
		} finally {
			// Shut down all event loops to terminate all threads.
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
	}

	public void close() {
		bossGroup.shutdownGracefully();
		workerGroup.shutdownGracefully();
	}

}
