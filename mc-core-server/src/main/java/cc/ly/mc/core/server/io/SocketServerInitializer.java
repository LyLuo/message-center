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

import cc.ly.mc.core.io.ConnectedListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

import java.util.List;

import cc.ly.mc.core.io.DisconnectedListener;

/**
 */
public class SocketServerInitializer extends ChannelInitializer<SocketChannel> {
	
	private List<ConnectedListener> connectedListeners;
	
	private List<DisconnectedListener> disconnectedListeners;
	
	public SocketServerInitializer(List<ConnectedListener> connectedListeners, List<DisconnectedListener> disconnectedListeners){
		this.connectedListeners = connectedListeners;
		this.disconnectedListeners = disconnectedListeners;
	}
	
	@Override
	public void initChannel(SocketChannel ch) throws Exception {
		ChannelPipeline pipeline = ch.pipeline();
		pipeline.addLast("decoder", new SocketServerDecoder());
		pipeline.addLast("encoder", new SocketServerEncoder());
		pipeline.addLast("handler", new SocketServerHandler(connectedListeners, disconnectedListeners));
	}
}
